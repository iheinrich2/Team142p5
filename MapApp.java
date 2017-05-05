/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          P5
// FILE:             MapApp.java
//
// TEAM:    p5team 142
// Authors:
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)
// Author4: (Evan Ogren, eogren@wisc.edu, eogren, Lec 002)
// Author4: (Mitchel Abts, abts3@wisc.edu, abts3, Lec 003)
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class that reads/parses the input file and creates NavigationGraph
 * object.
 * 
 * @author CS367
 *
 */
public class MapApp {

	private NavigationGraph graphObject;

	/**
	 * Constructs a MapApp object
	 * 
	 * @param graph
	 *            NaviagtionGraph object
	 */
	public MapApp(NavigationGraph graph) {
		this.graphObject = graph;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage: java MapApp <pathToGraphFile>");
			System.exit(1);
		}

		// read the filename from command line argument
		String locationFileName = args[0];
		try {
			NavigationGraph graph = createNavigationGraphFromMapFile(locationFileName);
			MapApp appInstance = new MapApp(graph);
			appInstance.startService();

		} catch (FileNotFoundException e) {
			System.out.println("GRAPH FILE: " + locationFileName + " was not found.");
			System.exit(1);
		} catch (InvalidFileException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

	/**
	 * Displays options to user about the various operations on the loaded graph
	 */
	public void startService() {

		System.out.println("Navigation App");
		Scanner sc = new Scanner(System.in);

		int choice = 0;
		do {
			System.out.println();
			System.out.println("1. List all locations");
			System.out.println("2. Display Graph");
			System.out.println("3. Display Outgoing Edges");
			System.out.println("4. Display Shortest Route");
			System.out.println("5. Quit");
			System.out.print("Enter your choice: ");

			while (!sc.hasNextInt()) {
				sc.next();
				System.out.println("Please select a valid option: ");
			}
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println(graphObject.getVertices());
				break;
			case 2:
				System.out.println(graphObject.toString());
				break;
			case 3: {
				System.out.println("Enter source location name: ");
				String srcName = sc.next();
				Location src = graphObject.getLocationByName(srcName);

				if (src == null) {
					System.out.println(srcName + " is not a valid Location");
					break;
				}

				List<Path> outEdges = graphObject.getOutEdges(src);
				System.out.println("Outgoing edges for " + src + ": ");
				for (Path path : outEdges) {
					System.out.println(path);
				}
			}
				break;

			case 4:
				System.out.println("Enter source location name: ");
				String srcName = sc.next();
				Location src = graphObject.getLocationByName(srcName);

				System.out.println("Enter destination location name: ");
				String destName = sc.next();
				Location dest = graphObject.getLocationByName(destName);

				if (src == null || dest == null) {
					System.out.println(srcName + " and/or " + destName + " are not valid Locations in the graph");
					break;
				}

				if (src == dest) {
					System.out.println(srcName + " and " + destName + " correspond to the same Location");
					break;
				}

				System.out.println("Edge properties: ");
				// List Edge Property Names
				String[] propertyNames = graphObject.getEdgePropertyNames();
				for (int i = 0; i < propertyNames.length; i++) {
					System.out.println("\t" + (i + 1) + ": " + propertyNames[i]);
				}
				System.out.println("Select property to compute shortest route on: ");
				int selectedPropertyIndex = sc.nextInt() - 1;

				if (selectedPropertyIndex >= propertyNames.length) {
					System.out.println("Invalid option chosen: " + (selectedPropertyIndex + 1));
					break;
				}

				String selectedPropertyName = propertyNames[selectedPropertyIndex];
				List<Path> shortestRoute = graphObject.getShortestRoute(src, dest, selectedPropertyName);
				for (Path path : shortestRoute) {
					System.out.print(path.displayPathWithProperty(selectedPropertyIndex) + ", ");
				}
				if (shortestRoute.size() == 0) {
					System.out.print("No route exists");
				}
				System.out.println();

				break;

			case 5:
				break;

			default:
				System.out.println("Please select a valid option: ");
				break;

			}
		} while (choice != 5);
		sc.close();
	}

	/**
	 * Reads and parses the input file passed as argument create a
	 * NavigationGraph object. The edge property names required for the
	 * constructor can be got from the first line of the file by ignoring the
	 * first 2 columns - source, destination. Use the graph object to add
	 * vertices and edges as you read the input file.
	 * 
	 * @param graphFilepath
	 *            path to the input file
	 * @return NavigationGraph object
	 * @throws FileNotFoundException
	 *             if graphFilepath is not found
	 * @throws InvalidFileException
	 *             if header line in the file has < 3 columns or if any line
	 *             that describes an edge has different number of properties
	 *             than as described in the header or if any property value is
	 *             not numeric
	 */

	public static NavigationGraph createNavigationGraphFromMapFile(String graphFilepath)
			throws InvalidFileException, IOException {
		// TODO: read/parse the input file graphFilepath and create
		// NavigationGraph with vertices and edges
		String[] line1Array;

		FileReader fr = new FileReader(graphFilepath);
		BufferedReader br = new BufferedReader(fr);

		try {
			String firstLine = br.readLine();
			line1Array = firstLine.split(" ");
		} catch (IOException excep) {
			throw new InvalidFileException("File did not contain correct information format");
		}

		String[] edgeProperties = new String[line1Array.length - 2];

		for (int i = 0; i < edgeProperties.length; i++) {
			edgeProperties[i] = line1Array[i + 2];
		}

		NavigationGraph graph = new NavigationGraph(edgeProperties);
		String[] lineArray = new String[line1Array.length];
		String line;

		while ((line = br.readLine()) != null) {

			lineArray = line.split(" ");
			Location firstLoc = new Location(lineArray[0].toLowerCase());
			Location secondLoc = new Location(lineArray[1].toLowerCase());
			graph.addVertex(firstLoc);
			graph.addVertex(secondLoc);
			ArrayList<Double> pathList = new ArrayList<Double>();

			for (int i = 2; i < lineArray.length; i++) {

				try {
					pathList.add(Double.parseDouble(lineArray[i]));
				} catch (NullPointerException nullie) {
					throw new InvalidFileException("Index was messed up");
				} catch (NumberFormatException numbie) {
					throw new InvalidFileException("Check format of file.");
				}
			}

			Path path = new Path(firstLoc, secondLoc, pathList);
			graph.addEdge(firstLoc, secondLoc, path);
		}

		fr.close();
		br.close();

		return graph;

	}

}
