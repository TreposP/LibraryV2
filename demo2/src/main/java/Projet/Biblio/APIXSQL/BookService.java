package Projet.Biblio.APIXSQL;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BookService {

    public static void main(String xmlData) {
        try {
            // Création d'une instance de DocumentBuilderFactory pour obtenir un DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Analyse de la chaîne XML en utilisant un StringReader et InputSource
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
            doc.getDocumentElement().normalize();

            // Récupération de tous les éléments "result"
            NodeList resultList = doc.getElementsByTagName("result");
            for (int i = 0; i < resultList.getLength(); i++) {
                Element resultElement = (Element) resultList.item(i);

                // Extraction des valeurs des éléments "titre", "auteur", "date", "editeur"
                String title = getElementValue(resultElement, "titre");
                String author = getElementValue(resultElement, "auteur");
                String date = getElementValue(resultElement, "date");

                // Affichage des valeurs extraites
                System.out.println("Titre: " + title);
                System.out.println("Auteur: " + author);
                System.out.println("Date: " + date);
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            // En cas d'erreur, imprimer la trace de l'exception
            e.printStackTrace();
        }
    }

    // Méthode pour extraire la valeur d'un élément donné à partir de son nom
    private static String getElementValue(Element parentElement, String elementName) {
        NodeList nodeList = parentElement.getElementsByTagName("binding");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute("name").equals(elementName)) {
                // Récupération du contenu du premier élément "literal"
                NodeList literalList = element.getElementsByTagName("literal");
                if (literalList.getLength() > 0) {
                    return literalList.item(0).getTextContent();
                }
            }
        }
        return null; // Retourne null si l'élément n'est pas trouvé
    }
}
