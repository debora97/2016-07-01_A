package it.polito.tdp.formulaone;

import java.awt.color.CMMException;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Season s = boxAnno.getValue();
    	if(s!=null) {
    		model.getDriver(s);
    		model.creaGrafo();
    		txtResult.appendText("il miglior pilota e' " +model.getBestDriver().getForename()+ " "+ model.getBestDriver().getSurname());
    	}else txtResult.appendText("sceglia una stagione");
    	
    	

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	try {
    		int numero= Integer.parseInt(textInputK.getText().trim());
    		if(numero!= 0) {
    			model.getDreamTeam(numero);
    			txtResult.appendText("il miglior dream team con " +numero+ "piloti e': \n"+model.getDreamTeam(numero));
    		}
    		
    	} catch(NumberFormatException e) {
    		txtResult.clear();
    		txtResult.appendText("inserisci n numero corretto");
    		return;
    	}

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	boxAnno.getItems().addAll(model.getSeason());
    }
}
