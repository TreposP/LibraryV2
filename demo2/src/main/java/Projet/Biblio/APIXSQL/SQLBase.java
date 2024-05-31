package Projet.Biblio.APIXSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class SQLBase {

    /**
     * Méthode privée pour établir la connexion à la base de données SQLite.
     *
     * @return Connexion établie
     */
    private Connection connect() {
        // Chemin de la base de données SQLite
        String url = "jdbc:sqlite:/Users/paulinetrepos/Desktop/LibraryV2-main/demo2/src/main/resources/Projet/Biblio/APIXSQL/Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connexion à SQLite établie avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données SQLite : " + e.getMessage());
        }
        return conn;
    }

    /**
     * Insère un nouvel utilisateur dans la base de données.
     */
    public void insertUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom de l'utilisateur : ");
        String nom = scanner.nextLine().toUpperCase();
        System.out.println("Entrez le prénom de l'utilisateur : ");
        String prenom = scanner.nextLine().toUpperCase();
        System.out.println("Entrez l'adresse de l'utilisateur : ");
        String address = scanner.nextLine().toUpperCase();
        System.out.println("Entrez le numéro de téléphone de l'utilisateur : ");
        String phone = scanner.nextLine().toUpperCase();

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

    /**
     * Supprime un utilisateur de la base de données.
     */
    public void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'ID de l'utilisateur à supprimer : ");
        int userId = scanner.nextInt();

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

    /**
     * Insère un nouveau prêt dans la base de données.
     */
    public void insertLoan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le titre du livre : ");
        String titre = scanner.nextLine().toUpperCase();
        System.out.println("Entrez l'auteur du livre : ");
        String auteur = scanner.nextLine().toUpperCase();
        System.out.println("Entrez l'ID de l'utilisateur : ");
        int idUser = scanner.nextInt();
        scanner.nextLine(); // Pour consommer le retour à la ligne

        int currentLoans = countCurrentLoans(idUser);
        if (currentLoans >= 5) {
            System.out.println("L'utilisateur a déjà emprunté 4 livres. Impossible d'effectuer un nouveau prêt.");
            return;
        }

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

    /**
 * Compte le nombre de livres actuellement empruntés par un utilisateur.
 *
 * @param userId ID de l'utilisateur
 * @return Nombre de livres actuellement empruntés par l'utilisateur
 */
private int countCurrentLoans(int userId) {
    String sql = "SELECT COUNT(*) AS count FROM Loan WHERE idUser = ? AND realDateReturnLoan IS NULL";

    try (Connection conn = this.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, userId);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("count");
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors du comptage des livres empruntés : " + e.getMessage());
    }
    return 0;
}

    /**
     * Permet de retourner un livre emprunté.
     */
    public void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'ID de l'utilisateur : ");
        int userId = scanner.nextInt();

        // Afficher les livres que l'utilisateur est en train d'emprunter
        listCurrentLoans(userId);

        System.out.println("Entrez le numéro du livre à retourner : ");
        int loanId = scanner.nextInt();
        LocalDate returnDate = LocalDate.now();

        String sql = "UPDATE Loan SET realDateReturnLoan = ? WHERE idUser = ? AND idLoan = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, returnDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setInt(2, userId);
            pstmt.setInt(3, loanId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Livre retourné avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec l'ID de l'utilisateur et le numéro du livre spécifiés.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du retour du livre : " + e.getMessage());
        }
    }

    public int Laterorno(String datestr1, String datestr2){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date1 = LocalDate.parse(datestr1, formatter);
            LocalDate date2 = LocalDate.parse(datestr2, formatter);
            return date1.compareTo(date2);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de format de date: " + e.getMessage());
            return 0;

        }
    }

    public void lateLoan (){
        String sql = "SELECT * FROM Loan WHERE RealDateReturnLoan IS NOT NULL";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.print("\n liste de livre rendu en retard: \n");
            while(rs.next()){
                LocalDate dateLoan = LocalDate.parse(rs.getString("dateLoan"));
                LocalDate dateReturnLoan = LocalDate.parse(rs.getString("dateReturnLoan"));
                LocalDate realDateReturnLoan = LocalDate.parse(rs.getString("RealDateReturnLoan"));

                if (Laterorno(rs.getString("dateReturnLoan"), rs.getString("RealDateReturnLoan")) < 0) {
                    System.out.print(rs.getInt("idLoan") + "\t");
                    System.out.print(rs.getString("titre") + "\t");
                    System.out.print(rs.getString("auteur") + "\t");
                    System.out.print(rs.getInt("idUser") + "\t");
                    System.out.print(dateLoan.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\t");
                    System.out.print(dateReturnLoan.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\t");
                    System.out.println(realDateReturnLoan.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n");
                }
            }
        }  catch(SQLException e){
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }

        sql = "SELECT * FROM Loan WHERE RealDateReturnLoan IS NULL";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.print("\n liste de livre pas encore rendu et en retard: \n");
            while(rs.next()){

                if(Laterorno(rs.getString("dateReturnLoan"), (LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) > 0){
                    System.out.print(rs.getInt("idLoan") + "\t");
                    System.out.print(rs.getString("titre") + "\t");
                    System.out.print(rs.getString("auteur") + "\t");
                    System.out.print(rs.getInt("idUser") + "\t");
                    System.out.print(rs.getString("dateLoan") + "\t");
                    System.out.print(rs.getString("dateReturnLoan") + "\t"+ "\n");
                }
            }
        }  catch(SQLException e){
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    /**
     * Méthode pour lister les emprunts actuels d'un utilisateur.
     *
     * @param userId ID de l'utilisateur
     */
    private void listCurrentLoans(int userId) {
        String sql = "SELECT * FROM Loan WHERE idUser = ? AND realDateReturnLoan IS NULL";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Livres actuellement empruntés par l'utilisateur : ");
            while (rs.next()) {
                int loanId = rs.getInt("idLoan");
                String bookTitle = rs.getString("titre");
                String bookAuthor = rs.getString("auteur");

                System.out.println(loanId + " - " + bookTitle + " by " + bookAuthor);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres empruntés : " + e.getMessage());
        }
    }

    /**
     * Sélectionne tous les utilisateurs enregistrés dans la base de données.
     */
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

    /**
     * Sélectionne tous les prêts enregistrés dans la base de données.
     */
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

    /**
     * Recherche un prêt par titre et auteur dans la base de données.
     */
    public void searchLoanByTitleAndAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le titre du livre : ");
        String titre = scanner.nextLine().toUpperCase();
        System.out.println("Entrez l'auteur du livre : ");
        String auteur = scanner.nextLine().toUpperCase();

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

    /**
     * Sélectionne tous les prêts pour un utilisateur spécifique.
     *
     * @param userId ID de l'utilisateur
     */
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

    /**
     * Vérifie si un livre est déjà emprunté.
     *
     * @param titre  Titre du livre
     * @param auteur Auteur du livre
     * @return true si le livre est déjà emprunté, false sinon
     */
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
