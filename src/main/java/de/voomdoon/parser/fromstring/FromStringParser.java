package de.voomdoon.parser.fromstring;

import java.text.ParseException;

/**
 * DOCME add JavaDoc for ObjectParser
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public interface FromStringParser<T> {

	/**
	 * DOCME add JavaDoc for method getResultClass
	 * 
	 * @return
	 * @since 0.1.0
	 */
	default Class<T> getResultClass() {
		throw new UnsupportedOperationException(
				"Method 'getResultClass' need to be implemented by " + getClass() + "!");
	}

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