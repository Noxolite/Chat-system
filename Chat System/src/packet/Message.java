package packet;

import java.net.InetAddress;
import java.util.Date;

public class Message extends Packet{
    private Date time;
    private String from;
    private String payload;
    private InetAddress ip ;

    public Message(Date time, String from, String payload, InetAddress ip){
        this.time = time;
        this.from = from;
        this.payload = payload;
        this.ip = ip;
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

    public void setPayload(String payload) {this.payload = payload;}

    public InetAddress getIp () {return ip ;}

    public void setIp(InetAddress ip){this.ip=ip;}


}