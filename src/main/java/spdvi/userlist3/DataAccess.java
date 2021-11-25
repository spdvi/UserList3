package spdvi.userlist3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DataAccess {
    
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
            //insertStatement.setString(6, user.getProfilePicture().toString());
            insertStatement.setString(6, null);
            
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
}
