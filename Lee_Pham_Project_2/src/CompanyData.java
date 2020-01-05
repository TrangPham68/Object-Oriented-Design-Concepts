import java.util.HashMap;

public class CompanyData implements DataProvider<String, Integer>
{
    HashMap<String,Integer> _company = new HashMap<String,Integer>();

    /**
     * Puts a key and value inside a hashmap
     */
    public CompanyData()
    {
        _company.put("Trump", 10);
        _company.put("Hillary", 5);
        _company.put("Andy", 100);
        _company.put("Chris", 60);
        _company.put("Trang", 140);
        _company.put("Billbo", -100);
    }

    /**
     * Returns the value associated with the specified key.
     * @param name
     * @return
     */
    public Integer get(String name)
    {
        if(_company.containsKey(name))
        {
            return _company.get(name);
        }
        else
        {
            return null;
        }
    }
}