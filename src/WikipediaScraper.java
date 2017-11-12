import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Wikipedia scraper
 * This program scrapes a page on wikipedia and extracts internal links from the page
 * The limit for number of links to extract from each page is set in the constructor.
 * If no argument is supplied the default link cap is 25.
 * */
class WikipediaScraper {

    private int linkCap;

    WikipediaScraper() {
        linkCap = 25;
    }

    WikipediaScraper(int linkCap) {
        this.linkCap = linkCap;
    }

    /**
     * Scrapes the given wikipedia page
     * @param page - WikiPage representing the wikipedia page to be visited.
     * */
    void scrape(WikiPage page) throws Exception {
        StringBuilder contents = new StringBuilder();
        String baseUrl = "https://en.wikipedia.org";
        URL url = new URL(baseUrl + page.getLink());

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            contents.append(line).append("\n");
        }
        page.setContent(contents.toString());
        extractInternalLinks(page);
    }

    /**
     * Extracts internal wikipedia links until it hits the link cap set in constructor
     * @param page - WikiPage instance representing the page from which to extract the links
     * NOTE: Page should be initialized with rawHTML content before links can be extracted
     * */
    private void extractInternalLinks(WikiPage page) {
        Set<String> links = new HashSet<>();
        String internalLinkPattern = "href=\"/wiki/(?!.*:)(.*?)\"";
        Pattern p = Pattern.compile(internalLinkPattern);
        String rawHTML = page.getRawHTML();
        Matcher m = p.matcher(rawHTML);
        while (!m.hitEnd() && links.size() < linkCap) {
            if (m.find()) {
                String match = rawHTML.substring(m.start(), m.end());
                String link = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));
                links.add(link);
            }
        }
        page.addAllLinks(links);
    }
}
