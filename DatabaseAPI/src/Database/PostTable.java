package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class PostTable {

    private PostTable() {}

    public static boolean insert(
            Integer cid,
            Integer rid,
            Integer aid,
            Date    date,
            String  commentTitle,
            String  commentContent,
            Integer rating
    ) {
        try (Connection connection = ConnectionFactory.createConnection()) {
            String postQuery = "INSERT INTO Post VALUES(?,?,?,?,?,?,?)";
            PreparedStatement statement = new StatementBuilder(connection, postQuery)
                    .integer(cid)
                    .integer(rid)
                    .integer(aid)
                    .date(date)
                    .integer(rating)
                    .str(commentContent)
                    .str(commentTitle)
                    .build();

            // Removed CommentContent and CommentRating
            statement.execute();
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
