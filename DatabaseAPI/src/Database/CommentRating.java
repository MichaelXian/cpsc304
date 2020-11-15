package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;

public final class CommentRating {

    private CommentRating(){}

    public static boolean insert(Integer rid, Integer rating) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String commentRatingQuery = "INSERT INTO CommentRating VALUES(?,?)";
            PreparedStatement statement = new StatementBuilder(connection, commentRatingQuery)
                    .integer(rid)
                    .integer(rating)
                    .build();

            return statement.execute();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
