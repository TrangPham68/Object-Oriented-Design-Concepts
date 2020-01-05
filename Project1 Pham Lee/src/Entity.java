import java.awt.*;
import java.util.ArrayList;

// Entity is a super class for Possession, Moment, and FriendableEntity.
public class Entity {
    String _name;
    Image _image;

    // Constructor that sets the name and image of the specified object
    public Entity(String name, Image image)
    {
        this._name = name;
        this._image = image;
    }

    // returns true if the name of the specified object is the same as name of the target object
    public boolean equals(Object o)
    {
        Entity thing = (Entity) o;
        return (_name.equals(thing._name));
    }
}
