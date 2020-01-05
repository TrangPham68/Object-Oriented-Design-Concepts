// FriendRequest is a class dealing with friend requests of different people and approving those requests.
class FriendRequest
{
    boolean _approve1, _approve2;
    FriendableEntity _entity1, _entity2;
    // Constructor that sets the boolean values(approve1 and approve2) and Friendableentities(entity1 and entity2)
    public FriendRequest(FriendableEntity entity1, FriendableEntity entity2)
    {
        this._entity1 = entity1;
        this._entity2 = entity2;
        _approve1 = false;
        _approve2 = false;
    }

    void setApprove1 (boolean a)
    {
        _approve1 = a;
    }
    void setApprove2 (boolean a)
    {
        _approve2 = a;
    }

    // approve method confirms when the specified entity approves the friend request.
    // If both entities approve the requests, then they become friends.
    public void approve (FriendableEntity entity) {
        if ((entity.equals(_entity1)) || (entity.equals(_entity2))) {
            if (entity.equals(_entity1)) {
                setApprove1(true);
            }
            if (entity.equals(_entity2)) {
                setApprove2(true);
            }
            if (_approve1 == true && _approve2 == true) {
                _entity1.addFriend(_entity2);
                _entity2.addFriend(_entity1);
            }
        }
        else
        {throw new IllegalArgumentException("They are not friends");}
    }

}
