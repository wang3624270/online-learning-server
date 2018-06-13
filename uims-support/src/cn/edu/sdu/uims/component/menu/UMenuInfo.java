package cn.edu.sdu.uims.component.menu;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.KeyStroke;
  
public class UMenuInfo implements Serializable {
	private static final long serialVersionUID = -2121889604583898623L;
	
	public UMenuInfo() {
	}
	public List<UMenuInfo> subMenuList;
	public String systemNo; //menuID是指菜单的唯一标志，用于进行监听表示
	public Integer menuNo; //menuNo指菜单编号
	public String menuName; //menuName菜单名称
	public Integer menuUpNo; //menuUpNo指菜单的上级菜单编号
	public String menuID;
	public int IsLeaf; //1代表是叶子菜单,2代表非叶子,0代表分隔符
	public String menudes; // 菜单描述: 现在存放帮助文档的相对路径 zhaopeng 2007.8.16
    /**menuRight指是否有权限使用菜单，"R"表示可见不可写 ，"V"表示不可见*/
	public String menuRight; 
	transient public JMenu menu; //menu用于存储实际的菜单对象，只有在实际生成菜单时，才有作用
	transient public KeyStroke accelerator;
	public Integer orderNum;
	public String menuCmd;
	public String menuContent;
	public String menuEnContent;
	public String csmType;
	public String target;
	public static MenuInfoComparator getComparator()
	{
		return MenuInfoComparator.getInstance();
	}
}
class MenuInfoComparator implements Comparator<UMenuInfo> {  
    
    private static MenuInfoComparator menuComparator = null;  
      
    public static MenuInfoComparator getInstance(){  
        if(menuComparator==null){  
        	menuComparator = new MenuInfoComparator();  
        }  
        return menuComparator;  
    }

	public int compare(UMenuInfo o1, UMenuInfo o2) {
		// TODO Auto-generated method stub

	    if (o1.orderNum < o2.orderNum) {
			return -1;
		} else if (o1.orderNum > o2.orderNum) {
			return 1;
		} else {
			return 0;
		}  
	     
	}  
  
   
} 