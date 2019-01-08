package furtado.murray.webcrawl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;
	
public class WebCrawlerTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;


	private Document doc;
	
	@Before
	public void setUp() {
		try {
		    System.setOut(new PrintStream(outContent));
			File input = new File("src/test/resources/test.html");
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		  } catch (IOException e) {
              System.err.println( e.getMessage());
          }
	}
	
	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	}
	
	@Test
	public void testProcessDocument() {
		WebCrawler crawler = new WebCrawler();
		crawler.processDocument(doc);
		assertEquals("Link: http://twitter.com/Wipro\r\n" + 
				"Link: http://example.com/relative-link\r\n" + 
				"Image: http://example.com/test-image.png\r\n", outContent.toString());
	}


}
