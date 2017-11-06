import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WikiPage {
    private String title;
    private String rawHTML;
    private String link;
    private String rootPage;
//    private Set<WikiPage> inboundLinks;
//    private Set<WikiPage> outboundLinks;
    private Set<String> links;

    WikiPage(String link) {
        setTitle(link.substring(6, link.length()));
        this.link = link;
        links = new HashSet<>();
//        inboundLinks = new HashSet<>();
//        outboundLinks = new HashSet<>();
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
//    public void addInboundLink(WikiPage page) {
//        inboundLinks.add(page);
//    }

//    public void addOutboundLink(WikiPage page) {
//        outboundLinks.add(page);
//    }

//    public int inDegree() {
//        return inboundLinks.size();
//    }

//    public int outDegree() {
//        return outboundLinks.size();
//    }

//    public Iterator<WikiPage> outboundLinksIterator() {
//        return outboundLinks.iterator();
//    }

//    public Iterator<WikiPage> inboundLinksIterator() {
//        return inboundLinks.iterator();
//    }

//    public boolean isLinkedFrom(WikiPage page) {
//        return inboundLinks.contains(page);
//    }

//    public boolean isLinkedTo(WikiPage page) {
//        return outboundLinks.contains(page);
//    }

}
