import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    static class Student {
        private int studentID;
        private String name;
        private String department;
        private int marks;

        public Student(int studentID, String name, String department, int marks) {
            this.studentID = studentID;
            this.name = name;
            this.department = department;
            this.marks = marks;
        }

        public int getStudentID() { return studentID; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public int getMarks() { return marks; }
    }

    static class StudentController {
        private Connection conn;

        public StudentController() throws Exception {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", ""); 
        }

        public void insertStudent(Student s) throws Exception {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
            ps.setInt(1, s.getStudentID());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDepartment());
            ps.setInt(4, s.getMarks());
            ps.executeUpdate();
        }

        public void getAllStudents() throws Exception {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Student");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("StudentID") + ", Name: " + rs.getString("Name") +
                        ", Department: " + rs.getString("Department") + ", Marks: " + rs.getInt("Marks"));
            }
        }

        public void updateStudent(int id, String newName, String newDept, int newMarks) throws Exception {
            PreparedStatement ps = conn.prepareStatement("UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?");
            ps.setString(1, newName);
            ps.setString(2, newDept);
            ps.setInt(3, newMarks);
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println("Student updated successfully.");
        }

        public void deleteStudent(int id) throws Exception {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Student WHERE StudentID=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Student deleted successfully.");
        }
    }

    public static void main(String[] args) {
        try {
            StudentController controller = new StudentController();
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n1. Insert Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        int marks = scanner.nextInt();

                        controller.insertStudent(new Student(id, name, dept, marks));
                        System.out.println("Student added!");
                        break;

                    case 2:
                        controller.getAllStudents();
                        break;

                    case 3:
                        System.out.print("Enter Student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new Department: ");
                        String newDept = scanner.nextLine();
                        System.out.print("Enter new Marks: ");
                        int newMarks = scanner.nextInt();

                        controller.updateStudent(updateId, newName, newDept, newMarks);
                        break;

                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        controller.deleteStudent(deleteId);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                }
            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
