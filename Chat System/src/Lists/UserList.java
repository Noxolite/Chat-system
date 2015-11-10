package Lists;
import ChatSystem.User;
import java.util.*;
public class UserList {

	private static UserList instanceUserList;

	private ArrayList<User> users;

	private UserList() {
		this.users=new ArrayList<User>();
	}


	public static synchronized UserList getInstance(){
		if (instanceUserList == null){
			instanceUserList = new UserList();
		}
		return instanceUserList;
	}

	public ArrayList<User> getUserList(){
		return users;
	}

	public void addUser(User user){    
		getInstance().getUserList().add(user);    
	}

}
