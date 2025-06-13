import javax.swing.*;
import java.util.ArrayList;

public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        setEmail(email);
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User))
            return false;
        User u = (User) o;
        return u.getEmail().toLowerCase().equals(this.email);
    }

    public static User logIn(ArrayList<User> users) {
        String email;
        String password;

        while (true) {
            email = JOptionPane.showInputDialog(null, "Please enter your email: ", "Login", JOptionPane.QUESTION_MESSAGE);
            password = JOptionPane.showInputDialog(null, "Please enter your password: ", "Login", JOptionPane.QUESTION_MESSAGE);

            for (User user : users) {
                if (user != null && user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
                    if (password.equals(user.getPassword())) {
                        JOptionPane.showMessageDialog(null, "You have successfully logged in!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        return user;
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Incorrect email or password. Please try again.", "Error!", JOptionPane.ERROR_MESSAGE);
            int input;
            do {
                input = JOptionPane.showOptionDialog(null, "Would you like to try again?", "Retry or Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (JOptionPane.NO_OPTION == input) {
                    return null;
                } else {
                    break;
                }
            } while (input != 1);
        }
    }


    public static User signUp(ArrayList<User> users) {
        String email;

        while (true) {
            email = JOptionPane.showInputDialog(null, "Please enter your email: ", "Sign Up", JOptionPane.QUESTION_MESSAGE);

            boolean emailExists = false;
            for (User user : users) {
                if (user != null && user.getEmail().equalsIgnoreCase(email)) {
                    emailExists = true;
                    break;
                }
            }

            if (!emailExists) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Email is taken, please enter a different email.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        String password = JOptionPane.showInputDialog(null, "Please enter your password: ", "Sign Up", JOptionPane.QUESTION_MESSAGE);
        JOptionPane.showMessageDialog(null, "Account successfully created!", "Success!", JOptionPane.INFORMATION_MESSAGE);

        while (true) {
            String userRole = JOptionPane.showInputDialog(null, "Enter [C] if you are a Customer or [S] if you are a Seller: ", "User Role", JOptionPane.QUESTION_MESSAGE);

            if (userRole != null && (userRole.equalsIgnoreCase("C") || userRole.equalsIgnoreCase("S"))) {
                User newUser = userRole.equalsIgnoreCase("C") ? new Customer(email, password) : new Seller(email, password);
                users.add(newUser);
                JOptionPane.showMessageDialog(null, "Account successfully added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                return newUser;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid role, please try again.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static User authentication(ArrayList<User> users) {
        while (true) {
            Object[] options = {"Log In", "Sign Up", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Authentication",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                return logIn(users);
            } else if (choice == 1) {
                return signUp(users);
            } else if (choice == 2) {
                return null;
            }
        }
    }
}
