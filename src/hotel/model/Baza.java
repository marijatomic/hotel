/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Baza {
    protected static Connection connection; 
    
    public static void spoji() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://"+Config.HOSTNAME+ "/"+Config.DATABASE + "?useUnicode=true&characterEncoding=utf-8" + "&user="+Config.USERNAME+"&password="+Config.PASSWORD;
            try {
                connection = (Connection)DriverManager.getConnection(url);
            } catch (SQLException ex) {
                System.out.println("Nastala je greška, neuspješno spajanje na bazu.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Nastala je greška, ne mogu pronaći klasu com.mysql.jdbc.Driver.");
        }
    }
    
    public static void odspoji (){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom odspajanja!");
        }
    }
}
