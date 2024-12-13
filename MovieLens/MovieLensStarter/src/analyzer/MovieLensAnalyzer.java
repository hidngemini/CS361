package analyzer;

import java.util.Map;
import java.util.Random;

import data.Movie;
import data.Reviewer;
import util.DataLoader;

/**
 * Please include in this comment your name and describe any extra credit that you implement
 * 
 *  
 */
public class MovieLensAnalyzer {
	private static Map<Integer, Movie> movies;
	private static Map<Integer, Reviewer> reviewers;
	
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

	public static void populateGraph(int choice) {
		

		switch (choice) {
			case 1 -> {

			}
			case 2 -> {

			}
			case 3 -> {

			}
		}
	}
	public static void main(String[] args){
		// Your program should take two command-line arguments: 
		// 1. A ratings file
		// 2. A movies file with information on each movie e.g. the title and genres		
		if(args.length != 2){
			System.err.println("Usage: java MovieLensAnalyzer [ratings_file] [movie_title_file]");
			System.exit(-1);
		}		
		
		DataLoader p = new DataLoader();
		p.loadData(args[0], args[1]);
		reviewers = p.getReviewers();
		movies = p.getMovies();
		//p.printMovieList();
		//p.printReviewerList();
		
		// FILL IN THE REST OF YOUR PROGRAM
		if (System.getProperty("os.name").startsWith("Windows")) {
			printWindows(args);
		} else {
			printUnix(args);
		}
		int choice = 1;
		populateGraph(choice);
	}
}
