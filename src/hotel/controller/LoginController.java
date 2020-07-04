/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.Zaposlenik;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * 
 */
public class LoginController implements Initializable {
    public static int id_logiranog;
    @FXML
    TextField kimeTxt;
    
    @FXML
    PasswordField lozinkaTxt;
    @FXML
    Label poruka;
    
    @FXML
    Button prijavaBtn;
    
    //metoda za prijavu - 2 korisnika kreator naloga i vozac
    public void prijava(ActionEvent e) throws IOException{
        String kime = kimeTxt.getText();
        String lozinka = lozinkaTxt.getText();
        if(kime.equals("") || lozinka.equals("")){
            poruka.setTextFill(Color.BLACK);
            poruka.setText("Unesite sve vrijednosti!");
            
        } else {
            Zaposlenik z = new Zaposlenik(kime, lozinka);
            id_logiranog = z.VratiIdLogiranogKorisnika();
            if(Zaposlenik.login(kime, lozinka) && Zaposlenik.tip(kime, lozinka) == "admin"){
                try{
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pocetnaAdmin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Hotel");
                    stage.setScene(new Scene(root, 600, 415));
                    stage.show();
                    kimeTxt.setText("");
                    lozinkaTxt.setText("");
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(Zaposlenik.login(kime, lozinka) && (Zaposlenik.tip(kime, lozinka) == "zaposlenik")){
                Zaposlenik z1 = new Zaposlenik(kime, lozinka);
                id_logiranog = z1.VratiIdLogiranogKorisnika();
                try {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("hotel/view/pocetna.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Hotel");
                    stage.setScene(new Scene(root, 600, 415));
                    stage.show();
                    kimeTxt.setText("");
                    lozinkaTxt.setText("");
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                poruka.setTextFill(Color.RED);
                poruka.setText("Pogrešni ste unijeli korisničko ime ili lozinku!");
            }
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
