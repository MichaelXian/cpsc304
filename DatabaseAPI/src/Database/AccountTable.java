package Database;

import java.sql.*;

public final class AccountTable {

    private AccountTable() {

    }

    public static boolean insert(Integer aid, String username, String firstName, String lastName, String password, Date date, String address) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cookingsite", "root", "root")) {
            String personalInfoQuery = "INSERT INTO PersonalInfo VALUES(?, ?, ?, ?)";
            PreparedStatement personalInfoStatement = getPersonalInfoStatement(personalInfoQuery, firstName, lastName, date, address, connection);
            String accountInsertQuery = "INSERT INTO Account VALUES(?, ?, ?, ?, ?)";
            PreparedStatement accountStatement = getAccountStatement(accountInsertQuery, connection, aid, username, firstName, lastName, password);
            personalInfoStatement.execute();
            accountStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static PreparedStatement getPersonalInfoStatement(String query, String firstName, String lastName, Date date, String address, Connection connection) throws SQLException {
        PreparedStatement personalInfoStatement = connection.prepareStatement(query);
        personalInfoStatement.setString(1, firstName);
        personalInfoStatement.setString(2, lastName);
        personalInfoStatement.setDate(3, date);
        personalInfoStatement.setString(4, address);
        return personalInfoStatement;
    }

    private static PreparedStatement getAccountStatement(String query, Connection connection, Integer aid, String username, String firstName, String lastName, String password) throws SQLException {
        PreparedStatement accountStatement = connection.prepareStatement(query);
        accountStatement.setInt(1, aid);
        accountStatement.setString(2, username);
        accountStatement.setString(3, firstName);
        accountStatement.setString(4, lastName);
        accountStatement.setString(5, password);
        return accountStatement;
    }
}
