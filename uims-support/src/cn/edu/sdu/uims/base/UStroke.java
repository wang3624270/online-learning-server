package cn.edu.sdu.uims.base;

import java.awt.Stroke;
import java.io.Serializable;

import cn.edu.sdu.uims.graph.view.UBasicStroke;

public class UStroke implements Serializable{
	public String name;
	public float width = 1.0f;
	public float []dash;
	//public Object obj = null;
	public int cap=UBasicStroke.CAP_BUTT;
	public int join=UBasicStroke.JOIN_BEVEL;
	public float miterlimit=0;
	public float dash_phase=0;
	transient public Stroke stroke;	
	
	public UStroke(String name,float width){
		this.name = name;
		this.width=width;
//		stroke=new UBasicStroke(width);
	}
	public UStroke(String name,float width,int cap, int join) {
		this.name=name;
		this.width=width;
		this.cap=cap;
		this.join=join;
//		stroke=new UBasicStroke(width,cap,join);
		
	}
	public UStroke(String name,float width, int cap, int join, float miterlimit) {
		this.name=name;
		this.width=width;
		this.cap=cap;
		this.join=join;
		this.miterlimit=miterlimit;
//		stroke=new UBasicStroke(width,cap,join,miterlimit);
		
	}

	public UStroke(String name,float width, int cap, int join, float miterlimit, float[] dash, float dash_phase) {
		this.name=name;
		this.width=width;
		this.cap=cap;
		this.join=join;
		this.miterlimit=miterlimit;
		this.dash=dash;
		this.dash_phase=dash_phase;
//		stroke=new UBasicStroke(width,cap,join,miterlimit,dash,dash_phase);
		
	}
	public UStroke(String name,float width,float[] dash){
		this.name = name;
		this.width=width;
		this.dash=dash;
//		stroke=new UBasicStroke(width, cap,
//				join, miterlimit, dash, dash_phase);
	}
	public void MakeBasicStroke(){
		if(stroke == null) {
			if(dash != null) {
				stroke=new UBasicStroke(width, cap,
				join, miterlimit, dash, dash_phase);				
			}
			else {
				stroke=new UBasicStroke(width);				
			}
		}
	}
}

