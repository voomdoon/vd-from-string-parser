package de.voomdoon.parser.fromstring.test;

import java.text.ParseException;

import de.voomdoon.parser.fromstring.FromStringParser;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class TestObjectParser implements FromStringParser<TestObject> {

	/**
	 * @since 0.1.0
	 */
	@Override
	public Class<TestObject> getResultClass() {
		return TestObject.class;
	}

	/**
	 * @since 0.1.0
	 */
	@Override
	public TestObject parse(String string) throws ParseException {
		return new TestObject(string.toUpperCase());
	}
}
