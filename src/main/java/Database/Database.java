package Database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private final DataSource dataSource;

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Opens Connection to the MySQL database.
     * DO NOT FORGET TO CLOSE IT AFTER USE.
     */
    public Connection openConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
