package Database.Statements;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public final class StatementBuilder {

    private final PreparedStatement statement;
    private Integer counter;

    public StatementBuilder(Connection connection, String query) throws SQLException {
        this.statement = connection.prepareStatement(query);
        this.counter = 1;
    }

    public StatementBuilder integer(Integer integer) throws SQLException {
        statement.setInt(counter, integer);
        counter++;
        return this;
    }

    public StatementBuilder str(String string) throws SQLException {
        statement.setString(counter, string);
        counter++;
        return this;
    }

    public StatementBuilder date(Date date) throws SQLException {
        statement.setDate(counter, date);
        counter++;
        return this;
    }

    public PreparedStatement build() {
        return statement;
    }

}
