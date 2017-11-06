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
//        String page1 = "/wiki/Ethereum";
//        String page2 = "/wiki/Floorball";
//        WikiPageGraph graph = new WikiPageGraph();
//        WikiPage p1 = graph.addPageFor("/wiki/Ethereum");
//        WikiPage p2 = graph.addPageFor("/wiki/Floorball");

        WikiPage p1 = new WikiPage("/wiki/Ethereum");
        p1.setRootPage(p1.getTitle());
        WikiPage p2 = new WikiPage("/wiki/Floorball");
        p2.setRootPage(p2.getTitle());
//        pages.add(p1);
//        pages.add(p2);
        try {
            s.scrape(p1);
            s.extractInternalLinks(p1);
            Set<String> links = p1.getLinks();
            System.out.println("Links level 1: " + links.size());
//            Set<String> nextLevel = crawlLinks(links);
            Set<String> page1Links = new HashSet<>();
            Set<String> page2Links = new HashSet<>();
            for (String link : links) {
                WikiPage page = new WikiPage(link);
                page.setRootPage(p1.getTitle());
                s.scrape(page);
                s.extractInternalLinks(page);
                page1Links.addAll(page.getLinks());
                pages.add(page.getLink());
                savePage(page);
            }
            System.out.println("Next level return size: " + page1Links.size());
            System.out.println("Pages level 1: " + pages.size());
            crawlLinks(page1Links, p1.getTitle());
//            Set<String> nextLevel = new HashSet<>();
//            for (String link : links) {
////                WikiPage page = graph.addPageFor(link);
//                WikiPage page = new WikiPage(link);
//                s.scrape(page);
////                graph.addLinkBetween(p1.getLink(), link);
////                Set<String> subLinks = s.extractInternalLinks(page);
//                s.extractInternalLinks(page);
//                nextLevel.addAll(page.getLinks());
//            }

            links.addAll(page1Links);
            System.out.println("Links final: " + links.size());
            System.out.println("Pages final: " + pages.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            String rawHTMLPage1 = s.scrape("/wiki/" + page1);
//            savePage(rawHTMLPage1, page1, page1);
//            Set<String> links = s.extractInternalLinks(rawHTMLPage1);
//            Set<String> subLinks = new HashSet<>();
//            for (String link : links) {
//                String rawHTML = s.scrape(link);
//                String pageTitle = link.substring(6, link.length());
//                savePage(rawHTML, page1, pageTitle);
//                Set<String> nextLevelLinks = s.extractInternalLinks(rawHTML);
//                subLinks.addAll(nextLevelLinks);
//                crawlLinks(nextLevelLinks, page1);
//            }
//            links.addAll(subLinks);
//            System.out.println("Line 20: " + links.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static void crawlLinks(Set<String> links, String rootPage) throws Exception {
        WikipediaScraper s = new WikipediaScraper();
//        Set<String> nextLevel = new HashSet<>();
        for (String link : links) {
            WikiPage page = new WikiPage(link);
            s.scrape(page);
            page.setRootPage(rootPage);
//            s.extractInternalLinks(page);
//            nextLevel.addAll(page.getLinks());
            pages.add(page.getLink());
            savePage(page);
        }
//        System.out.println("Next level: " + nextLevel.size());
        System.out.println("Next level pages: " + pages.size());
//        return nextLevel;
    }
//
//    private static void crawlLinks(Set<String> links, String basePage) {
//        WikipediaScraper s = new WikipediaScraper();
//        Set<String> nextLevelLinks = new HashSet<>();
////        int crawlLength = links.size() < 50 ? links.size() : 50;
////        int counter = 0;
//        for (String link : links) {
////            if (counter > crawlLength) {
////                break;
////            }
//            try {
//                String rawHTML = s.scrape(link);
//                String pageTitle = link.substring(6, link.length());
//                savePage(rawHTML, basePage, pageTitle);
//                nextLevelLinks.addAll(s.extractInternalLinks(rawHTML));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            counter++;
//        }
//        System.out.println("Line 46: " + nextLevelLinks.size());
//    }

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
//
//    private static void savePage(String rawHTML, String basePage, String title) {
//        String dirPath = "files/" + basePage + "/raw_content/";
//        File dir = new File(dirPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File file = new File(dirPath + title + ".txt");
//
//        try {
//            if (!file.exists()) {
//
//                file.createNewFile();
//            }
//            FileWriter fw = new FileWriter(file.getAbsoluteFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(rawHTML);
//            bw.close();
//        } catch (IOException e) {
//            System.out.println(title);
//            System.out.println(file.getAbsolutePath());
//            e.printStackTrace();
//        }
//    }
}
