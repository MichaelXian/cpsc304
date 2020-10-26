package Database;

import Database.Statements.Val;

import java.sql.*;

import static Database.Statements.StatementFactory.getStatement;
import static Database.Statements.ValFactory.val;

public final class AccountTable {

    private AccountTable() {

    }

    public static boolean insert(Integer aid, String username, String firstName, String lastName, String password, Date date, String address) {
        try (Connection connection = ConnectionFactory.createConnection()) {

            // TODO: Builder would probably be better
            // Setup PersonalInfo Query
            String personalInfoQuery = "INSERT INTO PersonalInfo VALUES(?, ?, ?, ?)";
            PreparedStatement personalInfoStatement = getStatement(connection, personalInfoQuery, val(firstName), val(lastName), val(date), val(address));

            // Setup AccountInsert Query
            String accountInsertQuery = "INSERT INTO Account VALUES(?, ?, ?, ?, ?)";
            PreparedStatement accountStatement = getStatement(connection, accountInsertQuery, val(aid), val(username), val(firstName), val(lastName), val(password));

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
