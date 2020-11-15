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
            String postQuery = "INSERT INTO Post VALUES(?,?,?,?,?)";
            PreparedStatement statement = new StatementBuilder(connection, postQuery)
                    .integer(cid)
                    .integer(rid)
                    .integer(aid)
                    .date(date)
                    .str(commentTitle)
                    .build();

            /* insert comment content */
            if (!CommentContent.insert(commentTitle, date, commentContent)) return false;
            /* insert comment rating */
            if (!CommentRating.insert(rid, rating)) return false;

            return statement.execute();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
