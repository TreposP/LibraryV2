module Projet.Biblio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;

    exports Projet.Biblio;
    opens Projet.Biblio to javafx.fxml;

    exports Projet.Biblio.Book;
    opens Projet.Biblio.Book to javafx.fxml;

    exports Projet.Biblio.User;
    opens Projet.Biblio.User to javafx.fxml;

    exports Projet.Biblio.Util;
    opens Projet.Biblio.Util to javafx.fxml;

    exports Projet.Biblio.StartPage;
    opens Projet.Biblio.StartPage to javafx.fxml;

    exports Projet.Biblio.Screen;
    opens Projet.Biblio.Screen to javafx.fxml;
}