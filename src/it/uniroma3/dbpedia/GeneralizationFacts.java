package it.uniroma3.dbpedia;



public class GeneralizationFacts {

	public static void main(String[] args) {
		Generalization g = new Generalization();
		TsvReader tr= new TsvReader();
		String[] row,temp= new String[5];
		tr.readIterative("unlabeled_fact.tsv");
		long righe = 0;
		long gen=0;

		
		TsvPrinter tp = new TsvPrinter();
		tp.init("generalizated_fact.tsv");
		
		while ((row = tr.readNextRow()) != null){
			temp [0]=row[0];
			temp [1]=row[1];
			temp [2]=g.substituteWord(row[2]);
			temp [3]=row[3];
			temp [4]=row[4];
			righe++;
			if(!(row[2].toLowerCase()).equals(temp[2])){
			System.out.println(row[2]+"-->"+temp[2]);
			gen++;
			}
			tp.writeRow(temp);
			
		}
		System.out.println("Sono stati generalizzati "+gen+" pattern su "+righe);
		

	}

}
