package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.constant.UConstants;

public class UChartTemplate extends UTemplate {
	public UCellAttribute title,note;
	public UCellAttribute getTitle() {
		return title;
	}
	public void setTitle(UCellAttribute title) {
		this.title = title;
	}
	public UCellAttribute getNote() {
		return note;
	}
	public void setNote(UCellAttribute note) {
		this.note = note;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String[][] getLabel() {
		return label;
	}
	public void setLabel(String[][] label) {
		this.label = label;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	public int dimension = 2;
	public int type = UConstants.CHART_CELL;
	public String label[][];
	public int data [];

	public void initDataObject(){
		int i = 0; 
		int count = 1;
		for(i = 0; i < dimension;i++){
			 count *=  label[i].length;
		}
		data = new int[count];
	}
	public void read(DataInputStream in) throws IOException {
		int len,len1,i,j;
		super.read(in);
		title = (UCellAttribute)readTemplate(in);
		note = (UCellAttribute)readTemplate(in);
		dimension = in.readInt();
		type = in.readInt();
		len = in.readInt();
		if(len  == 0)
			label = null;
		else {
			label = new String[len][];
			for(i = 0; i < len; i++) {
				len1 = in.readInt();
				if(len1 == 0){
					label[i] = null;
				}
				else {
					label[i] = new  String[len1];
					for(j = 0; j< len1;j++){
						label[i][j]= readString(in);
					}
				}
			}
		}
		len = in.readInt();
		if(len == 0){
			data = null;
		}
		else {
			data = new int[len];
			for(i = 0; i < len;i++){
				data[i] = in.readInt();
			}
		}
	}
	public void write(DataOutputStream out) throws IOException {
		int i,j;
		super.write(out);
		writeTemplate(out, title);
		writeTemplate(out, note);
		out.writeInt(dimension);
		out.writeInt(type);
		if(label == null) 
			out.writeInt(0);
		else {
			out.writeInt(label.length);
			for(i= 0;i <label.length;i++){
				if(label[i]== null)
					out.writeInt(0);
				else {
					out.writeInt(label[i].length);
					for(j = 0; j < label[i].length;j++)
						writeString(out,label[i][j]);
				}
			}
		}
		if(data == null)
			out.writeInt(0);
		else {
			out.writeInt(data.length);
			for(i = 0; i < data.length;i++)
				out.writeInt(data[i]);
		}
	}
}
