package search.scraper;

import com.google.re2j.Pattern;
import datastructures.concrete.ChainedHashSet;
import datastructures.interfaces.ISet;
import org.apache.http.entity.ContentType;

public class Constants {
    public static ISet<String> contentTypeWhitelist() {
        ISet<String> whitelist = new ChainedHashSet<>();
        //whitelist.add(ContentType.APPLICATION_ATOM_XML.getMimeType());
        whitelist.add(ContentType.APPLICATION_XHTML_XML.getMimeType());
        //whitelist.add(ContentType.APPLICATION_XML.getMimeType());
        whitelist.add(ContentType.DEFAULT_TEXT.getMimeType());
        whitelist.add(ContentType.TEXT_HTML.getMimeType());
        whitelist.add(ContentType.TEXT_PLAIN.getMimeType());
        //whitelist.add(ContentType.TEXT_XML.getMimeType());
        return whitelist;
    }

    public static UriMatchRule fileExtensionBlacklist() {
        String[] blacklist = new String[]{
                ".doc", ".docx", ".log", ".odt", ".rtf", ".tex", ".txt", ".wpd", ".wps", ".csv", ".dat",
                ".pps", ".ppt", ".pptx", ".tar", ".xml", ".aif", ".m3u", ".m4a", ".mid", ".mp3", ".mpa",
                ".wav", ".avi", ".flv", ".m4v", ".mov", ".mp4", ".mpg", ".swf", ".vob", ".bmp", ".gif",
                ".jpg", ".png", ".psd", ".pspimage", ".tif", ".tiff", ".ps", ".svg", ".pdf", ".xlr",
                ".xls", ".xlsx", ".sql", ".apk", ".app", ".jar", ".rom", ".sav", ".css", ".js", ".xhtml",
                ".otf", ".ttf", ".dll", ".ico", ".7z", ".gz", ".pkg", ".rar", ".rpm", ".tar.gz", ".zip",
                ".zipx", ".bin", ".iso", ".msi", ".part", ".torrent", ".xml"
        };
        StringBuilder out = new StringBuilder();
        String sep = "";
        for (String extension : blacklist) {
            out.append(sep);
            sep = "|";

            out.append("/.*" + extension);
            out.append(sep);
            out.append("/.*" + extension.toUpperCase());
        }

        return new UriMatchRule(null, Pattern.compile(out.toString()));
    }
}
