/**
 * 
 */
package cn.edu.sdu.uims.component.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * @author 赵鹏 2007.1.22
 *	可隐藏panel,包括一个标题按钮和显示panel
 *	通过点击标题按钮来隐藏和显示panel
 *
 */
public class HideablePanel extends JPanel implements ActionListener{

	/**状态常量: 显示*/
	public static final String STATE_SHOW="Panel_SHOW";
	/**状态常量: 隐藏*/
	public static final String STATE_HIDE="Panel_HIDE";
	/**标题按钮点击事件的命令*/
	public static final String COMMAND_CHANGESTATE="changeState";
	//标题按钮
	private final JButton btn_ShowAndHide = new JButton();
	//用于显示 的panel
	private  JPanel panel_display =null;
	//状态变量
	private String state;
	//可隐藏panel的组panel
	private HideableGroupPanel groupPanel=null;
	//是否选中标志;
	private boolean isSelected=false;
	//选中时的border
	private Border selectedBorder=new LineBorder(java.awt.Color.blue,2);
	//为选中时的border
	private Border notSelectedBorder;
	private ImageIcon selectedIcon=new ImageIcon("image/icon/panel_show.gif");
	private ImageIcon notSelectedIcon=new ImageIcon("image/icon/panel_hide.gif");
	
	/**
	 * Create the panel
	 */
	public HideablePanel(String caption,JPanel panel_display){
		this(caption,HideablePanel.STATE_HIDE,panel_display);
	}
	public HideablePanel(String caption,String state,JPanel panel_display) {
		super();
		setLayout(null);
		
		this.state=state;
		btn_ShowAndHide.setBounds(5, 0, 200, 20);
		btn_ShowAndHide.setFocusTraversalPolicyProvider(true);
		btn_ShowAndHide.setOpaque(true);
		notSelectedBorder=btn_ShowAndHide.getBorder();
		add(btn_ShowAndHide);
		this.setCaption(caption);
		btn_ShowAndHide.setActionCommand(COMMAND_CHANGESTATE);
		btn_ShowAndHide.addActionListener(this);
		
		this.panel_display=panel_display;
		panel_display.setLocation(5,20);
		if(state.equals(STATE_SHOW)){
			showPanel();
		}else{
			hidePanel();
		}
		//
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd=e.getActionCommand();
		if(cmd!=null&&cmd.equals(COMMAND_CHANGESTATE)){
			if(this.state.equals(STATE_HIDE)){
				showPanel();
				setSelectedState(true);
			}else if(this.state.equals(STATE_SHOW)){
				hidePanel();
				setSelectedState(true);
			}
		}
	}
	/**
	 * 显示panel
	 *
	 */
	public void showPanel(){

		add(panel_display);
		this.setSize(new Dimension(getShowWidth(),getShowHeight()));
		this.state=STATE_SHOW;
		this.btn_ShowAndHide.setIcon(selectedIcon);
		this.btn_ShowAndHide.setToolTipText("请点击隐藏面板");
		if(this.groupPanel!=null){
			this.groupPanel.updatePanelsLayout();
		}
		this.updateUI();
	}
	/**
	 * 隐藏panel
	 *
	 */
	public void hidePanel(){
		this.remove(panel_display);
		this.setSize(new Dimension(getHideWidth(),getHideHeight()));
		this.state=STATE_HIDE;
		this.btn_ShowAndHide.setIcon(notSelectedIcon);
		
		this.btn_ShowAndHide.setToolTipText("请点击显示面板");
		if(this.groupPanel!=null){
			this.groupPanel.updatePanelsLayout();
		}
	}
	/**
	 * 
	 * @return panel显示时的宽度
	 */
	public int getShowWidth(){
		return Math.max(btn_ShowAndHide.getWidth(),panel_display.getWidth());
	}
	/**
	 * 
	 * @return panel显示时的高度
	 */
	public int getShowHeight(){
		return btn_ShowAndHide.getHeight()+panel_display.getHeight();
	}
	/**
	 * 
	 * @return panel隐藏时的宽度
	 */
	public int getHideWidth(){
		return Math.max(btn_ShowAndHide.getWidth(),panel_display.getWidth()); 
	}
	/**
	 * 
	 * @return panel隐藏时的高度
	 */
	public int getHideHeight(){
		return btn_ShowAndHide.getHeight();
	}
	
	public HideableGroupPanel getGroupPanel() {
		return groupPanel;
	}
	public void setGroupPanel(HideableGroupPanel groupPanel) {
		this.groupPanel = groupPanel;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if (state != null) {
			if (state.equals(STATE_SHOW)) {
				this.showPanel();
			}else if(state.equals(STATE_HIDE)){
				this.hidePanel();
			}
		}
	}
	public void setCaption(String caption){
		this.btn_ShowAndHide.setText("   "+caption);
	}
	public void setSelectedState(boolean isSelected){
		//isSelected 为true表示选中 
		this.isSelected=isSelected;
		if(isSelected){
			this.btn_ShowAndHide.setBorder(	selectedBorder);
			if(this.groupPanel!=null){
				this.groupPanel.setPanelSelected(this);
			}
		}else{
			this.btn_ShowAndHide.setBorder(notSelectedBorder);
		}
	}
	public boolean getSelectedState(){
		return this.isSelected;
	}
	public JPanel getPanel_display() {
		return panel_display;
	}
	public void setPanel_display(JPanel panel_display) {
		this.panel_display = panel_display;
	}
	public ImageIcon getSelectedIcon() {
		return selectedIcon;
	}
	public void setSelectedIcon(ImageIcon selectedIcon) {
		this.selectedIcon = selectedIcon;
	}
	public ImageIcon getNotSelectedIcon() {
		return notSelectedIcon;
	}
	public void setNotSelectedIcon(ImageIcon notSelectedIcon) {
		this.notSelectedIcon = notSelectedIcon;
	}
}

