package de.voomdoon.parser.fromstring;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogLevel;
import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class FromStringParserTest extends TestBase {

	/**
	 * @since 0.1.0
	 */
	private FromStringParsers parser = new FromStringParsers();

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_default_enum() throws Exception {
		logTestStart();

		LogLevel actual = parser.parse(LogLevel.class, "INFO");

		assertThat(actual).isEqualTo(LogLevel.INFO);
	}

	/**
	 * @throws ParseException
	 * @since 0.1.0
	 */
	@Test
	void test_default_Number_Integer() throws ParseException {
		logTestStart();

		Integer actual = parser.parse(Integer.class, "123");

		assertThat(actual).isEqualTo(Integer.valueOf(123));
	}

	/**
	 * @throws ParseException
	 * @since 0.1.0
	 */
	@Test
	void test_default_Number_long() throws ParseException {
		logTestStart();

		long actual = parser.parse(long.class, "123");

		assertThat(actual).isEqualTo(123L);
	}

	/**
	 * @throws ParseException
	 * @since 0.1.0
	 */
	@Test
	void test_default_Number_Long() throws ParseException {
		logTestStart();

		Long actual = parser.parse(Long.class, "123");

		assertThat(actual).isEqualTo(Long.valueOf(123));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_default_Pattern() throws Exception {
		logTestStart();

		Pattern actual = parser.parse(Pattern.class, "abc.*");

		assertThat(actual).extracting(Pattern::pattern).isEqualTo("abc.*");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_Math_BigDecimal() throws Exception {
		logTestStart();

		BigDecimal actual = parser.parse(BigDecimal.class, "123.456");

		assertThat(actual).isEqualTo(BigDecimal.valueOf(123456, 3));
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_Math_BigInteger() throws Exception {
		logTestStart();

		BigInteger actual = parser.parse(BigInteger.class, "123");

		assertThat(actual).isEqualTo(BigInteger.valueOf(123));
	}
}
