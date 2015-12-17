
package dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Tanner Summers
 */
public class DijkstraSAlgorithm 
{
  /**
    * main method 
    * 
    * @param    String[]    args: used for arguments, not used for this program
    * @param
    * @return   
    */
    public static void main(String[] args) 
    {
        // create needed variables
        Scanner input = new Scanner(System.in);
        // holds filenames
        String filename = null;
        // used to validate filename
        FileReader file_name_reader = null;
        // create the class that will be doing everything
        Graph graph;
        // bool used in many different areas for valid checks
        boolean valid = true;
        // string to hold the user chosen option
        String option = new String();
        // for inner options
        String sub_option = new String();
        // used to source city name
        String source = new String();
        // used to destination city name
        String destination = new String();
        // used to make position of index
        int pos = -1;
        // used as an int to hold distance
        int dist = 0;
        // loop to check for valid fle name
        do{
            valid = true;
            System.out.print("Please enter file that contains the vertex info: ");
            filename = input.nextLine();
            // attempt to create object
            try 
            {
                // create object
                file_name_reader = new FileReader(new File(filename));
            } 
            catch (FileNotFoundException ex) 
            {
                // if file not found, throw exception
                System.out.println("File not found " + ex);
                valid = false;
            }
        // loop if file is invalid
        }while(valid == false);
        // creaet graph object using filename
        graph = new Graph(filename);
        // load info from file
        graph.loadVertexes();
        // loop to check for valid fle name
        do{
            valid = true;
            System.out.print("Please enter file that contains the edges info: ");
            filename = input.nextLine();
            // attempt to create object
            try 
            {
                file_name_reader = new FileReader(new File(filename));
            } 
            catch (FileNotFoundException ex) 
            {
                // if file not found, throw exception
                System.out.println("File not found " + ex);
                valid = false;
            }
        // loop if file is invalid
        }while(valid == false);
        // load roads from file into graph class        
        graph.loadEdges(filename);
        // display menu
        menuDisplay();
        // do loop that loops until user chooses to exit
        do{
            // loop until user enters proper command
            do{
                System.out.print("Command? ");
                option = input.nextLine();        
                // if user input matches this pattern (valid input)
                if(option.matches("[QDIRHE]{1}"))
                    valid = true;
                else
                    valid = false;
            // if inout is invalid, reloop
            }while(!valid);
            // match input with proper sequence of commands/code
            switch(option.charAt(0))
            {
                // user chose to query city information
                case 'Q':
                    // loop until user enters proper command
                    do{
                        System.out.print("City Code: ");
                        sub_option = input.nextLine(); 
                        // if user input matches this pattern (valid input)
                        if(sub_option.matches("[A-Z]{2}"))
                            valid = true;
                        else
                            valid = false;
                    // if inout is invalid, reloop
                    }while(!valid);
                    // retrieve city info in string
                    sub_option = graph.findVertexInfo(sub_option);
                    // if string is null
                    if(sub_option == null)
                        System.out.println("No such city located");
                    // if city is not null
                    else
                        System.out.println(sub_option);
                    break; 
                // user choose to find distance between two cities
                case 'D':
                    // loop until user enters proper command
                    do{
                        System.out.print("Ciy Codes: ");
                        sub_option = input.nextLine();  
                        // if user input matches this pattern (valid input)
                        if(sub_option.matches("[A-Z]{2}\\s[A-Z]{2}"))
                            valid = true;
                        else
                            valid = false;
                    // if inout is invalid, reloop
                    }while(!valid);
                    // since the input is also in the form of 
                    // 2 char a space and two char
                    // break down the source and distination from 
                    // user input and split it up into sub strings
                    source = sub_option.substring(0, 2);
                    destination = sub_option.substring(3, 5);
                    // calculate shortest path and get position of 
                    // destination after filling previous array
                    pos = graph.Dijkstra(source, destination);
                    // if pos is invalid array subscript
                    if(pos == -1)
                        System.out.println("No such distance");
                    // pos is valid subscript
                    else
                    {
                        // display info
                        System.out.print("The minimum distance between " + source.toUpperCase() + " and " + destination.toUpperCase() +
                                           " is " + (int)graph.arrayWeight(pos) + " through the route: ");
                        System.out.println(graph.prev(pos));
                    }
                    break;
                //insert a new road    
                case 'I':
                    // loop until user enters proper command
                    do{
                        System.out.print("City codes and distance: ");
                        sub_option = input.nextLine();     
                        // if user input matches this pattern (valid input)
                        if(sub_option.matches("[A-Z]{2}\\s[A-Z]{2}\\s\\d+"))
                            valid = true;
                        else
                            valid = false;
                    // if inout is invalid, reloop    
                    }while(!valid);
                    // since the input is also in the form of 
                    // 2 char a space and two char
                    // break down the source and distination from 
                    // user input and split it up into sub strings
                    source = sub_option.substring(0, 2);
                    destination = sub_option.substring(3, 5);
                    // get distance from user input and turn it into a double
                    dist = Integer.parseInt(sub_option.substring(6, sub_option.length()));
                    // add edge to matrix and if method returns true
                    // if was succesfull , display info
                    if(graph.addEdge(source, destination, dist))
                    {
                        System.out.println("You have inserted a road from " +
                                           graph.findVertexName(source).toUpperCase() 
                                           + " to " + 
                                           graph.findVertexName(destination).toUpperCase()
                                           + " with a distance of " + dist);
                    }
                    // road cant be added
                    else
                        System.out.println("Could not insert this road");
                     break; 
                // remove a road
                case 'R':
                    // loop until user enters proper command
                    do{
                        System.out.print("City codes: ");
                        sub_option = input.nextLine();
                        // if user input matches this pattern (valid input)
                        if(sub_option.matches("[A-Z]{2}\\s[A-Z]{2}"))
                            valid = true;
                        else
                            valid = false;
                    // if inout is invalid, reloop
                    }while(!valid);
                    // if raod can be deleted
                    if(graph.deleteEdge(source, destination))
                    {
                        // display info
                        System.out.println("You have delete a road from " +
                                           graph.findVertexName(source).toUpperCase() 
                                           + " to " + 
                                           graph.findVertexName(destination).toUpperCase()
                                           + " with a distance of " + dist); 
                    }
                    else
                    {
                        // road cant be added, display info
                        System.out.println("The road from " +
                                           graph.findVertexName(source).toUpperCase() 
                                           + " and " + 
                                           graph.findVertexName(destination).toUpperCase()
                                           + " doesn't exist");
                    }
                    break;
                // re-display the menu
                case 'H':
                    // display menu
                        menuDisplay();
                        break;
                // exit the loop (end program)
                case 'E':
                    // end program!!!!!!!!!!!!
                    System.out.println("End Program");
                    break;
            }
        // loop until user chooses E to exit
        }while (option.charAt(0) != 'E');
    }
    
    /**
    * display user menu
    * 
    * @param  
    * @param    
    * @return       
    */
    public static void menuDisplay()
    {
        // menu
        System.out.println("   Q   Query the city information by entering the city code.");
        System.out.println("   D   Find the minimum distance between two cities.");
        System.out.println("   I   Insert a road by entering two city codes and distance.");
        System.out.println("   R   Remove an existing road by entering two city codes.");
        System.out.println("   H   Display this message.");
        System.out.println("   E   Exit.");
    }
}
