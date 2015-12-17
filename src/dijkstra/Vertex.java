
package dijkstra;

//vertex class that holds all the city info
public class Vertex
{
    // private variables for city info
    private int index;
    private String city_code;
    private String city_name;
    private int population;
    private int elevation;
    private double weight;

    /**
    * default constructor
    * 
    * @param    
    * @param    
    * @return     
    */
    public Vertex()
    {
        // default settings
        this.weight = 0;
        this.index = 0;
        this.city_code = null;
        this.city_name = null;
        this.population = 0;
        this.elevation = 0;
    }

    /**
    * return index
    * 
    * @param       
    * @return   Int      return index 
    */
    public int getIndex()
    {
        return this.index;
    }

    /**
    * get city code
    * 
    * @param       
    * @return   String      return city code 
    */
    public String getCityCode()
    {
        return this.city_code;
    }

   /**
    * return city name
    * 
    * @param      
    * @return   String      city name
    */
    public String getCityName()
    {
        return this.city_name;
    }

    /**
    * get population
    * 
    * @param      
    * @return   int     population of city
    */
    public int getPopulation()
    {
        return this.population;
    }

    /**
    * get elevation
    * 
    * @param      
    * @return   int     return elevation of city
    */
    public int getElevation()
    {
        return this.elevation;
    }

    /**
    * get weight 
    * 
    * @param      
    * @return   double      get weight of city
    */
    public double getWeight()
    {
        return this.weight;
    }

    /**
    * set weight
    * 
    * @param    dobule      weight of current city  
    * @return
    */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    /**
    * set index
    * 
    * @param     int    index of current city 
    * @return
    */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
    * set city code
    * 
    * @param    String  City code for city      
    * @return   
    */
    public void setCityCode(String city_code)
    {
        this.city_code = city_code;
    }

    /**
    * set the city name
    * 
    * @param    String      the city name    
    * @return
    */
    public void setCityName(String city_name)
    {
        this.city_name = city_name;
    }

    /**
    * set population
    * 
    * @param    int     population of city      
    * @return
    */
    public void setPopulation(int population)
    {
        this.population = population;
    }

    /**
    * set elevation
    * 
    * @param    int     elevation of city  
    * @return
    */
    public void setElevation(int elevation)
    {
        this.elevation = elevation;
    }  

    /**
    * tostring method, returns all vertex info
    * 
    * @param      
    * @return   String      String of all city info
    */
    @Override
    public String toString()
    {
        // return all city info
        return (this.index + " " + this.city_code + " " 
                + this.city_name + " " + this.population
                + " " + this.elevation);
    }

    /**
    * pass in a Vertex object and return a copy of it
    * 
    * @param    Vertex      vertex you are copying       
    * @return   Vertex      return new copied vertex object
    */
    public static Vertex copy(Vertex temp)
    {
        //create temp vertex
        Vertex temp2 = new Vertex();
        // set all info into new vertex
        temp2.setIndex(temp.getIndex());
        temp2.setCityCode(temp.getCityCode());
        temp2.setCityName(temp.getCityName());
        temp2.setPopulation(temp.getPopulation());
        temp2.setElevation(temp.getElevation());
        temp2.setWeight(temp.getWeight());
        // return new vertex
        return temp2;
    }
}
    