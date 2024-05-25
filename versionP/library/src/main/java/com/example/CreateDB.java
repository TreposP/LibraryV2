package com.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    public static void createNewDatabase() {
        String url = "jdbc:sqlite:src/main/resources/Database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Le pilote est " + meta.getDriverName());
                System.out.println("La nouvelle base de données a été créée.");

                // Création des tables
                String createUserTable = "CREATE TABLE IF NOT EXISTS User ("
                        + " id INTEGER PRIMARY KEY,"
                        + " nom TEXT NOT NULL,"
                        + " prenom TEXT NOT NULL,"
                        + " address TEXT,"
                        + " phone TEXT"
                        + ");";

                String createLoanTable = "CREATE TABLE IF NOT EXISTS Loan ("
                        + " idLoan INTEGER PRIMARY KEY,"
                        + " idUser INTEGER,"
                        + " titre TEXT NOT NULL,"
                        + " auteur TEXT NOT NULL,"
                        + " dateLoan TEXT,"
                        + " dateReturnLoan TEXT,"
                        + " RealDateReturnLoan TEXT,"
                        + " FOREIGN KEY (idUser) REFERENCES User(id),"
                        + " UNIQUE(titre, auteur)"
                        + ");";

                try (Statement stmt = conn.createStatement()) {
                    // Création de la table User
                    stmt.execute(createUserTable);
                    System.out.println("Table User créée avec succès.");

                    // Création de la table Loan
                    stmt.execute(createLoanTable);
                    System.out.println("Table Loan créée avec succès.");
                }

            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la base de données : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase();
    }
}
