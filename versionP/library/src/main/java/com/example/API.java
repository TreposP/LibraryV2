package com.example;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class API {
    
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

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
            scanner.close();
            return;
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
            scanner.close();
        }
    }
}