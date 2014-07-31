package it.polimi.modaclouds.space4cloud.analysis.main;

import java.nio.file.Paths;

public class Main {
	
	private static final String DEFAULT_OUTPUT = "aggregated.csv";

	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("To few arguments, provide the location of the directory with the results");
			return;
		}		
		Analyzer analyzer = new Analyzer();		
		analyzer.setBaseDir(args[0]);
		if(args.length < 2){
			System.out.println("Using default output file "+DEFAULT_OUTPUT);			
			analyzer.setOutputFile(Paths.get(analyzer.getBaseDir().toAbsolutePath().toString(),DEFAULT_OUTPUT).toString());
		}else{
			analyzer.setOutputFile(args[1]);
		}
			
		
		int result = analyzer.aggregateResults();
		if(result == -1)
			System.out.println("An error occured while aggregating results");
		else
			System.out.println("Aggregation Comlpeated");
			
		
	}

}
