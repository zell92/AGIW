package it.uniroma3.dbpedia;

public class Score {
	public static final String FILE_NAME = "join.tsv";
	private long nRow;
	TsvReader tr,tr2;
	TsvPrinter tp; 

	public Score(){
		tr= new TsvReader();
		tr2= new TsvReader();

		tp = new TsvPrinter();
		nRow = contaRighe();
	}

	private long contaRighe() {
		long n=0;
		tr.readIterative(FILE_NAME);
		while (tr.readNextRow()!=null)
			n++;
		tr.readIterativeStop();
		return n;
	}

	public void generatedScoredTsv(){
		//tp.init("scoredJoin.tsv");
		tr.readIterative(FILE_NAME);

		long pAndR=0,notPAndR=0,pAndNotR=0;

		String [] readRow,writeRow,temp;
		while((temp=tr.readNextRow())!=null){

			pAndR=0;
			notPAndR=0;
			pAndNotR=0;
			tr2.readIterative(FILE_NAME);

			while((readRow=tr2.readNextRow())!=null){
				if (temp[0].equals(readRow[0]) && temp[4].equals(readRow[4])){
					pAndR++;

				}
				if (temp[0].equals(readRow[0]) && !temp[4].equals(readRow[4])){
					pAndNotR++;
				}
				if (!temp[0].equals(readRow[0]) && temp[4].equals(readRow[4])){
					notPAndR++;

				}


			}
			tr2.readIterativeStop();
			System.out.println(pAndR++);
		}
		


	}

	public long getnRow() {
		return nRow;
	}


}
