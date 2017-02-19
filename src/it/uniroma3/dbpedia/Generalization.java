package it.uniroma3.dbpedia;



public class Generalization {

	private String dictonaryNat,dictonarySport;
	public  String[] ordinal = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
			"eleventh","twelfth","thirteenth","fourteenth","fifteenth","sixteenth","seventeenth",
			"eighteenth","nineteenth","twentieth","thirtieth","fortieth","fiftieth","sixtieth","seventieth","eightieth",
			"ninetieth","hundredth","thousandth","millionth"};


	public Generalization(){
		dictonaryNat=" (?i)(";
		dictonarySport=" (?i)(";
		TsvReader tr = new TsvReader();
		tr.readIterative("generalization.tsv");
		String[] row;
		while ((row = tr.readNextRow()) != null){
			if (row[1].equals("NAT"))
				dictonaryNat=dictonaryNat+"|"+row[0].replaceAll("\\s+$", "");
			else if (row[1].equals("SPORT"))
				dictonarySport=dictonarySport+"|"+row[0].replaceAll("\\s+$", "");
		}
		dictonaryNat=dictonaryNat+") ";
		dictonarySport=dictonarySport+") ";
			
	}

	//generalizza la frase per : Numeri ordinali, misure (peso e distanza), data, et�, moneta,anno, mese, nazionalit� e sport
	public String substituteWord(String phrase){
		String newPhrase=" "+phrase+" ";
		
		//System.out.println(newPhrase);


		
		newPhrase = convertOrdinal(newPhrase);
		//System.out.println(newPhrase);

		newPhrase = convertMeasure(newPhrase);
		//System.out.println(newPhrase);

		newPhrase = convertDate(newPhrase);
		//System.out.println(newPhrase);

		newPhrase = convertAge(newPhrase);
		//System.out.println(newPhrase);

		newPhrase = convertMoney(newPhrase);
		//System.out.println(newPhrase);
		
		newPhrase = convertYear(newPhrase);
		newPhrase = convertMonth(newPhrase);

		newPhrase = convertNationality(newPhrase);
		newPhrase = convertSport(newPhrase);

		//System.out.println(newPhrase);



		return newPhrase.substring(1,newPhrase.length()-1);
	}


	private String convertOrdinal(String phrase){
		String r = phrase;
		
		for(String w:phrase.split(" |\\.|,|;|:|\\?|!"))
			for (String s: this.ordinal){
			if (w.indexOf("(?i)"+s)>=0 || w.matches("(?i)[0-9]*[0-9](?:st|nd|rd|th)"))
				r=r.replaceAll(w,"ORD");
		}
		return r;

	}
	
	private String convertNationality(String phrase){
		String r = phrase;
		r=r.replaceAll(this.dictonaryNat," NAT ");
		return r;

	}
	
	private String convertSport(String phrase){
		String r = phrase;
		r=r.replaceAll(this.dictonarySport," SPORT ");
		return r;

	}
	

	
	
	private String convertMeasure(String phrase){
		String r = phrase;
		r = r.replaceAll(" (?i)[0-9]+( |)(kg|g|kilogram|kilograms|gram|grams|metre|metres|m|km|kilometre|kilometres|cm|centimetre|"
				+ "centimetres|\"|in|inches|inch|yard"
				+ "|yd|yards|feet|foot|ft|byte|b|bit|kilobyte|kb|gigabyte|gb|terabyte|tb) "," MEASURE ");
		return r;

	}
	

	private String convertDate(String phrase){
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
		r = r.replaceAll("(?i)(([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])[/]([0-1][0-9]|[1-9])"
				+ "[/]([1-2][0-9][0-9][0-9]|[0-9][0-9])|([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9]) "
				+ "([0-1][0-9]|[1-9]) ([1-2][0-9][0-9][0-9]|[0-9][0-9])|([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])"
				+ "[-]([0-1][0-9]|[1-9])[-]([1-2][0-9][0-9][0-9]|[0-9][0-9])|([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])"
				+ "[.]([0-1][0-9]|[1-9])[.]([1-2][0-9][0-9][0-9]|[0-9][0-9])|([0-3][0-9]|[1-9]) "
				+ "((january|jan)|(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)|"
				+ "(september|sep)|(october|oct)|(november|nov)|(december|dec)) ([1-2][0-9][0-9][0-9])|((january|jan)|"
				+ "(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)|(september|sep)|"
				+ "(october|oct)|(november|nov)|(december|dec)) ([0-3][0-9]|[1-9]), ([1-2][0-9][0-9][0-9]))", "DATE");
		
		r = r.replaceAll("(?i)((january|jan)|(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)) [1-2][0-9][0-9][0-9]", "DATE");
		return r;
	}
	
	private String convertAge (String phrase){
		String r = phrase;
		r = r.replaceAll(" (?i)(((at|age of) "
				+ " ([1-9][0-9]|[1-9]|ten|eleven|twelve|thirteen|fourteen|fourteen|fifteen|sixteen|seventeen|"
				+ "eighteen|nineteen|(|(one|a)(-|)hundred(|-and-|-))(|twenty|thirty|fourty|fifty|sixty|seventy|"
				+ "eighty|ninety)(|-)(one|two|three|four|five|six|seven|eight|nine|))"
				+ "( years old| yo|)|([1-9][0-9]|[1-9]|ten|eleven|twelve|thirteen|fourteen|fourteen|fifteen|"
				+ "sixteen|seventeen|eighteen|nineteen|(|(one|a)(-|)hundred(|-and-|-))(|twenty|thirty|fourty|"
				+ "fifty|sixty|seventy|eighty|ninety)(|-)(one|two|three|four|five|six|seven|eight|nine|)) "
				+ "(years old|yo))) ", " AGE ");
		return r;
	}
	
	private String convertMoney (String phrase){
		String r = phrase;
		r = r.replaceAll("(?i)((€|\\$|£|¥)( |)[1-9]*[0-9](\\.[0-9]+|,[0-9]+|)|"
				+ "[1-9]*[0-9](\\.[0-9]+|,[0-9]+|) (euro|dollar|pound|yen)(s|)|"
				+ "[1-9]*[0-9](\\.[0-9]+|,[0-9]+|)( |)(€|\\$|£|¥))", "MONEY");
		return r;
	}
	
	private String convertYear (String phrase){
		String r = phrase;
		r = r.replaceAll(" [1-2][0-9][0-9][0-9] ", " YEAR ");
		return r;
	}
	
	private String convertMonth (String phrase){
		String r = phrase;
		r = r.replaceAll(" (?i)((on|in) (january|jan)|(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)) ", " MONTH ");
		return r;
	}


}
