
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class StringData implements DataProvider <String, String>{

    List <String> a;
    List <String> b;

    /**
     * Sets the key and value of the List
     */
    public StringData( ) {
        a = new ArrayList<>();
        a.add ("a");
        a.add ("b");
        a.add ("c");
        a.add ("d");
        a.add ("e");
        a.add ("f");
        a.add ("g");
        a.add ("h");

        b= new ArrayList<>();
        b.add ("1");
        b.add ("2");
        b.add ("3");
        b.add ("4");
        b.add ("5");
        b.add ("6");
        b.add ("7");
    }
    /**
     * Returns the value associated with the specified key.
     * @param str
     * @return str
     */
    public String get(String str)
    {
        for (int i = 0; i< a.size(); i++)
        {
            if (str==null)
            {
                return null;
            }
            if (str.equals(a.get(i)))   //how to get value
                return b.get(i);
        }
        return null;   //what if even data provider doesn't have it
    }
}
