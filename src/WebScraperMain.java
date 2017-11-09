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
        WikiPage p2 = new WikiPage("/wiki/Floorball");
        p1.setRootPage(p1.getTitle());
        p2.setRootPage(p2.getTitle());
        try {
            s.scrape(p1);
            s.scrape(p2);
            s.extractInternalLinks(p1);
            s.extractInternalLinks(p2);
            Set<String> links = new HashSet<>();
            links.addAll(crawlLinks(p1));
            links.addAll(crawlLinks(p2));
            System.out.println("Links final: " + links.size());
            System.out.println("Pages final: " + pages.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> crawlLinks(WikiPage root) throws Exception {
        int depth = 2;
        WikipediaScraper s = new WikipediaScraper();
        Set<String> links = root.getLinks();
        Set<String> pageLinks = new HashSet<>();
        pageLinks.addAll(links);
        for (int i = 0; i < depth; i++) {
            for (String link : pageLinks) {
                if (!pages.contains(link)) {
                    WikiPage page = new WikiPage(link);
                    page.setRootPage(root.getTitle());
                    s.scrape(page);
                    //Only extract links from the first level
                    if (i == 0) {
                        s.extractInternalLinks(page);
                        links.addAll(page.getLinks());
                    }
                    savePage(page);
                    pages.add(page.getLink());
                }
            }
            pageLinks = links;
        }
        System.out.println(root.getTitle() + " has " + links.size() + " links.");
        return links;
    }

    private static void savePage(WikiPage page) {
        saveFile(page, "html");
        saveFile(page, "content");
        saveFile(page, "words");
        saveFile(page, "links");
    }

    private static void saveFile(WikiPage page, String path) {
        String dirPath = "data/" + path + "/" + page.getRootPage() + "/";
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
            switch (path) {
                case "html":
                    bw.write(page.getRawHTML());
                    break;
                case "content":
                    bw.write(page.getTextContent());
                    break;
                case "words":
                    bw.write(page.getBagOfWords());
                    break;
                case "links":
                    for (String link : page.getLinks()) {
                        bw.write(link);
                        bw.newLine();
                    }
                    break;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occured while creating this file:");
            System.out.println(page.getTitle());
            System.out.println(file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
