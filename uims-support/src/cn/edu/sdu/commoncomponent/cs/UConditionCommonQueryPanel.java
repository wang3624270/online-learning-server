package cn.edu.sdu.commoncomponent.cs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.commoncomponent.form.QueryCell;
import cn.edu.sdu.commoncomponent.form.QueryCellOptionInfo;
import cn.edu.sdu.commoncomponent.util.UConditionCommonQueryUtils;
import cn.edu.sdu.uims.component.complex.UComplexPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UConditionCommonQueryPanel extends UComplexPanel implements MouseListener{

	private String KEY_BACKSPACE = " ";

	private JTextArea sqlResultArea;
	private JLabel conditionTip;
	private JTextField conditionDate;
	private JTextField conditionArea;
	private JList queryCellList;
	private JList operatorList;
	private JList conditionOptionList;
	private JScrollPane queryCellPane;
	private JScrollPane operatorPane;
	private JScrollPane conditionOptionPane;
	
	private JButton buttonOK;
	private JButton buttonClear;
	private UConditionCommonQueryDialog dlgOwner; 

	private List leftList = new ArrayList();
	private List rightList = new ArrayList();
	// 输入的条件信息
	private String conditionInputInfo;
	// 以下拉列表框的形式获得的查询条件
	private QueryCellOptionInfo[] conditionOptionListInfo;
	// 选择的时间条件
	private Date conditionDateInfo;
	// 当前选中的displayName值
	private String selectDisplayName;
	// 当前选中的QueryCell值
	private QueryCell selectCell;
	// 当前sql子句单元的list列表
	private List resultList = new ArrayList();
	private String sqlStr = "";
	private boolean actionEventCanSend = false;
	
	public UConditionCommonQueryDialog getDlgOwner() {
		return dlgOwner;
	}
	public void setDlgOwner(UConditionCommonQueryDialog dlgOwner) {
		this.dlgOwner = dlgOwner;
	}
	
	public  void initContents(){
		initInnerContents();
		initComponentData();
	}
	public void initComponentData() {
		List<QueryCell> list = UConditionCommonQueryUtils.getQueryCellList(null);
		setJListData(queryCellList,queryCellPane, list);		
		setJListData(operatorList, operatorPane, getOperatorInfoArray());
		getHashMap(list);
	}

	public void setJListData(JList jList, JScrollPane pane, List list){
		// TODO Auto-generated method stub
		if (list == null || list.size()== 0)
			jList.setListData(new ArrayList().toArray());
		else {
			jList.setListData(list.toArray());
		}
		pane.updateUI();

	}
	public void initInnerContents(){

		sqlResultArea = new JTextArea();
		sqlResultArea.setRows(10);
		sqlResultArea.setBounds(10,10,381,117);
		
		queryCellList = new JList();
		queryCellList.addMouseListener(this);
		queryCellPane = new JScrollPane();
		queryCellPane.setViewportView(queryCellList);
		queryCellPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		queryCellPane.getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		queryCellPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		queryCellPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		queryCellPane.setBounds(10,133,150,290);
		
		operatorList = new JList();
		operatorList.addMouseListener(this);
		operatorPane = new JScrollPane();
		operatorPane.setViewportView(operatorList);
		operatorPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		operatorPane.getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		operatorPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		operatorPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		operatorPane.setBounds(165,133,150,290);
		
		conditionTip = new JLabel("必须为6位，例如：20080901");
		conditionTip.setBounds(320,133,170,25);
		conditionTip.setVisible(false);
		
		conditionDate = new JTextField();
		conditionDate.addActionListener(this);
		conditionDate.setBounds(320,163,153,260);
		conditionDate.setVisible(false);
		
		conditionArea = new JTextField();
		conditionArea.addActionListener(this);
		conditionArea.setBounds(320,133,153,290);
		conditionArea.setVisible(false);
		
		conditionOptionList = new JList();
		conditionOptionList.addMouseListener(this);
		conditionOptionPane = new JScrollPane();
		conditionOptionPane.setVisible(false);
		conditionOptionPane.setViewportView(conditionOptionList);
		conditionOptionPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		conditionOptionPane.getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		conditionOptionPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		conditionOptionPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		conditionOptionPane.setBounds(320,133,153,290);
		
		buttonOK = new JButton("确定");
		buttonOK.addActionListener(this);
		buttonOK.setBounds(404,25,73,27);
		
		buttonClear = new JButton("清空");
		buttonClear.addActionListener(this);
		buttonClear.setBounds(404,70,73,27);
		
		this.setLayout(null);
		add(sqlResultArea);
		add(queryCellPane);
		add(operatorPane);
		add(conditionTip);
		add(conditionDate);
		add(conditionArea);
		add(conditionOptionPane);
		add(buttonOK);
		add(buttonClear);
	}
	/** *获得操作符左右两边的hashMap */
	public void getHashMap(List<QueryCell> list) {

		if (list == null || list.size() == 0)
			return;
		QueryCell cell = null;
		QueryCellOptionInfo info = null;
		for (int i = 0; i < list.size(); i++) {
			cell = list.get(i);
			leftList.add(new ListOptionInfo(addBackSpace(cell.getPoName().trim()
					+ "." + cell.getColumnName().trim()),addBackSpace(cell.getDisplayName().trim())));
			if (cell.getQueryCellOptionList() != null
					&& cell.getQueryCellOptionList().size() > 0) {
				for (int j = 0; j < cell.getQueryCellOptionList().size(); j++) {
					info = (QueryCellOptionInfo) cell.getQueryCellOptionList()
							.get(j);
					String displayName = info.getLabel() == null ? "'"+info
							.getCell().getDisplayName().trim()+"'"
							: "'"+info.getLabel()+"'";
					String value =addBackSpace(getValueFromQueryCellOptionInfo(info));
					rightList.add(new ListOptionInfo(value,displayName));
				}
			}
		}
	}

	/**
	 * 根据查询条件选择项获得其对应的value值
	 * @param obj 查询条件选择项
	 * @return
	 */
	public String getValueFromQueryCellOptionInfo(QueryCellOptionInfo obj) {
		String value = "";
		if (((QueryCellOptionInfo) obj).getValue() != null) {
			String classType = ((QueryCellOptionInfo) obj).getCell()
					.getClassType().trim();
			if (classType != null && !classType.equals("Integer")) {
				// 如果是字符类型的要加单引号
				value += "'" + ((QueryCellOptionInfo) obj).getValue().trim() + "'";
			} else {
				value += "" + ((QueryCellOptionInfo) obj).getValue().trim();
			}
		} else {
			// 和po相关联
			if (((QueryCellOptionInfo) obj).getCell() != null) {
				QueryCell temp = ((QueryCellOptionInfo) obj).getCell();
				value += (temp.getPoName() == null ? "" : ""
						+ temp.getPoName().trim()+ ".")
						+ temp.getColumnName().trim();
			}
		}
		return value;
	}


	/**
	 * 获得操作符集合
	 * @return
	 */
	public List<ListOptionInfo> getOperatorInfoArray() {
		List<ListOptionInfo>list  = new ArrayList<ListOptionInfo>();
		list.add(new ListOptionInfo(" () ", "()"));
		list.add(new ListOptionInfo(" + ", "加"));
		list.add(new ListOptionInfo(" - ", "减"));
		list.add(new ListOptionInfo(" * ", "乘"));
		list.add(new ListOptionInfo(" / ", "除"));
		list.add(new ListOptionInfo(" = ", "等于"));
		list.add(new ListOptionInfo(" != ", "不等于"));
		list.add(new ListOptionInfo(" > ", "大于"));
		list.add(new ListOptionInfo(" < ", "小于"));
		list.add(new ListOptionInfo(" >= ", "大于等于"));
		list.add(new ListOptionInfo(" <= ", "小于等于"));
		list.add(new ListOptionInfo(" like ", "包含"));
		list.add(new ListOptionInfo(" and ", "并且"));
		list.add(new ListOptionInfo(" or ", "或者"));
		list.add(new ListOptionInfo(" not ", "非"));
		return list;
	}

	/**
	 * 选中插询条件时发生的操作，eg：选中'学号'时的动作
	 * @param selectCell
	 */
	public void doSelectQueryCell(QueryCell selectCell){
		if (selectCell != null) {
			selectDisplayName = " " + selectCell.getDisplayName();
			resultList.add(selectCell);
			sqlResultArea.insert(selectDisplayName, sqlResultArea
					.getCaretPosition());
			if (selectCell.getQueryCellOptionList() != null) {
				QueryCellOptionInfo[] optionList = new QueryCellOptionInfo[selectCell
						.getQueryCellOptionList().size()];
				for (int j = 0; j < selectCell.getQueryCellOptionList()
						.size(); j++) {
					optionList[j] = (QueryCellOptionInfo) selectCell
							.getQueryCellOptionList().get(j);
				}
				setJListDataList(conditionOptionList,conditionOptionPane, optionList);
				conditionArea.setVisible(false);
				conditionDate.setVisible(false);
				conditionTip.setVisible(false);
				conditionOptionPane.setVisible(true);
			} else if (selectCell.getClassType().equals("Date")) {
				conditionOptionPane.setVisible(false);
				conditionArea.setVisible(false);
				conditionDate.setVisible(true);
				conditionTip.setVisible(true);
			} else {
				conditionOptionPane.setVisible(false);
				conditionDate.setVisible(false);
				conditionTip.setVisible(false);
				conditionArea.setVisible(true);
			}
		}
	}
	/**
	 * 选中操作符时发生的操作，eg：选中'等于='时的动作
	 * @param operatorInfo
	 */
	public void  doSelectOperator(ListOptionInfo operatorInfo){
		if (operatorInfo != null) {
			if (operatorInfo != null) {
				resultList.add(operatorInfo.getValue());
				sqlResultArea.insert(operatorInfo.getValue(), sqlResultArea
						.getCaretPosition());
			}
		}
	}
	/**
	 *  选中选择列表时发生的操作，eg：选中'学生类型中的-普通硕士'时的动作
	 * @param info
	 */
	public void doSelectOption(QueryCellOptionInfo info){
		if (info != null) {
			resultList.add(info);
			String displayName = info.getLabel() == null ? addBackSpace("'"+info
					.getCell().getDisplayName().trim()+"'")
					: addBackSpace("'"+info.getLabel().trim()+"'");
			sqlResultArea.insert(displayName, sqlResultArea.getCaretPosition());
		}
	}
	/**
	 * 手动输入条件时发生的操作，eg：输入'200710001'时的动作
	 */
	public void   doSelectConditionArea(){
		if (selectCell == null || conditionArea.getText() == null)
			return;
		String temp = ((String) conditionArea.getText()).trim();
		if (selectCell.getClassType() != null
				&& selectCell.getClassType().equals("Integer")) {
			try {
				temp = addBackSpace("'"+String.valueOf(Integer.parseInt(temp))+"'");
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"该项目对应数字类型，输入不合法，请重新输入!");
				return;
			}
		}else {
			temp = addBackSpace("'" + temp + "'");
		}
		resultList.add(temp);
		sqlResultArea.insert(temp, sqlResultArea.getCaretPosition());
		conditionArea.setText("");
	}
	/**
	 * 手动输入日趋时发生的操作，eg：输入'20070101'时的动作
	 */
	public void doSelectConditionDate(){
		String dateStr = "";
		if (conditionDate.getText() == null)
			return;
		if (((String) conditionDate.getText()).trim().length() != 8) {
			JOptionPane.showMessageDialog(null, "您输入的日期格式不正确，请重新输入8位数字!");
		} else {
			String temp = ((String) conditionDate.getText()).trim();
			dateStr = addBackSpace("'" + temp.substring(0, 4) + "-"
					+ temp.substring(4, 6) + "-" + temp.substring(6) + "'");
			resultList.add(dateStr);
			sqlResultArea.insert(dateStr, sqlResultArea
					.getCaretPosition());
		}
		conditionDate.setText("");
	}

	 public String getExpression(){
	 String temp=sqlResultArea.getText()==null?"":((String)sqlResultArea.getText()).trim();
	 if(temp==null||temp.trim().equals("")){
		 return temp;
	 }
	 temp=splitClause(temp);
	 ListOptionInfo info=null;
	 if(leftList!=null&&leftList.size()>0){
		 for(int i=0;i<leftList.size();i++){
			 info=(ListOptionInfo)leftList.get(i);
			 temp=temp.replace(info.getLabel(), info.getValue());
		 }
	 }
	 if(rightList!=null&&rightList.size()>0){
		 for(int i=0;i<rightList.size();i++){
			 info=(ListOptionInfo)rightList.get(i);
			 temp=temp.replace(info.getLabel(), info.getValue());
		 }
	 }
	 return  temp;
	 }
/**
 * 将sql中的所有操作符用" "隔开，以区分各项(着重处理<，>，=这三类操作符)
 * @param temp
 * @return
 */
	 public String splitClause(String temp){
		 if(temp!=null){
			 temp=temp.replace("!=","  !=  ");
			 temp=temp.replace("<="," <= "); 
			 temp=temp.replace(">=","  >=  ");
			 temp=temp.replace("+","  +  ");
			 temp=temp.replace("*","  *  ");
			 temp=temp.replace("/","  /  ");
			 
			 /********index中存放<、>和=这三类操作符所在的index位置******/
			 List index=new ArrayList();
			 List notList=new ArrayList();
			 int ind=0;
			 int index2=0;
			 while(index2!=-1){
				 	int index1=temp.indexOf("<=",ind);
				 	if(!notList.contains(index1)&&index1!=-1){
						notList.add(index1);
					}
				    index2=temp.indexOf("<",ind);
					if(index1!=index2&&index2!=-1){
						index.add(index2);
					}
					ind=ind+index2+1;
			 }
			 ind=0;
			 index2=0;
			 while(index2!=-1){
				 	int index1=temp.indexOf(">=",ind);
				 	if(!notList.contains(index1)&&index1!=-1){
						notList.add(index1);
					}
				    index2=temp.indexOf(">",ind);
					if(index1!=index2&&index2!=-1){
						index.add(index2);
					}
					ind=ind+index2+1;
			 }
			 ind=0;
			 index2=0;
			 while(index2!=-1){
				    index2=temp.indexOf("=",ind);
					if(index2!=-1&&!notList.contains(index2-1)){
						index.add(index2);
					}
					ind=ind+index2+1;
			 }
			 String substr=null;
			 int length=temp.length();
			 /********将index按照降序排列，并将结果存入newIndex数组*****/
			 int []newIndex=new int[index.size()];
			 for(int i=0;i<index.size();i++){
				 newIndex[i]=(Integer)index.get(i);
			 }
			 for(int i=0;i<newIndex.length;i++){
				 int indextemp=0;;
				 for(int j=0;j<newIndex.length-i-1;j++){
					 if(newIndex[j]>newIndex[j+1]){
						 indextemp=newIndex[j];
						 newIndex[j]=newIndex[j+1];
						 newIndex[j+1]=indextemp;
					 }
				 }
			 }
			 if(newIndex!=null&&newIndex.length>0&&length>1){
				 for(int i=newIndex.length;i>0;i--){
					  ind=(Integer)newIndex[i-1];
					 if(ind==0){
						  temp=KEY_BACKSPACE+temp.substring(0,1)+KEY_BACKSPACE+temp.substring(1);
					  }else if(ind==length){
						  temp=temp.substring(0,length-1)+KEY_BACKSPACE+temp.substring(length-1)+KEY_BACKSPACE;
					  } else{
						  temp=temp.substring(0,ind)+KEY_BACKSPACE+temp.substring(ind,ind+1)+KEY_BACKSPACE+temp.substring(ind+1);
					  } 
				 }
			 }			
			 temp=KEY_BACKSPACE+temp+KEY_BACKSPACE;
		 }
		 return temp;
	 }


	public String addBackSpace(String temp) {
		return temp == null ? "" : KEY_BACKSPACE+ temp + KEY_BACKSPACE;
	}
	void setJListDataList(JList jList,JScrollPane pane, Object []a){
		if (a == null)
			jList.setListData(new ArrayList().toArray());
		else {
			jList.setListData(a);
		}
		if(pane != null)
			pane.updateUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== buttonOK) {
			if(dlgOwner != null) {
				dlgOwner.doOk();
			}else {
				if(actionEventCanSend) {
					ActionEvent e1 = new ActionEvent(this, 0, "buttonOk");
					if(getUParent() != null) {
						getUParent().getEventAdaptor().actionPerformed(e1);
					}
				}
			}
		} else if (e.getSource()== buttonClear) {
			resultList.clear();
			sqlResultArea.setText("");
		}else if(e.getSource()== conditionArea ) {
			doSelectConditionArea();
		} else if(e.getSource()== conditionDate) {
			doSelectConditionDate();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()== queryCellList) {
			 selectCell = (QueryCell) queryCellList.getSelectedValue();
			 doSelectQueryCell(selectCell);
		} else if (e.getSource()== operatorList) {
			ListOptionInfo operatorInfo = (ListOptionInfo) operatorList.getSelectedValue();
			doSelectOperator(operatorInfo);
		} else if (e.getSource()== conditionOptionList) {
			QueryCellOptionInfo info= (QueryCellOptionInfo) conditionOptionList.getSelectedValue();
			doSelectOption(info);
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			}
		}
	}
	public Object getData() {
		return sqlResultArea.getText();
	}

	public void setData(Object obj) {
		if(obj != null)
		sqlResultArea.setText(obj.toString());
	}
	
}
