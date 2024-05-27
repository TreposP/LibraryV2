package Projet.Biblio.APIXSQL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;


public class MainApp {

    public static void main(String[] args) throws IOException {

        // prêt à scanner
        SQLBase example = new SQLBase();
        Scanner scanner = new Scanner(System.in);

        // Menu principal : choix entre gestion des utilisateurs et rechercher un livre
        while (true) {
            System.out.println("\nMenu Principal");
            System.out.println("1. Gestion des utilisateurs");
            System.out.println("2. Rechercher un livre");
            System.out.println("3. fermer l'application");


            int choixMenu = Integer.parseInt(scanner.nextLine().trim());

            if (choixMenu == 1){
                boolean GestiontoReturnMenu = true;
                while (GestiontoReturnMenu) {
                    System.out.println("\nMenu de gestion des utilisateurs");
                    System.out.println("1. Ajouter un utilisateur");
                    System.out.println("2. Supprimer un utilisateur");
                    System.out.println("3. Ajouter un prêt");
                    System.out.println("4. Rendre un livre emprunté");
                    System.out.println("5. Afficher les utilisateurs");
                    System.out.println("6. Afficher tous les prêts");
                    System.out.println("7. Rechercher un prêt par titre et auteur");
                    System.out.println("8. Afficher les prêts d'un utilisateur");
                    System.out.println("9. Quitter");
                    System.out.println("Faites votre choix : ");

                    int choixGestion = scanner.nextInt();
                    scanner.nextLine(); // Pour consommer le retour à la ligne

                    switch (choixGestion) {
                        case 1:
                            example.insertUser();
                            break;
                        case 2:
                            example.removeUser();
                            break;
                        case 3:
                            example.insertLoan();
                            break;
                        case 4:
                            example.removeBook();
                            break;
                        case 5:
                            example.selectUsers();
                            break;
                        case 6:
                            example.selectLoans();
                            break;
                        case 7:
                            example.searchLoanByTitleAndAuthor();
                            break;
                        case 8:
                            System.out.println("Entrez l'ID de l'utilisateur : ");
                            int userId = scanner.nextInt();
                            scanner.nextLine(); // Pour consommer le retour à la ligne
                            example.selectLoansByUserId(userId);
                            break;
                        case 9:
                            GestiontoReturnMenu = false;
                            System.out.println("Merci, au revoir !");
                            //scanner.close();
                            //return;

                        default:
                            System.out.println("Retour au menu principal");
                    }
                }
            }

            else if (choixMenu == 2){
                // Initialisation des filtres
                System.out.print("Choisissez une option : (1) ISBN, (2) Auteur/Titre/Date : ");
                int choix = Integer.parseInt(scanner.nextLine().trim());

                String isbn = "";
                String titre = "";
                String auteur = "";
                String date = "";

                if (choix == 1) {
                    System.out.print("Entrez l'ISBN du livre : ");
                    isbn = scanner.nextLine().trim();
                } else if (choix == 2) {
                    System.out.print("Entrez le titre du livre (vide pour ignorer ce filtre) : ");
                    titre = scanner.nextLine().trim();

                    System.out.print("Entrez l'auteur du livre (vide pour ignorer ce filtre) : ");
                    auteur = scanner.nextLine().trim();

                    System.out.print("Entrez l'année de publication du livre (vide pour ignorer ce filtre) : ");
                    date = scanner.nextLine().trim();
                } else {
                    System.out.println("Option invalide.");
                    //scanner.close();
                    //return;
                }

                // Création d'un client HTTP
                CloseableHttpClient httpClient = HttpClients.createDefault();

                // Construction de la requête SPARQL
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("PREFIX rdarelationships: <http://rdvocab.info/RDARelationshipsWEMI/>\n");
                queryBuilder.append("PREFIX dcterms: <http://purl.org/dc/terms/>\n");
                queryBuilder.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n");
                queryBuilder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n");
                queryBuilder.append("PREFIX bnf-onto: <http://data.bnf.fr/ontology/bnf-onto/>\n");
                queryBuilder.append("SELECT DISTINCT ?titre ?auteur ?date\n");
                queryBuilder.append("WHERE {\n");

                if (!isbn.isEmpty()) {
                    queryBuilder.append("  ?edition bnf-onto:isbn \"" + isbn + "\" ;\n");
                    queryBuilder.append("        rdarelationships:workManifested ?work .\n");

                } else {
                    queryBuilder.append("  ?edition rdarelationships:workManifested ?work .\n");

                    if (!titre.isEmpty()) {
                        queryBuilder.append("  FILTER regex(?titre, \"" + titre + "\", \"i\") \n");
                    }
                    if (!auteur.isEmpty()) {
                        queryBuilder.append("  ?work dcterms:creator ?creator .\n");
                        queryBuilder.append("  ?creator foaf:name ?auteur .\n");
                        queryBuilder.append("  FILTER regex(?auteur, \"" + auteur + "\", \"i\") \n");
                    }
                    if (!date.isEmpty()) {
                        queryBuilder.append("  ?work dcterms:date ?date .\n");
                        queryBuilder.append("  FILTER regex(?date, \"" + date + "\", \"i\") \n");
                    }
                }
                queryBuilder.append("  ?work rdfs:label ?titre ;\n");
                queryBuilder.append("        dcterms:creator ?creator;\n");
                queryBuilder.append("        dcterms:date ?date.\n");
                queryBuilder.append("  ?creator foaf:name ?auteur .\n");
                queryBuilder.append("}\n");
                queryBuilder.append("LIMIT 50\n");

                try {
                    // Encoder la requête SPARQL pour l'URL
                    String encodedQuery = URLEncoder.encode(queryBuilder.toString(), "UTF-8");

                    // Créer une requête HTTP GET avec l'URL de l'API SPARQL
                    String sparqlEndpoint = "https://data.bnf.fr/sparql?query=" + encodedQuery;
                    HttpGet httpGet = new HttpGet(sparqlEndpoint);

                    // Exécuter la requête HTTP
                    HttpResponse response = httpClient.execute(httpGet);

                    // Obtenir l'entité de la réponse
                    HttpEntity entity = response.getEntity();

                    // Vérifier si la réponse est OK avant de convertir en chaîne XML
                    if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                        // Convertir l'entité en une chaîne XML
                        String xmlData = EntityUtils.toString(entity);

                        // Appeler la méthode main de BookService et lui passer les données XML
                        BookService.main(xmlData);
                    } else {
                        System.out.println("La requête SPARQL a retourné une réponse non valide: " + response.getStatusLine());
                    }
                } catch (Exception e) {
                    // En cas d'erreur, imprimer la trace de l'exception
                    e.printStackTrace();
                } finally {
                    // Fermer le client HTTP
                    httpClient.close();
                    //scanner.close();
                }
            }
            else if (choixMenu == 3){
                System.out.println("Application fermée !");
                scanner.close();
                return;
            }
            else{
                System.out.println("Option invalide.");
            }
        }
    }
}
