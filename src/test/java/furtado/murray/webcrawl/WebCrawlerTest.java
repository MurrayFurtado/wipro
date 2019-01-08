package furtado.murray.webcrawl;

import static org.junit.Assert.assertEquals;


import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;
	
public class WebCrawlerTest {
	
	private Document doc; 
	
	@Before
	public void setUp() {
		try {
			File input = new File("src/test/resources/test.html");
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		  } catch (IOException e) {
              System.err.println( e.getMessage());
          }
	}

	@Test
	public void testProcessDocument() {
		WebCrawler crawler = new WebCrawler();
		crawler.processDocument(doc);
		assertEquals("Link: http://twitter.com/Wipro\n" + 
				"Link: http://example.com/relative-link\n" + 
				"Image: http://example.com/test-image.png\n", crawler.getOutput());
	}

	
}
