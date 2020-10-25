package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class PersonalInfoTable {

    private PersonalInfoTable() {

    }

    static PreparedStatement getPersonalInfoStatement(String query, String firstName, String lastName, Date date, String address, Connection connection) throws SQLException {
        PreparedStatement personalInfoStatement = connection.prepareStatement(query);
        personalInfoStatement.setString(1, firstName);
        personalInfoStatement.setString(2, lastName);
        personalInfoStatement.setDate(3, date);
        personalInfoStatement.setString(4, address);
        return personalInfoStatement;
    }
}
