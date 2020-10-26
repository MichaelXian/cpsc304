package Database.Statements;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public final class StatementFactory {

    private StatementFactory() {

    }

    public static PreparedStatement getStatement(Connection conn, String query, Val... vals) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        for (int i = 0; i < vals.length; i++) {
            if (vals[i].getClazz() == Integer.class) {
                statement.setInt(i + 1, vals[i].getInteger());
            } else if (vals[i].getClazz() == String.class) {
                statement.setString(i + 1, vals[i].getStr());
            } else if (vals[i].getClazz() == Date.class) {
                statement.setDate(i + 1, vals[i].getDate());
            } else if (vals[i].getClazz() == null) {
                statement.setNull(i + 1, NULL);
            } else {
                throw new RuntimeException("Value has unknown type");
            }
        }
        return statement;
    }

}
