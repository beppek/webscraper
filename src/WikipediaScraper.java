import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikipediaScraper {

    private String baseUrl = "https://en.wikipedia.org";

    /**
     * Scrapes the given wikipedia page
     * @param page - WikiPage representing the wikipedia page to be visited.
     * */
    public void scrape(WikiPage page) throws Exception {
        String contents = "";
        URL url = new URL(baseUrl + page.getLink());

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            contents += line + "\n";
        }
        page.setRawHTML(contents);
    }

    /**
     * Extracts all internal wikipedia links
     * @param page - WikiPage instance representing the page from which to extract the links
     * NOTE: Page should be initialized with rawHTML content before links can be extracted
     * @return List of internal links to other wikipedia articles
     * */
    public void extractInternalLinks(WikiPage page) {
        Set<String> links = new HashSet<>();
        Pattern p = Pattern.compile("href=\"/wiki/(?!.*:)(.*?)\"");
        String rawHTML = page.getRawHTML();
        Matcher m = p.matcher(rawHTML);
        while (!m.hitEnd() && links.size() < 25) {
            if (m.find()) {
                String match = rawHTML.substring(m.start(), m.end());
                String link = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));
                links.add(link);
            }
        }
        page.addAllLinks(links);
    }
}
