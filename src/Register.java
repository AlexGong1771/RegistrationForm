import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JDialog {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPasswordField textField5;
    private JPasswordField textField6;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel RegisterPannel;

    public Register(JFrame owner) {
        super(owner);
        setTitle("Create a new account");
        setContentPane(RegisterPannel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(e -> registerUser());
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void registerUser() {
        String name = textField1.getText();
        String email = textField2.getText();
        String phone = textField3.getText();
        String address = textField4.getText();
        String password = String.valueOf(textField5.getPassword());
        String confirm = String.valueOf(textField6.getPassword());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please you have to fill in all the fields", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords doesn't match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDB(name, email, phone, address, password);
        if (user != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register user", "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;

    private User addUserToDB(String name, String email, String phone, String address, String password) {
        User user = null;
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/MyStore";
        String dbPassword = "gongalschi";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, dbPassword);
            String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                user = new User(name, email, password, phone, address);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return user;
    }
}
