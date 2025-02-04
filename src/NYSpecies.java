package project3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * This class contains the main method and program for this project. Program is run through the command line, in which
 * the command line argument must be one String containing the file name.
 * This program handles user input to search for species through the data set:
 * "Biodiversity by County - Distribution of Animals, Plants and Natural Communities" from data.ny.gov.
 * @author Adam Soliman
 * @version 3-6-24
 */
public class NYSpecies {
	// C:\Users\adoma\Downloads\Biodiversity_by_County_-_Distribution_of_Animals__Plants_and_Natural_Communities_20240307.csv
    /**
     * The main() method for the program
     * @param args	Array of strings on the command line when the program is run;
     * 				The first argument on the command line should be a String containing the file name. 
     */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Error: No command line input, please input the file name \n");
			System.exit(1);
		}
		File input = new File(args[0]);
		
		if (!input.exists()) {
			System.err.println("Error: the file " + input.getAbsolutePath() + " does not exist \n");
			System.exit(1);
		}
		if (!input.canRead()) {
			System.err.println("Error: the file " + input.getAbsolutePath() + " cannot be read \n");
			System.exit(1);
		}
		Scanner sc = null;
		
		try {
			sc = new Scanner (input) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file " + input.getAbsolutePath() + " cannot be read.\n"); 
			System.exit(1);
		}
		
		SpeciesList list = new SpeciesList();
		String line = null;
		Scanner parse = null;
		String county = null;
		String category = null;
		String taxonomicGroup = null;
		String taxonomicSubGroup = null;
	    String scientificName = null;
	    String commonName = null;
	    String NYListingStatus = null;
	    Species current = null;
	    sc.nextLine(); // skip the column headings
		
	    while (sc.hasNextLine()) {
			try {
				// put each line into new scanner and change delimiter
				line = sc.nextLine(); 
				parse = new Scanner(line);
				parse.useDelimiter(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				// Pattern inspired by Selphiron on StackOverflow
				// https://stackoverflow.com/questions/40770990/split-string-by-comma-but-ignore-commas-in-brackets-or-in-quotes
				
				// set fields
				county = parse.next();
				category = parse.next();
				taxonomicGroup = parse.next();
				taxonomicSubGroup = parse.next();
				scientificName = parse.next();
				commonName = parse.next();
				parse.next(); // skip Year Last Documented
				NYListingStatus = parse.next();
				
				if (county == null || county.equals("")) // ignores a set that doensn't have a valid county
					continue;
			} catch(NoSuchElementException e) {
				System.err.println(line);
				continue;
			}
			
			try {
				current = new Species(category, taxonomicGroup, taxonomicSubGroup, scientificName,
						commonName, NYListingStatus);
				list.add(current, county);
			} catch (IllegalArgumentException ex) {
				// catches if any of the inputs are null or empty strings, and ignores it and moves on, as per
				// instructions.
			}
		}	
		//interactive mode:
		Scanner userInput  = new Scanner(System.in); 
		String userValue = "";
		do {
			System.out.println("Search for a species. Type \"quit\" to stop.");
			userValue = userInput.nextLine();
			if (!userValue.equalsIgnoreCase("quit")) {
				try {
					SpeciesList result = list.getByName(userValue);
					if (result == null) {
						System.out.println("No matching species found.");
						continue;
					}	
					for (Species s : result) {
						System.out.println(s + "\n");
					}
				} catch (IllegalArgumentException ex) {
					System.err.println("Error: empty input");
				}
			}
		} while(!userValue.equalsIgnoreCase("quit"));
		
		userInput.close();
	}
	
}