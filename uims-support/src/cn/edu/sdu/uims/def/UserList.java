package cn.edu.sdu.uims.def;

import javax.swing.ImageIcon;
import javax.swing.JList;

/*
 * UserList��һ��JList
 * ����ʾ�û��б�
 */
public class UserList
    extends JList {
  UserListData[] data;
  public int size;
  ImageIcon icon;

  public UserList() {
    super();
    icon=new ImageIcon("image/icon.gif");
    /*icon = new ImageIcon(
        getClass().getResource("image/icon.gif"));*/
  }

  public void setData(Object[] obj) {
    size = obj.length;

    data = new UserListData[obj.length];
    for (int i = 0; i < obj.length; ++i) {
      data[i] = new UserListData(obj[i], icon);
    }
    this.setCellRenderer(new UserListCellRenderer());
    setListData(data);
  }
}
