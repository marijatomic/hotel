/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.Gost;
import hotel.model.Rezervacija;
import hotel.model.Soba;
import hotel.model.Zaposlenik;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

public class RezervacijeZaposlenikController implements Initializable {
    
    @FXML
    TableView<Rezervacija> rezervacijeTbl;
    
    @FXML
    private TableColumn<Rezervacija, Integer> idTblCol;
    
    @FXML
    private TableColumn<Rezervacija, Date> datumRTblCol;
    
    @FXML
    private TableColumn<Rezervacija, Date> datumDTblCol;
    
    @FXML
    private TableColumn<Rezervacija, Date> datumPTblCol;
    
    @FXML
    private TableColumn<Rezervacija, String> brojTblCol;
    
    @FXML
    private TableColumn<Rezervacija, String> gostTblCol;
    
    @FXML
    private TableColumn<Rezervacija, String> zaposlenikTblCol;
    
    @FXML 
    Label idTxt;
    
    @FXML 
    DatePicker datumRTxt;
    
    @FXML
    DatePicker datumDTxt;
    
    @FXML
    DatePicker datumPTxt;
    
    @FXML 
    ComboBox<Soba> brojCB;
    
    @FXML 
    ComboBox<Gost> gostCB;
      
    @FXML 
    ComboBox<Zaposlenik> zaposlenikCB;
    
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
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pocetna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hotel");
            stage.setScene(new Scene(root, 600, 415));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    private final ObservableList<Soba> brojeviSoba = FXCollections.observableArrayList(
            Soba.vratiBrojSobe()
     );
    private final ObservableList<Gost> ImeiPrezimeGosta = FXCollections.observableArrayList(
            Gost.vratiImeiPrezimeGosta()
     );
    private final ObservableList<Zaposlenik> ImeiPrezimeZaposlenika = FXCollections.observableArrayList(
            Zaposlenik.vratiImeiPrezimeZaposlenika()
     );
    private void DetaljanPregledIzTablice() {
        rezervacijeTbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Rezervacija rezervacija = rezervacijeTbl.getItems().get(rezervacijeTbl.getSelectionModel().getSelectedIndex());
                idTxt.setText(String.valueOf(rezervacija.getId()));
                datumRTxt.setPromptText(String.valueOf(rezervacija.getDatum_rezervacije()));
                datumDTxt.setPromptText(String.valueOf(rezervacija.getDatum_dolaska()));
                datumPTxt.setPromptText(String.valueOf(rezervacija.getDatum_polaska()));
                brojCB.setPromptText(rezervacija.vracanjeSvihSoba());
                gostCB.setPromptText(rezervacija.dajGosta());
                zaposlenikCB.setPromptText(rezervacija.dajZaposlenuka());
            }
        });
    }
    
    //spremanje rezervacije
    public void spasi(ActionEvent e) throws ParseException, SQLException{
        try{
            LocalDate datumR = datumRTxt.getValue();
            java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(datumR.toString());
            
            LocalDate datumD = datumDTxt.getValue();
            java.util.Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(datumD.toString());
            
            LocalDate datumP = datumPTxt.getValue();
            java.util.Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(datumP.toString());
            
            
            int id_soba = brojCB.getValue().vratiIdSobe(brojCB.getValue().toString());
            
            int id_zaposlenik = zaposlenikCB.getValue().dajIdZaposlenika(zaposlenikCB.getValue().toString());
            
            int id_gost = gostCB.getValue().dajIdGosta(gostCB.getValue().toString());
            
            Rezervacija rezervacja = new Rezervacija(d, d1, d2, id_soba, id_zaposlenik, id_gost);
            rezervacja.spasi();
            
            this.initialize(null, null);
            idTxt.setText("");
            datumRTxt.setPromptText("");
            datumDTxt.setPromptText("");
            datumPTxt.setPromptText("");
            brojCB.setPromptText("");
            zaposlenikCB.setPromptText("");
            gostCB.setPromptText("");
        }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom spremanja!");
        }
    }
    //ažuriranje rezervacije
    public void uredi(ActionEvent e) throws ParseException, SQLException{
        try {
            Rezervacija rezervacija = new Rezervacija();
            rezervacija.setId(Integer.parseInt(idTxt.getText()));
            LocalDate datumR = datumRTxt.getValue();
            java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(datumR.toString());
            rezervacija.setDatum_dolaska(d);
            LocalDate datumD = datumDTxt.getValue();
            java.util.Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(datumD.toString());
            rezervacija.setDatum_dolaska(d1);
            LocalDate datumP = datumPTxt.getValue();
            java.util.Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(datumP.toString());
            rezervacija.setDatum_polaska(d2);
            
            rezervacija.setId_soba(brojCB.getValue().vratiIdSobe(brojCB.getValue().toString()));
            rezervacija.setId_zaposlenik(zaposlenikCB.getValue().dajIdZaposlenika(zaposlenikCB.getValue().toString()));
            rezervacija.setId_gost(gostCB.getValue().dajIdGosta(gostCB.getValue().toString()));
            rezervacija.uredi();
            this.initialize(null, null);
            idTxt.setText("");
            datumRTxt.setPromptText("");
            datumDTxt.setPromptText("");
            datumPTxt.setPromptText("");
            brojCB.setPromptText("");
            zaposlenikCB.setPromptText("");
            gostCB.setPromptText("");
         }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom ažuriranja!"+exc.getMessage());
            } 
    }
    //brisanje rezervacije
    public void izbrisi(ActionEvent e){
        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setId(Integer.parseInt(idTxt.getText()));
        rezervacija.izbrisi();
        this.initialize(null, null);
        idTxt.setText("");
        datumRTxt.setPromptText("");
        datumDTxt.setPromptText("");
        datumPTxt.setPromptText("");
        brojCB.setPromptText("");
        zaposlenikCB.setPromptText("");
        gostCB.setPromptText("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<Rezervacija> data = Rezervacija.vratiSveRezervacije();
        idTblCol.setCellValueFactory(new PropertyValueFactory<Rezervacija, Integer>("id"));
        datumRTblCol.setCellValueFactory(new PropertyValueFactory<Rezervacija, Date>("datum_rezervacije"));
        datumDTblCol.setCellValueFactory(new PropertyValueFactory<Rezervacija, Date>("datum_dolaska"));
        datumPTblCol.setCellValueFactory(new PropertyValueFactory<Rezervacija, Date>("datum_polaska"));
        brojTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rezervacija,String>,ObservableValue<String>>() {
        @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Rezervacija, String> param) {
      
                    return new SimpleStringProperty(param.getValue().vracanjeSvihSoba());      
            }
        });
        gostTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rezervacija,String>,ObservableValue<String>>() {
        @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Rezervacija, String> param) {
      
                    return new SimpleStringProperty(param.getValue().dajGosta());      
            }
        });
        zaposlenikTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rezervacija,String>,ObservableValue<String>>() {
        @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Rezervacija, String> param) {
      
                    return new SimpleStringProperty(param.getValue().dajZaposlenuka());      
            }
        });
        
        rezervacijeTbl.setItems(data);
        DetaljanPregledIzTablice();
        brojCB.setItems(brojeviSoba);
        gostCB.setItems(ImeiPrezimeGosta);
        zaposlenikCB.setItems(ImeiPrezimeZaposlenika);
    }    
    
}
