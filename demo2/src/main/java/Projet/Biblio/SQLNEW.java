package Projet.Biblio;

import Projet.Biblio.Loan.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Projet.Biblio.User.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLNEW {

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

    public ObservableList<User> getUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT id, nom, prenom, address, phone FROM User";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("prenom"));
                user.setLastName(rs.getString("nom"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des utilisateurs : " + e.getMessage());
        }
        return userList;
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO User(nom, prenom, address, phone) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getLastName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getPhone());
            pstmt.executeUpdate();
            System.out.println("Utilisateur inséré avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage());
        }
    }

    public void removeUser(int userId) {
        String sql = "DELETE FROM User WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE User SET nom = ?, prenom = ?, address = ?, phone = ? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getLastName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getPhone());
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }

        public ObservableList<Loan> getLoans() {
            ObservableList<Loan> loanList = FXCollections.observableArrayList();
            String sql = "SELECT idUser, title, author, state, dateLoan, dateReturnLoan, realDateReturnLoan FROM Loan";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Loan loan = new Loan();
                    loan.setIdUser(rs.getInt("idUser"));
                    loan.setTitle(rs.getString("title"));
                    loan.setAutor(rs.getString("author"));
                    loan.setIsLate(rs.getString("state"));
                    loan.setDateLoan(rs.getDate("dateLoan").toLocalDate());
                    loan.setDateReturnLoan(rs.getDate("dateReturnLoan").toLocalDate());
                    loan.setRealDateReturnLoan(rs.getDate("realDateReturnLoan") != null ? rs.getDate("realDateReturnLoan").toLocalDate() : null);
                    loanList.add(loan);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la sélection des prêts : " + e.getMessage());
            }
            return loanList;
        }

        public void insertLoan(Loan loan) {
            String sql = "INSERT INTO Loan(idUser, title, author, state, dateLoan, dateReturnLoan, realDateReturnLoan) VALUES(?,?,?,?,?,?,?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, loan.getIdUser());
                pstmt.setString(2, loan.getTitle());
                pstmt.setString(3, loan.getAutor());
                pstmt.setObject(4, loan.getIsLate());
                pstmt.setDate(5, java.sql.Date.valueOf(loan.getDateLoan()));
                pstmt.setDate(6, java.sql.Date.valueOf(loan.getDateReturnLoan()));
                pstmt.setDate(7, loan.getRealDateReturnLoan() != null ? java.sql.Date.valueOf(loan.getRealDateReturnLoan()) : null);
                pstmt.executeUpdate();
                System.out.println("Prêt inséré avec succès.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'insertion du prêt : " + e.getMessage());
            }
        }

        public void removeLoan(int loanId) {
            String sql = "DELETE FROM Loan WHERE id = ?";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, loanId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Prêt supprimé avec succès.");
                } else {
                    System.out.println("Aucun prêt trouvé avec l'ID spécifié.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression du prêt : " + e.getMessage());
            }
        }

        public void updateLoan(Loan loan) {
            String sql = "UPDATE Loan SET idUser = ?, title = ?, author = ?, state = ?, dateLoan = ?, dateReturnLoan = ?, realDateReturnLoan = ? WHERE id = ?";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, loan.getIdUser());
                pstmt.setString(2, loan.getTitle());
                pstmt.setString(3, loan.getAutor());
                pstmt.setObject(4, loan.getIsLate());
                pstmt.setDate(5, java.sql.Date.valueOf(loan.getDateLoan()));
                pstmt.setDate(6, java.sql.Date.valueOf(loan.getDateReturnLoan()));
                pstmt.setDate(7, loan.getRealDateReturnLoan() != null ? java.sql.Date.valueOf(loan.getRealDateReturnLoan()) : null);
                pstmt.setInt(8, loan.getIdLoan());
                pstmt.executeUpdate();
                System.out.println("Prêt mis à jour avec succès.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour du prêt : " + e.getMessage());
            }
        }
}


