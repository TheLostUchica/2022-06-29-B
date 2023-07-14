/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Adiacenza;
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

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	Album a1 = this.cmbA1.getValue();
    	if(a1!=null) {
    		this.txtResult.appendText("Adiacenze dell'album: "+a1.toString()+"\n");
    		for(Adiacenza a : model.getAd(a1)) {
    			this.txtResult.appendText(a.toString()+"\n");
    		}
    	}else {
    		this.txtResult.appendText("Inserire un album!\n");
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	Album a1 = this.cmbA1.getValue();
    	Album a2 = this.cmbA2.getValue();
    	String s = this.txtX.getText();
    	
    	try {
    		int x = Integer.parseInt(s);
    		
    		if(a1!=null && a2!=null) {
        		List<Album> result = model.ricorsione(a1, a2, x);
        		if(result.size()>0) {
        			for(Album a : result) {
        				this.txtResult.appendText(a.toString()+"\n");
        			}
        		}else {
        			this.txtResult.appendText("Non esiste un percorso per gli album selezionati.\n");
        		}
        	}else {
        		this.txtResult.appendText("Inserire un album!\n");
        	}
    	}catch(NumberFormatException e) {
			e.printStackTrace();
			this.txtResult.appendText("Dati inseriti nel formato sbagliato.");
		}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.cmbA1.getItems().clear();
    	this.cmbA2.getItems().clear();
    	
    	String s = this.txtN.getText();
    	
    	try {
    		int sec = Integer.parseInt(s);
    		model.creaGrafo(sec);
    		this.txtResult.appendText("Grafo creato con "+model.getGrafo().vertexSet().size()+ " vertici e "+model.getGrafo().edgeSet().size()+" archi. \n");
    		this.setCombos();
    	}catch(NumberFormatException e) {
			e.printStackTrace();
			this.txtResult.appendText("Dati inseriti nel formato sbagliato.");
		}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setCombos() {
    	List<Album> list = model.setCombos();
    	this.cmbA1.getItems().addAll(list);
    	this.cmbA2.getItems().addAll(list);
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
