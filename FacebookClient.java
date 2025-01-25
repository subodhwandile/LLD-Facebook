package facebookLLD;

import java.time.LocalDate;
import java.util.*;

public class FacebookClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<WorkExperience> workHistory = new ArrayList<>();
		workHistory.add(new WorkExperience(
				"TechCorp",
				Role.SDE,
				LocalDate.of(2017, 6, 1), // June 1, 2017
				LocalDate.of(2020, 12, 31), // December 31, 2020
				false
				));
		workHistory.add(new WorkExperience(
				"InnovateX",
				Role.SSE,
				LocalDate.of(2021, 1, 1), // January 1, 2021
				null,
				true // Current organization
				));

		// Create Education objects
		List<Education> educationHistory = new ArrayList<>();
		educationHistory.add(new Education(
				"Tech Institute of Technology",
				"Tech University",
				Degree.BTECH,
				LocalDate.of(2013, 8, 1), // August 1, 2013
				LocalDate.of(2017, 6, 30), // June 30, 2017
				false
				));
		educationHistory.add(new Education(
				"Global Academy",
				"Global University",
				Degree.MTECH,
				LocalDate.of(2018, 8, 1), // August 1, 2018
				LocalDate.of(2020, 6, 30), // June 30, 2020
				false
				));

		// Create User object
		User user1 = new User(
				"John Doe",
				"U1",
				"password123",
				workHistory,
				educationHistory
				);
		User user2 = new User(
				"Subodh",
				"U2",
				"password123",
				workHistory,
				educationHistory
				);
		User user3 = new User(
				"Vaibhav",
				"U3",
				"password123",
				workHistory,
				educationHistory
				);
		FacebookSystem facebookObj = FacebookSystem.getInstance();
		facebookObj.addUser(user1);
		facebookObj.addUser(user2);
		facebookObj.addUser(user3);
		List<User> searchedUsers = facebookObj.search(SearchStrategyType.NAME, "subodh");
		for (User user : searchedUsers) {
			System.out.println(user.getName());
		}
		facebookObj.sendFollowRequest(user1, user2);
		user2.displayFollowRequest();
		user2.acceptRejectFollowRequest(user2, true);
		Post newPost = new Post("Subodh first post", "example description");
		facebookObj.addPost("U2", newPost);
	}
}

enum SearchStrategyType {
	NAME,
}

interface ISearchStrategy {
	public List<User> search(String searchKey, Map<String, User> usersList);
}

class SearchByName implements ISearchStrategy{

	@Override
	public List<User> search(String searchKey, Map<String, User> usersList) {
		// TODO Auto-generated method stub
		List<User> result = new ArrayList<>();
		for (User value : usersList.values()) {
			if ((value.getName().toLowerCase()).contains(searchKey.toLowerCase())) {
				result.add(value);
			}
		}
		return result;
	}
}

class SearchStrategyFactory {
	public static ISearchStrategy createSearchObj(SearchStrategyType type) {
		switch(type) {
		case NAME :
			return new SearchByName();
		default :
			System.out.println("No search strategy in system");
			return null;
		}
	}
}

class FacebookSystem {
	private static FacebookSystem instance;
	private final UserController userController;
	private FacebookSystem() {
		this.userController = UserController.getInstance();
	}
	public void addPost(String userID, Post newPost) {
		// TODO Auto-generated method stub
		userController.addPost(userID, newPost);
	}
	public void sendFollowRequest(User fromUser, User toUser) {
		// TODO Auto-generated method stub
		userController.sendFollowRequest(fromUser, toUser);
	}
	public List<User> search(SearchStrategyType type, String searchKey) {
		// TODO Auto-generated method stub
		ISearchStrategy strategyObj = SearchStrategyFactory.createSearchObj(type);
		return userController.searchUser(strategyObj, searchKey);
	}
	public static FacebookSystem getInstance() {
		if (instance == null) {
			synchronized(FacebookSystem.class) {
				if (instance == null) {
					instance = new FacebookSystem();
				}
			}
		}
		return instance;
	}
	public void addUser(User user) {
		userController.addUser(user);
	}
}

class UserController {
	private static UserController instance;
	private Map<String, User> userMap;
	private UserController() {
		this.userMap = new HashMap<>();
	}
	public void addPost(String user2, Post newPost) {
		// TODO Auto-generated method stub
		if (userMap.containsKey(user2)) {
			User user = userMap.get(user2);
			user.addPost(newPost);
		} else {
			System.out.println("User not found");
		}
	}
	public void sendFollowRequest(User fromUser, User toUser) {
		// TODO Auto-generated method stub
		toUser.addFollowRequest(fromUser);
	}
	public List<User> searchUser(ISearchStrategy strategyObj, String searchKey) {
		// TODO Auto-generated method stub
		return strategyObj.search(searchKey, userMap);
	}
	public void addUser(User user) {
		// TODO Auto-generated method stub
		if (userMap.containsKey(user.getId())) {
			userMap.put(user.getId(), user);
			System.out.println("User " + user.getName() + " is updated");
		} else {
			userMap.put(user.getId(), user);
			System.out.println("User " + user.getName() + " is added");
		}
	}
	public static UserController getInstance() {
		if (instance == null) {
			synchronized(UserController.class) {
				if (instance == null) {
					instance = new UserController();
				}
			}
		}
		return instance;
	}
}
class Post {
	private String title;
	private String description;
	//private image photo
	public Post(String title, String description) {
		this.title = title;
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Post [title=" + title + ", description=" + description + "]";
	}
	
}
class User {
	private String name;
	private String id;
	private String password;
	private List<WorkExperience> workHistory;
	private List<Education> educationHistory;
	private List<User> followRequests;
	private List<User> followers;
	private List<User> following;
	private List<Post> posts;
	private List<Post> feed;
	public User(String name, String id, String password, List<WorkExperience> workHistory,
			List<Education> educationHistory) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.workHistory = workHistory;
		this.educationHistory = educationHistory;
		this.followers = new ArrayList<>();
		this.following = new ArrayList<>();
		this.followRequests = new ArrayList<>();
		this.posts = new ArrayList<>();
		this.feed = new ArrayList<>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}

	public List<User> getFollowRequests() {
		return followRequests;
	}
	public void setFollowRequests(List<User> followRequests) {
		this.followRequests = followRequests;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public List<User> getFollowing() {
		return following;
	}
	public void setFollowing(List<User> following) {
		this.following = following;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Post> getFeed() {
		return feed;
	}
	public void setFeed(List<Post> feed) {
		this.feed = feed;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<WorkExperience> getWorkHistory() {
		return workHistory;
	}
	public void setWorkHistory(List<WorkExperience> workHistory) {
		this.workHistory = workHistory;
	}
	public List<Education> getEducationHistory() {
		return educationHistory;
	}
	public void setEducationHistory(List<Education> educationHistory) {
		this.educationHistory = educationHistory;
	}
	public void addFollower(User user) {
		followers.add(user);
	}
	public void addFollowing(User user) {
		following.add(user);
	}
	public void addFollowRequest(User user) {
		sendNotification(user.getName() + " sent you follow request");
		followRequests.add(user);
	}
	public void displayFollowRequest() {
		for (User user : followRequests) {
			System.out.println(user.toString());
		}
	}

	public void acceptRejectFollowRequest(User user, boolean followFlag) {
		followRequests.remove(user);
		if (followFlag == true) {
			addFollower(user);
			user.sendNotification(this.name + " accepted your follow request");
		} else {
			user.sendNotification(this.name + " rejected your follow request");
		}
	}

	public void sendNotification(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	public void addPost(Post post) {
		System.out.println(this.name + " post success");
		posts.add(post);
		notifyAllObservers(post);
	}

	private void notifyAllObservers(Post post) {
		// TODO Auto-generated method stub
		for (User follower : this.followers) {
			System.out.println(this.name + " posted new photo");
			follower.addPostToFeed(post);
		}
	}
	private void addPostToFeed(Post post) {
		// TODO Auto-generated method stub
		feed.add(post);
	}
	@Override
	public String toString() {
		return "User{name='" + name + "', followers=" + followers.size() + ", following=" + following.size() + "}";
	}
}

enum Role {
	SDE,
	SDE2,
	SSE
}

class WorkExperience {
	private String organization;
	private Role role;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isPresentOrganization;
	public WorkExperience(String organization, Role role, LocalDate startDate, LocalDate endDate, boolean isPresentOrganization) {
		this.organization = organization;
		this.role = role;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isPresentOrganization = isPresentOrganization;
	}
}

enum Degree {
	BTECH,
	BE,
	MTECH
}

class Education {
	private String college;
	private String university;
	private Degree degree;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isPersuing;
	public Education(String college, String university, Degree degree, LocalDate startDate, LocalDate endDate,
			boolean isPersuing) {
		this.college = college;
		this.university = university;
		this.degree = degree;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isPersuing = isPersuing;
	}
}