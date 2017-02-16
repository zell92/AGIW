package it.uniroma3.dbpedia;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DBPEdia {
	
	public static void main(String[]args){
		TsvReader tr= new TsvReader();
		String[] row;
		tr.readIterative("instances_types.tsv");
		Map<String, String> instancesTypes = new HashMap<String, String>();
		String e1;
		String e2;
		
			while ((row = tr.readNextRow()) != null){
				e1=(String)(row[0].subSequence(28, row[0].length()));
				e2=(String)(row[2].subSequence(28, row[2].length()));
				if(e1.contains("#"))
					e1="Thing";
				if(e2.contains("#"))
					e2="Thing";
				instancesTypes.put(e1, e2);
			}

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
		while ((row = tr.readNextRow()) != null) {
			if(row[3]!=null){
				st = new StringTokenizer(row[3]);
				while (st.hasMoreTokens()) {
					t=st.nextToken();
						if( t.contains("[[") && first){
							temp[j]=t;
							j++;
							e1=instancesTypes.get(t.substring(2,t.indexOf('|')));
							if(e1==null)
								temp[j]="Thing";
							else
								temp[j]=e1;
							j++;
							first=false;
						}
						else if( t.contains("[[") && !first){
							temp[j]=pattern;
							j++;
							temp[j]=t;
							j++;
							e1=instancesTypes.get(t.substring(2,t.indexOf('|')));
							if(e1==null)
								temp[j]="Thing";
							else
								temp[j]=e1;
							if((!pattern.equals("") && !pattern.equals(". ") && !pattern.equals(", ") && !pattern.equals("; ") ) && x<8 )
								tp.writeRow(temp);
							j=0;
							x=0;
							pattern= new String();
							temp[j]=t;
							j++;
							e1=instancesTypes.get(t.substring(2,t.indexOf('|')));
							if(e1==null)
								temp[j]="Thing";
							else
								temp[j]=e1;
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
