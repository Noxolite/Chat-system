package ChatSystem;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {

	private String nickName;
	private InetAddress ip;
	
	public User(String nickName){
		try {
			this.ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.nickName = nickName + "(" + ip.toString() + ")";
	}
	
	public User(String nickName, InetAddress ip){
		this.ip = ip;
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public InetAddress getIp() {
		return ip;
	}
	
	public String toString(){
		return this.nickName;
	}
	
}
