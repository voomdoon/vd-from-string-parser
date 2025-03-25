# vd-from-string-parser

**Modular utility to parse Java objects from `String` values.**

This library supports a variety of common types and can be extended with custom parsers using Java’s Service Provider Interface (SPI).

This utility is used at [vd-from-properties-parser](https://github.com/voomdoon/vd-from-properties-parser) to support parsing objects from `Properties`.


## Features

- Built-in support for:
  - Primitive types and their wrappers (`int`, `long`, `double`, `boolean`, etc.)
  - `BigInteger`, `BigDecimal`
  - `Pattern`, `Class`, `URL`, `URI`
  - Enums
- Extensible via SPI (Service Provider Interface via `META-INF/services`)
- Simple, consistent API
- Validates and initializes custom parsers at runtime

---

## Usage

```java
FromStringParsers parsers = FromStringParsers.DEFAULT;

int number = parsers.parse("42", int.class);
URL url = parsers.parse("https://example.com", URL.class);
Pattern pattern = parsers.parse("\\d+", Pattern.class);
MyEnum value = parsers.parse("VALUE", MyEnum.class);
```

## Adding Custom Parsers

To support custom types, implement the `FromStringParser<T>` interface:

```java
public class MyTypeParser implements FromStringParser<MyType> {

    @Override
    public Class<MyType> getResultClass() {
        return MyType.class;
    }

    @Override
    public MyType parse(String string) throws ParseException {
        // convert string to MyType
    }
}
```

Register the parser using Java’s SPI mechanism:

1. Create a file named  
   `META-INF/services/de.voomdoon.parser.fromstring.FromStringParser`

2. List your implementation class inside that file:

   ```
   com.example.MyTypeParser
   ```

Custom parsers are discovered and loaded automatically.

---

## Error Handling

- If no parser is registered for a given type, `parse` throws `UnsupportedOperationException`.
- If a parser fails to declare its result class, an `InvalidParserException` is thrown during initialization.
