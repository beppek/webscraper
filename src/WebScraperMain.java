import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WebScraperMain {
    private static Set<String> pages = new HashSet<>();
    public static void main(String[] args) {
        WikipediaScraper s = new WikipediaScraper();

        WikiPage p1 = new WikiPage("/wiki/Ethereum");
        p1.setRootPage(p1.getTitle());
        WikiPage p2 = new WikiPage("/wiki/Floorball");
        p2.setRootPage(p2.getTitle());
        try {
            s.scrape(p1);
            s.scrape(p2);
            s.extractInternalLinks(p2);
            s.extractInternalLinks(p1);
            Set<String> links = p1.getLinks();
            links.addAll(p2.getLinks());
            Set<String> page1Links = new HashSet<>();
            Set<String> page2Links = new HashSet<>();
//            for (String link : p1.getLinks()) {
//                WikiPage page = new WikiPage(link);
//                page.setRootPage(p1.getTitle());
//                s.scrape(page);
//                s.extractInternalLinks(page);
//                page1Links.addAll(page.getLinks());
//                pages.add(page.getLink());
//                savePage(page);
//            }
//            crawlLinks(page1Links, p1.getTitle());
            crawlLinks(p1);
            links.addAll(page1Links);
            System.out.println("Links final: " + links.size());
            System.out.println("Pages final: " + pages.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void crawlLinks(Set<String> links, String rootPage) throws Exception {
    private static void crawlLinks(WikiPage p) throws Exception {
        WikipediaScraper s = new WikipediaScraper();
        Set<String> links = new HashSet<>();
        for (String link : p.getLinks()) {
            WikiPage page = new WikiPage(link);
            page.setRootPage(page.getTitle());
            s.scrape(page);
//            s.extractInternalLinks(page);
//            links.addAll(page.getLinks());
            pages.add(page.getLink());
            savePage(page);
        }
//        for (String link : links) {
//            WikiPage page = new WikiPage(link);
//            s.scrape(page);
//            page.setRootPage(rootPage);
//            pages.add(page.getLink());
//            savePage(page);
//        }
        System.out.println("Next level pages: " + pages.size());
//        return links;
    }

    private static void savePage(WikiPage page) {
        String dirPath = "files/" + page.getRootPage() + "/raw_content/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dirPath + page.getTitle() + ".txt");

        try {
            if (!file.exists()) {

                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(page.getRawHTML());
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occured while creating this file:");
            System.out.println(page.getTitle());
            System.out.println(file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
