package it.polito.tdp.poweroutages;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Model model=new Model();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listaNERC"
    private ChoiceBox<Nerc> listaNERC; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalysis"
    private Button btnAnalysis; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void AnalyseWorstCase(ActionEvent event) {
    	txtResult.clear();
    	Nerc n=this.listaNERC.getValue();
    	String maxOreOutage=this.txtHours.getText();
    	String maxAnniTotali=this.txtYears.getText();
    	Integer numeroOre=0;
    	Integer maxAnni=0;
    	try {
    		numeroOre=Integer.parseInt(maxOreOutage);
    		maxAnni=Integer.parseInt(maxAnniTotali);
    	}catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		throw new NumberFormatException();
    	}
    	Duration maxOre=Duration.ofHours(numeroOre);
    	if(n==null) {
    		txtResult.setText("Selezionare NERC!");
    		return;
    	}
    	List<PowerOutage> risultato=model.analyseWorstCase(maxOre,n,maxAnni);
    	if(risultato==null) {
    		txtResult.appendText("Risultato nullo");
    		return;
    	}
    	for(PowerOutage po:risultato) {
    		txtResult.appendText(po.toString());
    	}
    	
    }
    void loadData() {
    	List<Nerc> NERC=model.getNercList();
    	for(Nerc n:NERC) {
    		model.mapNerc.put(n.getId(),n);
    	}
    	this.listaNERC.getItems().addAll(NERC);
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	loadData();
    	assert listaNERC != null : "fx:id=\"listaNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalysis != null : "fx:id=\"btnAnalysis\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}

