/**
 * 
 */
package DBTester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mercenery
 *
 */
public class PostgresTester {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dictionary", "postgres",
                    "postgres");
			PreparedStatement st = connection.prepareStatement("select * from word_define;");
			ResultSet result = st.executeQuery();
			System.out.println(result.getString("word" + result.getInt("definition")));
		} catch (ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
