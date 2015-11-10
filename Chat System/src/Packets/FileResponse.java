package Packets;

public class FileResponse extends Packet{
	private Boolean response;
	private String id;
	
	public FileResponse(Boolean response, String id){
		this.response = response;
		this.id = id;
	}

	public Boolean getResponse() {
		return this.response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
