package com.example.mvc;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

@Repository
public class DocumentDAO {
    private static final String DB_URL =  "jdbc:mysql://localhost:3306/myspring";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2023";

    @PostConstruct
    public void createTable() throws ClassNotFoundException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS documents (id INT AUTO_INCREMENT PRIMARY KEY, content VARCHAR(255))";
        Class.forName("com.mysql.jdbc.Driver");
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            stmt.execute(createTableQuery);
        }
        catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }






    public List<Document> getAllDocuments() throws ClassNotFoundException {
        List<Document> documents = new ArrayList<>();
        Class.forName("com.mysql.jdbc.Driver");
        try (

             Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM documents")) {

            while (rs.next()) {
                Document document = new Document();
                document.setId(rs.getInt("id"));
                document.setContent(rs.getString("content"));
                documents.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }

        return documents;
    }

    public Document getDocument(int id) throws ClassNotFoundException {
        Document document = null;
        Class.forName("com.mysql.jdbc.Driver");
        try (
             Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM documents WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                document = new Document();
                document.setId(rs.getInt("id"));
                document.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }

        return document;
    }

    public Integer createDocument(Document document) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try (
             Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO documents (id, content) VALUES (?, ?)")) {;
            stmt.setString(2, document.getContent());
            stmt.setInt(1, document.getId());
            stmt.executeUpdate();
            return document.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
        return document.getId();
    }

    public boolean updateDocument(Integer id,Document document) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try (
             Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE documents SET content = ? WHERE id = ?")) {

            stmt.setString(1, document.getContent());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
        return false;
    }

    public boolean deleteDocument(int id) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try (
             Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM documents WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
        return false;
    }
}
