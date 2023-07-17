/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	if( this.cmbA1.getValue() == null) {
    		this.txtResult.setText("Selezionare un album.");
    		
    	}
    	this.txtResult.clear();
    	Album a = this.cmbA1.getValue();
    	
    	this.txtResult.setText("Componente connessa - " + a);
    	this.txtResult.appendText("\nDImensione componente = " + this.model.getNconnessi(a) );
    	this.txtResult.appendText("\nDurata componente = " + this.model.getSommaConnessi(a));
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.model.clearGrafo();
    	if( this.txtDurata == null) {
    		this.txtResult.setText("Inserire un valore.");
    		return;
    	}
    	
    	int n = 0;
    	
    	try {
    		n = Integer.parseInt(this.txtDurata.getText());
    		if( n < 0) {
    			this.txtResult.setText("Inserire un valore numerico maggiore di 0.");
    			return;
    		}
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un valore numerico valido.");
    		return;
    		
    	}
    	
    	this.model.creaGrafo(n);
		this.txtResult.setText("Grafo creato.");
		this.txtResult.appendText("\n#Vertici: " + this.model.getNVertici() );
		this.txtResult.appendText("\n#Archi: " + this.model.getNArchi() );
		
		
		List<Album> albums = new ArrayList<>( this.model.getVertici());
		for( Album a : albums) {
			this.cmbA1.getItems().add(a);
			
		}
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	if( this.cmbA1.getValue() == null) {
    		this.txtResult.setText("Selezionare un album.");
    		
    	}
    	this.txtResult.clear();
    	Album a = this.cmbA1.getValue();
    	
    	
    	if( this.txtX == null) {
    		this.txtResult.setText("Inserire un valore soglia.");
    		return;
    	}
    	
    	int n = 0;
    	
    	try {
    		n = Integer.parseInt(this.txtX.getText());
    		if( n < 0) {
    			this.txtResult.setText("Inserire un valore numerico maggiore di 0.");
    			return;
    		}
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un valore numerico valido.");
    		return;
    	}
    	
    	List<Album> result = new ArrayList<>( this.model.cerca(a, n));
    	for( Album album : result) {
    		this.txtResult.appendText( album + "\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
