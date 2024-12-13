package analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import data.Movie;
import data.Reviewer;
import graph.Graph;
import util.DataLoader;

/**
 * Please include in this comment your name and describe any extra credit that you implement
 * 
 *  
 */
public class MovieLensAnalyzer {
	private static Map<Integer, Movie> movies;
	private static Map<Integer, Reviewer> reviewers;
	private static Graph<Integer> graph;

	private static final String 
	TITLE_MSG = "=====-- MOVIE LENS ANALYZER --=====",
	WELCOME_MSG = "Welcome to the Movie Lens Analyzer!",
	FILE_MSG = "The files being analyzed are:",
	ADJACENCY_CHOICE_MSG = "Please choose (1-3) how you'd like to define movie adjacency:";
	
	private static final String
	ANSI_BOLD = "\u001B[1m",
	ANSI_RESET = "\u001B[0m",
	ANSI_RED = "\u001B[31m",
	ANSI_GREEN = "\u001B[32m",
	ANSI_YELLOW = "\u001B[33m",
	ANSI_BLUE = "\u001B[34m",
	ANSI_GREY = "\u001B[30m";

	private static void printUnix(String[] args) {
		String[] colours = {ANSI_RED, ANSI_YELLOW, ANSI_GREEN, ANSI_BLUE};
		Random rand = new Random();
		System.out.print(ANSI_BOLD); // set bold
		System.out.print(colours[rand.nextInt(colours.length)]); // choose random colour for title
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

	private static void printWindows(String[] args) {
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

	private static void populateGraph(int choice) {
		// add vertices to graph
		for (int i = 0; i < movies.size(); i++) {
			graph.addVertex(i);
		}

		System.out.println("Populating graph!");
		switch (choice) {
			case 1 -> {
				populateEdges12Same(); // 15ish seconds :)
			}
			case 2 -> {
				System.out.println("2 undone");
			}
			case 3 -> {
				populateEdges33PercentSame(); // 35ish seconds :(
			}
		}
		System.out.println("Population complete!");
	}
	
	private static void populateEdges12Same() {
		HashMap<Double, HashSet<Integer>> ratingGroups = new HashMap<Double, HashSet<Integer>>();
		Set<Integer> reviewerIDs = reviewers.keySet();
		int numSame;
		for (int m1 = 0; m1 < movies.size(); m1++) {
			for (int r : reviewerIDs) {
				double score = reviewers.get(r).getMovieRating(m1);
				if (!ratingGroups.keySet().contains(score)) {
					ratingGroups.put(score, new HashSet<Integer>());
				}
				ratingGroups.get(score).add(r);
			}
			numSame = 0;
			for (int m2 = 0; m2 < m1; m2++) {
				for (int r : reviewerIDs) {
					double score = reviewers.get(r).getMovieRating(m2);
					if (ratingGroups.get(score).contains(r)) {
						numSame++;
					}
				}
				if (numSame >= 12) {
					graph.addEdge(m1, m2);
					graph.addEdge(m2, m1);
				}
			}
		}
	}

	private static void populateEdges33PercentSame() {
		int numSame = 0;
		Set<Integer> reviewerIDs = reviewers.keySet();
		for (int m1 = 0; m1 < movies.size(); m1++) {
			for (int m2 = 0; m2 < movies.size(); m2++) {
				numSame = 0;
				if (m1 != m2) {
					for (int r : reviewerIDs) {
						if (reviewers.get(r).getMovieRating(m1) == reviewers.get(r).getMovieRating(m2)) {
							numSame++;
						}
					}
					if (numSame >= movies.size()/3) {
						graph.addEdge(m1, m2);
					}
				}
			}
		}
	}
	
	private static void runAnalysis(int choice) {

	}
	public static void main(String[] args){	
		if (args.length != 2) {
			System.err.println("Usage: java MovieLensAnalyzer [ratings_file] [movie_title_file]");
			System.exit(-1);
		}		
		
		// initialize movies and reviewers
		DataLoader p = new DataLoader();
		p.loadData(args[0], args[1]);
		reviewers = p.getReviewers();
		movies = p.getMovies();
		
		// initialize empty graph
		graph = new Graph<Integer>();

		if (System.getProperty("os.name").startsWith("Windows")) {
			printWindows(args);
		} else {
			printUnix(args);
		}

		System.out.println("[Option 1] Movies are adjacent if at least 12 users gave both the same rating");
		System.out.println("[Option 2] Movies are adjacent if at least 12 users gave both a rating");
		System.out.println("[Option 3] Movie A is adjacent to Movie B if 33% of the users rated A gave the same rating to B");
		System.out.println();
		System.out.print("Type Selection: ");

		Scanner sc = new Scanner(System.in);
		int choice = -1;
		while (choice > 3 || choice < 1) {
			choice = sc.nextInt();
		}
		populateGraph(choice);
		
		System.out.println();
		System.out.println("[Option 1] Print out graph statistics");
		System.out.println("[Option 2] Print node information");
		System.out.println("[Option 3] Display shortest path between two nodes");
		System.out.println("[Option 4] Quit");
		System.out.println();

		if (!System.getProperty("os.name").startsWith("Windows")) {
			System.out.print(ANSI_BOLD);
		}
		System.out.print("Type Selection: ");
		choice = sc.nextInt();;
		while (choice != 4) {
			if (!System.getProperty("os.name").startsWith("Windows")) {
				System.out.print(ANSI_BOLD);
			}
			System.out.print("Type Selection: ");
			runAnalysis(choice);
			choice = sc.nextInt();
		}
		sc.close();
		System.out.println();
		System.out.println("Thank you!");
	}
}

