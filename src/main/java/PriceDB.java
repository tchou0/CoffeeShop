import java.util.ArrayList;
import java.sql.*;

public class PriceDB {

    public double findPrice(SizeType size, DrinkType type) {
        System.out.println("*=*=*=*=*=*=*=* Welcome to My Coffee Shop *=*=*=*=*=*=*=* ");

        String driverClassname = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/coffeeshop";


        try {
            Class.forName(driverClassname);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;

        double price = 0;

        try {
            connection = DriverManager.getConnection(url, "tchou", "QQ112233!");
        } catch (SQLException e) {
            System.getLogger("PriceDB").log(System.Logger.Level.ERROR, "Couldn't open SQL connection");
        }

        try {
            PreparedStatement statement = connection.prepareStatement("""
                            SELECT name, price
                            FROM beverage
                            where type =  ? AND size = ? ;
                            
                    """);
            statement.setString(1, type.toString());
            statement.setString(2, size.toString());
            ResultSet results = null;

            results = statement.executeQuery();



            while (results.next()) {
                String name = results.getString("name");
                price = results.getDouble("price");
                System.out.printf("%s costs %10.2f\n", name, price);

            }
            connection.close();
        } catch (SQLException e) {
            System.getLogger("PriceDB").log(System.Logger.Level.ERROR, "Couldn't execute statement");
        } catch (RuntimeException e) {
            System.getLogger("PriceDB").log(System.Logger.Level.ERROR, "RUNTIME EXCEPTION");
        }

        return price;
    }
}
