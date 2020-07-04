/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.Gost;
import hotel.model.Zaposlenik;
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

/**
 * FXML Controller class
 *
 * 
 */
public class PregledGostijuController implements Initializable {

    @FXML
    TableView<Gost> gostiTbl;
    
    @FXML
    private TableColumn<Gost, Integer> idTblCol;
    
    @FXML
    private TableColumn<Gost, String> imeTblCol;
    
    @FXML
    private TableColumn<Gost, String> prezimeTblCol;
    
    @FXML
    private TableColumn<Gost, String> adresaTblCol;
    
    @FXML
    private TableColumn<Gost, String> telTblCol;
    
    @FXML
    private TableColumn<Gost, String> racunTblCol;
    
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
    TextField racunTxt;
    
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
    
    public void spremi_gosta(ActionEvent e) throws ParseException{
        try{  
            String ime=imeTxt.getText();
            String prezime=prezimeTxt.getText();
            String adresa=adresaTxt.getText();
            String tel=telTxt.getText();
            String racun=racunTxt.getText();
            
            
            Gost g=new Gost(ime, prezime,adresa,tel,racun);
            g.spasi();
            this.initialize(null, null);
            idTxt.setText("");
            imeTxt.setText("");
            prezimeTxt.setText("");
            adresaTxt.setText("");
            telTxt.setText("");
            racunTxt.setText("");
        
        }catch(RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Nastala je greška. Unesite sve podatke!");
        } 

    }
    
    public void obrisi_gosta(ActionEvent e){
        try{
            Gost g1=new Gost();
            g1.setId(Integer.parseInt(idTxt.getText()));
            g1.izbrisi();
            this.initialize(null, null);
            idTxt.setText("");
            imeTxt.setText("");
            prezimeTxt.setText("");
            adresaTxt.setText("");
            telTxt.setText("");
            racunTxt.setText("");
           
        }catch(RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Nastala je greška. Odaberite zaposlenika!");
        }
    }
    
    public void uredi_gosta(ActionEvent e) throws ParseException{
        try {
            
            Gost g1 =new Gost();
            g1.setId(Integer.parseInt(idTxt.getText()));
            g1.setIme(imeTxt.getText());
            g1.setPrezime(prezimeTxt.getText());
            g1.setAdresa(adresaTxt.getText());
            g1.setTelefon(telTxt.getText());
            g1.setBr_racuna(racunTxt.getText());
         
            
            g1.uredi();
            this.initialize(null, null);
            idTxt.setText("");
            imeTxt.setText("");
            prezimeTxt.setText("");
            adresaTxt.setText("");
            telTxt.setText("");
            racunTxt.setText("");
            
         }catch(RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Nastala je greška. Unesite sve podatke!");
         }
        
    }
        
    
    
     private void setCellValueFromTable() {
        gostiTbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Gost g=gostiTbl.getItems().get(gostiTbl.getSelectionModel().getSelectedIndex());
                idTxt.setText(String.valueOf(g.getId()));
                imeTxt.setText(g.getIme());
                prezimeTxt.setText(g.getPrezime());
                adresaTxt.setText(g.getAdresa());
                telTxt.setText(g.getTelefon());
                racunTxt.setText(g.getBr_racuna());
           
                
            }
        
        });
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Gost> data=Gost.vratiSveGoste();
   
         idTblCol.setCellValueFactory(new PropertyValueFactory<Gost, Integer>("Id"));
         imeTblCol.setCellValueFactory(new PropertyValueFactory<Gost, String>("Ime"));
         prezimeTblCol.setCellValueFactory(new PropertyValueFactory<Gost, String>("Prezime"));
         adresaTblCol.setCellValueFactory(new PropertyValueFactory<Gost, String>("Adresa"));
         telTblCol.setCellValueFactory(new PropertyValueFactory<Gost, String>("Telefon"));
         racunTblCol.setCellValueFactory(new PropertyValueFactory<Gost, String>("Br_racuna"));
         gostiTbl.setItems(data);
    
         setCellValueFromTable();
    }    
    
}
