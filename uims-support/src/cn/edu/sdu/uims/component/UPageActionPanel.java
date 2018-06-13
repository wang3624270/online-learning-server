package cn.edu.sdu.uims.component;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UPageActionComponentI;
import cn.edu.sdu.uims.form.impl.UTableQueryDataForm;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.service.UModelSessionI;

public class UPageActionPanel  implements ActionListener{
	private JPanel panel;
	private JButton firstPageButton, lastPageButton, nextPageButton,	prevPageButton;
	private JLabel pageDispLabel0, pageDispLabel1, pageDispLabel2,	pageDispLabel3;
	private JTextField pageNumField;
	private UTableQueryDataForm tableQueryDataForm = null;
	private UPageActionComponentI owner;
	private int pageNum = 40;
	public UTableQueryDataForm getTableQueryDataForm(){
		return tableQueryDataForm;
	}
	public void setTableQueryDataForm(UTableQueryDataForm tableQueryDataForm) {
		this.tableQueryDataForm = tableQueryDataForm;
	}
	public JPanel getJPanel(){
		return panel;
	}
	public UPageActionPanel(UPageActionComponentI owner){
		this(owner, 40,null);
	}
	public UPageActionPanel(UPageActionComponentI owner,int pageNum){
		this(owner, pageNum,null);
	}
	public UPageActionPanel(UPageActionComponentI owner, int pageNum, JPanel panel){
		this.owner = owner;
		this.pageNum = pageNum;
		this.panel = panel;
		init();
	}
	public String getPageDispLabel2(int p0, int p1) {
		boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
		UModelSessionI s = UFactory.getModelSession();
		String str = "";
		if(!isEnglish) {
			str = s.getPromptInfoByName("第", isEnglish);
			str += p0;
			str += "页";
			str += "/";
			str += s.getPromptInfoByName("共", isEnglish);
			str += p1;
			str += "页";
		}else {
			str = s.getPromptInfoByName("第", isEnglish);
			str += p0;
			str += "/";
			str += s.getPromptInfoByName("共", isEnglish);
			str += p1;			
		} 
		return str;
	}
	public String getPageDispLabel3(int num) {
		boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
		UModelSessionI s = UFactory.getModelSession();
		String str = "(";
		if(!isEnglish) {
			str += s.getPromptInfoByName("共有", isEnglish);
			str += num;
			str += "条记录";
		}else {
			str += s.getPromptInfoByName("共有", isEnglish);
			str += num;
		} 
		str += ")";
		return str;
	}

	public void init(){
		boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
		
		String str;
		if(panel == null) {
			panel = new JPanel();
			panel.setLayout(new FlowLayout());
		}
		tableQueryDataForm = new UTableQueryDataForm();
		tableQueryDataForm.setStart(0);
		tableQueryDataForm.setTotal(0);
		tableQueryDataForm.setPageNum(pageNum);
		pageDispLabel0 = new JLabel(UFactory.getModelSession().getPromptInfoByName("每页显示", isEnglish));
		pageNumField = new JTextField(6);
		pageNumField.addActionListener(this);
		if(!isEnglish)
			pageDispLabel1 = new JLabel("条");
		pageDispLabel2 = new JLabel(getPageDispLabel2(1,1));
		pageDispLabel3 = new JLabel(getPageDispLabel3(1));
		firstPageButton = new JButton(UFactory.getModelSession().getPromptInfoByName("首页", isEnglish));
		firstPageButton.addActionListener(this);
		prevPageButton = new JButton(UFactory.getModelSession().getPromptInfoByName("上一页", isEnglish));
		prevPageButton.addActionListener(this);
		nextPageButton = new JButton(UFactory.getModelSession().getPromptInfoByName("下一页", isEnglish));
		nextPageButton.addActionListener(this);
		lastPageButton = new JButton(UFactory.getModelSession().getPromptInfoByName("最后一页", isEnglish));
		lastPageButton.addActionListener(this);
		panel.add(pageDispLabel0);
		panel.add(pageNumField);
		if(pageDispLabel1 != null)
			panel.add(pageDispLabel1);
		panel.add(pageDispLabel2);
		panel.add(pageDispLabel3);
		panel.add(firstPageButton);
		panel.add(prevPageButton);
		panel.add(nextPageButton);
		panel.add(lastPageButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int start, num;
			String s = pageNumField.getText();
			if (s == null || s.equals(""))
				return;
			num = Integer.parseInt(s);
			tableQueryDataForm.setPageNum(num);
			start = tableQueryDataForm.getStart();
			if (e.getSource() == firstPageButton) {
				start = 0;
			} else if (e.getSource() == lastPageButton) {
				start = tableQueryDataForm.getTotal()
						- tableQueryDataForm.getTotal() % num;
				if (start == 0) {
					start = tableQueryDataForm.getTotal() - num;
					if (start < 0)
						start = 0;
				}
			} else if (e.getSource() == nextPageButton) {
				if (start + num < tableQueryDataForm.getTotal()) {
					start = start + num;
				}
			} else if (e.getSource() == prevPageButton) {
				if (start - num < 0)
					start = 0;
				else
					start = start - num;
			}
			tableQueryDataForm.setStart(start);
			if(tableQueryDataForm.getHandler() == null) {
				if(owner != null) {
					owner.doTablePageDataQuery(tableQueryDataForm);
				}
			}else {
				tableQueryDataForm.getHandler().doPageDataQuery(tableQueryDataForm);
			}
		
	}
	public List getIdList(){
		if (tableQueryDataForm == null)
			return null;
		return tableQueryDataForm.getIdList();
	}
	public int getStart() {
		if (tableQueryDataForm != null)
			return tableQueryDataForm.getStart();
		else
			return 0;
	}

	public List getPageDataList() {
		if (tableQueryDataForm == null)
			return null;
		int total = tableQueryDataForm.getTotal();
		int no, pageNum;
		if (total < 1) {
			no = 0;
			pageNum = 0;
		} else {
			no = tableQueryDataForm.getStart()
					/ tableQueryDataForm.getPageNum() + 1;
			pageNum = (tableQueryDataForm.getTotal() - 1)
					/ tableQueryDataForm.getPageNum() + 1;
		}
		pageNumField.setText(tableQueryDataForm.getPageNum() + "");
		pageDispLabel2.setText(getPageDispLabel2(no,pageNum));
		pageDispLabel3.setText(getPageDispLabel3(total));
		return  tableQueryDataForm.getDataList();
	}

}
