package glob;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testNoArguments() {
        Main.main(new String[]{});
        assertEquals("Usage: $(COMMAND) [PATTERN]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testNullArguments() {
        Main.main(null);
        assertEquals("Usage: $(COMMAND) [PATTERN]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testTooManyArguments() {
        Main.main(new String[]{"?at", "extra"});
        assertEquals("Usage: $(COMMAND) [PATTERN]" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testMatchingInputLines() {
        String inputData = "cat\nbat\npattern\nboat\n";
        System.setIn(new ByteArrayInputStream(inputData.getBytes()));

        Main.main(new String[]{"?at"});

        String expectedOutput = "1" + System.lineSeparator() +
                                "1" + System.lineSeparator() +
                                "0" + System.lineSeparator() +
                                "0" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testIOExceptionHandling() {
        InputStream faultyInputStream = new InputStream() {
            @Override
            public int read() throws java.io.IOException {
                throw new java.io.IOException("Error de lectura simulado");
            }
        };

        System.setIn(faultyInputStream);
        Main.main(new String[]{"?at"});

        assertEquals("-1" + System.lineSeparator(), outContent.toString());
    }
}