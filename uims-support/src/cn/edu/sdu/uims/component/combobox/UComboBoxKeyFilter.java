package cn.edu.sdu.uims.component.combobox;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.util.PinYinUtil;

public class UComboBoxKeyFilter extends UComboBoxType {

	/**
	 * 显示用模型
	 */
	protected DefaultComboBoxModel showModel;
	/**
	 * 正在选择
	 */
	private boolean selectingItem;
	private MetalFilterComboBoxUI ui;
	private Integer currentMouseIndex = -1;

	/**
	 * 创建一个JFilterComboBox， 其项取自现有的ComboBoxModel。 由于提供了ComboBoxModel，
	 * 使用此构造方法创建的组合框不创建默认组合框模型， 这可能影响插入、移除和添加方法的行为方式。
	 * 
	 * @param aModel
	 *            - 提供显示的项列表的 ComboBoxModel
	 */
	public UComboBoxKeyFilter() {
		showModel = new DefaultComboBoxModel();
		ui = new MetalFilterComboBoxUI(this);
	}

	public void initContents() {
		// TODO Auto-generated method stub
		setUI(ui);
		this.setMaximumRowCount(20);
		// showModel.removeAllElements();
		currentMouseIndex = -1;
		showModel.addListDataListener(this);
	}

	public void setData(Object obj) {
		if (obj != null && obj instanceof Integer) {
			ComboBoxModel model = getModel();
			int value = (Integer) obj;
			int size = model.getSize();
			if (obj == null || value == -1 || size == 0 || value == 0) {
				getEditor().setItem(null);
			} else if (value < -1) {
				throw new IllegalArgumentException("setSelectedIndex: " + value
						+ " out of bounds");
			} else {
				getEditor().setItem(null);
				for (int i = 0; i < size; i++) {
					ListOptionInfo option = (ListOptionInfo) model
							.getElementAt(i);
					if (Integer.parseInt(option.getValue()) == value) {
						getEditor().setItem(option);
						break;
					}
				}
			}
		} else {
			getEditor().setItem(null);
		}
	}

	public void setOldIndex(Integer index) {
		ComboBoxModel model = getModel();
		int size = model.getSize();
		if (index == null || index == -1 || size == 0 || index == 0) {
//			getEditor().setItem(null);
		} else if (index < -1) {
			throw new IllegalArgumentException("setSelectedIndex: " + index
					+ " out of bounds");
		} else {
			ListOptionInfo option = (ListOptionInfo) model.getElementAt(index);
			getEditor().setItem(option);
		}
	}

	public void setUserData(Object obj) {
		getEditor().setItem(obj);
	}

	@Override
	public void updateUI() {
		// setUI();
		ListCellRenderer renderer = getRenderer();
		if (renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}

	@Override
	public Object getSelectedItem() {
		return showModel.getSelectedItem();
	}

	@Override
	public void setSelectedItem(Object anObject) {
		Object oldSelection = selectedItemReminder;
		Object objectToSelect = anObject;
		if (oldSelection == null || !oldSelection.equals(anObject)) {

			if (anObject != null && !isEditable()) {
				boolean found = false;
				for (int i = 0; i < showModel.getSize(); i++) {
					Object element = showModel.getElementAt(i);
					if (anObject.equals(element)) {
						found = true;
						objectToSelect = element;
						break;
					}
				}
				if (!found) {
					return;
				}
			}

			selectingItem = true;
			showModel.setSelectedItem(objectToSelect);
			selectingItem = false;

			if (selectedItemReminder != showModel.getSelectedItem()) {
				selectedItemChanged();
			}
		}
		fireActionEvent();
	}

	@Override
	public void setSelectedIndex(int anIndex) {
		currentMouseIndex = anIndex;
		int size = showModel.getSize();
		if (anIndex == -1 || size == 0) {
			setSelectedItem(null);
		} else if (anIndex < -1) {
			throw new IllegalArgumentException("setSelectedIndex: " + anIndex
					+ " out of bounds");
		} else if (anIndex >= size) {
			setSelectedItem(showModel.getElementAt(size - 1));
		} else {
			setSelectedItem(showModel.getElementAt(anIndex));
		}

	}

	@Override
	public int getSelectedIndex() {
		Object sObject = showModel.getSelectedItem();
		int i, c;
		Object obj;

		for (i = 0, c = showModel.getSize(); i < c; i++) {
			obj = showModel.getElementAt(i);
			if (obj != null && obj.equals(sObject))
				return i;
		}
		return -1;
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		Object oldSelection = selectedItemReminder;
		Object newSelection = showModel.getSelectedItem();
		if (oldSelection == null || !oldSelection.equals(newSelection)) {
			selectedItemChanged();
			if (!selectingItem) {
				fireActionEvent();
			}
		}
	}

	@Override
	protected void selectedItemChanged() {
		if (selectedItemReminder != null) {
			fireItemStateChanged(new ItemEvent(this,
					ItemEvent.ITEM_STATE_CHANGED, selectedItemReminder,
					ItemEvent.DESELECTED));
		}

		selectedItemReminder = showModel.getSelectedItem();

		if (selectedItemReminder != null) {
			fireItemStateChanged(new ItemEvent(this,
					ItemEvent.ITEM_STATE_CHANGED, selectedItemReminder,
					ItemEvent.SELECTED));
		}
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// currentMouseIndex = e.getIndex0();
		if (selectedItemReminder != showModel.getSelectedItem()) {
			selectedItemChanged();
		}
	}

	@Override
	public void setEditable(boolean aFlag) {
		super.setEditable(true);
	}

	/**
	 * 返回显示用模型
	 * 
	 * @return
	 */
	public DefaultComboBoxModel getShowModel() {
		return showModel;
	}

	/**
	 * Metal L&F 风格的 UI 类
	 * 
	 * @author Sun
	 * 
	 */
	class MetalFilterComboBoxUI extends MetalComboBoxUI {

		/**
		 * 编辑区事件监听器
		 */
		protected EditorListener editorListener;
		/**
		 * 该 UI 类负责绘制的控件
		 */
		protected UComboBoxKeyFilter filterComboBox;

		private ComboPopup comPopup;

		public MetalFilterComboBoxUI(UComboBoxKeyFilter c) {
			filterComboBox = c;
			comPopup = new FilterComboPopup(filterComboBox);
		}

		@Override
		public void installUI(JComponent c) {
			// filterComboBox = (UComboBoxKeyFilter) c;
			filterComboBox.setEditable(true);
			super.installUI(c);
		}

		@Override
		public void configureEditor() {
			super.configureEditor();
			editor.addKeyListener(getEditorListener());
			editor.addMouseListener(getEditorListener());
			editor.addFocusListener(getEditorListener());
		}

		@Override
		public void unconfigureEditor() {
			super.unconfigureEditor();
			if (editorListener != null) {
				editor.removeKeyListener(editorListener);
				editor.removeMouseListener(editorListener);
				editor.removeFocusListener(editorListener);
				editorListener = null;
			}
		}

		@Override
		protected ComboPopup createPopup() {
			// return new FilterComboPopup(filterComboBox);
			return comPopup;
		}

		/**
		 * 初始化并返回编辑区事件监听器
		 * 
		 * @return
		 */
		protected EditorListener getEditorListener() {
			if (editorListener == null) {
				editorListener = new EditorListener();
			}
			return editorListener;
		}

		/**
		 * 按关键字进行查询，该方法中，可以自行加入各种查询算法
		 */
		protected void findMatchs() {
			ComboBoxModel model = filterComboBox.getModel();
			DefaultComboBoxModel showModel = filterComboBox.getShowModel();
			showModel.removeAllElements();
			String text = getEditorText();
			if (text == null || text.length() == 0) {
				for (int i = 0; i < model.getSize(); i++) {
					showModel.addElement(model.getElementAt(i));
				}
			} else
				for (int i = 0; i < model.getSize(); i++) {
					if (PinYinUtil
							.isSelectShowItem(model.getElementAt(i), text))
						showModel.addElement(model.getElementAt(i));
				}
			((FilterComboPopup) popup).repaint();
		}

		/**
		 * 返回编辑区文本
		 * 
		 * @return
		 */
		private String getEditorText() {
			return filterComboBox.getEditor().getItem().toString();
		}

		/**
		 * 弹出面板类
		 * 
		 * @author Sun
		 * 
		 */

	  

		class FilterComboPopup extends BasicComboPopup {

		    protected class ListMouseHandler extends MouseAdapter { 
		        public void mousePressed(final MouseEvent e) { 
		        } 
		        public void mouseReleased(final MouseEvent e) { 
		        	setVisible(false); 
		        } 
		    } 
		    public MouseListener creacreateMouseListener(){
		    	return new ListMouseHandler();
		    }
			public FilterComboPopup(JComboBox combo) {
				super(combo);
			}

			@Override
			protected JList createList() {
				JList list = super.createList();
				list.setModel(filterComboBox.getShowModel());
				return list;
			}

			@Override
			public void setVisible(boolean b) {
				super.setVisible(b);
				if (!b) {
					if(comboBox.getSelectedItem() != null )
						comboBox.getEditor().setItem(comboBox.getSelectedItem());
				}
			}
			@Override
			public void show() {
				findMatchs();
				super.show();
			}
			

		}

		/**
		 * 编辑区事件监听器类
		 * 
		 * @author Sun
		 * 
		 */
		class EditorListener implements KeyListener, MouseListener,
				FocusListener {

			/**
			 * 旧文本，用于键盘输入时的比对
			 */
			private String oldText = "";

			@Override
			public void keyReleased(KeyEvent e) {
				String newText = getEditorText();
				if (!newText.equals(oldText)) {
					findMatchs();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				oldText = getEditorText();
				if (!isPopupVisible(filterComboBox)) {
					setPopupVisible(filterComboBox, true);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				findMatchs();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (!isPopupVisible(filterComboBox)) {
					setPopupVisible(filterComboBox, true);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!isPopupVisible(filterComboBox)) {
					setPopupVisible(filterComboBox, true);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(selectingItem)
					setOldIndex(currentMouseIndex);
			}

		}
	}
}
