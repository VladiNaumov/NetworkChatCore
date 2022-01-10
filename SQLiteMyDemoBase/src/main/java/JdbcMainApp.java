import java.sql.*;


/*
CREATE TABLE students (
    id    INTEGER PRIMARY KEY AUTOINCREMENT,
    name  TEXT,
    score INTEGER
);
 */

public class JdbcMainApp {

    // подключение к BD
    private static Connection connection;

    // выполнения запросов
    private static Statement stmt;

    private static PreparedStatement psInsert;

    // CRUD

    public static void main(String[] args) {
        try {
            connect();
            CreateDB();
           // stmt.executeUpdate("INSERT INTO  students (name, score) VALUES ('Max', 90 );");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");


           connection = DriverManager.getConnection("jdbc:sqlite:mybase.db");

            stmt = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно подключиться к БД");
        }
    }

    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (psInsert != null) {
                psInsert.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void CreateDB() throws SQLException
    {
        stmt = connection.createStatement();
        stmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' TEXT, 'score' INT);");

        //  stmt.execute( "CREATE TABLE if not exists 'students' ( 'id 'INTEGER PRIMARY KEY AUTOINCREMENT, 'name'  TEXT, 'phone' INT);");

        System.out.println("Таблица создана или уже существует.");
    }

    public static void WriteDB() throws SQLException
    {
        stmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
        stmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
        stmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");

        System.out.println("Таблица заполнена");
    }


    private static void preparedStatementExample() throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 1; i <= 50; i++) {
            // insert into students (name, score) values (?, ?);
            psInsert.setString(1, "BOB" + i);
            psInsert.setInt(2, 100);
            psInsert.executeUpdate();
        }
        connection.commit();
    }

    private static void prepareStatements() throws SQLException {
        psInsert = connection.prepareStatement("insert into students (name, score) values (?, ?);");
    }

    private static void batchExample() throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 1; i <= 50; i++) {
            stmt.addBatch(String.format("insert into students (name, score) values ('%s', %d);", "BOB #" + i, 100));
        }
        stmt.executeBatch();
        connection.commit();
    }

    private static void dropAndCreateTable() throws SQLException {
        stmt.executeUpdate("drop table if exists students;");
        stmt.executeUpdate("CREATE TABLE if not exists students (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name  TEXT,\n" +
                "    score INTEGER\n" +
                ");;");
    }

    private static void fillTable() throws SQLException {
        long time = System.currentTimeMillis();
        connection.setAutoCommit(false);
        for (int i = 1; i <= 50; i++) {
            // 1 BOB #1 100
            stmt.executeUpdate(String.format("insert into students (name, score) values ('%s', %d);", "BOB #" + i, 100));
        }
        connection.commit();
        System.out.println("TIME: " + (System.currentTimeMillis() - time));
    }

    private static void clearTableExample() throws SQLException {
        stmt.executeUpdate("delete from students;");
    }

    private static void deleteOneExample() throws SQLException {
        stmt.executeUpdate("delete from students where id = 5;");
    }

    private static void updateExample() throws SQLException {
        stmt.executeUpdate("update students set score = 100 where id > 0;");
    }

    private static void readExample() throws SQLException {
        try (ResultSet rs = stmt.executeQuery("select * from students where id > 2;")) {
            while (rs.next()) {
                //   id     name     score
                // > 1      Bob      90
                //   2      Jack     80
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt("score"));
            }
        }
    }

    private static void insertExample() throws SQLException {
        stmt.executeUpdate("insert into students (name, score) values ('Max', 90);");
    }



    public static int getStudentScoreByName(String name) {
        // >
        // > 100

        // >
        // > __
        try (ResultSet rs = stmt.executeQuery("select score from students where name = '" + name + "';")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
