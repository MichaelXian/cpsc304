package Database;

import Database.Statements.StatementBuilder;
import oracle.ucp.common.waitfreepool.Tuple;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

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

    public static String averageRating() {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String query =
                    "SELECT c.typename, AVG(rating) " +
                    "FROM recipe r, content c, foodtype f " +
                    "WHERE r.content = c.content AND c.Typename = f.Typename " +
                    "GROUP BY c.typename";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            // %-10s pads the argument to 10 characters. (the negative means left-aligned)
            // Just to make the output look nicer and more like a table
            // Also too lazy to make it an actual table in the frontend, I'd rather just print out a string
            // that looks like a table lmao
            StringBuilder table = new StringBuilder(String.format("\n%-10s%-10s", "FoodType", "Average Rating"));
            while (result.next()) {
                String foodtype = result.getString("typename");
                String rating = result.getString("AVG(rating)");
                String row = String.format("\n%-10s%-10s", foodtype, rating);
                table.append(row);
            }
            System.out.println(table.toString());
            return table.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
