package cn.edu.sdu.uims.component.tree;

import java.io.Serializable;

import javax.swing.JMenuItem;

import cn.edu.sdu.uims.component.menu.UMenuInfo;

/**
 * 树导航使用的树结点，并且还用于权限表格的生成
 * @author created by zzy 060923
 */
public class UTreeNodeMenu extends UTreeNode implements Serializable {
    private static final long serialVersionUID = -6131302752134893836L;

    private int id = -1;

    // other attributes here

    private JMenuItem relatedMenuItem;


    private UMenuInfo menuInfo;
    
    
    public UTreeNodeMenu(int id, String nam) {
        this(id, nam, null, null);
    }

    public UTreeNodeMenu(int id, String nam, JMenuItem item) {
        this(id, nam, item, null);
    }

    public UTreeNodeMenu(int id, String nam, UMenuInfo info) {
        this(id, nam, null, info);
    }

    public UTreeNodeMenu(int id, String nam, JMenuItem item, UMenuInfo info) {
        super(nam);
        this.name = nam;

        this.id = id;
        relatedMenuItem = item;
        menuInfo = info;
    }

    // get or set method ... eg. getID() setID()...
    public JMenuItem getRelatedMenuItem() {
        return relatedMenuItem;
    }

    public void setRelatedMenuItem(JMenuItem item) {
        relatedMenuItem = item;
    }


   public UMenuInfo getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(UMenuInfo menuInfo) {
        this.menuInfo = menuInfo;
    }
    
}