package analyzer;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import data.Movie;
import data.Reviewer;
import graph.Graph;
import graph.GraphAlgorithms;
import util.DataLoader;

/**
 * MovieLens Analyzer builds a graph based on movie ratings
 * 
 *  @author maddyS
 * 	@version 18.12.24
 */
public class MovieLensAnalyzer {
	private static Map<Integer, Movie> movies;
	private static Map<Integer, Reviewer> reviewers;
	private static Graph<Integer> graph;

	// messages in constants for easy changes
	private static final String 
	TITLE_MSG = "=====-- MOVIE LENS ANALYZER --=====",
	WELCOME_MSG = "Welcome to the Movie Lens Analyzer!",
	FILE_MSG = "The files being analyzed are:",
	ADJACENCY_CHOICE_MSG = "Please choose (1-4) how you'd like to define movie adjacency:",
	BUILD_OPTION_1_MSG = "[Option 1] Movies are adjacent if at least 12 users gave both the same rating",
	BUILD_OPTION_2_MSG = "[Option 2] Movies are adjacent if at least 12 users gave both a rating",
	BUILD_OPTION_3_MSG = "[Option 3] Movie A is adjacent to Movie B if over 1/3 of the users that rated A gave the same rating to B",
	BUILD_OPTION_TERRIBLE_MSG = "[Option 4] Every movie is adjacent to every other movie. Why? Great Question.",
	USAGE_MSG = "Usage: java MovieLensAnalyzer [ratings_file] [movie_title_file]",
	ANALYSIS_OPTION_1_MSG = "[Option 1] Print out graph statistics",
	ANALYSIS_OPTION_2_MSG = "[Option 2] Print node information",
	ANALYSIS_OPTION_3_MSG = "[Option 3] Display shortest path between two nodes",
	ANALYSIS_OPTION_4_MSG = "[Option 4] Quit";
	
	// colours! (only works on UNIX based machines (which, as far as I'm concerned, is just not Windows))
	private static final String
	ANSI_BOLD = "\u001B[1m",
	ANSI_RESET = "\u001B[0m",
	ANSI_RED = "\u001B[31m",
	ANSI_GREEN = "\u001B[32m",
	ANSI_YELLOW = "\u001B[33m",
	ANSI_BLUE = "\u001B[34m",
	ANSI_GREY = "\u001B[30m";

	/**
	 * gets a random ANSI colour for the title.
	 * 
	 * @return a random ANSI colour choosing from RED, GREEN, BLUE, and YELLOW
	 * 
	 */
	private static String getRandomUnixColour() {
		String[] colours = {ANSI_RED, ANSI_YELLOW, ANSI_GREEN, ANSI_BLUE};
		Random rand = new Random();
		return colours[rand.nextInt(colours.length)];
	}

	/**
	 * prints welcome message, choosing between UNIX and WINDOWS machines
	 * for colour functionality
	 * 
	 * @param args command line arguments; movie and rating csv files
	 */
	private static void printIntro(String[] args) {
		if (System.getProperty("os.name").startsWith("Windows")) {
			printIntroWindows(args);
		} else {
			printIntroUnix(args);
		}
	}

	/**
	 * prints welcome message and file paths on UNIX machines 
	 * 
	 * @param args command line arguments; movie and rating csv files
	 */
	private static void printIntroUnix(String[] args) {
		System.out.print(ANSI_BOLD); // set bold
		System.out.print(getRandomUnixColour()); // choose random colour for title
		System.out.println(TITLE_MSG);
		System.out.println();
		System.out.println(WELCOME_MSG);
		System.out.print(ANSI_RESET); // clear formatting
		System.out.println(FILE_MSG);
		System.out.print("Reviews: ");
		System.out.print(ANSI_GREY); // set grey colour
		System.out.println(args[0]);
		System.out.print(ANSI_RESET); // clear formatting
		System.out.print("Movies:  ");
		System.out.print(ANSI_GREY); // set grey colour
		System.out.println(args[1]);
		System.out.print(ANSI_RESET); // clear formatting
		System.out.println();
		System.out.print(ANSI_BOLD); // set bold
		System.out.println(ADJACENCY_CHOICE_MSG);
		System.out.print(ANSI_RESET); // clear formatting
	}

	/**
	 * prints welcome message and file paths on WINDOWS
	 * 
	 * @param args command line arguments; movie and rating csv files
	 */
	private static void printIntroWindows(String[] args) {
		// windows terminal does not support ascii sequences for colour and boldness :(
		System.out.println(TITLE_MSG);
		System.out.println();
		System.out.println(WELCOME_MSG);
		System.out.println(FILE_MSG);
		System.out.print("Reviews: ");
		System.out.println(args[0]);
		System.out.print("Movies:  ");
		System.out.println(args[1]);
		System.out.println();
		System.out.println(ADJACENCY_CHOICE_MSG);
	}

	/**
	 * prints options for construction of the graph itself
	 */
	private static void printBuildOptions() {
		System.out.println(BUILD_OPTION_1_MSG);
		System.out.println(BUILD_OPTION_2_MSG);
		System.out.println(BUILD_OPTION_3_MSG);
		System.out.println(BUILD_OPTION_TERRIBLE_MSG);
		System.out.println();
		System.out.print("Type Selection: ");

	}

	/**
	 * populates the graph depending on user choice
	 * 
	 * @param choice int (1-4) inclusive indicating which build method to use
	 */
	private static void populateGraph(int choice) {
		// add vertices to graph
		for (int i = 0; i < movies.size(); i++) {
			graph.addVertex(i);
		}

		System.out.println("Populating graph!");
		switch (choice) {
			case 1 -> {populateEdges12Same();} // 15ish seconds :)
			case 2 -> {populateEdges12SameViews();}
			case 3 -> {populateEdges33PercentSame();} // 35ish seconds :(
			case 4 -> {populateEveryEdge();} // practically instant :D (it better be)
		}
		System.out.println("Population complete!");
	}
	
	/**
	 * populates the graph with edges. U is adjacent to V if at least 
	 * 12 people rated U the same as V
	 */
	private static void populateEdges12Same() {
		int numSame = 0;
		Set<Integer> reviewerIDs = reviewers.keySet();
		for (int m1 = 0; m1 < movies.size(); m1++) {
			for (int m2 = 0; m2 < movies.size(); m2++) {
				numSame = 0;
				if (m1 != m2) {
					for (int r : reviewerIDs) {
						double m1review = reviewers.get(r).getMovieRating(m1);
						double m2review = reviewers.get(r).getMovieRating(m2);
						if ((m1review != -1 && m2review != -1) && (m1review == m2review)) {
							numSame++;
						}
					}
					if (numSame >= 12) {
						graph.addEdge(m1, m2);
					}
				}
			}
		}
	}

	/**
	 * populates the graph with edges. U is adjacent to V if at least
	 * 12 people rated both U and V
	 */
	private static void populateEdges12SameViews() {
		int numSame = 0;
		Set<Integer> reviewerIDs = reviewers.keySet();
		for (int m1 = 0; m1 < movies.size(); m1++) {
			for (int m2 = 0; m2 < movies.size(); m2++) {
				numSame = 0;
				if (m1 != m2) {
					for (int r : reviewerIDs) {
						double m1review = reviewers.get(r).getMovieRating(m1);
						double m2review = reviewers.get(r).getMovieRating(m2);
						if ((m1review != -1 && m2review != -1)) {
							numSame++;
						}
					}
					if (numSame >= 12) {
						graph.addEdge(m1, m2);
					}
				}
			}
		}
	}

	/**
	 * populates the graph with edges. U is adjacent to V if at least
	 * 33% of the people who rated U gave the same rating to V. Because
	 * of this option, every graph will be directed, even if though all
	 * other options do not need a directed graph
	 */
	private static void populateEdges33PercentSame() {
		int numSame = 0;
		int total = 0;
		Set<Integer> reviewerIDs = reviewers.keySet();
		for (int m1 = 0; m1 < movies.size(); m1++) {
			for (int m2 = 0; m2 < movies.size(); m2++) {
				numSame = 0;
				total = 0;
				if (m1 != m2) {
					for (int r : reviewerIDs) {
						double m1review = reviewers.get(r).getMovieRating(m1);
						double m2review = reviewers.get(r).getMovieRating(m2);
						if (m1review != -1 && m2review != -1) {
							if (m1review == m2review) {
								numSame++;
							}
							total++;
						}
					}
					if (numSame > total / 3) {
						graph.addEdge(m1, m2);
					}
				}
			}
		}
	}
	
	/**
	 * populates the graph with edges. U is always adjacent to V for all
	 * U, V. This was mostly for testing, but I left it in because I
	 * thought it was kind of funny.
	 */
	private static void populateEveryEdge() {
		for (int m1 = 0; m1 < movies.size(); m1++) {
			for (int m2 = 0; m2 < movies.size(); m2++) {
				if (m1 != m2) {
					graph.addEdge(m1,m2);
				}
			}
		}
	}

	/**
	 * prints options for analyzing the graph
	 */
	private static void printAnalysisOptions() {
		System.out.println();
		System.out.println(ANALYSIS_OPTION_1_MSG);
		System.out.println(ANALYSIS_OPTION_2_MSG);
		System.out.println(ANALYSIS_OPTION_3_MSG);
		System.out.println(ANALYSIS_OPTION_4_MSG);
		System.out.println();
	}

	/**
	 * runs a graph analysis option
	 * 
	 * @param choice int (1-3) choice from analysis options (excluding EXIT)
	 * @param sc is the Scanner used in main. This is to get node selections 
	 * for path finding and printing node information
	 * @throws Exception if PQ.pop() fails it's preconditions in Dijkstra's. 
	 * This shouldn't happen unless I messed up badly
	 */
	private static void runAnalysis(int choice, Scanner sc) throws Exception {
		switch (choice) {
			case 1 -> {printGraphStats();}
			case 2 -> {
				System.out.print("Enter movie ID: (0-");
				System.out.print(movies.size()-1);
				System.out.print("): "); // sad face print :(
				int node = sc.nextInt();
				System.out.println();
				printNodeInformation(node);
			}
			case 3 -> {
				System.out.print("Please enter the starting movie ID: ");
				int startingNode = sc.nextInt();
				System.out.print("Please enter the ending movie ID: ");
				int endingNode = sc.nextInt();
				System.out.println();
				printShortestPath(startingNode, endingNode);
			}
		};
		printAnalysisOptions();
	}

	/**
	 * prints graph statistics, choosing between UNIX and WINDOWS for colours
	 */
	private static void printGraphStats() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			printGraphStatsWindows();
		} else {
			printGraphStatsUnix();
		}
	}

	/**
	 * prints graph stats with colours for UNIX
	 */
	private static void printGraphStatsUnix() {
		String statsColour = getRandomUnixColour();

		int numNodes = graph.numVertices();
		int numEdges = graph.numEdges();
		double density = (double) (numEdges) / (double) (numNodes * (numNodes-1)); // graph is always directed
		int maximumDegree = -1;
		int maximumDegreeNode = -1;
		for (int i = 0; i< numNodes; i++) {
			if (maximumDegree < graph.degree(i)) {
				maximumDegree = graph.degree(i);
				maximumDegreeNode = i;
			}
		}
		int[][] fwSolution = GraphAlgorithms.floydWarshall(graph);
		int diameter = 0;
		int diameterFrom = 0;
		int diameterTo = 0;
		int totalShortestPath = 0;
		int numAveraged = 0;
		for (int i = 0; i < fwSolution.length; i++) {
			for (int j = 0; j < fwSolution[0].length; j++) {
				if (fwSolution[i][j] != 0 && fwSolution[i][j] != Integer.MAX_VALUE) {
					totalShortestPath += fwSolution[i][j];
					numAveraged++;
					if (diameter < fwSolution[i][j]) {
						diameter = fwSolution[i][j];
						diameterFrom = i;
						diameterTo = j;
					}
				}
			}
		}
		double averageShortestPath = (double) totalShortestPath / (double) numAveraged;

		System.out.print(ANSI_BOLD);
		System.out.println("=====-- GRAPH STATISTICS --=====");

		System.out.print(ANSI_BOLD);
		System.out.print(statsColour);
		System.out.print("Number of Nodes:        ");
		System.out.print(ANSI_RESET);
		System.out.println(numNodes);

		System.out.print(ANSI_BOLD);
		System.out.print(statsColour);
		System.out.print("Number of Edges:        ");
		System.out.print(ANSI_RESET);
		System.out.println(numEdges);

		System.out.print(ANSI_BOLD);
		System.out.print(statsColour);
		System.out.print("Graph Density:          ");
		System.out.print(ANSI_RESET);
		System.out.println(density);

		System.out.print(ANSI_BOLD);
		System.out.print(statsColour);
		System.out.print("Highest Degree:         ");
		System.out.print(ANSI_RESET);
		System.out.print(maximumDegree);
		System.out.print(" (Node ");
		System.out.print(maximumDegreeNode);
		System.out.println(")");

		System.out.print(ANSI_BOLD);
		System.out.print(statsColour);
		System.out.print("Graph Diameter:         ");
		System.out.print(ANSI_RESET);
		System.out.print(diameter);
		System.out.print(" (from ");
		System.out.print(diameterFrom);
		System.out.print(" to ");
		System.out.print(diameterTo);
		System.out.println(")");

		System.out.print(ANSI_BOLD);
		System.out.print(statsColour);
		System.out.print("Average Path Length:    ");
		System.out.print(ANSI_RESET);
		System.out.println(averageShortestPath);

		System.out.println(ANSI_GREY);
		System.out.println("Please Note: This graph is directed.");
		System.out.print(ANSI_RESET);
	}

	/**
	 * prints graph statistics normally (only happens on WINDOWS) 
	 * (without colours D:)
	 */
	private static void printGraphStatsWindows() {
		int numNodes = graph.numVertices();
		int numEdges = graph.numEdges();
		double density = (double) (numEdges) / (double) (numNodes * (numNodes-1)); // graph is always directed
		int maximumDegree = -1;
		int maximumDegreeNode = -1;
		for (int i = 0; i< numNodes; i++) {
			if (maximumDegree < graph.degree(i)) {
				maximumDegree = graph.degree(i);
				maximumDegreeNode = i;
			}
		}
		int[][] fwSolution = GraphAlgorithms.floydWarshall(graph);
		int diameter = 0;
		int diameterFrom = 0;
		int diameterTo = 0;
		int totalShortestPath = 0;
		int numAveraged = 0;
		for (int i = 0; i < fwSolution.length; i++) {
			for (int j = 0; j < fwSolution[0].length; j++) {
				if (fwSolution[i][j] != 0 && fwSolution[i][j] != Integer.MAX_VALUE) {
					totalShortestPath += fwSolution[i][j];
					numAveraged++;
					if (diameter < fwSolution[i][j]) {
						diameter = fwSolution[i][j];
						diameterFrom = i;
						diameterTo = j;
					}
				}
			}
		}
		double averageShortestPath = (double) totalShortestPath / (double) numAveraged;

		System.out.println("=====-- GRAPH STATISTICS --=====");

		System.out.print("Number of Nodes:        ");

		System.out.println(numNodes);

		System.out.print("Number of Edges:        ");
		System.out.println(numEdges);

		System.out.print("Graph Density:          ");
		System.out.println(density);

		System.out.print("Highest Degree:         ");
		System.out.print(maximumDegree);
		System.out.print(" (Node ");
		System.out.print(maximumDegreeNode);
		System.out.println(")");

		System.out.print("Graph Diameter:         ");
		System.out.print(diameter);
		System.out.print(" (from ");
		System.out.print(diameterFrom);
		System.out.print(" to ");
		System.out.print(diameterTo);
		System.out.println(")");

		System.out.print("Average Path Length:    ");
		System.out.println(averageShortestPath);

		System.out.println("Please Note: This graph is directed.");
	}

	/**
	 * prints:
	 *  - Movie Name
	 *  - Number of Ratings
	 *  - Genre(s)
	 *  - Neightbours 
	 * 
	 * @param node movieID of node to get information of
	 */
	private static void printNodeInformation(int node) {
		System.out.print(movies.get(node));
		Collection<Integer> adjacents = graph.getNeighbors(node);
		System.out.println("Neightbours:");
		for (int movieID : adjacents) {
			System.out.print("   ");
			System.out.println(movies.get(movieID).getTitle());
		}
	}

	/**
	 * prints shortest path from startingNode to endingNode using
	 * Dijkstra's algorithm
	 * 
	 * @param startingNode start node
	 * @param endingNode end node
	 * @throws Exception shouldn't ever be thrown unless I messed up. Comes from PQ.pop()
	 */
	private static void printShortestPath(int startingNode, int endingNode) throws Exception {
		int[] dijkstraSoln = GraphAlgorithms.dijkstrasAlgorithm(graph, startingNode);
		Stack<Integer> path = new Stack<Integer>();
		int curNode = endingNode;
		if (dijkstraSoln[curNode] == -1 && startingNode != endingNode) {
			System.out.println("There does not exist a path between these two films in this graph.");
		} else {
			while (curNode != startingNode) { // if we're at start point, we've found the whole path
				path.push(curNode); // push current node
				curNode = dijkstraSoln[curNode]; // get previous node in path
			}

			int prev = startingNode; // first node in path
			while (!path.isEmpty()) {
				int curr = path.pop(); // first node in stack (second node in path)

				System.out.print(movies.get(prev).getTitle());
				System.out.print(" ===> ");
				System.out.println(movies.get(curr).getTitle());

				prev = curr; // move prev into curr
			}
		}
	}

	/**
	 * main function! runs the movie lens analyzer program in 
	 * command line.
	 * 
	 * @param args command line arguments. Usage: java MovieLensAnalyzer 
	 * [ratings_file] [movie_title_file]
	 */
	public static void main(String[] args) throws Exception {	
		if (args.length != 2) {
			System.err.println(USAGE_MSG);
			System.exit(-1);
		}		
		
		// initialize movies and reviewers
		DataLoader p = new DataLoader();
		p.loadData(args[0], args[1]);
		reviewers = p.getReviewers();
		movies = p.getMovies();
		
		// initialize empty graph
		graph = new Graph<Integer>();

		printIntro(args);

		printBuildOptions();

		Scanner sc = new Scanner(System.in);
		int choice = -1;
		while (choice > 4 || choice < 1) {
			choice = sc.nextInt();
		}
		populateGraph(choice);
		
		printAnalysisOptions();

		System.out.print("Type Selection: ");
		choice = sc.nextInt();
		while (choice != 4) {
			runAnalysis(choice, sc);
			System.out.println();
			System.out.print("Type Selection: ");
			choice = sc.nextInt();
		}
		
		sc.close();
		System.out.println();
		System.out.println("Thank you!");
	}
}

