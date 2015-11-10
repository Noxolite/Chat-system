package Packets;

public class Bye extends Packet{
	private String nickname;
	
	public Bye(String nickname){
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
