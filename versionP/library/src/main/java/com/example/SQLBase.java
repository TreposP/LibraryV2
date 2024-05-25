package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SQLBase {

    private Connection connect() {
        // Chemin de la base de données SQLite
        String url = "jdbc:sqlite:/Users/CYTech Student/IdeaProjects/versionP/library/src/main/resources/Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connexion à SQLite établie avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données SQLite : " + e.getMessage());
        }
        return conn;
    }

    public void insertUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom de l'utilisateur : ");
        String nom = scanner.nextLine();
        System.out.println("Entrez le prénom de l'utilisateur : ");
        String prenom = scanner.nextLine();
        System.out.println("Entrez l'adresse de l'utilisateur : ");
        String address = scanner.nextLine();
        System.out.println("Entrez le numéro de téléphone de l'utilisateur : ");
        String phone = scanner.nextLine();

        String sql = "INSERT INTO User(nom, prenom, address, phone) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);
            pstmt.executeUpdate();
            System.out.println("Utilisateur inséré avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage());
        }
    }

    public void insertLoan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le titre du livre : ");
        String titre = scanner.nextLine();
        System.out.println("Entrez l'auteur du livre : ");
        String auteur = scanner.nextLine();
        System.out.println("Entrez l'ID de l'utilisateur : ");
        int idUser = scanner.nextInt();
        scanner.nextLine(); // Pour consommer le retour à la ligne

        if (isBookAlreadyLoaned(titre, auteur)) {
            System.out.println("Ce livre est déjà emprunté. Impossible d'effectuer un nouveau prêt.");
            return;
        }

        LocalDate dateLoan = LocalDate.now();
        LocalDate dateReturnLoan = dateLoan.plusDays(15); // Date de retour automatique dans 15 jours

        String sql = "INSERT OR IGNORE INTO Loan(titre, auteur, idUser, dateLoan, dateReturnLoan, RealDateReturnLoan) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titre);
            pstmt.setString(2, auteur);
            pstmt.setInt(3, idUser);
            pstmt.setString(4, dateLoan.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(5, dateReturnLoan.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setNull(6, java.sql.Types.DATE); // RealDateReturnLoan initialisé à NULL par défaut
            pstmt.executeUpdate();
            System.out.println("Prêt inséré avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion du prêt : " + e.getMessage());
        }
    }

    public void selectUsers() {
        String sql = "SELECT id, nom, prenom, address, phone FROM User";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("nom") + "\t" +
                        rs.getString("prenom") + "\t" +
                        rs.getString("address") + "\t" +
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des utilisateurs : " + e.getMessage());
        }
    }

    public void selectLoans() {
        String sql = "SELECT idLoan, titre, auteur, idUser, dateLoan, dateReturnLoan, RealDateReturnLoan FROM Loan";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.print(rs.getInt("idLoan") + "\t");
                System.out.print(rs.getString("titre") + "\t");
                System.out.print(rs.getString("auteur") + "\t");
                System.out.print(rs.getInt("idUser") + "\t");
                System.out.print(rs.getString("dateLoan") + "\t");
                System.out.print(rs.getString("dateReturnLoan") + "\t");

                // Affichage spécifique pour la date de retour effective
                String realDateReturnLoan = rs.getString("RealDateReturnLoan");
                if (realDateReturnLoan == null) {
                    System.out.println("NULL");
                } else {
                    System.out.println(realDateReturnLoan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des prêts : " + e.getMessage());
        }
    }

    public void searchLoanByTitleAndAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le titre du livre : ");
        String titre = scanner.nextLine();
        System.out.println("Entrez l'auteur du livre : ");
        String auteur = scanner.nextLine();

        String sql = "SELECT * FROM Loan WHERE titre = ? AND auteur = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titre);
            pstmt.setString(2, auteur);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Ce livre est déjà emprunté.");
                System.out.println("ID du prêt : " + rs.getInt("idLoan"));
                System.out.println("ID de l'utilisateur : " + rs.getInt("idUser"));
                System.out.println("Date de prêt : " + rs.getString("dateLoan"));
                System.out.println("Date de retour prévue : " + rs.getString("dateReturnLoan"));
                String realDateReturnLoan = rs.getString("RealDateReturnLoan");
                if (realDateReturnLoan == null) {
                    System.out.println("Date de retour effective : NULL");
                } else {
                    System.out.println("Date de retour effective : " + realDateReturnLoan);
                }
            } else {
                System.out.println("Ce livre n'est pas actuellement emprunté.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du prêt : " + e.getMessage());
        }
    }

    public void selectLoansByUserId(int userId) {
        String sql = "SELECT idLoan, titre, auteur, dateLoan, dateReturnLoan, RealDateReturnLoan FROM Loan WHERE idUser = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("Livres empruntés par l'utilisateur avec ID " + userId + ":");
                while (rs.next()) {
                    System.out.print(rs.getInt("idLoan") + "\t");
                    System.out.print(rs.getString("titre") + "\t");
                    System.out.print(rs.getString("auteur") + "\t");
                    System.out.print(rs.getString("dateLoan") + "\t");
                    System.out.print(rs.getString("dateReturnLoan") + "\t");

                    // Affichage spécifique pour la date de retour effective
                    String realDateReturnLoan = rs.getString("RealDateReturnLoan");
                    if (realDateReturnLoan == null) {
                        System.out.println("NULL");
                    } else {
                        System.out.println(realDateReturnLoan);
                    }
                }
            } else {
                System.out.println("Aucun livre emprunté par l'utilisateur avec ID " + userId);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des prêts : " + e.getMessage());
        }
    }

    private boolean isBookAlreadyLoaned(String titre, String auteur) {
        String sql = "SELECT * FROM Loan WHERE titre = ? AND auteur = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titre);
            pstmt.setString(2, auteur);

            ResultSet rs = pstmt.executeQuery();

            // Si on trouve une ligne, le livre est déjà emprunté
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification du prêt : " + e.getMessage());
        }

        return false;
    }
}
