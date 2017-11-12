import java.util.HashSet;
import java.util.Set;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.Renderer;

/**
 * This class represents the wikipedia page in the program
 * Handles the raw content of the page as well as filtering of text and words
 * Also keeps track of the internal links on the page
 * */
public class WikiPage {
    private String title;
    private String rawHTML;
    private String link;
    private String rootPage;
    private String textContent;
    private Set<String> links;

    WikiPage(String link) {
        setTitle(link.substring(6, link.length()));
        this.link = link;
        links = new HashSet<>();
    }

    public String getTitle() {
        return this.title;
    }

    private void setTitle(String rawTitle) {
        this.title = rawTitle.replaceAll("[^a-zA-Z0-9.\\-]", "_");
    }

    public void setContent(String rawHTML) {
        setRawHTML(rawHTML);
        setTextContent();
    }

    String getRawHTML() {
        return this.rawHTML;
    }

    private void setRawHTML(String rawHTML) {
        this.rawHTML = rawHTML;
    }

    public String getLink() {
        return this.link;
    }

    void addAllLinks(Set<String> links) {
        this.links = links;
    }

    public Set<String> getLinks() {
        return this.links;
    }

    String getRootPage() {
        return this.rootPage;
    }

    void setRootPage(String rootPage) {
        this.rootPage = rootPage;
    }

    private void setTextContent() {
        Source src = new Source(rawHTML);
        Renderer renderer = new Renderer(src);
        textContent = preprocessText(renderer.toString());
    }

    String getTextContent() {
        return textContent;
    }

    /**
     * Returns the text content as a bag of words with everything except the words of the article filtered out
     * */
    String getBagOfWords() {
        String text = getTextContent();
        text = text.replaceAll("[^a-z|^']", " ");
        text = text.trim().replaceAll(" +", " ");
        return text;
    }

    /**
     * Process the text and clean up leftover tags and other non-text elements.
     * */
    private String preprocessText(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("\r", " ");
        text = text.replaceAll("\n", " ");
        text = text.replaceAll("\\<.*?>", "");
        text = text.replaceAll("\\[.*?\\]", "");
        text = text.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        text = text.replaceAll("\\.", " ");
        text = text.replaceAll(",", "");
        text = text.replaceAll("\\?", "");
        text = text.replaceAll(";", "");
        text = text.replaceAll("\"", "");
        text = text.replaceAll("/", " ");
        text = text.replaceAll(":", "");
        text = text.replaceAll("\\(", " ");
        text = text.replaceAll("\\*", "");
        text = text.replaceAll("_", "");
        text = text.replaceAll("!", "");
        text = text.replaceAll("#", "");
        text = text.replaceAll("\\)", " ");
        text = text.replaceAll("\\^", "");
        text = text.replaceAll("\\|", "");
        text = text.replaceAll("=", "");
        text = text.trim().replaceAll(" +", " ");
        return text;
    }
}
