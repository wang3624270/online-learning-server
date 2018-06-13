package cn.edu.sdu.uims.def;

import javax.swing.Icon;

/*
 * һ��UserListData����б����һ��
 * ��һ��ͼ���һ���ַ����
 */
public class UserListData {
  private Icon icon;
  private Object obj;
  public UserListData(Object obj, Icon icon) {
    this.obj = obj;
    this.icon = icon;
  }

  public Object getItem() {
    return obj;
  }

  public void setItem(Object obj) {
    this.obj = obj;
  }

  public Icon getIcon() {
    return icon;
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
  }
}
