package it.uniroma3.dbpedia;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DBPEdia {
	
	public static void main(String[]args){
		TsvReader tr= new TsvReader();
		tr.readIterative("instances_types.tsv");
		Map<String, String> instancesTypes = new HashMap<String, String>();
		String[] row;
			while ((row = tr.readNextRow()) != null)
				instancesTypes.put((String)(row[0].subSequence(28, row[0].length())), row[2]);	
		tr.readIterative("sentences_all.tsv");
	 	TsvPrinter tp = new TsvPrinter();
		tp.init("unlabeled_fact.tsv");
		StringTokenizer st;
		String[] temp = new String[5];
		String t;
		String pattern= new String();
		int j=0;
		int x=0;
		boolean first=true;
		String subject;
		while ((row = tr.readNextRow()) != null) {
			subject=row[0];
			if(row[3]!=null){
				st = new StringTokenizer(row[3]);
				while (st.hasMoreTokens()) {
					t=st.nextToken();
						if( t.contains("[[") && first){
							temp[j]=t;
							j++;
							temp[j]=instancesTypes.get(t.substring(2,t.indexOf('|')));
							j++;
							first=false;
						}
						else if( t.contains("[[") && !first){
							temp[j]=pattern;
							j++;
							temp[j]=t;
							j++;
							temp[j]=instancesTypes.get(t.substring(2,t.indexOf('|')));
							if((!pattern.equals("") && !pattern.equals(". ") && !pattern.equals(", ") && !pattern.equals("; ") ) && x<10 && (subject.equals(temp[3].substring(2,temp[3].indexOf('|'))) || subject.equals(temp[0].substring(2,temp[0].indexOf('|')))) )
								tp.writeRow(temp);
							j=0;
							x=0;
							pattern= new String();
							temp= new String[5];
							temp[j]=t;
							j++;
							temp[j]=instancesTypes.get(t.substring(2,t.indexOf('|')));
							j++;
						}
						else{
							pattern=pattern.concat(t+" ");
							x++;
						}		 			
				}
			}
		}
		tp.close();
		//tr.readIterativeStop();
	}
	
	public static void print(String[] a){
		int i;
		for(i=0; i<a.length; i++){
			System.out.print(a[i]+" ");
		}
		System.out.println("");
	}
}
