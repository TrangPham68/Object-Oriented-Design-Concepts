import java.util.*;

public class GraphSearchEngineImpl implements GraphSearchEngine {

    /**
     * Finds a shortest path, if one exists, between nodes s and
     * t. The path will be a list: (s, ..., t). If no
     * path exists, then this method will return null.
     *
     * @param s the start node.
     * @param t the target node.
     * @return a shortest path in the form of a List of Node objects
     * or null if no path exists.
     */
    public List<Node> findShortestPath (Node s, Node t){
        Queue nodeToVisit = new LinkedList<>();       //store node that need to be visited
        HashMap<Node, Node> tracking2 = new HashMap<>();  //hash map that store a node and a parent node
        boolean foundpath = false;
        tracking2.put(s, null);     //Root node --> parent is null

        nodeToVisit.add(s);
        Node currentNode;      // create a current node
        while ((nodeToVisit.size() > 0) && (foundpath == false)) {       // run till there is no more node to visit or foundpath == true

            currentNode = (Node) nodeToVisit.poll();
            List neighbor = (List) currentNode.getNeighbors();    // store list of neighbor of currentnode
            for (Object now : neighbor) {                      //for each loop to look through every node in neighbor
                if (!tracking2.containsKey(now)) {      //if the HashMap does not has node
                    nodeToVisit.add(now);                         //add into the list of node to visit
                    tracking2.put((EntityNode) now, currentNode);      //add the node to hashmap with parent is currentnode

                }
                if (now.equals(t)) {            // if node t is found
                    foundpath = true;                //foundpath == true which will eventually stop the while loop
                    nodeToVisit.poll();
                }

            }

        }

        if (foundpath==false) {return null;}             // if node is never found till the end --> return null
        else {
            List shortestPath = new ArrayList();
            Node parent = t;                               //set end node as the parent node
            while (parent != null)           // run till the parent is null --> root node
            {
                shortestPath.add(0, parent);        //add to the front the parent
                parent = tracking2.get(parent);          // update parent as the parentNode of the parent

            }
            return shortestPath;             // return the list of the shortest path
        }
    }

}
