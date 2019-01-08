# wipro

Installation
Run mvn clean install to build the code and run the tests.

WebCrawler will output to a text file in the project directory.

The processDocument() method has been made public to allow junit to test this method. 
This is due to the difference in the way the JSoup api loads html from a url vs file. 

It is assumed that all urls should be output as absolute.



