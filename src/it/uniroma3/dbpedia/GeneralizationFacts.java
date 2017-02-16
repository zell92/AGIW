package it.uniroma3.dbpedia;

public class testGen {

	public static void main(String[] args) {
		Generalization g = new Generalization();
		String a = g.substituteWord("1\" Snow rugby 1 metre prova 1kg 22 apr 2000, $50");
		System.out.println(a);
		String b = " MEASURE snow rugby MEASURE prova MEASURE DATE 22 apr 2000 ";
		b = b.replaceAll("( ([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])[/]([0-1][0-9]|[1-9])"
				+ "[/]([1-2][0-9][0-9][0-9]|[0-9][0-9]) |"
				+ "( [1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9]) "
				+ "([0-1][0-9]|[1-9]) ([1-2][0-9][0-9][0-9]|[0-9][0-9]) |"
				+ " ([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])"
				+ "[-]([0-1][0-9]|[1-9])[-]([1-2][0-9][0-9][0-9]|[0-9][0-9]) |"
				+ " ([1-9][0-9][0-9][0-9]|[0-9][0-9]|[1-9])"
				+ "[.]([0-1][0-9]|[1-9])[.]([1-2][0-9][0-9][0-9]|[0-9][0-9]) |"
				+ " ([0-3][0-9]|[1-9]) "
				+ "((january|jan)|(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)|"
				+ "(september|sep)|(october|oct)|(november|nov)|(december|dec)) ([1-2][0-9][0-9][0-9]) |"
				+ " ((january|jan)|"
				+ "(february|feb)|(march|mar)|(april|apr)|may|(june|jun)|(july|jul)|(august|aug)|(september|sep)|"
				+ "(october|oct)|(november|nov)|(december|dec)) ([0-3][0-9]|[1-9]), ([1-2][0-9][0-9][0-9]) )", " DATE ");
		System.out.println(b);

	}

}
