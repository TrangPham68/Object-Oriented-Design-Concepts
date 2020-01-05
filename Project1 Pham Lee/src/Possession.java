// Possession is a class sets the price of the possession, name, and image. It also modifies certain fields.
class Possession extends Entity
{
    Person _owner;
    private float _price;

    //Constructor that sets the name, image, and price of the Possession
    public Possession(String name, Image image, float val)
    {
        super(name, image);
        _price = val;
    }

    // returns the price of the Possession
    public float getPrice()
    {
        return _price;
    }

    // sets the owner of the Possession
    public void setOwner(Person owner)
    {
        _owner = owner;
    }
}
