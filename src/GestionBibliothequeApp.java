
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestionBibliothequeApp extends Application {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // تأكد من تغيير اسم السيرفر وقاعدة البيانات
    private static final String USER = "probdd";  // ضع اسم المستخدم الخاص بك
    private static final String PASSWORD = "probddpass";  // ضع كلمة المرور الخاصة بك

        TextField txtCode = new TextField();
        TextField txtTitle = new TextField();
        TextField txtAuthor = new TextField();
        TextField txtCategory = new TextField();
        TextField txtYear = new TextField();
        TextField txtQuantity = new TextField();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion de la Bibliothèque");
        
        // Boutons principaux
        Button btnManageBooks = new Button("Gérer les livres");
        Button btnManageStudents = new Button("Gérer les étudiants");
        Button btnBorrow = new Button("Emprunter un livre");
        Button btnReturn = new Button("Retourner un livre");
      
        // Gestion des actions des boutons
        btnManageBooks.setOnAction(e -> openManageBooksWindow());
        btnManageStudents.setOnAction(e -> openManageStudentsWindow());
        btnBorrow.setOnAction(e-> openBorrowWindow());
        btnReturn.setOnAction(e -> openReturnWindow());
        
        VBox layout = new VBox(50);
        layout.getChildren().addAll(btnManageBooks, btnManageStudents, btnBorrow, btnReturn);
        layout.setPadding(new Insets(50));
        
        Scene scene = new Scene(layout, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void openManageBooksWindow() {
        Stage bookStage = new Stage();
        bookStage.setTitle("Gestion des livres");
        VBox layout = new VBox(20);
        Button btnAdd = new Button("Ajouter un livre");
        Button btnEdit = new Button("Modifier un livre");
        Button btnDelete = new Button("Supprimer un livre");
        Button btnSearch = new Button("Rechercher un livre");
        
        layout.setAlignment(Pos.CENTER);  // لجعل الأزرار في وسط النافذة
        btnAdd.setOnAction(e -> openAddBookWindow());
        btnEdit.setOnAction(e -> openEditBookWindow());
        btnDelete.setOnAction(e -> openDeleteBookWindow());
        btnSearch.setOnAction(e -> openSearchBookWindow());
        
        layout.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnSearch);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 600, 500);
        bookStage.setScene(scene);
        bookStage.show();
    }

    private void openAddBookWindow() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Ajouter un livre");
    
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
    
        Label lblCode = new Label("Code du livre:");
        TextField txtCode = new TextField();
    
        Label lblTitle = new Label("Titre du livre:");
        TextField txtTitle = new TextField();
    
        Label lblAuthor = new Label("Auteur:");
        TextField txtAuthor = new TextField();
    
        Label lblCategory = new Label("Catégorie:");
        TextField txtCategory = new TextField();
    
        Label lblYear = new Label("Année de publication:");
        TextField txtYear = new TextField();
    
        Label lblQuantity = new Label("Quantité disponible:");
        TextField txtQuantity = new TextField();
    
        Button btnSubmit = new Button("Ajouter");
    
     // فقط عناصر الواجهة هنا
     layout.getChildren().addAll(
        lblCode, txtCode,
        lblTitle, txtTitle,
        lblAuthor, txtAuthor,
        lblCategory, txtCategory,
        lblYear, txtYear,
        lblQuantity, txtQuantity,
        btnSubmit
    );

    // التعامل مع الضغط على الزر
    btnSubmit.setOnAction(e -> {
        String code = txtCode.getText();
        String titre = txtTitle.getText();
        String auteur = txtAuthor.getText();
        String categorie = txtCategory.getText();
        int annee = Integer.parseInt(txtYear.getText());
        int quantite = Integer.parseInt(txtQuantity.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO livres (code_livre, titre, auteur, categorie, annee_publication, quantite_disponible) VALUES (?, ?, ?, ?, ?, ?)";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            pstmt.setString(2, titre);
            pstmt.setString(3, auteur);
            pstmt.setString(4, categorie);
            pstmt.setInt(5, annee);
            pstmt.setInt(6, quantite);
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Livre ajouté avec succès !");
                alert.showAndWait();
                addBookStage.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout !");
            alert.showAndWait();
        }
    });

    Scene scene = new Scene(layout, 650, 700);
    addBookStage.setScene(scene);
    addBookStage.show();

}
        
        
    // 2. تعديل كتاب
    private void openEditBookWindow() {
        

        Stage editBookStage = new Stage();
        editBookStage.setTitle("Modifier un livre");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label lblCode = new Label("Code du livre à modifier:");
        
        Button btnSearch = new Button("Rechercher");
        Button btnEdit = new Button("Modifier");

        btnSearch.setOnAction(e -> {
            String code = txtCode.getText();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM livres WHERE code_livre = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, txtCode.getText());
                ResultSet rs = stmt.executeQuery();
            
                if (rs.next()) {
                    // تعبئة الحقول من قاعدة البيانات
                    txtTitle.setText(rs.getString("titre"));
                    txtAuthor.setText(rs.getString("auteur"));
                    txtCategory.setText(rs.getString("categorie"));
                    txtYear.setText(String.valueOf(rs.getInt("annee_publication")));
                    txtQuantity.setText(String.valueOf(rs.getInt("quantite_disponible")));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Livre non trouvé !");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la recherche !");
                alert.showAndWait();
            }
            
        });

        btnEdit.setOnAction(e -> {
    String code = txtCode.getText();
    String titre = txtTitle.getText();
    String auteur = txtAuthor.getText();
    String categorie = txtCategory.getText();
    String annee = txtYear.getText();
    String quantite = txtQuantity.getText();

    if (code.isEmpty() || titre.isEmpty() || auteur.isEmpty() || categorie.isEmpty() || annee.isEmpty() || quantite.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
    }

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, categorie = ?, annee_publication = ?, quantite_disponible = ? WHERE code_livre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, titre);
        stmt.setString(2, auteur);
        stmt.setString(3, categorie);
        stmt.setInt(4, Integer.parseInt(annee));
        stmt.setInt(5, Integer.parseInt(quantite));
        stmt.setString(6, code);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Livre modifié avec succès !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Aucun livre trouvé avec ce code !");
            alert.showAndWait();
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification !");
        alert.showAndWait();
    } catch (NumberFormatException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Année ou quantité invalide !");
        alert.showAndWait();
    }
});


        layout.getChildren().addAll(lblCode, txtCode, btnSearch, btnEdit);
        Scene scene = new Scene(layout, 650, 500);
        editBookStage.setScene(scene);
        editBookStage.show();
    }
    
     // 3. حذف كتاب
    private void openDeleteBookWindow() {
        Stage deleteBookStage = new Stage();
        deleteBookStage.setTitle("Supprimer un livre");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label lblCode = new Label("Code du livre à supprimer:");
        TextField txtCode = new TextField();
        Button btnDelete = new Button("Supprimer");

        btnDelete.setOnAction(e -> {
            String code = txtCode.getText();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM livres WHERE code_livre = ?";
                var pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, code);
                int rowsDeleted = pstmt.executeUpdate();

                if (rowsDeleted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Livre supprimé avec succès !");
                    alert.showAndWait();
                    deleteBookStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Livre non trouvé !");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression !");
                alert.showAndWait();
            }
        });

        layout.getChildren().addAll(lblCode, txtCode, btnDelete);
        Scene scene = new Scene(layout, 650, 500);
        deleteBookStage.setScene(scene);
        deleteBookStage.show();
    }

    
    // 4. بحث عن كتاب
    private void openSearchBookWindow() {
        Stage searchBookStage = new Stage();
        searchBookStage.setTitle("Rechercher un livre");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label lblSearch = new Label("Rechercher par titre, auteur, ou catégorie:");
        TextField txtSearch = new TextField();
        Button btnSearch = new Button("Rechercher");

        btnSearch.setOnAction(e -> {
            String searchText = txtSearch.getText();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM livres WHERE titre LIKE ? OR auteur LIKE ? OR categorie LIKE ?";
                var pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchText + "%");
                pstmt.setString(2, "%" + searchText + "%");
                pstmt.setString(3, "%" + searchText + "%");
                ResultSet rs = pstmt.executeQuery();

                // التعامل مع النتائج هنا (مثلا عرض النتائج في ListView أو في نافذة جديدة)
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(lblSearch, txtSearch, btnSearch);
        Scene scene = new Scene(layout, 650, 500);
        searchBookStage.setScene(scene);
        searchBookStage.show();
    }




    // 5. إدارة الطلاب
    private void openManageStudentsWindow() {
        Stage studentStage = new Stage();
        studentStage.setTitle("Gestion des étudiants");
        VBox layout = new VBox(20);
        Button btnAdd = new Button("Ajouter un étudiant");
        Button btnEdit = new Button("Modifier un étudiant");
        Button btnDelete = new Button("Supprimer un étudiant");
        Button btnList = new Button("Afficher la liste des étudiants");
        layout.setAlignment(Pos.CENTER);  
        btnAdd.setOnAction(e -> openAddStudentWindow());
        btnEdit.setOnAction(e -> openEditStudentWindow());
        btnDelete.setOnAction(e -> openDeleteStudentWindow());
        btnList.setOnAction(e -> openListStudentsWindow());
        
        layout.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnList);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 600, 500);
        studentStage.setScene(scene);
        studentStage.show();
    }
    
    
   
    private void openAddStudentWindow() {
        Stage addStudentStage = new Stage();
        addStudentStage.setTitle("Ajouter un étudiant");
    
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
    
        Label lblID = new Label("Numéro de l'étudiant:");
        TextField txtID = new TextField();
    
        Label lblNom = new Label("Nom:");
        TextField txtNom = new TextField();
    
        Label lblPrenom = new Label("Prénom:");
        TextField txtPrenom = new TextField();
    
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
    
        Label lblTel = new Label("Téléphone:");
        TextField txtTel = new TextField();
    
        Button btnSubmit = new Button("Ajouter");
    
        layout.getChildren().addAll(
        lblID, txtID,
        lblNom, txtNom,
        lblPrenom, txtPrenom,
        lblEmail, txtEmail,
        lblTel, txtTel,
        btnSubmit
    );

    // التعامل مع الضغط على الزر
    btnSubmit.setOnAction(e -> {
        String id = txtID.getText();
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO etudiants (num_etudiant, nom, prenom, email, telephone) VALUES (?, ?, ?, ?, ?)";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setString(4, email);
            pstmt.setString(5, tel);
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Étudiant ajouté avec succès !");
                alert.showAndWait();
                addStudentStage.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout !");
            alert.showAndWait();
        }
    });

    Scene scene = new Scene(layout, 650, 650);
    addStudentStage.setScene(scene);
    addStudentStage.show();
}

    private void openEditStudentWindow() {
    Stage stage = new Stage();
    stage.setTitle("Modifier Étudiant");

    // إنشاء الحقول
    TextField txtID = new TextField();
    TextField txtNom = new TextField();
    TextField txtPrenom = new TextField();
    TextField txtEmail = new TextField();
    TextField txtTelephone = new TextField();

    // زر التعديل
    Button btnSubmit = new Button("Modifier");

    // الشبكة
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20));
    grid.setVgap(10);
    grid.setHgap(10);

    // إضافة العناصر إلى الواجهة
    grid.add(new Label("Numéro Étudiant :"), 0, 0);
    grid.add(txtID, 1, 0);
    grid.add(new Label("Nom :"), 0, 1);
    grid.add(txtNom, 1, 1);
    grid.add(new Label("Prénom :"), 0, 2);
    grid.add(txtPrenom, 1, 2);
    grid.add(new Label("Email :"), 0, 3);
    grid.add(txtEmail, 1, 3);
    grid.add(new Label("Téléphone :"), 0, 4);
    grid.add(txtTelephone, 1, 4);
    grid.add(btnSubmit, 1, 5);

    // زر التعديل - منطق القاعدة
    btnSubmit.setOnAction(e -> {
        String id = txtID.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM etudiants WHERE num_etudiant = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // تعديل البيانات
                String nom = txtNom.getText();
                String prenom = txtPrenom.getText();
                String email = txtEmail.getText();
                String tel = txtTelephone.getText();

                String updateSql = "UPDATE etudiants SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE num_etudiant = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, nom);
                updateStmt.setString(2, prenom);
                updateStmt.setString(3, email);
                updateStmt.setString(4, tel);
                updateStmt.setString(5, id);
                updateStmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Étudiant modifié avec succès !");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Étudiant non trouvé !");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification !");
            alert.showAndWait();
        }
    });

    // إعداد المشهد
    Scene scene = new Scene(grid, 400, 300);
    stage.setScene(scene);
    stage.show();
}

    
    private void openDeleteStudentWindow() {
        Stage deleteStudentStage = new Stage();
        deleteStudentStage.setTitle("Supprimer un étudiant");

        VBox layout = new VBox(20);
        Label lblID = new Label("ID de l'étudiant à supprimer:");
        TextField txtID = new TextField();
        Button btnDelete = new Button("Supprimer");

        btnDelete.setOnAction(e -> {
            String id = txtID.getText();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM etudiants WHERE num_etudiant = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Étudiant supprimé avec succès !");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Étudiant non trouvé !");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression !");
                alert.showAndWait();
            }
        });

        layout.getChildren().addAll(lblID, txtID, btnDelete);
        Scene scene = new Scene(layout, 600, 500);
        deleteStudentStage.setScene(scene);
        deleteStudentStage.show();
    }

    // 5. عرض قائمة الطلاب
    private void openListStudentsWindow() {
    Stage listStudentsStage = new Stage();
    listStudentsStage.setTitle("Liste des étudiants");

    VBox layout = new VBox(20);
    ListView<String> studentsListView = new ListView<>();
    
    // إنشاء ObservableList لاحتواء بيانات الطلاب
    ObservableList<String> studentsList = FXCollections.observableArrayList();

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT * FROM etudiants";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        // استرجاع بيانات الطلاب من قاعدة البيانات وإضافتها إلى ObservableList
        while (rs.next()) {
            String studentInfo = "ID: " + rs.getString("num_etudiant") + 
                                 ", Nom: " + rs.getString("nom") + 
                                 ", Prénom: " + rs.getString("prenom") + 
                                 ", Email: " + rs.getString("email") + 
                                 ", Téléphone: " + rs.getString("telephone");
            studentsList.add(studentInfo);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la récupération des étudiants !");
        alert.showAndWait();
    }

    // إضافة الطلاب إلى ListView
    studentsListView.setItems(studentsList);

    layout.getChildren().add(studentsListView);

    Scene scene = new Scene(layout, 700, 600);
    listStudentsStage.setScene(scene);
    listStudentsStage.show();
}

    





    // 6. Emprunter un livre
    
    private void openBorrowWindow() {
    Stage stage = new Stage();
    stage.setTitle("Nouvel Emprunt");

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(15));

    // Étudiant
    Label lblStudent = new Label("Étudiant :");
    ComboBox<String> cmbStudents = new ComboBox<>();

    // Livre
    Label lblBook = new Label("Livre :");
    ComboBox<String> cmbBooks = new ComboBox<>();

    // Date d'emprunt
    Label lblDate = new Label("Date d'emprunt :");
    DatePicker datePicker = new DatePicker(LocalDate.now());

    // Durée
    Label lblDuree = new Label("Durée (en jours) :");
    TextField txtDuree = new TextField();

    // Bouton
    Button btnSubmit = new Button("Valider l'emprunt");
    btnSubmit.setOnAction(e -> {
        String studentId = cmbStudents.getValue();
        String bookId = cmbBooks.getValue();
        LocalDate date = datePicker.getValue();
        int duree = Integer.parseInt(txtDuree.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkBookSql = "SELECT quantite_disponible FROM livres WHERE code_livre = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkBookSql);
            checkStmt.setString(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("quantite_disponible") > 0) {
                String sql = "INSERT INTO emprunts (num_etudiant, code_livre, date_emprunt, duree_jours) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, studentId);
                pstmt.setString(2, bookId);
                pstmt.setDate(3, java.sql.Date.valueOf(date));
                pstmt.setInt(4, duree);
                pstmt.executeUpdate();

                // Mettre à jour la quantité
                String updateSql = "UPDATE livres SET quantite_disponible = quantite_disponible - 1 WHERE code_livre = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, bookId);
                updateStmt.executeUpdate();

                new Alert(Alert.AlertType.INFORMATION, "Emprunt enregistré !").showAndWait();
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Le livre n'est pas disponible.").showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement.").showAndWait();
        }
    });

    // Remplir ComboBox des étudiants et livres
    try (Connection conn = DatabaseConnection.getConnection()) {
        Statement stmt = conn.createStatement();

        ResultSet rsStudents = stmt.executeQuery("SELECT num_etudiant FROM etudiants");
        while (rsStudents.next()) {
            cmbStudents.getItems().add(rsStudents.getString("num_etudiant"));
        }

        ResultSet rsBooks = stmt.executeQuery("SELECT code_livre FROM livres WHERE quantite_disponible > 0");
        while (rsBooks.next()) {
            cmbBooks.getItems().add(rsBooks.getString("code_livre"));
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    layout.getChildren().addAll(
        lblStudent, cmbStudents,
        lblBook, cmbBooks,
        lblDate, datePicker,
        lblDuree, txtDuree,
        btnSubmit
    );

    Scene scene = new Scene(layout, 350, 400);
    stage.setScene(scene);
    stage.show();
}

    
    private void openReturnWindow() {
        Stage stage = new Stage();
        stage.setTitle("Retour de Livre");
    
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
    
        // Étudiant
        Label lblStudent = new Label("Étudiant :");
        ComboBox<String> cmbStudents = new ComboBox<>();
    
        // Livre
        Label lblBook = new Label("Livre :");
        ComboBox<String> cmbBooks = new ComboBox<>();
    
        // Bouton
        Button btnReturn = new Button("Valider le retour");
    
        btnReturn.setOnAction(e -> {
            String studentId = cmbStudents.getValue();
            String bookId = cmbBooks.getValue();
    
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Vérifier si l'emprunt existe
                String checkSql = "SELECT * FROM emprunts WHERE num_etudiant = ? AND code_livre = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, studentId);
                checkStmt.setString(2, bookId);
                ResultSet rs = checkStmt.executeQuery();
    
                if (rs.next()) {
                    // Supprimer l'emprunt
                    String deleteSql = "DELETE FROM emprunts WHERE num_etudiant = ? AND code_livre = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                    deleteStmt.setString(1, studentId);
                    deleteStmt.setString(2, bookId);
                    deleteStmt.executeUpdate();
    
                    // Mettre à jour la quantité
                    String updateSql = "UPDATE livres SET quantite_disponible = quantite_disponible + 1 WHERE code_livre = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, bookId);
                    updateStmt.executeUpdate();
    
                    new Alert(Alert.AlertType.INFORMATION, "Retour enregistré avec succès !").showAndWait();
                    stage.close();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Aucun emprunt trouvé pour cette combinaison.").showAndWait();
                }
    
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erreur lors du retour.").showAndWait();
            }
        });
    
        // Charger les étudiants et les livres empruntés
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rsStudents = stmt.executeQuery("SELECT DISTINCT num_etudiant FROM emprunts");
            while (rsStudents.next()) {
                cmbStudents.getItems().add(rsStudents.getString("num_etudiant"));
            }
    
            cmbStudents.setOnAction(ev -> {
                cmbBooks.getItems().clear();
                try {
                    String selectedStudent = cmbStudents.getValue();
                    if (selectedStudent != null) {
                        PreparedStatement pstmt = conn.prepareStatement(
                            "SELECT code_livre FROM emprunts WHERE num_etudiant = ?");
                        pstmt.setString(1, selectedStudent);
                        ResultSet rsBooks = pstmt.executeQuery();
                        while (rsBooks.next()) {
                            cmbBooks.getItems().add(rsBooks.getString("code_livre"));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
        layout.getChildren().addAll(lblStudent, cmbStudents, lblBook, cmbBooks, btnReturn);
    
        Scene scene = new Scene(layout, 350, 300);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("La connexion fonctionne !");
  
        }
    }
}