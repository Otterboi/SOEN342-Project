package flights;

import java.sql.*;

public class AirportDBController {
    public AirportDBController() {

    }
    public void addAirport(String name, String code, String cityName) throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlite:src/Database/AirportSimulation.db";
        Connection conn = null;

        //Airport name, code, city_id will be inserted in the database using a query
        //Get the city using cityName
        String sql = "INSERT INTO Airport (airport_name, airport_code, city_id) VALUES (?, ?, (SELECT city_id FROM City WHERE city_name = ?));";

        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(url);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, code);
            ps.setString(3, cityName);
            ps.executeUpdate();
            System.out.println("New Airport added successfully.");

            ps.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        conn.close();
    }
}
