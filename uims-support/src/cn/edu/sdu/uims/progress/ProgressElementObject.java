package cn.edu.sdu.uims.progress;


import java.io.Serializable;

public class ProgressElementObject implements Serializable{


	private int no;

	private int min = 0;

	private int max = 100;

	private int pos = 0;

	private String addString = "";

	public ProgressElementObject() {
		this(0);
	}
	public ProgressElementObject( int no) {
		super();
		this.no = no;
	}
	public ProgressElementObject(ProgressElementObject old){
		this.addString = old.addString;
		this.no = old.no;
		this.min = old.min;
		this.max = old.max;
		this.pos = old.pos;
	}
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}


	public String getKey() {
		return ""+ no;
	}


	public String getAddString() {
		return addString;
	}

	public void setAddString(String addString) {
		this.addString = addString;
	}
	public void appendAddString(String addString) {
		this.addString += addString;
	}
	public int getValue(){
		return Math.min((int)(pos*100/max),100);
	}
	public void clearAddString() {
		addString ="";
	}

}
