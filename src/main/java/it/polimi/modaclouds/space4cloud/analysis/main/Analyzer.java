package it.polimi.modaclouds.space4cloud.analysis.main;

import it.polimi.modaclouds.space4cloud.analysis.results.ResourceModelExtension;
import it.polimi.modaclouds.space4cloud.analysis.results.SolutionMultiResult;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Analyzer {

	private Map<String, SolutionMultiResult> statisticsMap;	
	private Map<String, List<ResourceModelExtension>> solutionsMap;	
	protected static final String STATISTICS_FILE_NAME = "statistics.xml";
	protected static final String SOLUTION_PREFIX = "solution";
	Path baseDir;
	Path statisticsFile;

	public Analyzer() {
		statisticsMap = new HashMap<String, SolutionMultiResult>();
		solutionsMap = new HashMap<String, List<ResourceModelExtension>>();
	}

	public int aggregateResults() {

		if(!baseDir.toFile().exists() || !baseDir.toFile().isDirectory()){
			System.out.println("The specified path does not point to a directory");
			return -1;
		}

		File[] projectFolders = baseDir.toFile().listFiles(new FileFilter() {			
			@Override
			public boolean accept(File arg0) {
				if(arg0.isDirectory())
					return true;
				return false;
			}
		});

		int result = 0;
		for(File folder:projectFolders){
			result = parseStatistics(folder);
			if(result == -1)
				return result;


			result = parseSolution(folder);
			if(result == -1)
				return result;
		}


		result = exportStatistics();
		if(result == -1)
			return result;


		return result;
	}



	private int exportStatistics() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(statisticsFile.toFile());
		} catch (FileNotFoundException e) {
			System.out.println("Could not write on "+statisticsFile.toAbsolutePath());
			return -1;
		}
		writer.println("Name, Cost, Generation Time, Generation Iteration");
		for(String name:statisticsMap.keySet()){
			SolutionMultiResult statistic = statisticsMap.get(name);
			String line = "";			
			line +=name+",";
			line +=statistic.getSolution().get(0).getCost()+",";
			line +=statistic.getSolution().get(0).getGenerationTime()+",";
			line +=statistic.getSolution().get(0).getGenerationIteration();
			writer.println(line);
		}

		writer.close();
		return 0;
	}

	private int parseStatistics(File folder) {
		File[] statisticFile = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String arg1) {
				if(arg1.equals(STATISTICS_FILE_NAME))
					return true;
				return false;
			}
		});

		if(statisticFile.length != 1){
			System.out.println("There has been an error accessing "+STATISTICS_FILE_NAME);
			return -1;
		}

		try {
			Unmarshaller um = JAXBContext.newInstance(SolutionMultiResult.class).createUnmarshaller();
			SolutionMultiResult statistic  = (SolutionMultiResult) um.unmarshal(statisticFile[0]);
			statisticsMap.put(folder.getName(),statistic);
		} catch (JAXBException e) {
			System.out.println("An error has occured unmarshalling the statistics file");
			e.printStackTrace();
			return -1;
		}

		return 0;

	}

	private int parseSolution(File folder) {
		File[] solutionFiles = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String arg1) {
				if(arg1.startsWith(SOLUTION_PREFIX) && arg1.endsWith(".xml"))
					return true;
				return false;
			}
		});

		if(solutionFiles.length < 1){
			System.out.println("There has been an error accessing solution files");
			return -1;
		}

		ArrayList<ResourceModelExtension> solutions = new ArrayList<ResourceModelExtension>(solutionFiles.length);
		solutionsMap.put(folder.getName(), solutions);
		for(File solutionFile:solutionFiles){
			try {
				Unmarshaller um = JAXBContext.newInstance("it.polimi.modaclouds.space4cloud.analysis.results").createUnmarshaller();
				ResourceModelExtension solution = (ResourceModelExtension) um.unmarshal(solutionFile);
				solutions.add(solution);			
			} catch (JAXBException e) {
				System.out.println("An error has occured unmarshalling the solution file "+solutionFile.getAbsolutePath());
				e.printStackTrace();
				return -1;
			}
		}

		return 0;

	}


	public void setBaseDir(String directory) {
		baseDir = Paths.get(directory);
	}

	public void setOutputFile(String outputfile) {
		statisticsFile = Paths.get(outputfile);
	}

	public Path getBaseDir() {
		return baseDir;
	}

	public Path getStatisticsFile() {
		return statisticsFile;
	}


}
