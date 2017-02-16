package it.uniroma3.dbpedia;

import java.util.ArrayList;
import java.util.List;


public class Generalization {

	private List<String[]> dictionary;
	public  String[] ordinal = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
			"eleventh","twelfth","thirteenth","fourteenth","fifteenth","sixteenth","seventeenth",
			"eighteenth","nineteenth","twentieth","thirtieth","fortieth","fiftieth","sixtieth","seventieth","eightieth",
			"ninetieth","hundredth","thousandth","millionth"};


	public Generalization(){
		TsvReader tr = new TsvReader();
		tr.readIterative("generalization.tsv");
		dictionary = new ArrayList<String[]>();
		String[] row;
		while ((row = tr.readNextRow()) != null)
			dictionary.add(row);	
	}

	//generalizza la frase per : Numeri ordinali, misure (peso e distanza), data, età, moneta, nazionalità e sport
	public String substituteWord(String phrase){
		
		String newPhrase=" "+phrase.toLowerCase()+" ";
		for (String p: newPhrase.split(" ") ){
			newPhrase = newPhrase.replaceAll(p, this.convertOrdinal(p));
		}
		
		newPhrase = convertMeasure(newPhrase);
		newPhrase = convertDate(newPhrase);
		newPhrase = convertAge(newPhrase);
		newPhrase = convertMoney(newPhrase);

		newPhrase = findInDictionary(newPhrase);




		return newPhrase.substring(1,newPhrase.length()-1);
	}

	public String findInDictionary(String phrase){
		String finalString=phrase;
		String subs ="";
		String gen ="";
		for (String[] p:this.dictionary){
			int j = finalString.indexOf(p[0].toLowerCase());
			if(j>-1)
				if(p[0].length()>subs.length()){
					subs=p[0];
					gen = p[1];
				}

		}

		int i = finalString.indexOf(subs.toLowerCase());
		if(i==0)
			finalString = finalString.replaceAll(subs.toLowerCase()+" ",gen+" ");
		else
			if(i==phrase.length()-subs.length())
				finalString = finalString.replaceAll(" "+subs.toLowerCase()," "+gen);
			else
				finalString = finalString.replaceAll(" "+subs.toLowerCase()+" "," "+gen+" ");


		return finalString;


	}
	public String convertOrdinal(String word){
		String r = word;
		for (String s: this.ordinal){
			if (word.indexOf(s)>=0 || word.matches("[0-9]*[0-9](?:st|nd|rd|th)"))
				r="ORD";
		}
		return r;

	}
	
	public String convertMeasure(String phrase){
		String r = phrase;
		r = r.replaceAll(" [0-9]+( |)(kg|g|kilogram|kilograms|gram|grams|metre|metres|m|km|kilometre|kilometres|cm|centimetre|"
				+ "centimetres|\"|in|inches|inch|yard"
				+ "|yd|yards|feet|foot|ft|byte|b|bit|kilobyte|kb|gigabyte|gb|terabyte|tb) "," MEASURE ");
		return r;

	}
	

	public String convertDate(String phrase){
		//Date ammesse:
		//gg-mm-aaaa
		//gg-mm-aa
		//gg mm aaaa
		//gg mm aa
		//gg/mm/aaaa
		//gg/mm/aa
		//gg.mm.aaaa
		//gg.mm.aa
		//aaaa-mm-gg
		//aa-mm-gg
		//aaaa mm gg
		//aa mm gg
		//aaaa/mm/gg
		//aa/mm/gg
		//aaaa.mm.gg
		//aa.mm.gg
		//gg mmmm aaaa
		//gg mmm aaaa
		//mmmm gg, aaaa
		//mmm gg, aaaa
		String r = phrase;
		r = r.replaceAll("(([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])[/]([0-1][0-9]|[1-9])"
				+ "[/]([1-2][0-9][0-9][0-9]|[0-9][0-9])|([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9]) "
				+ "([0-1][0-9]|[1-9]) ([1-2][0-9][0-9][0-9]|[0-9][0-9])|([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])"
				+ "[-]([0-1][0-9]|[1-9])[-]([1-2][0-9][0-9][0-9]|[0-9][0-9])|([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])"
				+ "[.]([0-1][0-9]|[1-9])[.]([1-2][0-9][0-9][0-9]|[0-9][0-9])|([0-3][0-9]|[1-9]) "
				+ "((january|jan)|(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)|"
				+ "(september|sep)|(october|oct)|(november|nov)|(december|dec)) ([1-2][0-9][0-9][0-9])|((january|jan)|"
				+ "(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)|(september|sep)|"
				+ "(october|oct)|(november|nov)|(december|dec)) ([0-3][0-9]|[1-9]), ([1-2][0-9][0-9][0-9]))", "DATE");
		return r;
	}
	
	public String convertAge (String phrase){
		String r = phrase;
		r = r.replaceAll(" ((at|age of) "
				+ " ([1-9][0-9]|[1-9]|ten|eleven|twelve|thirteen|fourteen|fourteen|fifteen|sixteen|seventeen|"
				+ "eighteen|nineteen|(|(one|a)(-|)hundred(|-and-|-))(|twenty|thirty|fourty|fifty|sixty|seventy|"
				+ "eighty|ninety)(|-)(one|two|three|four|five|six|seven|eight|nine|))"
				+ "( years old| yo|)|([1-9][0-9]|[1-9]|ten|eleven|twelve|thirteen|fourteen|fourteen|fifteen|"
				+ "sixteen|seventeen|eighteen|nineteen|(|(one|a)(-|)hundred(|-and-|-))(|twenty|thirty|fourty|"
				+ "fifty|sixty|seventy|eighty|ninety)(|-)(one|two|three|four|five|six|seven|eight|nine|)) "
				+ "(years old|yo)) ", " AGE ");
		return r;
	}
	
	public String convertMoney (String phrase){
		String r = phrase;
		r = r.replaceAll("(€|\\$|£|¥)( |)[1-9]*[0-9](\\.[0-9]+|,[0-9]+|)|"
				+ "[1-9]*[0-9](\\.[0-9]+|,[0-9]+|) (euro|dollar|pound|yen)(s|)|"
				+ "[1-9]*[0-9](\\.[0-9]+|,[0-9]+|)( |)(€|\\$|£|¥)", "MONEY");
		return r;
	}


}
