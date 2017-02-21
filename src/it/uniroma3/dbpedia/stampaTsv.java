package it.uniroma3.dbpedia;

public class stampaTsv {

	public static void main(String[] args) {
		Generalization g = new Generalization();
		TsvReader tr= new TsvReader();
		String[] row,temp;
		tr.readIterative("generalizated_facts_final.tsv");
		long righe = 0;



		while ((row = tr.readNextRow()) != null){
			System.out.println(row[3]);
			righe++;
		}
		System.out.println("totale righe: "+righe);	}

}

// 7883039