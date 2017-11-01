import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebScraperMain {
    public static void main(String[] args) {
        WikipediaScraper s = new WikipediaScraper();
        String page1 = "Australia";
        String page2 = "Floorball";
        Set<String> links;
        List<String> rawPagesHTML = new ArrayList<>();
        try {
            String rawHTML = s.scrape("/wiki/" + page1);
//            savePage(rawHTML, page1, page1);
            links = s.extractInternalLinks(rawHTML);
//            crawlLinks(links);
            Set<String> nextLevelLinks = new HashSet<>();
            for (String link : links) {
                String nextRawHTML = s.scrape(link);
                rawPagesHTML.add(nextRawHTML);
                nextLevelLinks.addAll(s.extractInternalLinks(nextRawHTML));
            }
            links.addAll(nextLevelLinks);
            for (String link : nextLevelLinks) {
                String nextRawHTML = s.scrape(link);
                rawPagesHTML.add(nextRawHTML);
                links.addAll(s.extractInternalLinks(nextRawHTML));
            }
            System.out.println(links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void crawlLinks(Set<String> links) {
//        WikipediaScraper s = new WikipediaScraper();
//        for (String link : links) {
//            try {
//                String rawHTML = s.scrape(link);
//                String pageTitle = link.substring(6, link.length());
//                savePage(rawHTML, pageTitle);
//                Set<String> subLinks = s.extractInternalLinks(rawHTML);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static void savePage(String rawHTML, String basePage, String title) {
        File file = new File("./files/" + basePage + "/raw_content/" + title + ".txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(rawHTML);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
