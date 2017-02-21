package it.uniroma3.dbpedia;



public class GeneralizationFacts {

	public static void main(String[] args) {
		Generalization g = new Generalization();
		TsvReader tr= new TsvReader();
		String[] row,temp;
		tr.readIterative("unlabeled_facts_final.tsv");
		long righe = 0;
		long gen=0;


		TsvPrinter tp = new TsvPrinter();
		tp.init("generalizated_facts_final.tsv");

		while ((row = tr.readNextRow()) != null){
			temp = row;
			String vecchia = row[3];
			temp [3]=g.substituteWord(vecchia);
			righe++;
			if(!(vecchia).equals(temp[3])){
				System.out.println(vecchia+"-->"+temp[3]);
				gen++;
			}
			tp.writeRow(temp);

		}
		System.out.println("Sono stati generalizzati "+gen+" pattern su "+righe);
		tp.close();
		tr.readIterativeStop();
	}

}
