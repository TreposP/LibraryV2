package Projet.Biblio;

import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import Projet.Biblio.Book.Book;

public class BookServiceNew {

    public static List<Book> getBooksFromXML(String xmlData) {
        List<Book> books = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
            doc.getDocumentElement().normalize();

            NodeList resultList = doc.getElementsByTagName("result");
            for (int i = 0; i < resultList.getLength(); i++) {
                Element resultElement = (Element) resultList.item(i);

                String title = getElementValue(resultElement, "titre");
                String author = getElementValue(resultElement, "auteur");
                String date = getElementValue(resultElement, "date");

                books.add(new Book(title, author, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private static String getElementValue(Element parentElement, String elementName) {
        NodeList nodeList = parentElement.getElementsByTagName("binding");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute("name").equals(elementName)) {
                NodeList literalList = element.getElementsByTagName("literal");
                if (literalList.getLength() > 0) {
                    return literalList.item(0).getTextContent();
                }
            }
        }
        return null;
    }
}
