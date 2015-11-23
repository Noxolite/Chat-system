package lists;

import java.util.*;

public class UserList extends Observable {

	private static UserList instanceUserList;

	private ArrayList<User> listUsers;

	private UserList() {
		this.listUsers = new ArrayList<User>();
	}

	public static synchronized UserList getInstance(){
		if (instanceUserList == null){
			instanceUserList = new UserList();
		}
		return instanceUserList;
	}

	public ArrayList<User> getUserList(){
		return this.listUsers;
	}

	public void addUser(User user){    
		this.listUsers.add(user);
		this.setChanged();
		this.notifyObservers(user);
		System.out.println(this.listUsers.toString());
	}
	
	public void deleteUser(User user){
		/*if(this.listUsers.contains(user)){
			System.out.println("User trouve");
		} else{
			System.out.println("User non trouve");
		}*/
		this.listUsers.remove(user);
		this.setChanged();
		this.notifyObservers(user);
		System.out.println(this.listUsers.toString());
	}
	
	public void removeAll(){
		this.listUsers.clear();
		this.setChanged();
		this.notifyObservers(0);
	}
	
	/*public boolean contains(User user){
		boolean trouve = false;
		/*Iterator<User> it = this.listUsers.iterator();
		while(it.hasNext() && trouve == false){
			if(it.next().equals(user)){
				trouve = true;
			}
		}
		for(User us : this.listUsers){
			if(us.equals(user)){
				trouve = true;
			}
		}
		return trouve;
	}*/

}
