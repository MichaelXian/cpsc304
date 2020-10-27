package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import static Database.ConnectionFactory.createConnection;

public final class PersonalInfoTable {

    private PersonalInfoTable() {

    }

    public static boolean insert(String firstName, String lastName, Date date_of_birth, String address) {
        try (Connection connection = createConnection()) {
            // Setup PersonalInfo Query
            String personalInfoQuery = "INSERT INTO PersonalInfo VALUES(?, ?, ?, ?)";
            PreparedStatement personalInfoStatement = new StatementBuilder(connection, personalInfoQuery)
                    .str(firstName)
                    .str(lastName)
                    .date(date_of_birth)
                    .str(address).build();

            // Execute PersonalInfo insert
            personalInfoStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
