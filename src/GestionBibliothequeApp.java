
import java.sql.Connection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestionBibliothequeApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion de la Bibliothèque");
        
        // Boutons principaux
        Button btnManageBooks = new Button("Gérer les livres");
        Button btnManageStudents = new Button("Gérer les étudiants");
        Button btnBorrow = new Button("Emprunter un livre");
        Button btnReturn = new Button("Retourner un livre");
        
        // Gestion des actions des boutons
        btnManageBooks.setOnAction(_ -> openManageBooksWindow());
        btnManageStudents.setOnAction(_ -> openManageStudentsWindow());
        btnBorrow.setOnAction(_ -> openBorrowWindow());
        btnReturn.setOnAction(_ -> openReturnWindow());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnManageBooks, btnManageStudents, btnBorrow, btnReturn);
        layout.setPadding(new Insets(10));
        
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void openManageBooksWindow() {
        Stage bookStage = new Stage();
        bookStage.setTitle("Gestion des livres");
        VBox layout = new VBox(10);
        Button btnAdd = new Button("Ajouter un livre");
        Button btnEdit = new Button("Modifier un livre");
        Button btnDelete = new Button("Supprimer un livre");
        Button btnSearch = new Button("Rechercher un livre");
        
        btnAdd.setOnAction(e -> openAddBookWindow());
        btnEdit.setOnAction(e -> openEditBookWindow());
        btnDelete.setOnAction(e -> openDeleteBookWindow());
        btnSearch.setOnAction(e -> openSearchBookWindow());
        
        layout.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnSearch);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 300, 200);
        bookStage.setScene(scene);
        bookStage.show();
    }

    private void openAddBookWindow() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Ajouter un livre");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        Label lblTitle = new Label("Titre du livre:");
        TextField txtTitle = new TextField();
        Label lblAuthor = new Label("Auteur:");
        TextField txtAuthor = new TextField();
        Button btnSubmit = new Button("Ajouter");
        layout.getChildren().addAll(lblTitle, txtTitle, lblAuthor, txtAuthor, btnSubmit);
        Scene scene = new Scene(layout, 300, 200);
        addBookStage.setScene(scene);
        addBookStage.show();
    }
    
    private void openEditBookWindow() {
        Stage editBookStage = new Stage();
        editBookStage.setTitle("Modifier un livre");
        editBookStage.show();
    }
    
    private void openDeleteBookWindow() {
        Stage deleteBookStage = new Stage();
        deleteBookStage.setTitle("Supprimer un livre");
        deleteBookStage.show();
    }
    
    private void openSearchBookWindow() {
        Stage searchBookStage = new Stage();
        searchBookStage.setTitle("Rechercher un livre");
        searchBookStage.show();
    }
    
    private void openManageStudentsWindow() {
        Stage studentStage = new Stage();
        studentStage.setTitle("Gestion des étudiants");
        VBox layout = new VBox(10);
        Button btnAdd = new Button("Ajouter un étudiant");
        Button btnEdit = new Button("Modifier un étudiant");
        Button btnDelete = new Button("Supprimer un étudiant");
        Button btnList = new Button("Afficher la liste des étudiants");
        
        btnAdd.setOnAction(e -> openAddStudentWindow());
        btnEdit.setOnAction(e -> openEditStudentWindow());
        btnDelete.setOnAction(e -> openDeleteStudentWindow());
        
        layout.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnList);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 300, 200);
        studentStage.setScene(scene);
        studentStage.show();
    }
    
    
   
    private void openAddStudentWindow() {
        Stage addStudentStage = new Stage();
        addStudentStage.setTitle("Ajouter un étudiant");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        Label lblName = new Label("Nom de l'étudiant:");
        TextField txtName = new TextField();
        Label lblID = new Label("ID de l'étudiant:");
        TextField txtID = new TextField();
        Button btnSubmit = new Button("Ajouter");
        layout.getChildren().addAll(lblName, txtName, lblID, txtID, btnSubmit);
        Scene scene = new Scene(layout, 300, 200);
        addStudentStage.setScene(scene);
        addStudentStage.show();
    }
    
    private void openEditStudentWindow() {
        Stage editStudentStage = new Stage();
        editStudentStage.setTitle("Modifier un étudiant");
        editStudentStage.show();
    }
    
    private void openDeleteStudentWindow() {
        Stage deleteStudentStage = new Stage();
        deleteStudentStage.setTitle("Supprimer un étudiant");
        deleteStudentStage.show();
    }
    
    
    private void openBorrowWindow() {
        Stage borrowStage = new Stage();
        borrowStage.setTitle("Emprunter un livre");
        
        Label lblBook = new Label("Sélectionner un livre:");
        TextField txtBook = new TextField();
        Label lblStudent = new Label("Sélectionner un étudiant:");
        TextField txtStudent = new TextField();
        Label lblDate = new Label("Date d'emprunt:");
        DatePicker datePicker = new DatePicker();
        Label lblDuration = new Label("Durée (jours):");
        TextField txtDuration = new TextField();
        Button btnSubmit = new Button("Confirmer");
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(lblBook, txtBook, lblStudent, txtStudent, lblDate, datePicker, lblDuration, txtDuration, btnSubmit);
        layout.setPadding(new Insets(10));
        
        Scene scene = new Scene(layout, 300, 300);
        borrowStage.setScene(scene);
        borrowStage.show();
    }
    
    private void openReturnWindow() {
        Stage returnStage = new Stage();
        returnStage.setTitle("Retourner un livre");
        
        Label lblBook = new Label("Sélectionner un livre à retourner:");
        TextField txtBook = new TextField();
        Label lblStatus = new Label("État du livre après retour:");
        TextField txtStatus = new TextField();
        Button btnSubmit = new Button("Mettre à jour");
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(lblBook, txtBook, lblStatus, txtStatus, btnSubmit);
        layout.setPadding(new Insets(10));
        
        Scene scene = new Scene(layout, 300, 200);
        returnStage.setScene(scene);
        returnStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("La connexion fonctionne !");
  
        }
    }
}
