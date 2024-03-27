package de.voomdoon.parser.fromstring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogLevel;
import de.voomdoon.parser.fromstring.test.TestObject;
import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class FromStringParsersTest extends TestBase {

	/**
	 * @since 0.1.0
	 */
	private FromStringParsers parsers = FromStringParsers.DEFAULT;

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_default_enum() throws Exception {
		logTestStart();

		LogLevel actual = parsers.parse(LogLevel.class, "INFO");

		assertThat(actual).isEqualTo(LogLevel.INFO);
	}

	/**
	 * @throws ParseException
	 * @since 0.1.0
	 */
	@Test
	void test_default_Number_Integer() throws ParseException {
		logTestStart();

		Integer actual = parsers.parse(Integer.class, "123");

		assertThat(actual).isEqualTo(Integer.valueOf(123));
	}

	/**
	 * @throws ParseException
	 * @since 0.1.0
	 */
	@Test
	void test_default_Number_long() throws ParseException {
		logTestStart();

		long actual = parsers.parse(long.class, "123");

		assertThat(actual).isEqualTo(123L);
	}

	/**
	 * @throws ParseException
	 * @since 0.1.0
	 */
	@Test
	void test_default_Number_Long() throws ParseException {
		logTestStart();

		Long actual = parsers.parse(Long.class, "123");

		assertThat(actual).isEqualTo(Long.valueOf(123));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_default_Pattern() throws Exception {
		logTestStart();

		Pattern actual = parsers.parse(Pattern.class, "abc.*");

		assertThat(actual).extracting(Pattern::pattern).isEqualTo("abc.*");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_initialization() throws Exception {
		logTestStart();

		TestObject actual = parsers.parse(TestObject.class, "abc");

		assertThat(actual).extracting(TestObject::getString).isEqualTo("ABC");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_Math_BigDecimal() throws Exception {
		logTestStart();

		BigDecimal actual = parsers.parse(BigDecimal.class, "123.456");

		assertThat(actual).isEqualTo(BigDecimal.valueOf(123456, 3));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_Math_BigInteger() throws Exception {
		logTestStart();

		BigInteger actual = parsers.parse(BigInteger.class, "123");

		assertThat(actual).isEqualTo(BigInteger.valueOf(123));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_URI() throws Exception {
		logTestStart();

		URI actual = parsers.parse(URI.class, "http://www.example.com");

		assertThat(actual).isEqualTo(new URI("http://www.example.com"));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_URL() throws Exception {
		logTestStart();

		URL actual = parsers.parse(URL.class, "http://www.example.com");

		assertThat(actual).isEqualTo(new URL("http://www.example.com"));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_URL_ParseException() throws Exception {
		logTestStart();

		ParseException actual = assertThrows(ParseException.class, () -> parsers.parse(URL.class, "localhost:123"));

		logger.debug("expected error: " + actual.getMessage());

		assertThat(actual).hasMessageContaining("localhost:123");
	}
}
