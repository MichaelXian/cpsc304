package Database.Statements;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class StatementFactory {

    private StatementFactory() {

    }

    public static PreparedStatement getStatement(Connection conn, String query, Val[] vals) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        for (int i = 0; i < vals.length; i++) {
            if (vals[i].getClazz() == Integer.class) {
                statement.setInt(i, vals[i].getInteger());
            } else if (vals[i].getClazz() == String.class) {
                statement.setString(i, vals[i].getStr());
            } else if (vals[i].getClazz() == Date.class) {
                statement.setDate(i, vals[i].getDate());
            }
        }
        return statement;
    }

}
