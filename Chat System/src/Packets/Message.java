package Packets;
import java.sql.Date;

public class Message extends Packet{
	private Date time;
	private String from;
	private String payload;
	
	public Message(Date time, String from, String payload){
		this.time = time;
		this.from = from;
		this.payload = payload;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}
