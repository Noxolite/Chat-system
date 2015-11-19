package Lists;
import ChatSystem.User;

import java.net.InetAddress;
import java.util.*;

public class UserList extends Observable {

	private static UserList instanceUserList;

	private HashMap<InetAddress,String> hm;

	private UserList() {
		this.hm = new HashMap<InetAddress,String>();
	}

	public static synchronized UserList getInstance(){
		if (instanceUserList == null){
			instanceUserList = new UserList();
		}
		return instanceUserList;
	}

	public HashMap<InetAddress,String> getUserList(){
		return this.hm;
	}

	public void addUser(User user){    
		this.getUserList().put(user.getIp(), user.getNickName());
		setChanged();
		notifyObservers(user);
		System.out.println(this.hm.values().toString());
	}
	
	public void deleteUser(User user){
		this.getUserList().remove(user.getIp());
		setChanged();
		notifyObservers(user);
	}
	
	public void removeAll(){
		this.getUserList().clear();
		setChanged();
		notifyObservers(0);
	}
	
	public boolean contains(User user){
		return this.getUserList().containsValue(user.getNickName());
	}

}
