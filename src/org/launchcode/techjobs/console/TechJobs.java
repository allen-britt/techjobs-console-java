package org.launchcode.techjobs.console;

import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);


                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine().toUpperCase();
                //searchTerm.toUpperCase(); // Setting all Search Strings to uppercase

                if (searchField.equals("all")) {
                    //System.out.println("Search all fields not yet implemented.");
                    printJobs(findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }
    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();
            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        /* How TF do I loop through an Arraylist<HashMap> */
        if (someJobs.size() > 0 ) {
            // Loop over all of the keys in the hashmap
            // Try somJobs.keySet()

            for (HashMap<String, String> field : someJobs) {

                for(Map.Entry<String, String> data: field.entrySet()){
                    System.out.println(data.getKey() + ": "+data.getValue() );

                }
                System.out.println("*********************");
            }

        } else System.out.println("Sorry there is nothing that matches your search");
    }

    //This method will pull in string choice.
    public static ArrayList<HashMap<String, String>> findByValue(String searchTerm){
        //  This method will allow the user
        //  to search across all columns in order to find job input
        // I need to grab the seach term and loop through each row in the exe file
        // If a row contains the search term word then I need to print that word.
        // I could pull every row and put it into an arraylist of hash maps.
        ArrayList<HashMap<String, String>> allJobs = JobData.findAll();
        ArrayList<HashMap<String, String>> matchingItems = new ArrayList<>();

        for (HashMap<String,String> row: allJobs){

            for(Map.Entry<String, String> column: row.entrySet()){
                if (column.getValue().toUpperCase().contains(searchTerm)){
                    //This is making sure that the hashmap is only being added to
                    // tmatchingItems onces
                    //THis will check to see if matchingItems contains row
                    // if so continue if not add row to matchingItems
                    if(Arrays.asList(matchingItems).contains(row)){
                        continue;
                    }
                    matchingItems.add(row);
                }
            }
        }

        return matchingItems;


    }
}

