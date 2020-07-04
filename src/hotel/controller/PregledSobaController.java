/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

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

public class PregledSobaController implements Initializable {

    @FXML
    TableView<Soba> sobeTbl;
    
    @FXML
    private TableColumn<Soba, Integer> idTblCol;
    
    @FXML
    private TableColumn<Soba, Integer> brojTblCol;
    
    @FXML
    private TableColumn<Soba, String> vrstaTblCol;
    
    @FXML
    Label idTxt;
    
    @FXML
    TextField brojTxt;
    
    @FXML
    ComboBox<VrstaSobe> vrstaCB;
    
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
    
    private void PrikaziDetaljno() {
        sobeTbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Soba s = sobeTbl.getItems().get(sobeTbl.getSelectionModel().getSelectedIndex());
                idTxt.setText(String.valueOf(s.getId()));
                brojTxt.setText(String.valueOf(s.getBroj_sobe()));
                vrstaCB.setPromptText(s.vratiVrstuSobe());
            }
        });
    }
    private final ObservableList<VrstaSobe> naziviVrstaSobe = FXCollections.observableArrayList(
            VrstaSobe.vratiNaziveVrstaSoba()
     );
    
    //spremanje sobe
    public void spasi(ActionEvent e) throws ParseException, SQLException{
        try{   
            int broj_sobe = Integer.parseInt(brojTxt.getText());
            int id_vrsta_sobe = vrstaCB.getValue().vratiId(vrstaCB.getValue().toString());
            Soba soba = new Soba(broj_sobe, id_vrsta_sobe);
            soba.spasi();
            this.initialize(null, null);
            idTxt.setText("");
            brojTxt.setText("");
            vrstaCB.setPromptText("");
        }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom spremanja!");
        }
    }
    //ažuriranje sobe
    public void uredi(ActionEvent e) throws ParseException, SQLException{
        try {
            Soba soba = new Soba();
            soba.setId(Integer.parseInt(idTxt.getText()));
            soba.setBroj_sobe(Integer.parseInt(brojTxt.getText()));
            soba.setId_vrsta_sobe(vrstaCB.getValue().vratiId(vrstaCB.getValue().toString()));
            soba.uredi();
            this.initialize(null, null);
            idTxt.setText("");
            brojTxt.setText("");
            vrstaCB.setPromptText("");
         }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom ažuriranja!"+exc.getMessage());
            } 
    }
    //brisanje sobe
    public void izbrisi(ActionEvent e){
        Soba soba = new Soba();
        soba.setId(Integer.parseInt(idTxt.getText()));
        soba.izbrisi();
        this.initialize(null, null);
        idTxt.setText("");
        brojTxt.setText("");
        vrstaCB.setPromptText("");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<Soba> data = Soba.vratiSveSobe();
        idTblCol.setCellValueFactory(new PropertyValueFactory<Soba, Integer>("id"));
        brojTblCol.setCellValueFactory(new PropertyValueFactory<Soba, Integer>("broj_sobe"));
        vrstaTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Soba,String>,ObservableValue<String>>() {
        @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Soba, String> param) {
      
                    return new SimpleStringProperty(param.getValue().vratiVrstuSobe());    
            }
        });
        
        sobeTbl.setItems(data);
        PrikaziDetaljno();
        vrstaCB.setItems(naziviVrstaSobe);
    }    
    
}
