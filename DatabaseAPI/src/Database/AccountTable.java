package Database;

import Database.Statements.Val;

import java.sql.*;

import static Database.Statements.StatementFactory.getStatement;

public final class AccountTable {

    private AccountTable() {

    }

    public static boolean insert(Integer aid, String username, String firstName, String lastName, String password, Date date, String address) {
        try (Connection connection = ConnectionFactory.createConnection()) {

            // Setup PersonalInfo Query
            String personalInfoQuery = "INSERT INTO PersonalInfo VALUES(?, ?, ?, ?)";
            Val[] personalInfoVals = {new Val(firstName), new Val(lastName), new Val(date), new Val(date), new Val(address)};
            PreparedStatement personalInfoStatement = getStatement(connection, personalInfoQuery, personalInfoVals);

            // Setup AccountInsert Query
            String accountInsertQuery = "INSERT INTO Account VALUES(?, ?, ?, ?, ?)";
            Val[] accountVals = {new Val(aid), new Val(username), new Val(firstName), new Val(lastName), new Val(password)};
            PreparedStatement accountStatement = getStatement(connection, accountInsertQuery, accountVals);

            // Execute Queries
            personalInfoStatement.execute();
            accountStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
