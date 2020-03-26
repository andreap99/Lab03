package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary dictionary;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLingua;

    @FXML
    private TextArea txtTesto;

    @FXML
    private Button btnControllo;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Label lblNumeroErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblTempo;
    

    @FXML
    void doClear(ActionEvent event) {
    	this.txtTesto.clear();
    	this.txtRisultato.clear();
    	this.lblNumeroErrori.setText("");
    	this.lblTempo.setText("");
    	this.btnClear.setDisable(true);
    }

    @FXML
    void doControllo(ActionEvent event) {
    	this.txtRisultato.clear();
    	 long inizio = System.currentTimeMillis();
    	 String input = this.txtTesto.getText();
    	 List<String> parole = this.dictionary.gestisciInput(input);
    	 //List<RichWord> lista = this.dictionary.spellCheckText(parole);
    	 List<RichWord> lista = this.dictionary.spellCheckTextLinear(parole);
    	 int numErrori = this.dictionary.contaErrori(lista);
    	 String paroleErrate = this.dictionary.ritornaParoleSbagliate(lista);
    	 this.txtRisultato.appendText(paroleErrate);
    	 this.lblNumeroErrori.setText("The text contains "+numErrori+" errors");
    	 long fine =System.currentTimeMillis();
    	 double tempo = (double) (fine - inizio)/1000;
    	 System.out.println("Nel caso lineare si ha un tempo in secondi di: "+tempo);
    	 
    	 
    	 
    	 long inizio2 = System.currentTimeMillis();
    	 List<RichWord> lista2 = this.dictionary.spellCheckTextDichotomic(parole);
    	 int numErrori2 = this.dictionary.contaErrori(lista);
    	 String paroleErrate2 = this.dictionary.ritornaParoleSbagliate(lista);
    	 this.txtRisultato.appendText(paroleErrate);
    	 this.lblNumeroErrori.setText("The text contains "+numErrori+" errors");
    	 long fine2 =System.currentTimeMillis();
    	 double tempo2 = (double) (fine2 - inizio2)/1000;
    	 System.out.println("Nel caso dicotomico si ha un tempo in secondi di: "+tempo2);
    	 
    	 
    	 
    	 
    	 this.lblTempo.setText("Spell check completed in "+tempo+" seconds");
    	 this.btnClear.setDisable(false);
    }

    @FXML
    void scegliLingua(ActionEvent event) {
    	String scelta = boxLingua.getValue();
    	dictionary.loadDictionary(scelta);
    	this.txtTesto.setDisable(false);
    }

    @FXML
    void initialize() {
        assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnControllo != null : "fx:id=\"btnControllo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblNumeroErrori != null : "fx:id=\"lblNumeroErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTempo != null : "fx:id=\"lblTempo\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setDictionary(Dictionary dictionary) {
    	this.dictionary = dictionary;
    	boxLingua.getItems().addAll("English", "Italian");
    }
}



