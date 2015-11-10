package Packets;

public class FileRequest extends Packet{
	private String path;
	private String filename;
	private String size;
	private String id;
	
	public FileRequest(String path, String filename, String size, String id){
		this.path = path;
		this.filename = filename;
		this.size = size;
		this.id = id; // Uniuqe ID to identify the request/file. See MessageDisgest()
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
