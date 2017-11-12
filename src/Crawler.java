import java.util.HashSet;
import java.util.Set;

/**
 * Crawler class
 * crawls the wikipedia pages supplied in the constructor
 * Initiate with start
 * */
class Crawler {

    private Set<String> visited = new HashSet<>();
    private WikiPage[] pages;

    Crawler(WikiPage[] pages) {
        this.pages = pages;
    }

    void start() {
        WikipediaScraper s = new WikipediaScraper();
        Set<String> links = new HashSet<>();
        for (WikiPage page : pages) {
            page.setRootPage(page.getTitle());
            try {
                s.scrape(page);
                links.addAll(crawlLinks(page));
                PageFileWriter pw = new PageFileWriter(page);
                pw.savePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("The crawler visited " + visited.size() + " pages, and extracted " + links.size() + " links.");
    }

    /**
     * Crawls the links from the root page down 2 levels
     * */
    private Set<String> crawlLinks(WikiPage root) throws Exception {
        int depth = 2;
        WikipediaScraper s = new WikipediaScraper();
        Set<String> links = root.getLinks();
        Set<String> pageLinks = new HashSet<>();
        pageLinks.addAll(links);
        for (int i = 0; i < depth; i++) {
            for (String link : pageLinks) {
                if (!visited.contains(link)) {
                    visited.add(link);
                    WikiPage page = new WikiPage(link);
                    page.setRootPage(root.getTitle());
                    s.scrape(page);
                    links.addAll(page.getLinks());
                    PageFileWriter pw = new PageFileWriter(page);
                    pw.savePage();
                }
            }
            pageLinks = new HashSet<>();
            pageLinks.addAll(links);
        }
        System.out.println("Finished crawling wiki/" + root.getTitle() + " and linked pages 2 levels deep.");
        return links;
    }
}
