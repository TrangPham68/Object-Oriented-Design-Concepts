import java.awt.*;
import java.util.ArrayList;
// Person is a class that contains the properties of the people. It
// It sets name, Image, Posessionslist, and Petslist of the person
class Person extends FriendableEntity
{
    ArrayList _possessionslist;
    ArrayList _petslist;

    // sets the name and image of the person using the super class
    public Person(String name, Image image)
    {
        super(name, image);
    }

    // sets the Possessionslist to the given ArrayList Possessions
    public void setPossessions (ArrayList Possessions)
    {
        _possessionslist = Possessions;
    }

    // sets the Petslist to the given ArrayList Pets
    public void setPets (ArrayList Pets)
    {
        _petslist = Pets;
    }
}