package Database;

import Database.Statements.StatementBuilder;
import oracle.ucp.common.waitfreepool.Tuple;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
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

    /* Aggregation with Group BY */
    public static String averageRating() {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String query =
                    "SELECT c.typename, AVG(rating) " +
                    "FROM recipe r, content c, foodtype f " +
                    "WHERE r.content = c.content AND c.Typename = f.Typename " +
                    "GROUP BY c.typename";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            HashMap<String, String> table = new HashMap<>();
            table.put("FoodType", "typename");
            table.put("Average Rating", "AVG(rating");
            return render(result, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Selection Query, lists all recipes that satisfy:
     * @param lo user defined lower bound, inclusive
     * @param hi user defined upper bound, inclusive
     * @return HTML string
     */
    public static String listRecipeWRating(int lo, int hi) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT r.rid")
                    .append("FROM recipe r")
                    .append("WHERE ").append(lo).append("<= r.rating AND r.rating <=").append(hi);
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(queryBuilder.toString());
            HashMap<String, String> table = new HashMap<>();
            table.put("Rating ID", "rid");
            return render(res, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Private helper function that renders html elements
     * @param result the query result set
     * @param titles a list of table headers to be rendered, key is title, value is column label
     * @return
     */
    public static String render(ResultSet result, HashMap<String, String> titles) throws Exception {
        // %-10s pads the argument to 10 characters. (the negative means left-aligned)
        // Just to make the output look nicer and more like a table
        // Also too lazy to make it an actual table in the frontend, I'd rather just print out a string
        // that looks like a table lmao

        /* Origin: */
//        StringBuilder table = new StringBuilder(String.format("\n%-10s%-10s", "FoodType", "Average Rating"));
//        while (result.next()) {
//            String foodtype = result.getString("typename");
//            String rating = result.getString("AVG(rating)");
//            String row = String.format("\n%-10s%-10s", foodtype, rating);
//            table.append(row);
//        }

        /* format the table header */
        StringBuilder table = new StringBuilder("\n");
        for (String key: titles.keySet()) {
            table.append(String.format("%-10s", titles.get(key)));
        }

        while (result.next()) {
            StringBuilder row = new StringBuilder("\n");
            for (String val: titles.values()) {
                String elem = result.getString(val);
                row.append(String.format("%-10s", elem));
            }
            table.append(row.toString());
        }
        System.out.println(table.toString());
        return table.toString();
    }
}
