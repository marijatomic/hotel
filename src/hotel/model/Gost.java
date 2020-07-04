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

public class Gost {
    private int id;
    private String ime;
    private String prezime;
    private String adresa;
    private String telefon;
    private String br_racuna;
    
    public static final int ID=1;
    public static final int IME=2;
    public static final int PREZIME=3;
    public static final int ADRESA=4;
    public static final int TELEFON=5;
    public static final int BR_RACUNA=6;
    private static final int Ime = 1;
    private static final int Prezime = 2;

    public Gost() {
        this.id = 0;
        this.ime = "";
        this.prezime = "";
        this.adresa = "";
        this.telefon = "";
        this.br_racuna = "";
    }

    public Gost(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public Gost(String ime, String prezime, String adresa, String telefon, String br_racuna) {
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.br_racuna = br_racuna;
    }

    public Gost(int id, String ime, String prezime, String adresa, String telefon, String br_racuna) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.br_racuna = br_racuna;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getBr_racuna() {
        return br_racuna;
    }

    public void setBr_racuna(String br_racuna) {
        this.br_racuna = br_racuna;
    }
    public void spasi() {
        try {
            Baza.spoji(); 
            PreparedStatement statement = connection.prepareStatement("INSERT INTO gost VALUES(null, ?,?,?,?,?)");
            statement.setString(1, this.ime);
            statement.setString(2, this.prezime);
            statement.setString(3, this.adresa);
            statement.setString(4, this.telefon);
            statement.setString(5, this.br_racuna);
            statement.executeUpdate();
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
    }
    public void uredi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE gost SET ime=?, prezime=?, adresa=?, telefon=?, br_racuna=? WHERE id = ?");
            ps.setString(1, this.ime);
            ps.setString(2, this.prezime);
            ps.setString(3, this.adresa);
            ps.setString(4, this.telefon);
            ps.setString(5, this.br_racuna);
            ps.setInt(6, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    public void izbrisi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM gost WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    //metoda koja vraća sve goste
    public static ObservableList<Gost> vratiSveGoste () {
        ObservableList<Gost> gost = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT * FROM gost");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Gost g;
                g = new Gost(rs.getInt(ID), rs.getString(IME), rs.getString(PREZIME), 
                rs.getString(ADRESA), rs.getString(TELEFON), rs.getString(BR_RACUNA));
                gost.add(g);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return gost;
    }
    //metoda koja vraća listu gostiju, njihovo ime i prezime
    public static ObservableList<Gost> vratiImeiPrezimeGosta () {
        ObservableList<Gost> gost = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st = connection.prepareStatement("SELECT ime, prezime FROM gost");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Gost g;
                g = new Gost(rs.getString(Ime), rs.getString(Prezime));
                gost.add(g);
            }
            Baza.odspoji();
        } catch (SQLException ex) {
        }
        return gost;
    }
    
    //metoda prima string ime i prezime gosta i za to ime i prezime vraća id gosta
    public int dajIdGosta(String ime_i_prezime) throws SQLException {
        String[] ime_i_prez = ime_i_prezime.split("\\s+");
        for (int i = 0; i < ime_i_prez.length; i++) {
            ime_i_prez[i] = ime_i_prez[i].replaceAll("[^\\w]", "");
        }
        Baza.spoji();
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM gost WHERE ime = ? AND prezime = ?");
        ps.setString(1, ime_i_prez[0]);
        ps.setString(2, ime_i_prez[1]);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return this.getIme() + " " + this.getPrezime();
    }
}
