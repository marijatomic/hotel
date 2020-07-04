/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.RadnoMjesto;
import hotel.model.Soba;
import hotel.model.VrstaSobe;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;


public class DodajRadnoMjestoController implements Initializable {
    
    @FXML
    TableView<RadnoMjesto> mjestoTbl;
    
    @FXML
    private TableColumn<RadnoMjesto, Integer> idTblCol;
    
    @FXML
    private TableColumn<RadnoMjesto, String> mjestoTblCol;
    
    @FXML
    private TableColumn<RadnoMjesto, String> opisTblCol;
    
    @FXML
    Label idTxt;
    
    @FXML
    TextField mjestoTxt;
    
    @FXML
    TextField opisTxt;
    
    @FXML
    Button dodajBtn;
    
    @FXML
    Button azurirajBtn;
    
    @FXML
    Button izbrisiBtn;
    
    //povratak unatrag
    public void povratakNatrag(ActionEvent e){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pregledZaposlenika.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pregled zaposlenika");
            stage.setScene(new Scene(root, 671, 584));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    private void PrikaziDetaljno() {
        mjestoTbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                RadnoMjesto rm = mjestoTbl.getItems().get(mjestoTbl.getSelectionModel().getSelectedIndex());
                idTxt.setText(String.valueOf(rm.getId()));
                mjestoTxt.setText(rm.getMjesto());
                opisTxt.setText(rm.getOpis());
            }
        });
    }
    
    //spremanje radnog mjesta
    public void spasi(ActionEvent e) throws ParseException, SQLException{
        try{   
            String mjesto = mjestoTxt.getText();
            String opis = opisTxt.getText();
            RadnoMjesto rm = new RadnoMjesto(mjesto, opis);
            rm.spasi();
            this.initialize(null, null);
            idTxt.setText("");
            mjestoTxt.setText("");
            opisTxt.setText("");
        }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom spremanja!");
        }
    }
    //ažuriranje radno mjesto
    public void uredi(ActionEvent e) throws ParseException, SQLException{
        try {
            RadnoMjesto rm = new RadnoMjesto();
            rm.setId(Integer.parseInt(idTxt.getText()));
            rm.setMjesto(mjestoTxt.getText());
            rm.setOpis(opisTxt.getText());
            rm.uredi();
            this.initialize(null, null);
            idTxt.setText("");
            mjestoTxt.setText("");
            opisTxt.setText("");
         }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom ažuriranja!"+exc.getMessage());
            } 
    }
    //brisanje radno mjesto
    public void izbrisi(ActionEvent e){
        RadnoMjesto rm = new RadnoMjesto();
        rm.setId(Integer.parseInt(idTxt.getText()));
        rm.izbrisi();
        this.initialize(null, null);
        idTxt.setText("");
        mjestoTxt.setText("");
        opisTxt.setText("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<RadnoMjesto> data = RadnoMjesto.vratiSvaRadnaMjesta();
        idTblCol.setCellValueFactory(new PropertyValueFactory<RadnoMjesto, Integer>("id"));
        mjestoTblCol.setCellValueFactory(new PropertyValueFactory<RadnoMjesto, String>("mjesto"));
        opisTblCol.setCellValueFactory(new PropertyValueFactory<RadnoMjesto, String>("opis"));
        
        mjestoTbl.setItems(data);
        PrikaziDetaljno();
    }    
    
}
