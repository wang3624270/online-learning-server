package cn.edu.sdu.uims.base;

public class UNameObjectPar {
	String name;
	Object object;
	public UNameObjectPar(){
		this("",null);
	}
	
	public UNameObjectPar(String name, Object obj){
		this.name = name;
		this.object = obj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String toString(){
		return name;
	}
}
