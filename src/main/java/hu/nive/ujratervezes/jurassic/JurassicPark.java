package hu.nive.ujratervezes.jurassic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class JurassicPark {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    JurassicPark(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException sqle) {
            System.out.println("Problem has occurred while getting connection!");
        }
        return connection;
    }

    List<String> checkOverpopulation() {
        List<String> resultList = null;
        try (Connection connection = getConnection()) {
            resultList = new ArrayList<>();
            String sql = "" +
                    "SELECT breed, expected, actual " +
                    "FROM dinosaur " +
                    "WHERE actual > expected " +
                    "ORDER BY breed;";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString(1));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return resultList;
    }

}
