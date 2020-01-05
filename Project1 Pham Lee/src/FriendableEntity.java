import java.util.ArrayList;

// FriendableEntity is a super class of both Person and Pet.
public class FriendableEntity extends Entity
{
    ArrayList _friendList;
    ArrayList _momentList;

    // Constructor sets the name, image, friendList, and momentList
    public FriendableEntity(String name, Image image)
    {
        super(name, image);
        _friendList = new ArrayList();
        _momentList = new ArrayList();
    }

    // sets the friendList to the given list
    public void setFriends (ArrayList list)
    {
        _friendList= list;
    }

    // sets the momentList to the given list
    public void setMoments (ArrayList list)
    {
        _momentList= list;
    }

    // adds a friend into the friendList
    public void addFriend(FriendableEntity friend)
    {
        _friendList.add(friend);
    }

    // returns a friend with whom the person appears the most happy on average
    public FriendableEntity getFriendWithWhomIAmHappiest() {
        float ct = 0;
        float val = 0;
        float ave = 0;
        float max = 0;
        FriendableEntity end = null;

        for (int i = 0; i < _friendList.size(); i++) {
            for (int m = 0; m < _momentList.size(); m++) {
                Moment currMoment = (Moment) _momentList.get(m);
                if (currMoment._participants.contains((FriendableEntity) _friendList.get(i))) {
                    val += (float) currMoment._smileValues.get(i);
                    ct++;
                }
            }
            ave = val / ct;
            if (ave > max) {
                max = ave;
                end = (FriendableEntity) _friendList.get(i);
            }

        }
        return end;
    }

    // returns a Moment in which the average happiness value is highest
    public Moment getOverallHappiestMoment()
    {
        float ct = 0;
        float val = 0;
        float ave = 0;
        float max = 0;
        Moment mom = null;
            for (int m = 0; m < _momentList.size(); m++) {
                Moment currMoment = (Moment) _momentList.get(m);
                ArrayList smilesz = currMoment._smileValues;
                for(int i=0; i<smilesz.size(); i++)
                {
                    val += (float) smilesz.get(i);
                    ct++;
                }
                ave=val/ct;
                if (max < ave) {
                    max = ave;
                    mom=currMoment;
                }
            }
        return mom;
    }

    // returns true if all people/pets in the set are all friend with each other.
    // also returns true if set is empty
    public static boolean isClique(ArrayList set)
    {
        boolean sol = true;
        for (int i=0; i<set.size()-1; i++)
        {
            for(int j=i+1; j<set.size(); j++)
            {
                FriendableEntity first = (FriendableEntity) set.get(i);
                boolean val = first._friendList.contains(set.get(j));
                if(!val)
                {
                    sol = val;
                }
            }
        }
        return sol;
    }

    // returns a list containing a maximum clique of friends.
    // the list should not contain the target person.
    public ArrayList findMaximumCliqueOfFriends()
    {
        ArrayList fin = new ArrayList();
        for(int i=0; i<_friendList.size(); i++)
        {
            ArrayList dummy  = new ArrayList();
            dummy.add(_friendList.get(i));
            for(int j=0; j<_friendList.size(); j++)
            {
                FriendableEntity per = (FriendableEntity) _friendList.get(i);
                if(per._friendList.contains(_friendList.get(j)))
                {
                    dummy.add(_friendList.get(j));

                    /*if(!isClique(dummy))
                    {
                        dummy.remove(_friendList.get(j));
                    }*/

                }
            }
            if (isClique(dummy) && dummy.size()>fin.size())
            {
                fin = dummy;
            }
        }
        return fin;
    }
}
