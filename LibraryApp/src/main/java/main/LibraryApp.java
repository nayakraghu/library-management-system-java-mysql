package main;

import java.util.Scanner;
import dao.BookDAO;
import dao.UserDAO;
import model.Book;

public class LibraryApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookDAO bookDao = new BookDAO();
        UserDAO userDao = new UserDAO();

        System.out.println("===== Admin Login =====");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (!userDao.login(username, password)) {
            System.out.println("Invalid username or password");
            sc.close();
            return;
        }

        System.out.println("Login Successful");

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1.  Add Book");
            System.out.println("2.  View All Books");
            System.out.println("3.  Search Book by ID");
            System.out.println("4.  Search Book by Genre");
            System.out.println("5.  Search Book by Author");
            System.out.println("6.  Update Book Quantity");
            System.out.println("7.  Delete Book");
            System.out.println("8.  Count Total Books");
            System.out.println("9.  Show Most Expensive Book");
            System.out.println("10. Sort Books by Price");
            System.out.println("11. Availability Report");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();

                    if (bookDao.titleExists(title)) {
                        System.out.println("Book with this title already exists!");
                        break;
                    }

                    System.out.print("Enter author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter genre: ");
                    String genre = sc.nextLine();

                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();

                    if (price <= 0) {
                        System.out.println("Price must be greater than 0.");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    if (qty < 0) {
                        System.out.println("Quantity cannot be negative.");
                        break;
                    }

                    System.out.print("Enter added date (yyyy-mm-dd): ");
                    String date = sc.nextLine();

                    Book book = new Book(title, author, genre, price, qty, date);
                    if (bookDao.addBook(book)) {
                        System.out.println("Book Added Successfully");
                    } else {
                        System.out.println("Failed to Add Book");
                    }
                    break;

                case 2:
                    bookDao.viewAllBooks();
                    break;

                case 3:
                    System.out.print("Enter book ID: ");
                    bookDao.searchById(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter genre: ");
                    bookDao.searchByGenre(sc.nextLine());
                    break;

                case 5:
                    System.out.print("Enter author name: ");
                    bookDao.searchByAuthor(sc.nextLine());
                    break;

                case 6:
                    System.out.print("Enter book ID: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter new quantity: ");
                    int newQty = sc.nextInt();
                    if (newQty < 0) {
                        System.out.println("Quantity cannot be negative.");
                        break;
                    }
                    if (bookDao.updateQuantity(uid, newQty)) {
                        System.out.println("Quantity Updated Successfully");
                    } else {
                        System.out.println("Update Failed");
                    }
                    break;

                case 7:
                    System.out.print("Enter book ID: ");
                    int did = sc.nextInt();
                    if (bookDao.deleteBook(did)) {
                        System.out.println("Book Deleted Successfully");
                    } else {
                        System.out.println("Delete Failed");
                    }
                    break;

                case 8:
                    bookDao.countBooks();
                    break;

                case 9:
                    bookDao.mostExpensiveBook();
                    break;

                case 10:
                    bookDao.sortByPrice();
                    break;

                case 11:
                    bookDao.availabilityReport();
                    break;

                case 12:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
