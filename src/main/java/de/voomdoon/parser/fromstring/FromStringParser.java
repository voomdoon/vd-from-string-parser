package de.voomdoon.parser.fromstring;

import java.text.ParseException;

/**
 * Parses a {@link String} into a value of type {@code T}.
 *
 * @author André Schulz
 *
 * @param <T>
 *            result type
 *
 * @since 0.1.0
 */
public interface FromStringParser<T> {

	/**
	 * Returns the result type supported by this parser.
	 * 
	 * @return result class
	 * @since 0.1.0
	 */
	default Class<T> getResultClass() {
		throw new UnsupportedOperationException(
				"Method 'getResultClass' need to be implemented by " + getClass() + "!");
	}

	/**
	 * Parses the supplied string.
	 * 
	 * @param string
	 *            {@link String} to parse
	 * @return parsed value
	 * @throws ParseException
	 *             if the {@link String} cannot be parsed
	 * @since 0.1.0
	 */
	T parse(String string) throws ParseException;
}
