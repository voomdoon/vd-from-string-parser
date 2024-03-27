package de.voomdoon.parser.fromstring;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.voomdoon.parser.fromstring.ParsersInitializer.InvalidParserException;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class FromStringParsers {

	/**
	 * @since 0.1.0
	 */
	public static final FromStringParsers DEFAULT = new FromStringParsers();

	/**
	 * @since 0.1.0
	 */
	private Map<Class<?>, FromStringParser<?>> parsers = new HashMap<>();

	/**
	 * @since 0.1.0
	 */
	private FromStringParsers() {
		addNumberParsers();

		parsers.put(boolean.class, Boolean::parseBoolean);
		parsers.put(Boolean.class, Boolean::parseBoolean);

		addMathParsers();

		parsers.put(Pattern.class, Pattern::compile);

		addNetParsers();

		addClassParser();

		try {
			new ParsersInitializer().getParsers().forEach((clazz, parser) -> parsers.put(clazz, parser));
		} catch (InvalidParserException e) {
			// TODO implement error handling
			throw new RuntimeException("Error at 'FromStringParsers': " + e.getMessage(), e);
		}
	}

	/**
	 * DOCME add JavaDoc for method parse
	 * 
	 * @param <T>
	 * @param clazz
	 * @param string
	 * @return
	 * @throws ParseException
	 * @since 0.1.0
	 */
	public <T> T parse(Class<T> clazz, String string) throws ParseException {
		if (clazz.equals(String.class)) {
			return (T) string.toString();
		} else if (clazz.isEnum()) {
			return parseEnum(clazz, string);
		}

		FromStringParser<?> parser = parsers.get(clazz);

		if (parser == null) {
			throw new UnsupportedOperationException("No parser for " + clazz + " registered!");
		}

		@SuppressWarnings("unchecked")
		T result = (T) parser.parse(string);

		return result;
	}

	/**
	 * @since 0.1.0
	 */
	private void addClassParser() {
		parsers.put(Class.class, string -> {
			try {
				return Class.forName(string);
			} catch (ClassNotFoundException e) {
				throw new ParseException(e.getMessage(), -1);
			}
		});
	}

	/**
	 * @since 0.1.0
	 */
	private void addMathParsers() {
		parsers.put(BigInteger.class, BigInteger::new);
		parsers.put(BigDecimal.class, BigDecimal::new);
	}

	/**
	 * @since 0.1.0
	 */
	private void addNetParsers() {
		parsers.put(URL.class, string -> {
			try {
				return new URL(string);
			} catch (MalformedURLException e) {
				throw new ParseException("Failed to parse URL from '" + string + "': " + e.getMessage(), -1);
			}
		});

		parsers.put(URI.class, URI::create);
	}

	/**
	 * @since 0.1.0
	 */
	private void addNumberParsers() {
		parsers.put(int.class, Integer::parseInt);
		parsers.put(Integer.class, Integer::parseInt);

		parsers.put(long.class, Long::parseLong);
		parsers.put(Long.class, Long::parseLong);

		parsers.put(double.class, Double::parseDouble);
		parsers.put(Double.class, Double::parseDouble);
	}

	/**
	 * DOCME add JavaDoc for method parseEnum
	 * 
	 * @param <T>
	 * @param clazz
	 * @param string
	 * @return
	 * @throws ParseException
	 * @since 0.1.0
	 */
	private <T> T parseEnum(Class<T> clazz, String string) throws ParseException {
		for (T element : clazz.getEnumConstants()) {
			if (element.toString().equals(string)) {
				return element;
			}
		}

		// TODO respect name

		throw new ParseException("No enum constant " + clazz + "." + string, -1);
	}
}
