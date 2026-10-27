package glob;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GlobPatternTest {

    @Test
    public void testGlobPatternNull() {
        assertThrows(IllegalArgumentException.class, ()-> new GlobPattern(null));
    }

    @Test
    void testExactMatch() {
        GlobPattern glob = new GlobPattern("cat");
        assertTrue(glob.match("cat"));
        assertFalse(glob.match("bat"));
    }

    @Test
    void testWildcardMatch() {
        GlobPattern glob = new GlobPattern("?at");
        assertTrue(glob.match("Cat"));
        assertTrue(glob.match("cat"));
        assertTrue(glob.match("bat"));
        assertFalse(glob.match("at"));
        assertFalse(glob.match("boat"));
    }

    @Test
    void testMultipleWildcards() {
        GlobPattern glob = new GlobPattern("??o?");
        assertTrue(glob.match("door"));
        assertTrue(glob.match("stop"));
        assertFalse(glob.match("top"));
    }

    @Test
    void testNullText() {
        GlobPattern glob = new GlobPattern("test");
        assertFalse(glob.match(null));
    }

    @Test
    void testLengthMismatch() {
        GlobPattern glob = new GlobPattern("test");
        assertFalse(glob.match("tes"));
        assertFalse(glob.match("testing"));
    }

    @Test
    void testEmptyStrings() {
        GlobPattern emptyPattern = new GlobPattern("");
        assertTrue(emptyPattern.match(""));
        assertFalse(emptyPattern.match("a"));
    }
}
