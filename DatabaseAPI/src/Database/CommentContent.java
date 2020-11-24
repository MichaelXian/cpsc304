package Database;

import Database.Statements.StatementBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class CommentContent {

    private CommentContent(){}

    public static boolean insert(String commentTitle, Date date, String commentContent){
        try (Connection connection = ConnectionFactory.createConnection()) {
            String commentRatingQuery = "INSERT INTO CommentContent VALUES(?,?,?)";
            PreparedStatement statement = new StatementBuilder(connection, commentRatingQuery)
                    .str(commentTitle)
                    .date(date)
                    .str(commentContent)
                    .build();
            statement.execute();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
