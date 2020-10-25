package Database;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cookingsite", "root", "root");
            String personalInfoQuery = "INSERT INTO PersonalInfo VALUES(?, ?, ?, ?)";
            String accountQuery = "INSERT INTO Account VALUES(?, ?, ?, ?, ?)";
            String firstName = "Bob";
            String lastName = "Bobington";
            PreparedStatement personalInfoStatement = connection.prepareStatement(personalInfoQuery);
            personalInfoStatement.setString(1, firstName);
            personalInfoStatement.setString(2, lastName);
            personalInfoStatement.setDate(3, new Date(1000L));
            personalInfoStatement.setString(4, "1234 Address St");
            PreparedStatement accountStatement = connection.prepareStatement(accountQuery);
            accountStatement.setInt(1, 1);
            accountStatement.setString(2, "username");
            accountStatement.setString(3, firstName);
            accountStatement.setString(4, lastName);
            accountStatement.setString(5, "pass");
            personalInfoStatement.execute();
            accountStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
