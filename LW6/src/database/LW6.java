
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LW6 {

    static    Connection connection = null;
    static    Statement statement = null;
    static    ResultSet resultSet = null;

    public static void main(String[] args) {

        connectionDB();
        
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose operation");
        System.out.println("Press 1 for add");
        System.out.println("Press 2 for delete");
        System.out.println("Press 3 for update");
        System.out.println("Press 4 for search");
        int i = scn.nextInt();
        
        switch(i) {   
            case 1 : 
                 addDB();
            break;
            case 2 :
                deleteDB();
            break;
            case 3:
                updateDB();
            break;
            case 4:
                searchDB();
            break;
        }   
  }    
    public static void connectionDB() {
     

        try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                String DB_URL = "jdbc:derby://localhost:1527/HMS";
                String user ="root";
                String pass ="root";
                connection  = DriverManager.getConnection(DB_URL, user, pass);
                System.out.println("Connection Success!");
                
                statement = connection.createStatement();
                String selectionSQL = "Select * from ROOT.DOCTOR";
                resultSet = statement.executeQuery(selectionSQL);
                
                //if database doesn't empty show all records!
                while(resultSet.next()){
                    int id = resultSet.getInt("doc_id");
                    String doc_name = resultSet.getString("DOC_NAME");
                    String doc_surname = resultSet.getString("DOC_SURNAME");
                    String doc_dept = resultSet.getString("DOC_DEPT");
                    System.out.println("Doctor ID :"+id+"\tDoctor Name :"+doc_name+""
                            + "\tDoctor Surname :"+doc_surname+"\tDoctor Dept :"+doc_dept);
                }

        } catch (ClassNotFoundException | SQLException e) {
            
                System.out.println("Connection Error!\t"+e.getMessage());
        
        }
    }
    private static void addDB() {
        
        try {
             String add_sql = "Insert into doctor"
                        + "(doc_id,doc_name,doc_surname,doc_dept)"
                        + "values(?,?,?,?)";
             
                PreparedStatement ps = connection.prepareStatement(add_sql);
                Scanner scn = new Scanner(System.in);
                System.out.println("Doctor ID :");
                int id = scn.nextInt();
                System.out.println("Doctor Name :");
                String name = scn.next();
                System.out.println("Doctor Surname :");
                String surname = scn.next();
                System.out.println("Doctor Department :");
                String dept = scn.next();
                ps.setInt(1,id);
                ps.setString(2,name);
                ps.setString(3,surname);
                ps.setString(4,dept.toUpperCase());
                ps.executeUpdate();
                System.out.println("Adding Operation Success!");
                ps.close();
               
        } catch (SQLException e) {
            System.out.println("Adding Error!"+e.getMessage());
        }
    }   
    private static void deleteDB() {
        
                Scanner scn2 = new Scanner(System.in);
                System.out.println("Press 1 for delete one record");
                System.out.println("Press 2 for delete all records");
                int selection = scn2.nextInt();
                switch(selection){
                    
                    case 1 : 
                try {
                  String delete_sql = "Delete from doctor where doc_id =?";
                  PreparedStatement ps = connection.prepareCall(delete_sql);
                  Scanner scn = new Scanner(System.in);
                  System.out.println("Please enter id number for delete transaction");
                  int id = scn.nextInt();
                  ps.setInt(1, id);
                  ps.execute();
                  System.out.println("Delete Transaction Success!");
                  ps.close();
                 } 
                catch (SQLException e) {
                  System.out.println("Delete Error! (one records )\t"+e.getMessage());
                }
                        
                    break;
                        
                    case 2 :
                        try {
                            String delete_sql = "Delete from doctor where doc_id >=1"; //all records
                            statement = connection.createStatement();
                            statement.executeUpdate(delete_sql);
                            System.out.println("Deleted all records!");
                        } catch (SQLException e) {
                            System.out.println("Delete Error! (all records )\t"+e.getMessage());
                        }
                        break;
                }
    }
    private static void updateDB()    {
        
            try {
                String updateSql = "Update doctor set doc_name =? where doc_id=?";
                PreparedStatement ps = connection.prepareCall(updateSql);
                Scanner scn = new Scanner(System.in);
                System.out.println("Doctor Name : ");
                String new_name = scn.nextLine();
                System.out.println("Which one of doctor (write to ID) ");
                int id = scn.nextInt();
                ps.setString(1, new_name);
                ps.setInt(2, id);
                ps.execute();
                System.out.println("Update Transaction Success!");
                ps.close();
                       
        } catch (SQLException e) {
            
                System.out.println("Update Error!\t"+e.getMessage());
        }
    }
    private static void searchDB() {

            try {
                String searchSQL = "Select * from doctor where doc_dept=?";
                //statement = connection.createStatement();
                PreparedStatement ps = connection.prepareStatement(searchSQL);
                Scanner scn = new Scanner(System.in);
                System.out.println("Doctor Department :");
                //int doc_id = scn.nextInt();
                String doc_dept2 = scn.next();
                //ps.setInt(1, doc_id);
                ps.setString(1, doc_dept2.toUpperCase());
                resultSet = ps.executeQuery();
                while(resultSet.next()){
                    int id = resultSet.getInt("doc_id");
                    String doc_name = resultSet.getString("DOC_NAME");
                    String doc_surname = resultSet.getString("DOC_SURNAME");
                    String doc_dept = resultSet.getString("DOC_DEPT");
                    System.out.println("Doctor ID :"+id+"\tDoctor Name :"+doc_name+""
                            + "\tDoctor Surname :"+doc_surname+"\tDoctor Dept :"+doc_dept);
                }
  
        } catch (SQLException e) {
            
            System.out.println("Search Error!\t"+e.getMessage());
        }
 
    }
  
}
