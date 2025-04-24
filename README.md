# 📚 Java Library Management System with Oracle Database

This is a desktop application developed using **JavaFX** that allows users to manage a university library. It connects to an **Oracle Database** using JDBC, providing a modern interface for managing books, students, borrowings, and returns.

---

## 🚀 Features

### 📘 Book Management
- Add a new book
- Edit existing book details
- Delete a book
- Search books by title, author, or category

### 👨‍🎓 Student Management
- Add a new student
- Edit student information
- Delete a student
- Display all students

### 📥 Borrow & Return
- Record a new borrowing
- Verify book availability before borrowing
- Record the return of a book and update quantity

### ⏰ Overdue Notifications
- Display a list of borrowed books that are overdue
- Show the student's name and the book title

---

## 🧰 Technologies Used

- Java 22 (JavaFX)
- Oracle 11g XE
- JDBC (Oracle ojdbc8.jar driver)
- SceneBuilder (for designing the GUI)
- CSS (for styling UI components)

---

## 📂 Project Structure

```bash
📁 src/
 ├── GestionBibliothequeApp.java     # Main Application File
 ├── DatabaseConnection.java         # Handles DB Connection
 ├── (other modules: students, books, borrowings...)
📁 lib/
 └── ojdbc8.jar                      # Oracle JDBC driver
📁 bin/                              # Compiled .class files
📄 style.css                         # CSS styling for JavaFX
📄 DataBase.sql                      # SQL script to create tables
```

---

## 🏁 How to Run

1. **Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git
cd YOUR_REPO
```

2. **Ensure you have Java JDK and Oracle DB installed**

3. **Add ojdbc8.jar to your classpath**

4. **Compile the project**
```bash
javac -cp lib/ojdbc8.jar -d bin src/*.java
```

5. **Run the application**
```bash
java -cp lib/ojdbc8.jar;bin GestionBibliothequeApp
```

> Use `:` instead of `;` on Mac/Linux

---

## 📌 Notes

- Make sure Oracle DB is running
- Update database credentials in `DatabaseConnection.java`
- Run `DataBase.sql` script to create necessary tables before use

---

## 📄 License

This project is for educational purposes and free to use and modify.

---

## 🙌 Author

Developed by **[AmEnA3]** as part of a database management project.
Feel free to fork and contribute!

