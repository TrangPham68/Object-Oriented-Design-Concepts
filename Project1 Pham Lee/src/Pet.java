// Pet is a class that creates a pet with an owner, name, and image.
class Pet extends FriendableEntity
{
    Person _owner;
    // Constructor sets the name and image of the pet using a super class.
    public Pet(String name, Image image)
    {
        super(name, image);
    }

    // setOwner method that sets the owner of the pet
    public void setOwner(Person owner)
    {
        _owner = owner;
    }
}