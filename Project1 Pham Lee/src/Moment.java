import java.awt.*;
import java.util.ArrayList;


// Moment is a class that shows a certain Moment that the Person is in.
class Moment extends Entity
{

    String name;
    Image image;
    public ArrayList _participants;
    public ArrayList _smileValues;

    // Constructor that sets the name, image, participants of the moment, and the smilevalues of the participants
    public Moment(String name, Image image, ArrayList participants, ArrayList smilevalues)
    {
        super(name, image);
        _participants = new ArrayList();
        _participants = participants;
        _smileValues = new ArrayList ();
        _smileValues = smilevalues;
    }

}


