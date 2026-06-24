package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Book;
import util.db;

public class BookDAO {

    public boolean addBook(Book b) {
        String sql = "INSERT INTO books(title, author, genre, price, quantity, added_date) VALUES(?,?,?,?,?,?)";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getGenre());
            ps.setDouble(4, b.getPrice());
            ps.setInt(5, b.getQuantity());
            ps.setString(6, b.getAddedDate());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean titleExists(String title) {
        String sql = "SELECT * FROM books WHERE title=?";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewAllBooks() {
        String sql = "SELECT * FROM books";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                printBook(rs);
            }
            if (!found) System.out.println("No books found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchById(int id) {
        String sql = "SELECT * FROM books WHERE id=?";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                printBook(rs);
            } else {
                System.out.println("Book not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByGenre(String genre) {
        String sql = "SELECT * FROM books WHERE genre=?";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, genre);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                printBook(rs);
            }
            if (!found) System.out.println("No books found in this genre.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author=?";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                printBook(rs);
            }
            if (!found) System.out.println("No books found by this author.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateQuantity(int id, int quantity) {
        String sql = "UPDATE books SET quantity=? WHERE id=?";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void countBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) System.out.println("Total Books: " + rs.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostExpensiveBook() {
        String sql = "SELECT * FROM books ORDER BY price DESC LIMIT 1";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Most Expensive Book:");
                printBook(rs);
            } else {
                System.out.println("No books found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sortByPrice() {
        String sql = "SELECT * FROM books ORDER BY price DESC";
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                printBook(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availabilityReport() {
        String availSql  = "SELECT COUNT(*) FROM books WHERE quantity > 0";
        String outSql    = "SELECT COUNT(*) FROM books WHERE quantity = 0";
        String totalSql  = "SELECT SUM(quantity) FROM books";
        try {
            Connection con = db.getConnection();
            ResultSet rs1 = con.prepareStatement(availSql).executeQuery();
            ResultSet rs2 = con.prepareStatement(outSql).executeQuery();
            ResultSet rs3 = con.prepareStatement(totalSql).executeQuery();
            if (rs1.next()) System.out.println("Available Titles : " + rs1.getInt(1));
            if (rs2.next()) System.out.println("Out of Stock     : " + rs2.getInt(1));
            if (rs3.next()) System.out.println("Total Copies     : " + rs3.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printBook(ResultSet rs) throws Exception {
        System.out.println(
            rs.getInt("id") + " | " +
            rs.getString("title") + " | " +
            rs.getString("author") + " | " +
            rs.getString("genre") + " | " +
            "Rs." + rs.getDouble("price") + " | " +
            "Qty:" + rs.getInt("quantity") + " | " +
            rs.getString("added_date")
        );
    }
}
