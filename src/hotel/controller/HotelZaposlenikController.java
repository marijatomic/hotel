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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HotelZaposlenikController implements Initializable {
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
