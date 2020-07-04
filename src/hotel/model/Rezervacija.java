/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;
import static hotel.model.Baza.connection;
import static hotel.model.Gost.vratiSveGoste;
import static hotel.model.Soba.vratiBrojSobe;
import static hotel.model.Soba.vratiSveSobe;
import static hotel.model.Zaposlenik.vratiSveZaposlenike;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rezervacija {
    private int id;
    private Date datum_rezervacije;
    private Date datum_dolaska;
    private Date datum_polaska;
    private int id_soba;
    private int id_zaposlenik;
    private int id_gost;
    
    public static final int ID=1;
    public static final int DATUM_REZERVACIJE=2;
    public static final int DATUM_DOLASKA=3;
    public static final int DATUM_POLASKA=4;
    public static final int ID_SOBA=5;
    public static final int ID_ZAPOSLENIK=6;
    public static final int ID_GOST=7;

    public Rezervacija() {
    }

    public Rezervacija(Date datum_dolaska, Date datum_polaska, int id_soba, int id_zaposlenik, int id_gost) {
        this.datum_dolaska = datum_dolaska;
        this.datum_polaska = datum_polaska;
        this.id_soba = id_soba;
        this.id_zaposlenik = id_zaposlenik;
        this.id_gost = id_gost;
    }

    public Rezervacija(Date datum_rezervacije, Date datum_dolaska, Date datum_polaska, int id_soba, int id_zaposlenik, int id_gost) {
        this.datum_rezervacije = datum_rezervacije;
        this.datum_dolaska = datum_dolaska;
        this.datum_polaska = datum_polaska;
        this.id_soba = id_soba;
        this.id_zaposlenik = id_zaposlenik;
        this.id_gost = id_gost;
    }
    

    public Rezervacija(int id, Date datum_rezervacije, Date datum_dolaska, Date datum_polaska, int id_soba, int id_zaposlenik, int id_gost) {
        this.id = id;
        this.datum_rezervacije = datum_rezervacije;
        this.datum_dolaska = datum_dolaska;
        this.datum_polaska = datum_polaska;
        this.id_soba = id_soba;
        this.id_zaposlenik = id_zaposlenik;
        this.id_gost = id_gost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum_rezervacije() {
        return datum_rezervacije;
    }

    public void setDatum_rezervacije(Date datum_rezervacije) {
        this.datum_rezervacije = datum_rezervacije;
    }

    public Date getDatum_dolaska() {
        return datum_dolaska;
    }

    public void setDatum_dolaska(Date datum_dolaska) {
        this.datum_dolaska = datum_dolaska;
    }

    public Date getDatum_polaska() {
        return datum_polaska;
    }

    public void setDatum_polaska(Date datum_polaska) {
        this.datum_polaska = datum_polaska;
    }

    public int getId_soba() {
        return id_soba;
    }

    public void setId_soba(int id_soba) {
        this.id_soba = id_soba;
    }

    public int getId_zaposlenik() {
        return id_zaposlenik;
    }

    public void setId_zaposlenik(int id_zaposlenik) {
        this.id_zaposlenik = id_zaposlenik;
    }

    public int getId_gost() {
        return id_gost;
    }

    public void setId_gost(int id_gost) {
        this.id_gost = id_gost;
    }
    public void spasi() {
        try {
            Baza.spoji(); 
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rezervacija VALUES(null, ?,?,?,?,?,?)");
            java.sql.Date sqlDate = new java.sql.Date(this.datum_rezervacije.getTime());
            statement.setDate(1, sqlDate);
            java.sql.Date sqlDate1 = new java.sql.Date(this.datum_dolaska.getTime());
            statement.setDate(2, sqlDate1);
            java.sql.Date sqlDate2 = new java.sql.Date(this.datum_polaska.getTime());
            statement.setDate(3, sqlDate2);
            statement.setInt(4, this.id_soba);
            statement.setInt(5, this.id_zaposlenik);
            statement.setInt(6, this.id_gost);
            statement.executeUpdate();
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
    }
    public void uredi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE rezervacija SET datum_rezervacije=?, datum_dolaska=?, datum_polaska=?, id_soba=?,id_zaposlenik=?, id_gost=?  WHERE id = ?");
            java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            ps.setDate(1, sqlDate);
            java.sql.Date sqlDate1 = new java.sql.Date(this.datum_dolaska.getTime());
            ps.setDate(2, sqlDate1);
            java.sql.Date sqlDate2 = new java.sql.Date(this.datum_polaska.getTime());
            ps.setDate(3, sqlDate2);
            ps.setInt(4, this.id_soba);
            ps.setInt(5, this.id_zaposlenik);
            ps.setInt(6, this.id_gost);
            ps.setInt(7, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    public void izbrisi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM rezervacija WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    //metoda koja vraća sve rezervacije
    public static ObservableList<Rezervacija> vratiSveRezervacije () {
        ObservableList<Rezervacija> rezervacija = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT * FROM rezervacija");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Rezervacija r;
                r = new Rezervacija(rs.getInt(ID), rs.getDate(DATUM_REZERVACIJE), 
                        rs.getDate(DATUM_DOLASKA), rs.getDate(DATUM_POLASKA), rs.getInt(ID_SOBA), rs.getInt(ID_ZAPOSLENIK), rs.getInt(ID_GOST));
                rezervacija.add(r);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return rezervacija;
    }
    //metoda koja vraća sve sobe
    public String vracanjeSvihSoba () {
        ObservableList <Soba> soba = vratiSveSobe();
        for (Soba s : soba) {
            if (s.getId() == this.id_soba) 
                return String.valueOf(s.getBroj_sobe());
        }
        return null;
    }
    //metoda koja vraća ime i prezime gosta
    public String dajGosta() {
        ObservableList <Gost> gost = vratiSveGoste();
        for (Gost g : gost) {
            if (g.getId() == this.getId_gost()) 
                return g.getIme() + " " + g.getPrezime();
        }
        return null;
    }
    //metoda koja vraća ime i prezime zaposlenika
    public String dajZaposlenuka() {
        ObservableList <Zaposlenik> zaposlenik = vratiSveZaposlenike();
        for (Zaposlenik z : zaposlenik) {
            if (z.getId() == this.getId_zaposlenik()) 
                return z.getIme() + " " + z.getPrezime();
        }
        return null;
    }
}
