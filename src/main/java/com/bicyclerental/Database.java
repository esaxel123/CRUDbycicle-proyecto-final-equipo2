package main.java.com.bicyclerental;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

public class Database {

    private static final String CLASS_NAME = Database.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private Connection con;

    //URL que identifica a la base de datos que nos queremos conectar
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bicicletas";

    //Driver de JDBC que vamos a usar para conectarnos a la base de datos
    private static final String DRIVER = "org.postgresql.Driver";

    // Objeto singleton
    private static Database DB = null;

    // Constructor privado para implementar Singleton
    private Database() {
        super();
    }

    private Properties properties ;
    private Database(String user, String password) {
        super();
        con = null;

        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("bicycle_rental.properties")));
            System.out.println(properties.get("DRIVER"));
            System.out.println(properties.get("URL"));
            System.out.println(properties.get("USER"));
            System.out.println(properties.get("PASSWD"));

        } catch (FileNotFoundException e) {
            LOGGER.severe(e.getMessage() );
        } catch (IOException e) {
            LOGGER.severe(e.getMessage() );
        }

        try {

            // Cargar el driver
            Class.forName(DRIVER);
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            // Abrir una conexion a la base de datos
            con = DriverManager.getConnection(DB_URL, props);

        } catch (ClassNotFoundException ex) {
            LOGGER.severe(ex.getMessage() );

        } catch (SQLException ex) {
            LOGGER.severe("Error: " + ex.getMessage());
            LOGGER.severe("Codigo : " + ex.getErrorCode());
        }
    }

    // Abrir la conexión y regresar objeto Database
    public static Database getDatabase(String user, String pass) {
        if (DB == null) {
            DB = new Database(user, pass);
        }
        return DB;
    }

    public ResultSet query(String sql) throws SQLException {

        ResultSet rs = null;
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        rs = statement.executeQuery(sql);

        return rs;
    }

    public ResultSet query(String sql, int scroll, int concur) throws SQLException {

        ResultSet rs = null;

        Statement statement = con.createStatement(scroll, concur);
        rs = statement.executeQuery(sql);

        return rs;
    }

    public int update(String sql) throws SQLException {
        int result = -1;

        Statement statement = con.createStatement(
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE
        );
        result = statement.executeUpdate(sql);
        return result;
    }
    public Connection getConnection() {
        return con;
    }

}
