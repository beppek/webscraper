/**
 * Main class for the web scraper program
 * Will scrape wikipedia with entry points of ethereum and floorball
 * */
public class WebScraperMain {
    public static void main(String[] args) {
        WikiPage[] pages = {new WikiPage("/wiki/Ethereum"), new WikiPage("/wiki/Floorball")};
        Crawler crawler = new Crawler(pages);
        crawler.start();
    }
}
