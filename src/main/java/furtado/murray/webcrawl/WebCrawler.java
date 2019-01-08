package furtado.murray.webcrawl;

import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;

public class WebCrawler {
	
    private HashSet<String> pagesVisited = new HashSet<String>();
    private static String startUrl = "http://www.wipro.com/";
    private StringBuilder output = new StringBuilder();
    
    public WebCrawler() {
    }

    
    private void getPageLinks(String URL) {

        if (!pagesVisited.contains(URL)) {
            try {
                pagesVisited.add(URL);
                output.append("Page ----- ").append(URL).append("\n");
                Document document = Jsoup.connect(URL).get();
                processDocument(document);
                PrintWriter out = new PrintWriter("output.txt");
                out.println(output.toString());
                out.close();
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }


	public void processDocument(Document document) {
		Elements links = document.select("a[href]");	
		output.append(outputTypeForPage(links,"href","Link"));
		Elements images = document.getElementsByTag("img");	
		output.append(outputTypeForPage(images,"src","Image"));
		for (Element page : links) {
			String absUrl = page.attr("abs:href");
			// Replace https:// with http:// via regex to ignore scheme mismatches
			absUrl = absUrl.replaceFirst("^(http[s]?://|\\.|http[s]?://|\\.)","http://");
			if(absUrl != "" && absUrl.startsWith(startUrl)) {
				getPageLinks(page.attr("abs:href"));
			}
		}
	}
	

    private String outputTypeForPage(Elements linksOnPage, String selector, String linkType) {
    	StringBuilder pageOut = new StringBuilder();
    	for (Element elem : linksOnPage) {
    		// render both relative and absolute links as absolute 
        	String link = elem.absUrl(selector);
        	if(!link.equals(""))
        		pageOut.append(linkType).append(": ").append(link).append("\n");
        }
    	return pageOut.toString();
    }
    
    public String getOutput() {
    	return output.toString();
    }
    

    public static void main(String[] args) {
        new WebCrawler().getPageLinks(startUrl);
    }
}
