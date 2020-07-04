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

public class VrstaSobe {
    private int id;
    private String naziv;
    private float cijena;
    private String opis;
    
    public static final int ID=1;
    public static final int NAZIV=2;
    public static final int CIJENA=3;
    public static final int OPIS=4;
    
    public static final int VS=1;

    public VrstaSobe() {
        this.id = 0;
        this.naziv = "";
        this.cijena = 0.0f;
        this.opis = "";
    }

    public VrstaSobe(String naziv) {
        this.naziv = naziv;
    }

    public VrstaSobe(String naziv, float cijena, String opis) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.opis = opis;
    }
    

    public VrstaSobe(int id, String naziv, float cijena, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.cijena = cijena;
        this.opis = opis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public float getCijena() {
        return cijena;
    }

    public void setCijena(float cijena) {
        this.cijena = cijena;
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO vrsta_sobe VALUES(null, ?,?,?)");
            statement.setString(1, this.naziv);
            statement.setFloat(2, this.cijena);
            statement.setString(3, this.opis);
            statement.executeUpdate();
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
    }
    
    public void uredi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE vrsta_sobe SET naziv=?, cijena=?, opis=? WHERE id = ?");
            ps.setString(1, this.naziv);
            ps.setFloat(2, this.cijena);
            ps.setString(3, this.opis);
            ps.setInt(4, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    
    public void izbrisi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM vrsta_sobe WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    //metoda koja vraća sve vrste soba
    public static ObservableList<VrstaSobe> vratiSveVrsteSoba () {
        ObservableList<VrstaSobe> vrsta_s = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT * FROM vrsta_sobe");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                VrstaSobe vs;
                vs = new VrstaSobe(rs.getInt(ID), rs.getString(NAZIV), rs.getFloat(CIJENA), rs.getString(OPIS));
                vrsta_s.add(vs);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return vrsta_s;
    }
    //metoda koja vraća sve nazive vrsta soba  
    public static ObservableList<VrstaSobe> vratiNaziveVrstaSoba () {
        ObservableList<VrstaSobe> vrsta_s = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st = connection.prepareStatement("SELECT naziv FROM vrsta_sobe");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                VrstaSobe vs;
                vs = new VrstaSobe(rs.getString(VS));
                vrsta_s.add(vs);
            }
            Baza.odspoji();
        } catch (SQLException ex) {
        }
        return vrsta_s;
    }
    //metoda vraća id prosljeđene vrste sobe
    public int vratiId(String vs) throws SQLException {
        Baza.spoji();
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM vrsta_sobe WHERE naziv = ?");
        ps.setString(1, vs);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return 0;
    } 
    @Override
    public String toString(){
        return this.naziv;
    }
}
