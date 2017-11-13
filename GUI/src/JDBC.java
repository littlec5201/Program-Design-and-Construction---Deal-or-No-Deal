
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {

    private static Connection connection;
    private static Statement statement;
    String url = "jdbc:derby://localhost:1527/AssignmentDataBase";
    String userName = "pdc";
    String password = "pdc";
    int rows = 0;
    private static String tableName = "PDC.DealOrNoDeal";

    public JDBC(String name, int offer) {
        try {
            createConnection();
            createTable();
            insertNewRecord(name, offer);
            shutdown();
        } catch (Exception ex) {
        }
    }

    public JDBC() {
        try {
            createConnection();
        } catch (Exception e) {
        }
    }

    /**
     * Creates a connection to the local database
     */
    public void createConnection() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } catch (Exception e) {
            System.out.println("Error create connection");
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new record into the database
     * @param playerName the name of the player for the current game
     * @param score the deal that the current player took
     */
    public void insertNewRecord(String playerName, int score) {
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
            int count = 1;
            while (results.next()) {
                count = results.getInt(1);
            }
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            Date date = new Date();
            String readableDate = dateFormat.format(date);
            statement.execute("INSERT INTO " + tableName + " VALUES"
                    + "(" + count + ", '" + playerName + "'," + score + ", '" + readableDate + "')");
            statement.close();
            System.out.println("Successfully put records in");
        } catch (Exception e) {
            System.out.println("Error inserting record");
            e.printStackTrace();
        }
    }

    /**
     * Closes the current connection to the database
     */
    public void shutdown() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error shutting connection down");
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return how many entries are in the database
     */
     public int getCount() {
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
            int count = 1;
            while (results.next()) {
                count = results.getInt(1);
            }
            return count;
        } catch (Exception ex) {
            
        }
        return 0;
    }

     /**
      * 
      * @param number the row in the database that the name will be retrieved from
      * @return the name at the specified row number
      */
    public String getName(int number) {
        String name = "";
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT name FROM " + tableName + " ORDER BY winnings DESC");
            if (results != null) {
                ResultSetMetaData rsmd = results.getMetaData();
                for (int i = 0; i < number; i++) {
                    results.next();
                }
                results.next();
                name = results.getString("Name");
            }
            results.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error selecting name");
            name = "No record here";
        }
        return name;
    }

    /**
      * 
      * @param number the row in the database that the winnings will be retrieved from
      * @return the winnings at the specified row number
      */
    public String getWinnings(int number) {
        String winnings = "";
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT winnings FROM " + tableName + " ORDER BY winnings DESC");
            if (results != null) {
                ResultSetMetaData rsmd = results.getMetaData();
                for (int i = 0; i < number; i++) {
                    results.next();
                }
                results.next();
                winnings += "$";
                winnings += results.getString("Winnings");
            }
            results.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error selecting winnings");
            winnings = "$0";
        }
        return winnings;
    }

    /**
      * 
      * @param number the row in the database that the date will be retrieved from
      * @return the date at the specified row number
      */
    public String getDate(int number) {
        String date = "";
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT date FROM " + tableName + " ORDER BY winnings DESC");
            if (results != null) {
                ResultSetMetaData rsmd = results.getMetaData();
                for (int i = 0; i < number; i++) {
                    results.next();
                }
                results.next();
                date = results.getString("Date");
            }
            results.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error selecting date");
            date = "No record here";
        }
        return date;
    }

    /**
     * Creates a table if it does exist already. If the table already exists, an exception will be thrown
     */
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String newTableName = "DealOrNoDeal";
            DatabaseMetaData metaData = connection.getMetaData();
            String sqlCreate = "create table " + newTableName
                    + " (GameNumber int,"
                    + "Name varchar(50), "
                    + "Winnings int,"
                    + "Date varchar(50))";
            statement.executeUpdate(sqlCreate);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error creating table. A table with the same name may already exist");
        }
    }
}
