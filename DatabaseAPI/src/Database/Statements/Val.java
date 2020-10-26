package Database.Statements;

import java.sql.Date;

public class Val {
    private Integer integer;
    private String str;
    private Date date;
    private Class clazz;

    public Val() {
        this.clazz = null;
    }

    public Val(Integer integer) {
        this.integer = integer;
        this.clazz = Integer.class;
    }

    public Val(String str) {
        this.str = str;
        this.clazz = String.class;
    }

    public Val(Date date) {
        this.date = date;
        this.clazz = Date.class;
    }

    public Integer getInteger() {
        if (this.clazz != Integer.class) {
            throw new RuntimeException("Asked for Integer in non-integer value");
        }
        return integer;
    }

    public String getStr() {
        if (this.clazz != String.class) {
            throw new RuntimeException("Asked for String in non-string value");
        }
        return str;
    }

    public Date getDate() {
        if (this.clazz != Date.class) {
            throw new RuntimeException("Asked for Date in non-date value");
        }
        return date;
    }

    public Class getClazz() {
        return clazz;
    }
}
