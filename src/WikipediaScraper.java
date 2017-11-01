import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Source;

public class WikipediaScraper {

    private String baseUrl = "https://en.wikipedia.org";

    /**
     * Scrapes the given wikipedia page
     * @param pageUrl - String representing the wikipedia page to be visited. Should follow the format /wiki/PageName
     * @return String - contents, representing the raw html of the given wikipedia page
     * */
    public String scrape(String pageUrl) throws Exception {
        String contents = "";
        URL url = new URL(baseUrl + pageUrl);

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            contents += line + "\n";
        }
        return contents;
    }

    /**
     * Extracts all internal wikipedia links
     * @param rawHTML - String representing the raw html of the page from which to extract the links
     * @return List of internal links to other wikipedia articles
     * */
    public Set<String> extractInternalLinks(String rawHTML) {
        Set<String> links = new HashSet<>();
        Pattern p = Pattern.compile("href=\"/wiki/(?!.*:)(.*?)\"");
        Matcher m = p.matcher(rawHTML);
        while (!m.hitEnd()) {
            if (m.find()) {
                String match = rawHTML.substring(m.start(), m.end());
                String link = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));
                links.add(link);
            }
        }
        return links;
    }
}
