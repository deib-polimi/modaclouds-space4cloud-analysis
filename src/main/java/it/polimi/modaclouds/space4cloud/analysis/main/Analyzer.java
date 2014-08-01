package it.polimi.modaclouds.space4cloud.analysis.main;

import it.polimi.modaclouds.space4cloud.analysis.results.ReplicaElement;
import it.polimi.modaclouds.space4cloud.analysis.results.ResourceContainer;
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
	Path solutionFile;

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
			if(result == -1){
				System.out.println("error in project: "+folder);				
			}


			result = parseSolution(folder);
			if(result == -1){
				System.out.println("error in project: "+folder);
			}
		}

		System.out.println("Aggregating Statistics...");
		result = exportStatistics();
		if(result == -1)
			return result;

		System.out.println("Aggregating Solutions...");
		result = exportSolutionAggregation();
		if(result == -1)
			return result;

		

		return result;
	}



	private int exportSolutionAggregation() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(solutionFile.toFile());
		} catch (FileNotFoundException e) {
			System.out.println("Could not write on "+solutionFile.toAbsolutePath());
			return -1;
		}
		

		//TODO: manage the multicloud case
		Map<String,Map<String,String>> types = new HashMap<String, Map<String,String>>(solutionsMap.size());
		Map<String,Map<String,List<Integer>>> replicas = new HashMap<String, Map<String,List<Integer>>>(solutionsMap.size());
		for(String name:statisticsMap.keySet()){
			List<ResourceModelExtension> extensions = solutionsMap.get(name);
			for(ResourceModelExtension extension:extensions){
				//TODO: in the multi-cloud we should get the provider here
				Map<String,String> tierTypes = new HashMap<String,String>(extension.getResourceContainer().size());
				Map<String,List<Integer>> tierReplicas= new HashMap<String,List<Integer>>(extension.getResourceContainer().size());			
				for(ResourceContainer tier:extension.getResourceContainer()){
					String id = tier.getId();
					String type = tier.getCloudResource().getResourceSizeID();
					tierTypes.put(id,type);
					
					List<Integer> tierReplicasValues = new ArrayList<Integer>(tier.getCloudResource().getReplicas().getReplicaElement().size()); 
					for(ReplicaElement replica:tier.getCloudResource().getReplicas().getReplicaElement()){
						tierReplicasValues.add(replica.getHour(), replica.getValue());
					}
					tierReplicas.put(id,tierReplicasValues);
				}
				replicas.put(name, tierReplicas);
				types.put(name, tierTypes);				
			}
		}
		
		
		//wirte the header of the file
		String header = "";
		//get the first model from the replicas hashmap for reference when building the file header
		String firstModelName = replicas.keySet().iterator().next();
		Map<String, List<Integer>> firstModelMap = replicas.get(firstModelName);
		header +="Model Name,";
		for(String tierId: firstModelMap.keySet()){
			header += "TierID,";
			header += "Type,";
			for(int i=0; i<firstModelMap.get(tierId).size();i++){
				header += "H"+i+",";
			}
		}
		
		
		writer.println(header);
		//write the data		
		for(String modelName:types.keySet()){
			String line = modelName+",";
			Map<String, String> tierTypeMap = types.get(modelName);
			Map<String, List<Integer>> tierReplicasMap = replicas.get(modelName);
			for(String tierId: tierTypeMap.keySet()){
				line += tierId+",";
				line += tierTypeMap.get(tierId)+",";
				List<Integer> replicaList = tierReplicasMap.get(tierId);
				for(int i=0; i<replicaList.size();i++){
					line += replicaList.get(i)+",";
				}
			}
			writer.println(line);
		}	
		writer.close();
		return 0;
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

	public void setStatisticFile(String outputfile) {
		statisticsFile = Paths.get(outputfile);
	}

	public Path getBaseDir() {
		return baseDir;
	}

	public Path getStatisticsFile() {
		return statisticsFile;
	}

	public Path getSolutionFile() {
		return solutionFile;
	}

	
	public void setSolutionFile(String solutionFile) {
		this.solutionFile = Paths.get(solutionFile);
	}


}
