package Database.Statements;

import java.sql.Date;

public final class ValFactory {

    public static Val val(Integer integer) {
        return new Val(integer);
    }

    public static Val val(String str) {
        return new Val(str);
    }

    public static Val val(Date date) {
        return new Val(date);
    }

    public static Val val() {
        return new Val();
    }

}
