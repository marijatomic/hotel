/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;
import static hotel.model.Baza.connection;
import static hotel.model.VrstaSobe.vratiNaziveVrstaSoba;
import static hotel.model.VrstaSobe.vratiSveVrsteSoba;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Soba {
    private int id;
    private int broj_sobe;
    private int id_vrsta_sobe;
    
    public static final int ID=1;
    public static final int BROJ_SOBE=2;
    public static final int ID_VRSTA_SOBE=3;
    public static final int br_sobe = 1;

    public Soba() {
        this.id = 0;
        this.broj_sobe = 0;
        this.id_vrsta_sobe = 0;
    }

    public Soba(int broj_sobe, int id_vrsta_sobe) {
        this.broj_sobe = broj_sobe;
        this.id_vrsta_sobe = id_vrsta_sobe;
    }

    public Soba(int broj_sobe) {
        this.broj_sobe = broj_sobe;
    }

    public Soba(int id, int broj_sobe, int id_vrsta_sobe) {
        this.id = id;
        this.broj_sobe = broj_sobe;
        this.id_vrsta_sobe = id_vrsta_sobe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBroj_sobe() {
        return broj_sobe;
    }

    public void setBroj_sobe(int broj_sobe) {
        this.broj_sobe = broj_sobe;
    }

    public int getId_vrsta_sobe() {
        return id_vrsta_sobe;
    }

    public void setId_vrsta_sobe(int id_vrsta_sobe) {
        this.id_vrsta_sobe = id_vrsta_sobe;
    }
    public void spasi() {
        try {
            Baza.spoji(); 
            PreparedStatement statement = connection.prepareStatement("INSERT INTO soba VALUES(null, ?,?)");
            statement.setInt(1, this.broj_sobe);
            statement.setInt(2, this.id_vrsta_sobe);
            statement.executeUpdate();
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
    }
    public void uredi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE soba SET broj_sobe=?, id_vrsta_sobe=? WHERE id = ?");
            ps.setInt(1, this.broj_sobe);
            ps.setInt(2, this.id_vrsta_sobe);
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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM soba WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
     //metoda koja vraća sve sobe
    public static ObservableList<Soba> vratiSveSobe () {
        ObservableList<Soba> soba = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT * FROM soba");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Soba s;
                s = new Soba(rs.getInt(ID), rs.getInt(BROJ_SOBE), rs.getInt(ID_VRSTA_SOBE));
                soba.add(s);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return soba;
    }
    //metoda koja vraća samo brojeve sobe
    public static ObservableList<Soba> vratiBrojSobe () {
        ObservableList<Soba> soba = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT broj_sobe FROM soba");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Soba s;
                s = new Soba(rs.getInt(br_sobe));
                soba.add(s);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return soba;
    }
    //metoda koja vraća naziv sobe
    public String vratiVrstuSobe() {
        ObservableList <VrstaSobe> nazivi_soba = vratiSveVrsteSoba();
        for (VrstaSobe vs : nazivi_soba) {
            if (vs.getId() == this.id_vrsta_sobe) 
                return vs.getNaziv();
        }
        return null;
    }
    
    public int vratiIdSobe(String s) throws SQLException {
        Baza.spoji();
        int i = Integer.parseInt(s);
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM soba WHERE broj_sobe = ?");
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return 0;
    }  
    @Override
    public String toString(){
        return String.valueOf(this.getBroj_sobe());
    }
}
