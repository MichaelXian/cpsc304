package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class RecipeTable {

    public static boolean insert(Integer rid, String name, String content, Integer cooking_time, Integer rating, String typename, Integer aid, Date date) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            // Setup Recipe Insert Query
            String recipeInsertQuery = "INSERT INTO Recipe VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement recipeStatement = new StatementBuilder(connection, recipeInsertQuery)
                    .integer(rid)
                    .str(content)
                    .str(name)
                    .integer(rating)
                    .date(date)
                    .integer(aid).build();

            System.out.println("Start inserting content");
            // Insert Content
            if (!ContentTable.insert(content, cooking_time, typename)) {
                // If failed to insert Content, exit
                return false;
            }
            System.out.println("Successfully inserted content");

            // Execute Recipe insert
            recipeStatement.execute();
            System.out.println("Successfully inserted recipe");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
