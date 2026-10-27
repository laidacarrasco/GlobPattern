package glob;

public class GlobPattern {
    private final String pattern;

    public GlobPattern(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        this.pattern = pattern;
    }

    public boolean match(String text) {
        if (text == null || text.length() != pattern.length()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            char p = pattern.charAt(i);
            if (p != '?' && p != text.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}