import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is a SUBSET of the unit tests with which we will grade your code.
 *
 * Make absolute sure that your code can compile together with this tester!
 * If it does not, you may get a very low grade for your assignment.
 */
public class FacebukPartialTester {
    private Person _barack, _michelle, _kevin, _ina, _joe, _malia;
    private Pet _bo, _sunny;
    private Moment _meAndBarack, _meJoeAndCo;
    private ArrayList _michelleAndBarack, _michelleJoeBoAndMalia;

    @Before
    public void setUp () {
        initPeople();
        initPets();
        initGroups();
        initPossessions();
        initMoments();
    }

    private void initPeople () {
        _michelle = new Person("Michelle", new Image("Michelle.png"));
        _barack = new Person("Barack", new Image("Barack.png"));
        _kevin = new Person("Kevin", new Image("Kevin.png"));
        _ina = new Person("Ina", new Image("Ina.png"));
        _joe = new Person("Joe", new Image("Joe.png"));
        _malia = new Person("Malia", new Image("Malia.png"));
    }

    private void initPets () {
        _bo = new Pet("Bo", new Image("Bo.png"));
        _sunny = new Pet("Sunny", new Image("Sunny.png"));

        _bo.setOwner(_michelle);
        _sunny.setOwner(_michelle);
    }

    private void initGroups () {
        // Kevin, Barack, and Ina
        final ArrayList michelleFriends = new ArrayList();
        michelleFriends.add(_kevin);
        michelleFriends.add(_barack);
        michelleFriends.add(_ina);

        // Michelle and Barack
        _michelleAndBarack = new ArrayList();
        _michelleAndBarack.add(_michelle);
        _michelleAndBarack.add(_barack);

        // Michelle, Joe, Bo, and Malia
        _michelleJoeBoAndMalia = new ArrayList();
        _michelleJoeBoAndMalia.add(_michelle);
        _michelleJoeBoAndMalia.add(_joe);
        _michelleJoeBoAndMalia.add(_bo);
        _michelleJoeBoAndMalia.add(_malia);

        // Malia and Sunny
        final ArrayList maliaAndSunny = new ArrayList();
        maliaAndSunny.add(_malia);
        maliaAndSunny.add(_sunny);

        // Michelle
        final ArrayList michelleList = new ArrayList();
        michelleList.add(_michelle);

        // Bo
        final ArrayList boList = new ArrayList();
        boList.add(_bo);

        // Set people's friend lists
        _michelle.setFriends(michelleFriends);
        _malia.setFriends(boList);
        _sunny.setFriends(boList);
        _barack.setFriends(michelleList);
        _kevin.setFriends(michelleList);
        _ina.setFriends(michelleList);

        // Finish configuring pets
        _bo.setFriends(maliaAndSunny);
        _sunny.setFriends(boList);
        final ArrayList boAndSunny = new ArrayList();
        boAndSunny.add(_bo);
        boAndSunny.add(_sunny);
        _michelle.setPets(boAndSunny);
    }

    private void initPossessions () {
        final Possession boxingBag = new Possession("BoxingBag", new Image("BoxingBag.png"), 20.0f);
        boxingBag.setOwner(_michelle);
        final ArrayList michellePossessions = new ArrayList();
        michellePossessions.add(boxingBag);
        _michelle.setPossessions(michellePossessions);
    }

    private void initMoments () {
        // Smiles
        final ArrayList michelleAndBarackSmiles = new ArrayList();
        michelleAndBarackSmiles.add(0.25f);
        michelleAndBarackSmiles.add(0.75f);

        final ArrayList michelleJoeBoAndMaliaSmiles = new ArrayList();
        michelleJoeBoAndMaliaSmiles.add(0.2f);
        michelleJoeBoAndMaliaSmiles.add(0.3f);
        michelleJoeBoAndMaliaSmiles.add(0.4f);
        michelleJoeBoAndMaliaSmiles.add(0.5f);

        // Moments
        _meAndBarack = new Moment("Me & Barack", new Image("MeAndBarack.png"), _michelleAndBarack, michelleAndBarackSmiles);
        _meJoeAndCo = new Moment("Me, Joe & co.", new Image("MeJoeAndCo.png"), _michelleJoeBoAndMalia, michelleJoeBoAndMaliaSmiles);

        final ArrayList michelleMoments = new ArrayList();
        michelleMoments.add(_meAndBarack);
        michelleMoments.add(_meJoeAndCo);
        _michelle.setMoments(michelleMoments);

        final ArrayList barackMoments = new ArrayList();
        barackMoments.add(_meAndBarack);
        _barack.setMoments(barackMoments);

        final ArrayList joeMoments = new ArrayList();
        joeMoments.add(_meJoeAndCo);
        _joe.setMoments(joeMoments);

        final ArrayList maliaMoments = new ArrayList();
        maliaMoments.add(_meJoeAndCo);
        _malia.setMoments(maliaMoments);

        final ArrayList boMoments = new ArrayList();
        boMoments.add(_meJoeAndCo);
        _bo.setMoments(boMoments);
    }

    @Test
    public void testEquals () {
        assertEquals(_michelle, new Person("Michelle", new Image("Michelle.png")));
        assertEquals(_michelle, new Person("Michelle", new Image("Michelle2.png")));  // should still work
        assertNotEquals(_michelle, _barack);
    }

    @Test
    public void testFindBestMoment () {
        assertEquals(_michelle.getOverallHappiestMoment(), _meAndBarack);
    }


    @Test
    public void testGetFriendWithWhomIAmHappiest ()
    {
        assertEquals(_michelle.getFriendWithWhomIAmHappiest(), _barack);
    }

    @Test
    public void testFriendRequest1 () {
        Person person1 = new Person("person1", new Image("person1.png"));
        Person person2 = new Person("person2", new Image("person2.png"));
        Pet pet1 = new Pet("pet1", new Image("pet1.png"));

        FriendRequest friendRequest1 = new FriendRequest(person1, person2);
        // Make sure the code also compiles for making friend requests for people and pets
        FriendRequest friendRequest2 = new FriendRequest(person1, pet1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFriendRequest2 () {
        Person person1 = new Person("person1", new Image("person1.png"));
        Person person2 = new Person("person2", new Image("person2.png"));
        Person person3 = new Person("person3", new Image("person3.png"));
        FriendRequest friendRequest = new FriendRequest(person1, person2);
        // This should raise an IllegalArgumentException:
        friendRequest.approve(person3);
    }

    // TODO: write more methods to test addFriend
    @Test
    public void testAddFriend() {
        Person person1 = new Person("person1", new Image("person1.png"));
        _michelle.addFriend(person1);
        ArrayList f = new ArrayList();
        f.add(_kevin);
        f.add(_barack);
        f.add(_ina);
        f.add(person1);

        person1.addFriend(_kevin);
        ArrayList f1 = new ArrayList();
        f1.add(_kevin);
        assertEquals(f1, person1._friendList);
        assertEquals(f, _michelle._friendList);
    }
    // TODO: write more methods to test approve
    @Test
    public void testApprove() {    //something is wrong with the code in approve
        Person person1 = new Person("person1", new Image("person1.png"));
        Person person2 = new Person("person2", new Image("person2.png"));
        FriendRequest friendRequest = new FriendRequest(person1, person2);
        ArrayList f = new ArrayList();
        friendRequest.approve(person1);
        assertEquals(person1._friendList, f);

        ArrayList f1 = new ArrayList();
        f1.add(person1);
        ArrayList f2 = new ArrayList();
        f2.add(person2);
        friendRequest.approve(person2);
        assertEquals(person2._friendList, f1);
        assertEquals(person1._friendList, f2);
    }

    // TODO: write more methods to test getFriendWithWhomIAmHappiest
    @Test
    public void testGetFriendWithWhomIAmHappiest2 ()
    {
        assertNotEquals(_bo.getFriendWithWhomIAmHappiest(),_barack);
    }

    @Test
    public void testGetFriendWithWhomIAmHappiest3()
    {
        assertEquals(_malia.getFriendWithWhomIAmHappiest(),_bo);
    }

    @Test
    public void testGetFriendWithWhomIAmHappiest4()
    {
        assertEquals(_bo.getFriendWithWhomIAmHappiest(),_malia);
    }

    @Test
    public void testGetFriendWithWhomIAmHappiest5()
    {
        Person person1 = new Person("person1", new Image("person1.png"));
        assertEquals(person1.getFriendWithWhomIAmHappiest(), null);
    }

    @Test
    public void testGetFriendWithWhomIAmHappiest6()
    {
        Person person2 = new Person("person2", new Image("person2.png"));
        Person person3 = new Person("person3", new Image("person3.png"));
        Person person4 = new Person("person4", new Image("person4.png"));
        Person person5 = new Person("person5", new Image("person5.png"));
        final ArrayList person3and45 = new ArrayList();
        person3and45.add(person3);
        person3and45.add(person4);
        final ArrayList person3and45Smiles = new ArrayList();
        person3and45Smiles.add(0.25f);
        person3and45Smiles.add(0.75f);
        person2._friendList.add(person3);
        person2._friendList.add(person5);
        Moment moment1 = new Moment("three people", new Image("ThreePeop.png"), person3and45, person3and45Smiles);
        person2._momentList.add(moment1);
        assertEquals(person2.getFriendWithWhomIAmHappiest(), person3);
        assertNotEquals(person2.getFriendWithWhomIAmHappiest(), person4);
    }
    // TODO: write more methods to test getOverallHappiestMoment
    @Test
    public void testBestMoment1()
    {
        assertNotEquals(_malia.getOverallHappiestMoment(), _meAndBarack);
    }

    @Test
    public void testBestMoment2()
    {
        assertEquals(_malia.getOverallHappiestMoment(),_meJoeAndCo);
    }

    @Test
    public void testBestMoment3()
    {
        Person person2 = new Person("person2", new Image("person2.png"));
        assertEquals(person2.getOverallHappiestMoment(),null);
    }

    // TODO: write methods to test isClique
    @Test
    public void testisClique1()
    {
        assertEquals(_michelle.isClique(_michelleAndBarack), true);
        assertEquals(_michelle.isClique(_michelle._friendList), false);
    }
    // TODO: write methods to test findMaximumCliqueOfFriends
    @Test
    public void testfindMaximumCliqueOfFriends()
    {
        ArrayList r = new ArrayList();
        r.add(_michelle);
        assertEquals(_barack.findMaximumCliqueOfFriends(), r);
        ArrayList r1 = new ArrayList();
        r1.add(_kevin);
        assertEquals(_michelle.findMaximumCliqueOfFriends(), r1);
    }

    @Test
    public void testfindMaximumCliqueofFriends2()
    {
        Person Melania = new Person("Melania", new Image("Melania.png"));
        Person Marlon = new Person("Marlon", new Image("Marlon.png"));
        Person Kevin = new Person("Kevin", new Image("Kevin.png"));
        Person Hillary = new Person("Hillary", new Image("Hillary.png"));
        Person Ivana = new Person("Ivana", new Image("Ivana.png"));
        Person Char = new Person("Char", new Image("Char.png"));
        Person Hump = new Person("Hump", new Image("Hump.png"));
        Person Tom = new Person("Tom", new Image("Tom.png"));
        Person Robin = new Person("Robin", new Image("Robin.png"));
        Melania.addFriend(Ivana);
        Melania.addFriend(Kevin);
        Melania.addFriend(Hillary);
        Melania.addFriend(Marlon);
        Marlon.addFriend(Melania);
        Marlon.addFriend(Char);
        Marlon.addFriend(Kevin);
        Marlon.addFriend(Hump);
        Ivana.addFriend(Melania);
        Ivana.addFriend(Tom);
        Ivana.addFriend(Hillary);
        Ivana.addFriend(Kevin);
        Kevin.addFriend(Melania);
        Kevin.addFriend(Marlon);
        Kevin.addFriend(Robin);
        Kevin.addFriend(Hillary);
        Kevin.addFriend(Hump);
        Kevin.addFriend(Ivana);
        Hillary.addFriend(Melania);
        Hillary.addFriend(Ivana);
        Hillary.addFriend(Kevin);
        Hillary.addFriend(Robin);
        ArrayList cliq = new ArrayList();
        cliq.add(Ivana);
        cliq.add(Kevin);
        cliq.add(Hillary);
        assertEquals(Melania.isClique(cliq), true);

        ArrayList test1 = new ArrayList();
        test1.add(Ivana);
        test1.add(Kevin);
        assertEquals(Melania.isClique(test1), true);

        assertEquals(Melania.findMaximumCliqueOfFriends(),cliq);
    }

}
