package Connect;

import java.sql.*;

public class Connect {

    private Statement stmt;
    private ResultSet rs;
    private Connection con;

    public Connect() {
        try{  
            Class.forName("com.mysql.jdbc.Driver");

            // Nanti prk nya diubah sesuai dengan nama db yang mau di connect
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sudut_meong","root","");
            stmt = con.createStatement();  
            
            System.out.println("Connected to the database..");
        }catch(Exception e){ 
            System.out.println(e);
        }  
    }

    public ResultSet executeQuery(String query){
        try{
            rs = stmt.executeQuery(query);
        }
        catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }

    public void executeUpdate(String query){
        try{
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void executePreparedQuery(String query) {
        try {
            PreparedStatement pStat = con.prepareStatement(query);
            pStat.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public PreparedStatement prepareStatement(String query) {
    	try {
			return con.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

}