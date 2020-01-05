import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityNode implements Node {
    String _name;
    List _list;
    public EntityNode (String name)
    {
        _name = name;
        _list = new ArrayList();

    }
    /**
     * Returns the name of the node (e.g., "Judy Garland").
     * @return the name of the Node.
     */
    public String getName ()
    {
        return _name;
    }

    /**
     * add a node into the neighbor list
     * @param a
     */
    public void set_list(EntityNode a)
    {
        _list.add(a);
    }

    /**
     * Returns the Collection of neighbors of the node.
     * @return the Collection of all the neighbors of this Node.
     */
    public Collection<? extends Node> getNeighbors ()
    {
        return _list;
    }
}
