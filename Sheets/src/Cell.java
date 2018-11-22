

public class Cell {
	public String value = "";
	public int r = 0, c = 0;
	public Cell() {
		this.value = "";
	}
	public Cell(String value) {
		this.value = value;
	}
	public Cell(int r, int c, String value) {
		this.r = r;
		this.c = c;
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	protected void setPosition(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
