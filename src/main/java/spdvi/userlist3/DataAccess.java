package spdvi.userlist3;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataAccess {

    // Come comments
    private Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        try {
            properties.load(DataAccess.class.getClassLoader().getResourceAsStream("application.properties"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public int insertUser(User user) {

        try (Connection connection = getConnection();) {
            
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO dbo.[User] (firstName, lastName, birthDate, gender, isAlive, profilePicture) "
                    + "VALUES (?,?,?,?,?,?)");
            insertStatement.setString(1, user.getFirstName());
            insertStatement.setString(2, user.getLastName());
            insertStatement.setString(3, user.getBirthDate().toString());
            insertStatement.setString(4, user.getGender());
            insertStatement.setBoolean(5, user.isIsAlive());
            if (user.getProfilePicture() != null) {
                insertStatement.setString(6, user.getProfilePicture().toString());            
            }
            else 
                insertStatement.setNull(6, java.sql.Types.NVARCHAR);


            int result = insertStatement.executeUpdate();

            if (result > 0) {
                PreparedStatement selectStatement = connection.prepareStatement(
                        "SELECT MAX(id) AS newId FROM dbo.[User]");
                ResultSet resultSet = selectStatement.executeQuery();
                if (!resultSet.next()) {
                    return 0;
                }
                return resultSet.getInt("newId");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM [User]"
            );
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        //                        LocalDateTime.parse(resultSet.getString("birthDate")).toLocalDate(),
                        LocalDate.parse(resultSet.getString("birthDate")),
                        resultSet.getString("gender"),
                        resultSet.getBoolean("isAlive"),
                        //new URL(resultSet.getString("profilePicture"))
                        null
                );

                try {
                    String imageUrlString = resultSet.getString("profilePicture");

                    if (imageUrlString != null) {
                        URL imageUrl = new URL(imageUrlString);
                        user.setProfilePicture(imageUrl);
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
                }

                users.add(user);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return users;
    }
}
