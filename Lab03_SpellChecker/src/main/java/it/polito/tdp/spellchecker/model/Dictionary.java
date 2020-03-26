package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {

	public Dictionary() {
		
	}
	
	private String lingua;
	
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	List<String> dizionario = new ArrayList<String>();
	
	public void loadDictionary(String lingua) {
		if(lingua.compareToIgnoreCase("Italian")==0) {
			dizionario.clear();
		try {
			FileReader fr = new FileReader("src\\main\\resources\\Italian.txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while((word = br.readLine()) != null) {
				dizionario.add(word);
			}
			br.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		}
		if(lingua.compareToIgnoreCase("English")==0) {
			dizionario.clear();
			try {
				FileReader fr = new FileReader("src\\main\\resources\\English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while((word = br.readLine()) != null) {
					dizionario.add(word);
				}
				br.close();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
			}
	}
	
	/**
	 * Restituisce una lista di RichWord, ossia parole e relativa correttezza
	 * @param inputText lista delle singole parole
	 * @return lista di RichWord
	 */
	/*
	public List<RichWord> spellCheckText(List<String> inputText){
		List<RichWord> lista = new ArrayList<RichWord>();
		for(String s : inputText) {
			if(dizionario.contains(s))
				lista.add(new RichWord(s,true));
			else
				lista.add(new RichWord(s, false));
		}
		return lista;
	}

	*/
	/**
	 * Elimina caratteri speciali e inserisce le singole parole in una lista
	 * @param input Stringa di input
	 * @return lista delle singole parole
	 */
	public List<String> gestisciInput(String input) {
		input = input.toLowerCase();
		input = input.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_'~()\\[\\]\"]", "");
		String array[] = input.split(" ");
		List<String> lista = new LinkedList<String>();
		for(String s : array) {
			lista.add(s);
		}
		
		return lista;
	}
	/**
	 * Conta il numero di errori nella lista
	 * @param lista di RichWord da controllare
	 * @return intero che corrisponde al numero di errori
	 */
	public int contaErrori(List<RichWord> lista) {
		int count = 0;
		for(RichWord r : lista)
			if(!r.isCorretta())
				count++;
		return count;
	}
	
	/**
	 * Ritorna una stringa contenente le parole errate
	 * @param lista di RichWord
	 * @return Stringa con parole sbagliate, una per riga
	 */
	public String ritornaParoleSbagliate(List<RichWord> lista) {
		String s = "";
		for(RichWord r : lista)
			if(!r.isCorretta())
				s += r.getParola()+"\n";
		return s;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputText){
		List<RichWord> lista = new ArrayList<RichWord>();
		for(String s : inputText) {
			if(dizionario.contains(s))
				lista.add(new RichWord(s,true));
			else
				lista.add(new RichWord(s, false));
		}
		return lista;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputText){
		List<RichWord> lista = new ArrayList<RichWord>();
		int x = this.dizionario.size()/2;
		for(String s : inputText) {
			int y = -1;
			int i = 0;
			boolean trovato = false;
			while(y==0 || i == this.dizionario.size()/2) {
				y = s.compareToIgnoreCase(this.dizionario.get(x));
				if(y<0)
					x = x/2;
				if(y>0)
					x = x + x/2;
				if(y==0) {
					lista.add(new RichWord(s, true));
					trovato = true;
				}
				i++;
			}
			if(!trovato)
				lista.add(new RichWord(s, false));
		}
		return lista;
	}
}
