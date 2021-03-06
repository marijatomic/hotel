/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PocetnaAdminController implements Initializable {

   @FXML
   Button hotelBtn;
   
   @FXML
   Button vrsteBtn;
   
   @FXML
   Button sobeBtn;
   
   @FXML 
   Button zaposleniciBtn;
   
   @FXML
   Button gostiBtn;
   
   @FXML
   Button rezervacijeBtn;
   
   @FXML
   Button odjavaBtn;
   
   //odjava
    public void odjava(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Prijavite se na sustav!");
            stage.setScene(new Scene(root, 400, 450));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
   
   //pregled hotela
    public void pregledHotela(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/hotel.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Podaci o hotelu");
            stage.setScene(new Scene(root, 671, 400));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    //pregled vrsta soba
    public void pregledVrstaSoba(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pregledVrsta.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pregled srsta soba");
            stage.setScene(new Scene(root, 671, 474));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    //pregled soba
    public void pregledSoba(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pregledSoba.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pregled soba");
            stage.setScene(new Scene(root, 671, 474));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    //pregled zaposlenika
    public void pregledZaposlenika(ActionEvent e) throws IOException{
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
    //pregled gostiju
    public void pregledGostiju(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pregledGostiju.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pregled gostiju");
            stage.setScene(new Scene(root, 671, 521));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    //pregled rezervacija
    public void pregledRezervacija(ActionEvent e) throws IOException{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pregledRezervacija.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pregled rezervacija");
            stage.setScene(new Scene(root, 671, 518));
            stage.show();
            ((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
