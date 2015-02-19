package main.java.de.oc.dojo.forestfire;

public class Position {

	private int col;
	private int row;
	
	
	public Position(int row, int col) {
		this.col = col;
		this.row = row;
	}


	public int getColumn() {
		return col;
	}


	public void setColumn(int x) {
		this.col = x;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int y) {
		this.row = y;
	}
}
