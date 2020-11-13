package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static Database.ConnectionFactory.createConnection;

public class ContentTable {

    public static boolean insert(String content, Integer cooking_time, String typename) {
        try (Connection connection = createConnection()) {
            // Setup Content Query
            String contentInsertQuery = "INSERT INTO Content VALUES(?, ?, ?)";
            PreparedStatement contentStatement = new StatementBuilder(connection, contentInsertQuery)
                    .str(content)
                    .integer(cooking_time)
                    .str(typename).build();

            // Execute Content insert
            contentStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
