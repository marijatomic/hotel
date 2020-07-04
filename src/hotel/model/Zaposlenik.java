/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import static hotel.model.Baza.connection;
import static hotel.model.RadnoMjesto.vratiSvaRadnaMjesta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Zaposlenik {
    private int id;
    private String ime;
    private String prezime;
    private String adresa;
    private String telefon;
    private float placa;
    private String kor_ime;
    private String lozinka;
    private int id_r_mjesto;
    
    public static final int ID=1;
    public static final int IME=2;
    public static final int PREZIME=3;
    public static final int TELEFON=4;
    public static final int ADRESA=5;
    public static final int PLACA=6;
    public static final int KOR_IME =7;
    public static final int LOZINKA = 8;
    public static final int ID_R_MJESTO=9;
    private static final int Ime = 1;
    private static final int Prezime = 2;

    public Zaposlenik() {
        this.id = 0;
        this.ime = "";
        this.prezime = "";
        this.adresa = "";
        this.telefon = "";
        this.placa = 0.0f;
        this.kor_ime = "";
        this.lozinka = "";
        this.id_r_mjesto = 0;
    }

    public Zaposlenik(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public Zaposlenik(String ime, String prezime, String adresa, String telefon, float placa, int id_r_mjesto) {
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.placa = placa;
        this.id_r_mjesto = id_r_mjesto;
    }

    public Zaposlenik(String ime, String prezime, String adresa, String telefon, float placa, String kor_ime, String lozinka, int id_r_mjesto) {
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.placa = placa;
        this.kor_ime = kor_ime;
        this.lozinka = lozinka;
        this.id_r_mjesto = id_r_mjesto;
    }

    public Zaposlenik(int id, String ime, String prezime, String adresa, String telefon, float placa, String kor_ime, String lozinka, int id_r_mjesto) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.placa = placa;
        this.kor_ime = kor_ime;
        this.lozinka = lozinka;
        this.id_r_mjesto = id_r_mjesto;
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

    public float getPlaca() {
        return placa;
    }

    public void setPlaca(float placa) {
        this.placa = placa;
    }

    public String getKor_ime() {
        return kor_ime;
    }

    public void setKor_ime(String kor_ime) {
        this.kor_ime = kor_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public int getId_r_mjesto() {
        return id_r_mjesto;
    }

    public void setId_r_mjesto(int id_r_mjesto) {
        this.id_r_mjesto = id_r_mjesto;
    }
    public void spasi() {
        try {
            Baza.spoji(); 
            PreparedStatement statement = connection.prepareStatement("INSERT INTO zaposlenik VALUES(null, ?,?,?,?,?,?,?,?)");
            statement.setString(1, this.ime);
            statement.setString(2, this.prezime);
            statement.setString(3, this.adresa);
            statement.setString(4, this.telefon);
            statement.setFloat(5, this.placa);
            statement.setString(6, this.kor_ime);
            statement.setString(7, this.lozinka);
            statement.setInt(8, this.id_r_mjesto);
            statement.executeUpdate();
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
    }
    public void uredi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE zaposlenik SET ime=?, prezime=?, adresa=?, telefon=?, placa=?, kor_ime=?, lozinka=?, id_r_mjesto=? WHERE id = ?");
            ps.setString(1, this.ime);
            ps.setString(2, this.prezime);
            ps.setString(3, this.adresa);
            ps.setString(4, this.telefon);
            ps.setFloat(5, this.placa);
            ps.setString(6, this.kor_ime);
            ps.setString(7, this.lozinka);
            ps.setInt(8, this.id_r_mjesto);
            ps.setInt(9, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    public void izbrisi() {
        Baza.spoji();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM zaposlenik WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Došlo je do greške!" + ex.getMessage());
        }
        Baza.odspoji();
    }
    //metoda koja vraća sve zaposlenike
    public static ObservableList<Zaposlenik> vratiSveZaposlenike () {
        ObservableList<Zaposlenik> zaposlenik = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st= connection.prepareStatement("SELECT * FROM zaposlenik");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Zaposlenik z;
                z = new Zaposlenik(rs.getInt(ID), rs.getString(IME), rs.getString(PREZIME), 
                rs.getString(ADRESA), rs.getString(TELEFON), rs.getFloat(PLACA),
                rs.getString(KOR_IME), rs.getString(LOZINKA), rs.getInt(ID_R_MJESTO));
                zaposlenik.add(z);
            }
            Baza.odspoji();
            
        } catch (SQLException ex) {
        }
        return zaposlenik;
    }
    //metoda koja vraća listu zaposlenika, njihovo ime i prezime
    public static ObservableList<Zaposlenik> vratiImeiPrezimeZaposlenika () {
        ObservableList<Zaposlenik> zaposlenik = FXCollections.observableArrayList();
        try {
            Baza.spoji();
            PreparedStatement st = connection.prepareStatement("SELECT ime, prezime FROM zaposlenik");
            ResultSet rs= st.executeQuery();
            while (rs.next()) {
                Zaposlenik z;
                z = new Zaposlenik(rs.getString(Ime), rs.getString(Prezime));
                zaposlenik.add(z);
            }
            Baza.odspoji();
        } catch (SQLException ex) {
        }
        return zaposlenik;
    }
    
    //metoda prima string ime i prezime zaposlenika i za to ime i prezime vraća id gosta
    public int dajIdZaposlenika(String ime_i_prezime) throws SQLException {
        String[] ime_i_prez = ime_i_prezime.split("\\s+");
        for (int i = 0; i < ime_i_prez.length; i++) {
            ime_i_prez[i] = ime_i_prez[i].replaceAll("[^\\w]", "");
        }
        Baza.spoji();
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM zaposlenik WHERE ime = ? AND prezime = ?");
        ps.setString(1, ime_i_prez[0]);
        ps.setString(2, ime_i_prez[1]);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); 
        }
        return 0;
    }
    
    // metoda koja nam vraća id logiranog zaposlenika
    public int VratiIdLogiranogKorisnika() {
        try {
            Baza.spoji();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM zaposlenik WHERE kor_ime =? AND lozinka=?");
            ps.setString(1, this.kor_ime);
            ps.setString(2, this.lozinka);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id=rs.getInt("id");
                return id;
            }
            Baza.odspoji();
        } catch (SQLException ex) {
            System.out.println("Greška!" + ex.getMessage());    
        }  
        return 0;
    }
    //provjera jesu li korisničko ime i lozinka točni
    public static boolean login (String k_ime, String lozinka) {
        try {
            Baza.spoji();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM zaposlenik WHERE kor_ime =? AND lozinka=?");
            ps.setString(1, k_ime);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Greška!" + ex.getMessage());
            return false;
        }
    }
    //metoda vraća tip zaposlenika
    public static String tip(String korisnicko_ime, String lozinka){
        try {
            String mjesto = "";
            Baza.spoji();
            PreparedStatement ps = connection.prepareStatement("SELECT r_mjesto.mjesto FROM zaposlenik, r_mjesto WHERE zaposlenik.kor_ime = ? AND zaposlenik.lozinka = ? AND zaposlenik.id_r_mjesto = r_mjesto.id");
            ps.setString(1, korisnicko_ime);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mjesto = rs.getString("mjesto");
            }
            if (mjesto.equals("administrator")) {
                return "admin";
            } else {
                return "zaposlenik";
            }
        } catch (SQLException ex) {
            System.out.println("Greška!"+ ex.getMessage());
            return "greska";
        }
    }
    //metoda koja vraća naziv radnog mjesta
    public String vracanjeSvihRadnihMjesta () {
        ObservableList <RadnoMjesto> radno_mjesto = vratiSvaRadnaMjesta();
        for (RadnoMjesto rm : radno_mjesto) {
            if (rm.getId() == this.id_r_mjesto) 
                return rm.getMjesto();
        }
        return null;
    }
    
    @Override
    public String toString(){
        return this.getIme() + " " + this.getPrezime();
    }
}
