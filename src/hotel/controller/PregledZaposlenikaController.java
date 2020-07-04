/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.Zaposlenik;
import hotel.model.RadnoMjesto;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;


public class PregledZaposlenikaController implements Initializable {

    @FXML
    TableView<Zaposlenik> zaposleniciTbl;
    
    @FXML
    private TableColumn<Zaposlenik, Integer> idTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> imeTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> prezimeTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> adresaTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> telTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, Float> placaTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> kimeTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> lozinkaTblCol;
    
    @FXML
    private TableColumn<Zaposlenik, String> rmjestoTblCol;
    
    @FXML
    Label idTxt;
    
    @FXML
    TextField imeTxt;
        
    @FXML
    TextField prezimeTxt;
    
    @FXML
    TextField adresaTxt;
    
    @FXML
    TextField telTxt;
        
    @FXML
    TextField placaTxt;
    
    @FXML
    TextField kimeTxt;
        
    @FXML
    TextField lozinkaTxt;
    
    @FXML
    ComboBox<RadnoMjesto> rmjestoCB;
    
    @FXML
    Button dodajBtn;
    
    @FXML
    Button azurirajBtn;
    
    @FXML
    Button izbrisiBtn;
    
    //povratak unatrag
    public void povratakNatrag(ActionEvent e){
        ((Node)(e.getSource())).getScene().getWindow().hide();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pocetnaAdmin.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hotel");
            stage.setScene(new Scene(root, 600, 415));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    //pregled radnih mjesta
    public void dodajRM(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/dodajRadnoMjesto.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pregled radnih mjesta");
            stage.setScene(new Scene(root, 671, 474));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void DetaljanPregledIzTablice() {
        zaposleniciTbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Zaposlenik zaposlenik = zaposleniciTbl.getItems().get(zaposleniciTbl.getSelectionModel().getSelectedIndex());
                idTxt.setText(String.valueOf(zaposlenik.getId()));
                imeTxt.setText(zaposlenik.getIme());
                prezimeTxt.setText(zaposlenik.getPrezime());
                adresaTxt.setText(zaposlenik.getAdresa());
                telTxt.setText(zaposlenik.getTelefon());
                placaTxt.setText(String.valueOf(zaposlenik.getPlaca()));
                kimeTxt.setText(zaposlenik.getKor_ime());
                lozinkaTxt.setText(zaposlenik.getLozinka());
                rmjestoCB.setPromptText(zaposlenik.vracanjeSvihRadnihMjesta());
            }
        });
    }
    private final ObservableList<RadnoMjesto> naziviRadnihMjesta = FXCollections.observableArrayList(
            RadnoMjesto.vratiNaziveRadnihMjesta()
     );
    //spremanje zaposlenika
    public void spasi(ActionEvent e) throws ParseException, SQLException{
        try{   
            String Ime = imeTxt.getText();
            String Prezime = prezimeTxt.getText();
            String Adresa = adresaTxt.getText();
            String Telefon = telTxt.getText();
            Float Placa = Float.parseFloat(placaTxt.getText());;
            String Korisnicko_ime = kimeTxt.getText();
            String Lozinka = lozinkaTxt.getText();
            int id_radno_mjesto = rmjestoCB.getValue().vratiId(rmjestoCB.getValue().toString());
            if(kimeTxt == null && lozinkaTxt == null){
                Zaposlenik zaposlenik = new Zaposlenik(Ime, Prezime, Adresa, Telefon, Placa, id_radno_mjesto);
                zaposlenik.spasi();
            }else{
                Zaposlenik zaposlenik = new Zaposlenik(Ime, Prezime, Adresa, Telefon, Placa, Korisnicko_ime, Lozinka, id_radno_mjesto);
                zaposlenik.spasi();
            }
            this.initialize(null, null);
            idTxt.setText("");
            imeTxt.setText("");
            prezimeTxt.setText("");
            adresaTxt.setText("");
            telTxt.setText("");
            placaTxt.setText("");
            kimeTxt.setText("");
            lozinkaTxt.setText("");
            rmjestoCB.setPromptText("");
        }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom spremanja!");
        }
    }
    //ažuriranje zaposlenika
    public void uredi(ActionEvent e) throws ParseException, SQLException{
        try {
            Zaposlenik zaposlenik = new Zaposlenik();
            zaposlenik.setId(Integer.parseInt(idTxt.getText()));
            zaposlenik.setIme(imeTxt.getText());
            zaposlenik.setPrezime(prezimeTxt.getText());
            zaposlenik.setAdresa(adresaTxt.getText());
            zaposlenik.setTelefon(telTxt.getText());
            zaposlenik.setPlaca(Float.parseFloat(placaTxt.getText()));
            zaposlenik.setKor_ime(kimeTxt.getText());
            zaposlenik.setLozinka(lozinkaTxt.getText());
            
            zaposlenik.setId_r_mjesto(rmjestoCB.getValue().vratiId(rmjestoCB.getValue().toString()));
            zaposlenik.uredi();
            this.initialize(null, null);
            idTxt.setText("");
            imeTxt.setText("");
            prezimeTxt.setText("");
            adresaTxt.setText("");
            telTxt.setText("");
            placaTxt.setText("");
            kimeTxt.setText("");
            lozinkaTxt.setText("");
            rmjestoCB.setPromptText("");
         }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom ažuriranja!"+exc.getMessage());
            } 
    }
    //brisanje zaposlenika
    public void izbrisi(ActionEvent e){
        Zaposlenik zaposlenik = new Zaposlenik();
        zaposlenik.setId(Integer.parseInt(idTxt.getText()));
        zaposlenik.izbrisi();
        this.initialize(null, null);
        idTxt.setText("");
        imeTxt.setText("");
        prezimeTxt.setText("");
        adresaTxt.setText("");
        telTxt.setText("");
        placaTxt.setText("");
        kimeTxt.setText("");
        lozinkaTxt.setText("");
        rmjestoCB.setPromptText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<Zaposlenik> data = Zaposlenik.vratiSveZaposlenike();
        idTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, Integer>("id"));
        imeTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("ime"));
        prezimeTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("prezime"));
        adresaTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("adresa"));
        telTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("telefon"));
        placaTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, Float>("placa"));
        kimeTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("kor_ime"));
        lozinkaTblCol.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("lozinka"));
        rmjestoTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik,String>,ObservableValue<String>>() {
        @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param) {
      
                    return new SimpleStringProperty(param.getValue().vracanjeSvihRadnihMjesta());      
            }
        });
        
        zaposleniciTbl.setItems(data);
        DetaljanPregledIzTablice();
        rmjestoCB.setItems(naziviRadnihMjesta);
    }    
    
}
