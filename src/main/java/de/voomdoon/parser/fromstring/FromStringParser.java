package de.voomdoon.parser.fromstring;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

//XXX move somewhere?

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class FromStringParser {

	/**
	 * DOCME add JavaDoc for ObjectParser
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static interface Parser<T> {

		/**
		 * DOCME add JavaDoc for method parse
		 * 
		 * @param <T>
		 * @param string
		 * @return
		 * @throws ParseException
		 * @since 0.1.0
		 */
		T parse(String string) throws ParseException;
	}

	/**
	 * @since 0.1.0
	 */
	private Map<Class<?>, Parser<?>> parsers = new HashMap<>();

	/**
	 * @since 0.1.0
	 */
	public FromStringParser() {
		addNumberParsers();

		parsers.put(boolean.class, Boolean::parseBoolean);
		parsers.put(Boolean.class, Boolean::parseBoolean);

		addMathParsers();

		parsers.put(Pattern.class, Pattern::compile);

		addClassParser();
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

		Parser<?> parser = parsers.get(clazz);

		if (parser == null) {
			throw new UnsupportedOperationException("No parser for " + clazz + " registered!");
		}

		@SuppressWarnings("unchecked")
		T result = (T) parser.parse(string);

		return result;
	}

	/**
	 * DOCME add JavaDoc for method registerParser
	 * 
	 * @param <T>
	 * @param clazz
	 * @param parser
	 * @since 0.1.0
	 */
	public <T> void registerParser(Class<T> clazz, Function<String, T> parser) {
		// TESTME registerParser
		// TODO implement registerParser
		throw new UnsupportedOperationException("Method 'registerParser' not implemented yet");
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
