package it.uniroma3.dbpedia;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Generalization {
	
	private Map<String, String> dictionary;
	public  String[] ordinal = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
			"eleventh","twelfth"};
	
	
	public Generalization(){
		TsvReader tr = new TsvReader();
		tr.readIterative("generalization.tsv");
		dictionary = new HashMap<String,String>();
		String[] row;
		while ((row = tr.readNextRow()) != null)
			dictionary.put(row[0].toLowerCase(), row[1]);	
	}
	
	public String substituteWord(String word){
		String s;
		String newWord=word.toLowerCase();
		for (String p: word.split(" ") ){
			s=this.dictionary.get(p);
			if (s != null){ //se non si trova nel dizionario
				newWord = newWord.replaceAll(p, s);
			}else
				newWord = newWord.replaceAll(p, this.convert(p));
		}
		

		
			
		return newWord;
	}
	
	public String convert(String word){
		String r = word;
		for (String s: this.ordinal){
			if (word.contains(s) || word.matches("[0-9]*[0-9](?:st|nd|rd|th)"))
				r="ORD";
		}
		return r;
			
		}


}
