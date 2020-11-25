package Database;

import Database.Statements.StatementBuilder;

import java.sql.*;

public final class AccountTable {

    private AccountTable() {

    }

    public static boolean insert(Integer aid, String username, String firstName, String lastName, String password, Date date_of_birth, String address) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            // Setup AccountInsert Query
            String accountInsertQuery = "INSERT INTO Account VALUES(?, ?, ?, ?, ?)";
            PreparedStatement accountStatement = new StatementBuilder(connection, accountInsertQuery)
                    .integer(aid)
                    .str(username)
                    .str(firstName)
                    .str(lastName)
                    .str(password).build();

            // Insert PersonalInfo
            if (!PersonalInfoTable.insert(firstName, lastName, date_of_birth, address)) {
                // If failed to insert PersonalInfo, exit
                return false;
            }

            // Execute Account insert
            accountStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean updatePassword(Integer aid, String password) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String query = "UPDATE Account SET password= ? WHERE aid = ?";
            PreparedStatement statement = new StatementBuilder(connection, query)
                    .str(password)
                    .integer(aid)
                    .build();
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
