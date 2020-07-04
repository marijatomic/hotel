/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;
import static hotel.model.Baza.connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RadnoMjesto {
    private int id;
    private String mjesto;
    private String opis;
    
    public static final int ID=1;
    public static final int MJESTO=2;
    public static final int OPIS=3;
    
    public static final int RM=1;
    
    public RadnoMjesto() {
        this.id = 0;
        this.mjesto = "";
        this.opis = "";
    }

    public RadnoMjesto(String mjesto) {
        this.mjesto = mjesto;
    }
    
    public RadnoMjesto(String mjesto, String opis) {
        this.mjesto = mjesto;
        this.opis = opis;
    }

    public RadnoMjesto(int id, String mjesto, String opis) {
        this.id = id;
        this.mjesto = mjesto;
        this.opis = opis;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    public void spasi() {
        try {
            Baza.spoji(); 
            PreparedStatement statement = connection.prepareStatement("INSERT INTO r_mjesto VALUES(null, ?,?)");
            statement.setString(1, this.mjesto);
            statement.setString(2, this.opis);
            statement.executeUpdate();
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
    }
    public void uredi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE r_mjesto SET mjesto=?, opis=? WHERE id = ?");
            ps.setString(1, this.mjesto);
            ps.setString(2, this.opis);
            ps.setInt(3, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    public void izbrisi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM r_mjesto WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    //metoda koja vraća sva radna mjesta
    public static ObservableList<RadnoMjesto> vratiSvaRadnaMjesta () {
        ObservableList<RadnoMjesto> mjesto = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT * FROM r_mjesto");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                RadnoMjesto rm;
                rm = new RadnoMjesto(rs.getInt(ID), rs.getString(MJESTO), rs.getString(OPIS));
                mjesto.add(rm);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return mjesto;
    }
    //metoda koja vraća sve nazive radnih mjesta  
    public static ObservableList<RadnoMjesto> vratiNaziveRadnihMjesta () {
        ObservableList<RadnoMjesto> mjesto = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st = connection.prepareStatement("SELECT mjesto FROM r_mjesto");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                RadnoMjesto rm;
                rm = new RadnoMjesto(rs.getString(RM));
                mjesto.add(rm);
            }
            Baza.odspoji();
        } catch (SQLException ex) {
        }
        return mjesto;
    }
    //metoda vraća id prosljeđenog radnog mjesta
    public int vratiId(String rm) throws SQLException {
        Baza.spoji();
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM r_mjesto WHERE mjesto = ?");
        ps.setString(1, rm);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return 0;
    }  
    @Override
    public String toString(){
        return this.mjesto;
    }
}
