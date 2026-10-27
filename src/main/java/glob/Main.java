package glob;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: glob [PATTERN]");
            return;
        }
        GlobPattern pattern = new GlobPattern(args[0]);
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(pattern.match(line) ? "1" : "0");
        }
    }
}