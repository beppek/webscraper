//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//public class WikiPageGraph {
//    private Map<String, WikiPage> pageMap;
//    private Set<WikiPage> heads;
//    private Set<WikiPage> tails;
//
//    WikiPageGraph() {
//        pageMap = new HashMap<>();
//        heads = new HashSet<>();
//        tails = new HashSet<>();
//    }
//
//    public WikiPage addPageFor(String link) {
//        if (link == null) {
//            throw new RuntimeException("Page can't be null");
//        }
//        if (pageMap.containsKey(link)) {
//            return pageMap.get(link);
//        } else {
//            WikiPage page = new WikiPage(link);
//            pageMap.put(link, page);
//            heads.add(page);
//            tails.add(page);
//            return page;
//        }
//    }
//
//    public WikiPage getPageFor(String link) {
//        if (link == null) {
//            throw new RuntimeException("Link can't be null");
//        } else if (!pageMap.containsKey(link)) {
//            throw new RuntimeException("Page doesn't exist");
//        }
//        WikiPage page = pageMap.get(link);
//        return page;
//    }
//
//    public boolean addLinkBetween(String from, String to) {
//        if (from == "" || to == "") {
//            throw new RuntimeException("To and from can not be null");
//        }
//        WikiPage src = addPageFor(from);
//        WikiPage tgt = addPageFor(to);
//        if (src.isLinkedTo(tgt)) {
//            return false;
//        } else {
//            src.addOutboundLink(tgt);
//            tgt.addInboundLink(src);
//
//            tails.remove(src);
//            heads.remove(tgt);
//
//            return true;
//        }
//    }
//
//    public boolean containsPageFor(String link) {
//        if (link == null) {
//            throw new RuntimeException("Link can't be null");
//        }
//        return pageMap.get(link) != null;
//    }
//
//    public boolean containsLinkBetween(String from, String to) {
//        if (from == "" || to == "") {
//            throw new RuntimeException("Links can't be empty strings");
//        }
//        if (!containsPageFor(from) || !containsPageFor(to)) {
//            return false;
//        }
//        WikiPage src = addPageFor(from);
//        WikiPage tgt = addPageFor(to);
//        return src.isLinkedTo(tgt);
//    }
//
//    public int pageCount() {
//        return pageMap.size();
//    }
//}
