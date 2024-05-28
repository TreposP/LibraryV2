package Projet.Biblio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Projet.Biblio.User.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
}
