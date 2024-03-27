package de.voomdoon.parser.fromstring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.voomdoon.parser.fromstring.ParsersInitializer.InvalidParserException;
import de.voomdoon.parser.fromstring.test.TestObject;
import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class ParsersInitializerTest extends TestBase {

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private class TestClassLoader extends URLClassLoader {

		/**
		 * @param urls
		 * @since 0.1.0
		 */
		public TestClassLoader(URL[] urls) {
			super(urls);
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public Enumeration<URL> getResources(String name) throws IOException {
			if ("META-INF/services/de.voomdoon.parser.fromstring.FromStringParser".equals(name)) {
				String location = System.getProperty("user.dir")
						+ "/src/test/resources/META-INF_services/valid/de.voomdoon.parser.fromstring.FromStringParser";

				return Collections.enumeration(List.of(new URL("file:/" + location)));
			}

			return super.getResources(name);
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private class TestClassLoaderForInvalidParser extends URLClassLoader {

		/**
		 * @param urls
		 * @since 0.1.0
		 */
		public TestClassLoaderForInvalidParser(URL[] urls) {
			super(urls);
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public Enumeration<URL> getResources(String name) throws IOException {
			if ("META-INF/services/de.voomdoon.parser.fromstring.FromStringParser".equals(name)) {
				String location = System.getProperty("user.dir")
						+ "/src/test/resources/META-INF_services/invalid/de.voomdoon.parser.fromstring.FromStringParser";

				return Collections.enumeration(List.of(new URL("file:/" + location)));
			}

			return super.getResources(name);
		}
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test() throws Exception {
		logTestStart();

		Map<Class<?>, FromStringParser<?>> actuals = new ParsersInitializer()
				.getParsers(new TestClassLoader(new URL[0]));

		assertThat(actuals).containsKey(TestObject.class);
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_error_invalidParserNotImplementingGetResultClass() throws Exception {
		logTestStart();

		assertThatThrownBy(() -> new ParsersInitializer().getParsers(new TestClassLoaderForInvalidParser(new URL[0])))
				.isInstanceOf(InvalidParserException.class).hasMessageContaining("Unable to initialize invalid parser")
				.hasMessageContaining("getResultClass");
	}
}
