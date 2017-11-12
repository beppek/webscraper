import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * This class saves the contents of the page on file.
 * It saves one file for the raw html content, one for text content, one for links and one for the words of the article
 * */
class PageFileWriter {

    private WikiPage page;
    private String[] paths = {"html", "content", "links", "words"};

    PageFileWriter(WikiPage page) {
        this.page = page;
    }

    void savePage() {
        for (String path : paths) {
            saveFile(path);
        }
    }

    private void saveFile(String path) {
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
            java.io.FileWriter fw = new java.io.FileWriter(file.getAbsoluteFile());
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
