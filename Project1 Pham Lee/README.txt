*Entity.java
Entity is a superclass Of FriendableEnity class, Possession class and Moment class. Knowing that the main four classes 
implements name and image in their constructor and a method called "Equals" which is used to check if the target and specified 
entities have the same name, we made Entity classes to be a super classes to avoid duplication in the syntax among the main four classes.


*FriendableEntity.java
- FriendableEntity is a class for Entities that can clarify as friend (Pet and Person)
- FriendableEnity inherits name and image from Entity class, and also it is a superclass for Person class and Pet class. 
- In fours main classes, we see that all four has similar in name and image, 
but there are still many similarity between Person and Pet so this class is created also
to avoid duplication of syntax in Person and Pet
- In addition to the constructor inherited from Entity, we add in two ArrayLists(friendList and MomentList)
- There are also method that Pet and Person can all used as both can be seen as Friend.
	+setFriends 
	+setMoments
	+addFriend 
		//add friend (Pet or Person) to the list of friend (friendList)
	+getFriendWithWhomIAmHappiest 
		//return a friend (Pet or Person) that the person/or Pet feels most happy with
	+getOverallHappiestMoment
		//return a moment that the person/ or Pet feels most happy with
	+isCLique
		//check if a groups of friends are clique (everybody is friend of each other)
	+findMaximumCliqueOfFriends
		//find the largest group of cliqued friends in a friendList
 

*FriendRequest
- FriendRequest is a class that takes in two FriendableEntity (Person/person, pet/pet or person/pet)
because the friend request needs the approval/involvement of both entities.
- FriendRequest also has approve method to take care of the requests. When the request is placed out between 2 
friendable enitites, only when both of the friendable enities agree, they will be added to friendList of each other.
- FriendRequest exsits to take care of the request and the approve process that we cannot put in other class
- Friendrequest has ownership with FriendableEntity because it needs to have information from two friendable entities 
for the request. 
