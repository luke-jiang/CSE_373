package search.misc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class UnbufferedReader {
    private static final int EOF = -1;
    private PushbackInputStream stream;

    public UnbufferedReader(PushbackInputStream stream) {
        this.stream = stream;
    }

    // Courtesy of https://stackoverflow.com/a/25341618/646543
    public String readLine() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int c = EOF;
        boolean isEnd = false;
        while (!isEnd) {
            c = this.stream.read();

            isEnd = this.isEnd(c);
            if (c == '\r') {
                int next = this.stream.read();
                if (next != '\n') {
                    this.stream.unread(next);
                }
            }

            if (!isEnd) {
                output.write(c);
            }
        }

        if (c == EOF && output.size() == 0) {
            return null;
        }

        return output.toString("UTF-8");
    }

    private boolean isEnd(int c) {
        return c == '\r' || c == '\n' || c == EOF;
    }
}
