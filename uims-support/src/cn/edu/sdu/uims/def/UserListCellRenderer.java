package cn.edu.sdu.uims.def;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/*
 * UserList��Ⱦ��
 */
public class UserListCellRenderer
    extends JLabel implements ListCellRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
                                                int index, boolean isSelected,
                                                boolean hasFocus) {
//��������б�����ݣ�����UserListData�����Լ������һ����
    UserListData data = (UserListData) value;
    /*
     * ��JLabel�Ϸ���ͼ����ַ�
     * �������ı����ұ�,�Լ�}�ߵļ��
     */
    setText(data.getItem().toString());
    setIcon(data.getIcon());
    setHorizontalTextPosition(SwingConstants.RIGHT);
    setIconTextGap(5);
    setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

    /*
     * ������Ŀ��ѡ��ʱ����ɫ
     */
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);
    return this;
  }
}
