
package dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph 
{
    // previosu array, each element pos
    // is pos of vertex and element content
    // is the element of the pervious vertex
    private int previous[] ;
    // array of visited vertexes
    private boolean viewed[];
    // object array of vertex
    private Vertex[] vex;
    // 2d matrix array to show connections and
    // distances since they are objects
    // EX: [i][j] == null, then no connection
    // else [i][j].distance = distance between them
    //private Adjacency[][] matrix;
    private int[][] matrix; 
    // scanner object for file input
    private Scanner input;
    // line count of cities  in file
    private int line_count;
    // holds files name 
    private String filename;
    // define what INF is
    private final double INF = Double.POSITIVE_INFINITY;
    
    /**
    * constructor
    * 
    * @param    String      String that holds the filename    
    * @return              
    */
    public Graph(String filename)
    {
        // set class string to this string
        this.filename = filename;
        // set up default line size
        this.line_count = 0;
        // create new scanner object and using the File
        // class check for a existing file
        try 
        {
            // attempt to create object for scanner for valid file name
            this.input = new Scanner(new File(filename));
        } 
        // if file does not exist
        catch (FileNotFoundException ex) 
        {
            // display this
            System.out.println("File not found " + ex);
        }
        // while loop to count how many lines (how many cities) in file
        while (this.input.hasNextLine()) 
        {
            // get current line of input
            String data = this.input.nextLine();
            // skip if line is only spaces or newline
            if(!data.matches("^\\s*$"))
                // increment count
                this.line_count++;
        }
        // create objects for class with how many cities
        this.vex = new Vertex[this.line_count];
        // create 2d array, that holds distances
        // between roads
        this.matrix = new int[this.line_count][];
        // create objects within 2d array
        for(int i = 0; i < this.line_count; i++)
            this.matrix[i] = new int[this.line_count];
        // give default distances 
        for(int i = 0; i < this.line_count; i++)
        {
            for(int j = 0; j < this.line_count; j++)
                // set to 0 by default
                this.matrix[i][j] = 0;
        }
        // create prev array
        previous = new int[this.line_count];
        // create visited array
        viewed = new boolean[this.line_count];
        // close previous object
        this.input.close();
    }
    
    /**
    * load the vertex (cities) from file into proper data structure
    * 
    * @param  
    * @return           
    */
    public void loadVertexes()
    {                
        // create string that appends a city with a 
        // two part name
        String fullname = new String(); 
        // attempt to create ner scanner object
        // with filename
        try 
        {
            // open new file and check for valid filename
            this.input = new Scanner(new File(this.filename));
        } 
        // if file is not valid
        catch (FileNotFoundException ex) 
        {
            // throw exception and show this message
            System.out.println("File not found " + ex);
        }
        // looping through the number of cities
        for(int i = 0; i < line_count; i++)
        {
            // set filename to empty
            fullname = "";
            // create current object at i
            this.vex[i] = new Vertex();
            // look for int
            if(this.input.hasNextInt())
               // set index
               this.vex[i].setIndex(this.input.nextInt());
            // look for pattern for city code
            if(this.input.hasNext("[a-zA-Z]{2}"))
               // set city code
               this.vex[i].setCityCode(this.input.next("[a-zA-Z]{2}"));
            // look for pattern for city name
            if(this.input.hasNext("[a-zA-Z]{2,}"))
               // append city name to string 
               fullname += this.input.next("[a-zA-Z]{3,}");
            // if next part also happens to match a string
            // to be the second have of a two part name for a city
            if(this.input.hasNext("[a-zA-Z]{3,}"))
            {
                // append it to string with space
                fullname += " ";
                fullname += this.input.next("[a-zA-Z]{3,}");
                // set city name
                this.vex[i].setCityName(fullname);
            }
            // if not a two part name
            else
                // set city name as is
                this.vex[i].setCityName(fullname);
            // look for a int as next input
            if(this.input.hasNextInt())
                // set as population
                this.vex[i].setPopulation(this.input.nextInt());
            // look for a int for elevation
            if(this.input.hasNextInt())
                // set elevation 
                this.vex[i].setElevation(this.input.nextInt());
        }
    }
    
    /**
    * load edges (roads) from file 
    * 
    * @param    String      String with the filename of the file with the edges
    * @return           
    */
    public void loadEdges(String filename)
    {
        // set ints to use as the index for the matrix array
        int source = 0, destination = 0, distance = 0;
        // attempt to create scanner object
        // using filename
        try 
        {
            // create new scanner object 
            // and check for valid filename
            this.input = new Scanner(new File(filename));
        } 
        // if invalid file, throw exception
        catch (FileNotFoundException ex) 
        {
            // show this exception messages
            System.out.println("File not found " + ex);
        }
        // loop number of cities
        while(this.input.hasNextInt())
        {
            // get inputs in this order
            // and assign them in the proper ints
            source = this.input.nextInt();
            destination = this.input.nextInt();
            distance = this.input.nextInt();
            // -1 due to zero index, intialize object in array
            // and set constructor with distance
           this.matrix[source-1][destination-1] = distance;
        }
    }
    
    /**
    * add a new edge (road)
    * 
    * @param    String      source city to add road from
    *           String      destination city to add road to
    *           int         destance of new road
    * @return   boolean     true if road was added, false if not        
    */
    public boolean addEdge(String source, String destination, int distance)
    {
        // create ints to makr source and destination
        // positions in array where each represents 
        // a from and to city
        int sourcepos = 0, destinationpos = 0;
        // booleans to end loop
        boolean first = false, second = false;
        // if invalid road, end method
        if(source.equals(destination))
            return false;
        // check for valid distance
        if (distance < 0)
            // end method
            return false;
        // loop to find index of city to match 
        // source and destination cities
        for(int i = 0; i < this.vex.length; i++)
        {
            // check for null index
            if(this.vex[i] != null)
            {
                // if found source
                if(this.vex[i].getCityCode().equals(source))
                {
                    // put into variable 
                    // -1 due to 0 index
                    sourcepos = i;
                    // mark boolean as true
                    first = true;
                }
                // if found destination
                if(this.vex[i].getCityCode().equals(destination))
                {
                    // put into variable 
                    // -1 due to 0 index
                    destinationpos = i;
                    // mark boolean as true
                    second = true;
                }
                // if source and destination is true
                if(first && second)
                    // end loop
                    break;
            }
        }
        // check for any possible 
        // chances of one city being valid
        // and the other not valid
        if(!first && !second)
            return false;
        else if(!first && second)
            return false;
        else if(first && !second)
            return false;

        // with valid information, set distance on new road
        this.matrix[sourcepos][destinationpos] = distance;
        // everything is valid, return true
        return true;
    }
    
    /**
    * delete a edge (road)
    * 
    * @param    String      source city to add road from
    *           String      destination city to add road to
    * @return   boolean     true if road was deleted, false if not        
    */
    public boolean deleteEdge(String source, String destination)
    {
        // ints that mark the cities source and 
        // destination index 
        int sourcepos = 0, destinationpos = 0;
        // booleans to check for proper cities
        boolean first = false, second = false;
        // make sure source and destination 
        // are not the same
        if(source.equals(destination))
            return false;
        // search for index of cities
        for(int i = 0; i < this.vex.length; i++)
        {
            // check for null index
            if(this.vex[i] != null)
            {
                // if found source
                if(this.vex[i].getCityCode().equals(source))
                {
                    // mark position with -1 
                    // due to zero index
                    sourcepos = i;
                    // mark as found
                    first = true;
                }
                // if found destination
                if(this.vex[i].getCityCode().equals(destination))
                {
                    // mark position with -1 
                    // due to zero index
                    destinationpos = i;
                    // mark as found
                    second = true;
                }
                // if both are found
                if(first && second)
                    // end loop
                    break;
            }
        }
        // check for any possible 
        // chances of one city being valid
        // and the other not valid
        if(!first && !second)
            return false;
        else if(!first && second)
            return false;
        else if(first && !second)
            return false;
        this.matrix[sourcepos][destinationpos] = 0;
        return true;
    }
    
    /**
    * find city name (vertex)
    * 
    * @param    String      String of city you want to query 
    * @return   String      string that name of a city (vertex)       
    */
    public String findVertexName(String ver)
    {
        // loop size of array
        for(int i = 0; i < this.vex.length; i++)
        {
            // if index city code matches one being 
            // searched for
            if(this.vex[i] != null)
            {
                if(this.vex[i].getCityCode().equals(ver))
                    // return the vertex info
                    return (this.vex[i].getCityName());
            }
        }
        // else return null
        return null;
    }
    
    /**
    * find city info (vertex)
    * 
    * @param    String      String of city you want to query 
    * @return   String      string that contains all info on a city (vertex)       
    */
    public String findVertexInfo(String ver)
    {
        // loop size of array
        for(int i = 0; i < this.vex.length; i++)
        {
            // if index city code matches one being 
            // searched for
            if(this.vex[i] != null)
            {
                if(this.vex[i].getCityCode().equals(ver))
                    // return the vertex info
                    return (this.vex[i].toString());
            }
        }
        // else return null
        return null;
    }
    
    /**
    * return a string of the previous cities code for a a chosen destination 
    * element after running Dijkstra method
    * 
    * @param    int         position of the last city
    * @return   String      all previous and destination city in order      
    */
    public String prev(int pos)
    {
        String temp = new String();
        // check for valid position 
        if(pos < 0)
            // return empty string0
            return "";
        // recursive call to method 
        // return string 
        // and delete the last comma
        // at end
        temp += prev(pos, temp);
        temp = temp.trim();
        temp = temp.substring(0, temp.length()-1);
        return temp;
    }
        
    /**
    * (recursive version) 
    * return a string of the previous cities code for a a chosen destination 
    * element after running Dijkstra method 
    * 
    * @param    int         position of the last city
    * @param    String      String used to append every city ever recursive call
    * @return   String      all previous and destination city in order      
    */
    private String prev(int pos, String ret)
    {
        // check for valid position
        if(pos < 0)
        // return empty string0
        return "";
        // get current position in
        // previous array.
        // the array element matches
        // i-1 of city index
        int new_pos = previous[pos];
        // recursion call to method
        // to find it's previous cities 
        ret = prev(new_pos, ret);
        // append to string with spaces in between each city
        ret += (vex[pos].getCityCode() + ", ");
        // return string with path of cities
        // of smallest path
        return (ret);
    }
    
    /**
    * find smallest weighted city in array 
    * 
    * @param    Vertex[]    array to looked for smallest weighted
    * @return   int         array pos of smallest weighted city      
    */
    public static int findSmallestWeight(Vertex[] arr)
    {
        // create needed variables
        double smallest = -1;
        int index = 0;
        int i = 0;
        // if arr is empty
        if(arr.length == 0)
            // return -1
            return -1;
        // if array has only a single element
        if(arr.length == 1)
            // return its index
            return 0;
        // loop array 
        for(i = 0; i < arr.length; i++)
        {
            // if element is not null
            if((arr[i] != null))
            {
                // consider it the smallest weight
                smallest = arr[i].getWeight();
                // record index
                index = i;
                // break from loop
                break;
            }
        }
        // start another loop
        for(i = i + 1; i < arr.length; i++)
        {
            // if element is not null and it has a smaller weight then
            // the selected weight before
            if((arr[i] != null) && (arr[i].getWeight() < smallest))
            {
                // record smallest weight
                smallest = arr[i].getWeight();
                // record index
                index = i;
            }
        }
        // if index is deleted
        if(arr[index] == null)
            // retrun invalid element
            return -1;
        // else
        else
            // return element
            return index;
    }

    /**
    * find smallest path between the two inputed cities 
    * 
    * @param    String      Source city 
    * @param    String      Destination city 
    * @return   int         array pos of last city to be used for prev method    
    */
    public int Dijkstra(String source, String destination)
    {
        // a group of bools to check either the 
        // source and destination information is accurate
        boolean first = false, second = false;
        // loop array of vertex
        for(int i = 0; i < this.line_count; i++)
        {
            // if current vertex is not null
            if(this.vex[i] != null)
            {
                // if source is found
                if(vex[i].getCityCode().equals(source))
                    // mark as true
                    first = true;
                // if destination is found
                if(vex[i].getCityCode().equals(destination))
                    // mark as true
                    second = true; 
                if(first && second)
                    break;
            } 
        } 
        // check for any possible 
        // chances of one city being valid
        // and the other not valid
        if(!first && !second)
            return -1;
        else if(!first && second)
            return -1;
        else if(first && !second)
            return -1;
        // create int that records current index
        // of vertex being used
        int current_node = 0;     
        double weight = 0;
        // start loop to initalize variables
        // and arrays
        for(int i = 0; i < this.line_count; i++)
        {
            // set all viewed elements to false
            viewed[i] = false;
            // set previous to an invalid element
            this.previous[i] = -1;  
            // if current city is not the source
            if(!(source.equals(vex[i].getCityCode())))
                // set the weight to infinity
                this.vex[i].setWeight(INF);
            // else
            else
            {
                // Set source weight to 0
                this.vex[i].setWeight(0);
                // record it as smallest weighted vertex
                current_node = i;
            }
        }
        // create temp vertex object
        Vertex temp = new Vertex();
        // create temp vertex array object
        Vertex[] arr = new Vertex[this.line_count];
        // copy original array into new one
        for(int i = 0; i < this.line_count; i++)
            // copy current element
            arr[i] = Vertex.copy(vex[i]);
        // while temp array length is not 0
        while (arr.length != 0)
        {
            // find index of vertex with smallest weight
            current_node = findSmallestWeight(arr);
            // if smallest vertext does not exist
             if(current_node == -1)
                // break loop
                break;
             // if smallest node is the destination
             if(arr[current_node].getCityCode().equals(destination))
                // break loop
                break;
            // all remaining vertices are inaccessible
            if(arr[current_node].getWeight() == INF)
                //break
                break;
            // create temp vertex of current node
            temp = Vertex.copy(arr[current_node]);
            // remove current node from arr
            arr[current_node] = null;
            // mark the vertex has been visited
            viewed[current_node] = true;
            // for each neighbor of the current vertex
            for(int i = 0; i < this.line_count; i++)
            {
                // if current road from source has a distance
                // and not already viewed
                if(matrix[current_node][i] > 0 && !viewed[i])
                {
                    // find weight beteen current node and i node
                    // plus current nodes weight
                    weight = (temp.getWeight() + matrix[current_node][i]);
                    // if weight + distance is less than weight of
                    // node i
                    if(weight < arr[i].getWeight())
                    {
                        // set node is weight
                        arr[i].setWeight(weight);
                        // and original array
                        this.vex[i].setWeight(weight);
                        // mark i nodes previous as current node
                        previous[i] = (current_node);
                    }
                }
            }
        }
        //return index number of destination
        return current_node;
    }
    
    /**
    * Get weight of a city at pos 
    * 
    * @param    int     pos of array element being looked for
    * @return   double  weight of that city
    */
    public double arrayWeight(int pos)
    {
        // check if current node is null
        if(vex[pos] == null)
            // if so return invalid number
            return -1;
        // else
        else
            // return weight of current index
            return vex[pos].getWeight();
    }
}
