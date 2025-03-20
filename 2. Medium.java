import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/your_database";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n1. Insert Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Product ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = scanner.nextInt();

                        PreparedStatement ps1 = conn.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?)");
                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setDouble(3, price);
                        ps1.setInt(4, qty);
                        ps1.executeUpdate();
                        System.out.println("Product added successfully.");
                        break;

                    case 2:
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                        while (rs.next()) {
                            System.out.println(rs.getInt("ProductID") + " " +
                                    rs.getString("ProductName") + " " +
                                    rs.getDouble("Price") + " " +
                                    rs.getInt("Quantity"));
                        }
                        break;

                    case 3:
                        System.out.print("Enter Product ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new Price: ");
                        double newPrice = scanner.nextDouble();
                        System.out.print("Enter new Quantity: ");
                        int newQty = scanner.nextInt();

                        PreparedStatement ps2 = conn.prepareStatement("UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?");
                        ps2.setString(1, newName);
                        ps2.setDouble(2, newPrice);
                        ps2.setInt(3, newQty);
                        ps2.setInt(4, updateId);
                        ps2.executeUpdate();
                        System.out.println("Product updated successfully.");
                        break;

                    case 4:
                        System.out.print("Enter Product ID to delete: ");
                        int deleteId = scanner.nextInt();

                        PreparedStatement ps3 = conn.prepareStatement("DELETE FROM Product WHERE ProductID=?");
                        ps3.setInt(1, deleteId);
                        ps3.executeUpdate();
                        System.out.println("Product deleted successfully.");
                        break;
                }
            } while (choice != 5);

            conn.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
