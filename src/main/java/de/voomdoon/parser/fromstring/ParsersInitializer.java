package de.voomdoon.parser.fromstring;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ParsersInitializer {

	/**
	 * DOCME add JavaDoc for ParsersInitializer
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class InvalidParserException extends Exception {

		/**
		 * @since 0.1.0
		 */
		private static final long serialVersionUID = -6402418526699871102L;

		/**
		 * DOCME add JavaDoc for constructor InvalidParserException
		 * 
		 * @param message
		 * @param cause
		 * @since 0.1.0
		 */
		public InvalidParserException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * DOCME add JavaDoc for method getParsers
	 * 
	 * @return
	 * @throws InvalidParserException
	 * @since 0.1.0
	 */
	public Map<Class<?>, FromStringParser<?>> getParsers() throws InvalidParserException {
		return getParsers(getClass().getClassLoader());
	}

	/**
	 * DOCME add JavaDoc for method getParsers
	 * 
	 * @param classLoader
	 * @return
	 * @throws InvalidParserException
	 * @since 0.1.0
	 */
	Map<Class<?>, FromStringParser<?>> getParsers(ClassLoader classLoader) throws InvalidParserException {
		@SuppressWarnings("rawtypes")
		ServiceLoader<FromStringParser> loaders = ServiceLoader.load(FromStringParser.class, classLoader);

		Map<Class<?>, FromStringParser<?>> result = new HashMap<>();

		for (FromStringParser<?> parser : loaders) {
			result.put(getResultClass(parser), parser);
		}

		return Map.copyOf(result);
	}

	/**
	 * DOCME add JavaDoc for method getResultClass
	 * 
	 * @param parser
	 * @return
	 * @throws InvalidParserException
	 * @since 0.1.0
	 */
	private Class<?> getResultClass(FromStringParser<?> parser) throws InvalidParserException {
		Class<?> resultClass;

		try {
			resultClass = parser.getResultClass();
		} catch (UnsupportedOperationException e) {
			throw new InvalidParserException("Unable to initialize invalid parser " + parser + ": " + e.getMessage(),
					e);
		}
		return resultClass;
	}
}
