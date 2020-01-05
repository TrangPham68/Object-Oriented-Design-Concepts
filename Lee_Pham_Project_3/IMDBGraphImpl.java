import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.HashMap;

public class IMDBGraphImpl implements IMDBGraph {

    private Collection<EntityNode> _actor;
    private Collection<EntityNode> _movie;
    private final Scanner scactors;
    private final Scanner scactress;
    private String currentActor;
    private HashMap<String, Node> _actorMap;
    private HashMap<String, Node> _movieMap;

    /**
     * take in 2 data files
     * @param actorFilename the path to the file of actors_list
     * @param actressFilename the path to the file of actress_list
     * @throws IOException
     */
    public IMDBGraphImpl(String actorFilename, String actressFilename) throws IOException
    {
        scactors = new Scanner(new File(actorFilename), "ISO-8859-1");
        scactress = new Scanner (new File(actressFilename) , "ISO-8859-1");
        _actor = new ArrayList<>();
        _movie = new ArrayList<>();
        _actorMap = new HashMap<>();
        _movieMap = new HashMap<>();
    }
    /**
     * Gets all the actor nodes in the graph.
     * @return a collection of all the actor and actress nodes in the graph.
     */
    public Collection<? extends Node> getActors () {    // looking line by line
        get_actorNodes(scactors);         // find all actors nodes, movie nodes and add in _actor collection and _movie collection
        get_actorNodes(scactress);           // find all actress nodes and movie nodes and add in _actor collection and _movie collection
        return _actor;                  // return collections of actors
    }
    /**
     * Gets all the movie nodes in the graph.
     * @return a collection of all the movie nodes in the graph.
     */
    public Collection<? extends Node> getMovies ()
    {
        get_actorNodes(scactors);         // find all actors nodes, movie nodes and add in _actor collection and _movie collection
        get_actorNodes(scactress);
        return _movie;
    }

    /**
     * Returns the movie node having the specified name.
     * @param name the name of the requested movie
     * @return the movie node associated with the specified name or null
     * if no such movie exists.
     */
    public Node getMovie (String name)
    {
        return getter (name, _movieMap);
    }

    /**
     * Returns the actor node having the specified name.
     * @param name the name of the requested actor
     * @return the actor node associated with the specified name or null
     * if no such actor exists.
     */
    public Node getActor (String name)
    {
        return getter (name, _actorMap);
    }

    /**
     * parsing the whole data file line by line
     * find, create and add all Actors Nodes and Movie Node to HashMaps and Lists
     * @param s scanner data type (expecting either the scactors or scactresses)
     */
    private void get_actorNodes(Scanner s) {
        boolean rightLine = false;    //check if starting the real list yet
        String str = "";
        while (s.hasNextLine())        //while not reaching the end --> keep going
        {
            String a = s.nextLine();    // read file line by line by using nextLine()
            if (a.equals ("-----------------------------------------------------------------------------") && rightLine ==true)
            {
                return;
            }
            if (a.contains("THE ACTORS LIST") || a.contains ("THE ACTRESSES LIST"))     //if reach line says "THE ACTORS LIST" --> move forward 4 more line
            //move 1 more line in while loops to get to the list
            {
                s.nextLine();
                s.nextLine();
                s.nextLine();
                s.nextLine();
                s.nextLine();
                rightLine = true;           //now we are in a right list --> start parsing
            }

            else if (rightLine == true)            // right line --> list
            {
                if (!(a.trim().equals ("")))     //check if the line is not an empty line
                {
                    int index = a.indexOf(")");     //find index of ")"
                    if (index < a.indexOf("\t")) {       //find if ")" is part of name
                        int temp = index;
                        index = a.indexOf(")", temp);
                        str = a.substring(0, index + 1);         //if it is get string from beginning to second ")"
                    }
                    else
                        str = a.substring(0, index + 1);         //else just get string from beginning to the first ")"
                    if (str.indexOf("\t") != 0)     //check if the line does not begin with tab
                    {
                        String[] movie = str.split("\t");   //split by tab
                        if(movie.length >1) {          // check if line has tab (only move on if it does)
                            currentActor = movie[0].trim();    //get name of current actor and store it
                            EntityNode actor = new EntityNode(currentActor);     //make new actor node
                            _actorMap.put (currentActor, actor);
                            _actor.add(actor);
                            if (movie.length ==2)      //array consists of name and movie data  (split 1 tab in middle)
                            {
                                addMovie(movie[1].trim(), actor);    // check and create movie node and update storage if it is
                            }
                            else if (movie.length == 3)   //array consist of name, empty and movie data (split double tab)
                            {
                                addMovie(movie[2].trim(), actor);
                            }
                        }
                    }
                    else     // else--> this line should contain only movie/TV info
                    {
                        str = str.trim();       //trim all empty space
                        addMovie(str, (EntityNode) getActor(currentActor));
                    }
                }
                else if (_actorMap.containsKey(currentActor))   //else --> reach the end of one actor's info
                {
                    if (getActor(currentActor).getNeighbors().size()== 0 )  //check if actor Node created valid to keep
                    {
                        _actorMap.remove(getActor(currentActor));    //contain no movie --> remove
                        _actor.remove(getActor(currentActor));
                    }
                }
            }
        }
    }

    /**
     * check if the data is movie (not a TV show or a TV series)
     * add movie to movie list if it is
     * @param movieName name of the movie
     * @param actor Node of the current actor
     */
    private void addMovie(String movieName, EntityNode actor)
    {
        if (!((movieName.contains ("\"")) || (movieName.contains("(TV)"))))    // check if it is a movie --> if it is not TV or series
        {
            if (_movieMap.containsKey(movieName))                      // check if MashMap already has the movie
            {
                EntityNode movie1 = (EntityNode) getMovie(movieName);    // if movie is already in HashMap --> find movie node
                movie1.set_list(actor);                               // add actor node to the movie's ActorList
                actor.set_list(movie1);                             // add movie node to actor's MovieList
            }
            else {
                EntityNode movieNode = new EntityNode (movieName);// if it is a new movie
                actor.set_list(movieNode);                    // add movie to actor's movie list
                movieNode.set_list(actor);
                _movieMap.put(movieName, movieNode);        // put movie in movie node
                _movie.add((EntityNode) getMovie(movieName));   // add movie to the collection
            }
        }
    }

    /**
     * helper method of getMovie() and getActor()
     * @param name a string name of either actor or movie
     * @param map a Hashmap of either a actor hashMap or movie Hashmap
     * @return a Node of the given String
     *         null if no node exist
     */
    private Node getter(String name, HashMap<String, Node> map)
    {
        if (map.containsKey(name))   //check if the map contain string
        {
            return map.get(name);   //if yes return the Node
        }
        else      // else --> maybe the graph is not updated yet
        {
            getActors();         //add Nodes to graph
            if(map.containsKey(name))    //check again
            {
                return map.get(name);
            }
            return null;     //if not --> no node exist
        }
    }
}
