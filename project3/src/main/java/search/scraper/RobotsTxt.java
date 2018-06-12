package search.scraper;

import com.google.re2j.Pattern;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

public class RobotsTxt {
    private IList<String> allowed;
    private IList<String> disallowed;

    public RobotsTxt(String userAgent, String text) {
        this.allowed = new DoubleLinkedList<>();
        this.disallowed = new DoubleLinkedList<>();

        Pattern currentUserAgent = Pattern.compile(".*");

        for (String rawLine : text.split("\n")) {
            String line = this.clean(rawLine);
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = this.extractParts(line);
            String directive = parts[0];
            String data = parts[1];

            if (directive.equals("User-agent")) {
                currentUserAgent = this.handleUserAgent(data);
            } else if (this.agentIsOk(currentUserAgent, userAgent)){
                this.handleDirectives(directive, data);
            }
        }
    }

    public IList<String> getAllowed() {
        return this.allowed;
    }

    public IList<String> getDisallowed() {
        return this.disallowed;
    }

    public UriMatchRule getMatchRule() {
        return new UriMatchRule(
                this.coalesce(this.allowed),
                this.coalesce(this.disallowed));
    }

    private Pattern coalesce(IList<String> patterns) {
        if (patterns == null) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        String separator = "";
        for (String pattern : patterns) {
            out.append(separator);
            out.append(Pattern.quote(pattern));
            separator = "|";
        }
        return Pattern.compile(out.toString());
    }

    private String clean(String line) {
        String[] parts = line.split("#", 2);
        return parts[0].trim();
    }

    private String[] extractParts(String line) {
        String[] out = line.split(":", 2);
        if (out.length != 2) {
            return new String[] {"Ignore", line};
        }
        return new String[] {out[0].trim(), out[1].trim()};
    }

    private boolean agentIsOk(Pattern currentUserAgent, String userAgent) {
        return currentUserAgent.matches(userAgent);
    }

    private Pattern handleUserAgent(String data) {
        return Pattern.compile(data.replace("*", ".*"));
    }

    private void handleDirectives(String directive, String data) {
        if (data.isEmpty()) {
            return;
        } else if (directive.equals("Ignore")) {
            return;
        }

        if (directive.equals("Disallow")) {
            this.disallowed.add(data.replace("*", ".*"));
        } else if (directive.equals("Allow")) {
            this.allowed.add(data.replace("*", ".*"));
        }
    }
}
