import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //URL de connexion à la base de données Oracle
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
     // Nom d'utilisateur pour accéder à la base de données
    private static final String USER = "probdd"; 
      // Mot de passe associé à l'utilisateur
    private static final String PASSWORD = "probddpass";  
     //  Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() {
        Connection conn = null;
        try {
              // Charger le driver JDBC d’Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
             // Établir la connexion à la base de données
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données !");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion !");
        }
        return conn;
    }
}