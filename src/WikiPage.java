import java.util.HashSet;
import java.util.Set;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.Renderer;

public class WikiPage {
    private String title;
    private String rawHTML;
    private String link;
    private String rootPage;
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
        String title = rawTitle.replaceAll("[^a-zA-Z0-9.\\-]", "_");
        this.title = title;
    }

    public String getRawHTML() {
        return this.rawHTML;
    }

    public void setRawHTML(String rawHTML) {
        this.rawHTML = rawHTML;
    }

    public String getLink() {
        return this.link;
    }

    public void addAllLinks(Set<String> links) {
        this.links = links;
    }

    public Set<String> getLinks() {
        return this.links;
    }

    public String getRootPage() {
        return this.rootPage;
    }

    public void setRootPage(String rootPage) {
        this.rootPage = rootPage;
    }

    String getTextContent() {
        Source src = new Source(rawHTML);
        Renderer renderer = new Renderer(src);
        return preprocessText(renderer.toString());
    }

    String getBagOfWords() {
        String text = getTextContent();
        text = text.replaceAll(" — ", "");
        text = text.replaceAll(" - ", "");
        text = text.replaceAll("&", "");
        text = text.replaceAll("\\+ ", "");
        text = text.replaceAll("\\d", "");
        text = text.replaceAll(" +", " ");
        return text;
    }

    private String preprocessText(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("\r", " ");
        text = text.replaceAll("\n", " ");
        text = text.replaceAll("\\<.*?>", "");
        text = text.replaceAll("\\[.*?\\]", "");
        text = text.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        text = text.replaceAll("\\.", "");
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
        return text;
    }
}
