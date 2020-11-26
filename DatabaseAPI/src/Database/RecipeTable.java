package Database;

import Database.Statements.StatementBuilder;

import java.sql.*;
import java.util.HashMap;

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
            table.put("Average Rating", "AVG(rating)");
            return render(result, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Selection & Projection Query, lists all recipes that satisfy:
     * @param lo user defined lower bound, inclusive
     * @param hi user defined upper bound, inclusive
     * @return HTML string
     */
    public static String listRecipeWRating(int lo, int hi) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT r.rid, r.name, r.rating ")
                    .append("FROM recipe r ")
                    .append("WHERE ").append(lo).append("<= r.rating AND r.rating <=").append(hi);
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(queryBuilder.toString());
            HashMap<String, String> table = new HashMap<>();
            table.put("Recipe ID", "rid");
            table.put("Name", "name");
            table.put("Rating", "rating");
            return render(res, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /** Join Query */
    public static String listRecipeWTimeInRange(int lo, int hi) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT r.rid, r.name, c.cooking_time ")
                    .append("FROM recipe r, content c ")
                    .append("WHERE ").append(lo).append("<= c.cooking_time AND c.cooking_time <= ").append(hi)
                    .append(" AND r.content = c.content");
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(queryBuilder.toString());
            HashMap<String, String> table = new HashMap<>();
            table.put("Recipe ID", "rid");
            table.put("Name", "name");
            table.put("Time", "cooking_time");
            return render(res, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /* Aggregation with Having */
    /* we define fast food as: the average cookiing time of such food does not exceed 10min */
    public static String averageRatingOfFastFood() {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String query =
                    "SELECT c.typename, AVG(rating) " +
                            "FROM recipe r, content c, foodtype f " +
                            "WHERE r.content = c.content AND c.Typename = f.Typename " +
                            "GROUP BY c.typename " +
                            "HAVING AVG(c.cooking_time) < 35";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            HashMap<String, String> table = new HashMap<>();
            table.put("FoodType", "typename");
            table.put("Average Rating", "AVG(rating)");
            return render(result, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /* Nested Aggregation */
    /* we define fast food as: the average cookiing time of such food does not exceed 10min */
    /* find the food type which the average of the rating of this food type is the maximum over all food types */
    public static String bestFoodType() {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String query =
                    "SELECT t.typename, AVG(t.rating) " +
                            "FROM " +
                            "  (SELECT c.typename, AVG(r.rating) AS average " +
                            "   From recipe r, content c, foodtype f " +
                            "   WHERE r.content = c.content AND c.Typename = f.Typename " +
                            "   GROUP BY c.typename) AS t " +
                            "WHERE t.average = (" +
                            "SELECT MAX(t.average) FROM t)";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            HashMap<String, String> table = new HashMap<>();
            table.put("FoodType", "typename");
            table.put("Average Rating", "AVG(rating)");
            return render(result, table);
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

        /* Original version: */
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
            table.append(String.format("%-30s", key));
        }

        while (result.next()) {
            StringBuilder row = new StringBuilder("\n");
            for (String val: titles.values()) {
                String elem = result.getString(val);
                row.append(String.format("%-30s", elem));
            }
            table.append(row.toString());
        }
        System.out.println(table.toString());
        return table.toString();
    }

    /* division */
    public static String commented() {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String query =
                    "SELECT r.name " +
                            "FROM recipe r " +
                            "WHERE NOT EXISTS ( " +
                                "(SELECT DISTINCT p.aid " +
                    "FROM Post p " +
                    "WHERE p.aid NOT IN (" +
                    "SELECT p2.aid " +
                    "FROM Post p2 " +
                    "WHERE p2.rid = r.rid) " +
                    ")" +
                    ")";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            HashMap<String, String> table = new HashMap<>();
            table.put("Recipe name", "r.name");
            return render(result, table);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
