import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // تأكد من تغيير اسم السيرفر وقاعدة البيانات
    private static final String USER = "probdd";  // ضع اسم المستخدم الخاص بك
    private static final String PASSWORD = "probddpass";  // ضع كلمة المرور الخاصة بك

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données !");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion !");
        }
        return conn;
    }
}