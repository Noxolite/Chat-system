package packet;

import java.net.InetAddress;

public class HelloBack extends Packet{
	private String nickname;
	private InetAddress ip;
	static final long serialVersionUID = 44L;
	
	public HelloBack(String nickname, InetAddress ip){
		this.ip = ip;
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public InetAddress getIp() {
		return this.ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}	
}
