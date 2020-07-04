/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.VrstaSobe;
import java.io.IOException;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.JOptionPane;

public class PregledVrstaController implements Initializable {

    @FXML
    TableView<VrstaSobe> vrsteTbl;
    
    @FXML
    private TableColumn<VrstaSobe, Integer> idTblCol;
    
    @FXML
    private TableColumn<VrstaSobe, String> nazivTblCol;
    
    @FXML
    private TableColumn<VrstaSobe, Float> cijenaTblCol;
    
    @FXML
    private TableColumn<VrstaSobe, String> opisTblCol;
    
    @FXML
    Label idTxt;
    
    @FXML
    TextField nazivTxt;
    
    @FXML
    TextField cijenaTxt;
    
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
        vrsteTbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                VrstaSobe vs = vrsteTbl.getItems().get(vrsteTbl.getSelectionModel().getSelectedIndex());
                idTxt.setText(String.valueOf(vs.getId()));
                nazivTxt.setText(vs.getNaziv());
                cijenaTxt.setText(String.valueOf(vs.getCijena()));
                opisTxt.setText(vs.getOpis());
            }
        });
    }
    //spremanje vrste sobe
    public void spasi(ActionEvent e) throws ParseException{
        try{   
            String naziv = nazivTxt.getText();
            Float cijena = Float.parseFloat(cijenaTxt.getText());
            String opis = opisTxt.getText();
            VrstaSobe vs = new VrstaSobe(naziv, cijena, opis);
            vs.spasi();
            this.initialize(null, null);
            idTxt.setText("");
            nazivTxt.setText("");
            cijenaTxt.setText("");
            opisTxt.setText("");
        }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Greška prilikom spremanja!");
        }
    }
    //ažuriranje vrste sobe
    public void uredi(ActionEvent e) throws ParseException{
        try {
            VrstaSobe vs =new VrstaSobe();
            vs.setId(Integer.parseInt(idTxt.getText()));
            vs.setNaziv(nazivTxt.getText());
            vs.setCijena(Float.parseFloat(cijenaTxt.getText()));
            vs.setOpis(opisTxt.getText());      
            vs.uredi();
            this.initialize(null, null);
            idTxt.setText("");
            nazivTxt.setText("");
            cijenaTxt.setText("");
            opisTxt.setText("");
         }catch(RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Greška prilikom ažuriranja!"+exc.getMessage());
            } 
    }
    //brisanje vrste sobe
    public void izbrisi(ActionEvent e){
        VrstaSobe vs = new VrstaSobe();
        vs.setId(Integer.parseInt(idTxt.getText()));
        vs.izbrisi();
        this.initialize(null, null);
        idTxt.setText("");
        nazivTxt.setText("");
        cijenaTxt.setText("");
        opisTxt.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<VrstaSobe> data = VrstaSobe.vratiSveVrsteSoba();
        idTblCol.setCellValueFactory(new PropertyValueFactory<VrstaSobe, Integer>("id"));
        nazivTblCol.setCellValueFactory(new PropertyValueFactory<VrstaSobe, String>("naziv"));
        cijenaTblCol.setCellValueFactory(new PropertyValueFactory<VrstaSobe, Float>("cijena"));
        opisTblCol.setCellValueFactory(new PropertyValueFactory<VrstaSobe, String>("opis"));
        
        vrsteTbl.setItems(data);
        PrikaziDetaljno();
    }    
    
}
