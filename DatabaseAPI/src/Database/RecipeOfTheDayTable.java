package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public final class RecipeOfTheDayTable {

    private RecipeOfTheDayTable() {}

    public static boolean insert(Date date, String name, Integer rid) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String recipeOfTheDayQuery = "INSERT INTO RecipeOfTheDay VALUE(?,?,?)";
            PreparedStatement statement = new StatementBuilder(connection, recipeOfTheDayQuery)
                    .date(date)
                    .str(name)
                    .integer(rid)
                    .build();
            return statement.execute();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
