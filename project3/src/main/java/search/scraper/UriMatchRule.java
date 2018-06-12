package search.scraper;

import com.google.re2j.Pattern;

import java.net.URI;

public class UriMatchRule {
    private final Pattern allowed;
    private final Pattern disallowed;
    private final Pattern internalAllowed;
    private final Pattern internalDisallowed;

    public UriMatchRule(Pattern allowed, Pattern disallowed) {
        this.allowed = allowed;
        this.disallowed = disallowed;
        this.internalAllowed = this.sanitize(allowed);
        this.internalDisallowed = this.sanitize(disallowed);
    }

    @Override
    public String toString() {
        return "allowed: " + this.internalAllowed + " disallowed: " + this.internalDisallowed;
    }

    private Pattern sanitize(Pattern general) {
        return general == null ? null : Pattern.compile("^(" + general + ")");
    }

    public boolean matches(URI uri) {
        String filepath = uri.getPath() + ((uri.getQuery() != null) ? "?" + uri.getQuery() : "");
        if (allowed != null && internalAllowed.matcher(filepath).find()) {
            return true;
        }
        if (disallowed != null && internalDisallowed.matcher(filepath).find()) {
            return false;
        }
        return true;
    }

    public UriMatchRule combine(UriMatchRule other) {
        return new UriMatchRule(
                this.combine(this.allowed, other.allowed),
                this.combine(this.disallowed, other.disallowed));
    }

    private Pattern combine(Pattern a, Pattern b) {
        if (a == null && b == null) {
            return null;
        } else if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        } else {
            return Pattern.compile(a.pattern() + "|" + b.pattern());
        }
    }
}
