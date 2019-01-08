package furtado.murray.webcrawl;

import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebCrawler {
	
    private HashSet<String> pagesVisited;
    private static String startUrl = "http://www.wipro.com/";
    public WebCrawler() {
    	pagesVisited = new HashSet<String>();
    }

    
    private void getPageLinks(String URL) {

        if (!pagesVisited.contains(URL)) {
            try {
                pagesVisited.add(URL);
                System.out.println("Page ----- " + URL);
                Document document = Jsoup.connect(URL).get();
                processDocument(document);
                
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }


	public void processDocument(Document document) {
		Elements links = document.select("a[href]");	
		outputTypeForPage(links,"href","Link");
		Elements images = document.getElementsByTag("img");	
		outputTypeForPage(images,"src","Image");
		for (Element page : links) {
			String absUrl = page.attr("abs:href");
			// Replace https:// with http:// via regex to ignore scheme mismatches
			absUrl = absUrl.replaceFirst("^(http[s]?://|\\.|http[s]?://|\\.)","http://");
			if(absUrl != "" && absUrl.startsWith(startUrl)) {
				getPageLinks(page.attr("abs:href"));
			}
				
		}
	}
	

    private void outputTypeForPage(Elements linksOnPage, String selector, String linkType) {
    	for (Element elem : linksOnPage) {
        	String link = elem.absUrl(selector);
        	if(!link.equals(""))
        		System.out.println(linkType + ": " + link);
        }
    }
    

    public static void main(String[] args) {
        new WebCrawler().getPageLinks(startUrl);
    }
}
