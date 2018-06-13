package cn.edu.sdu.commoncomponent.cs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class UConditionCommonQueryDialog extends JDialog {
	public final static String RETURN_OK = "ok";
	public final static String RETURN_CANCEL = "cancel";
	private String buttonValue = RETURN_CANCEL;
	private String sqlResult;
	private UConditionCommonQueryPanel srlInput; 
	
	public String getReturnValue() {
		return buttonValue;
	}
	public String getSqlResult() {
		return sqlResult;
	}
	public void setSqlResult(String sqlResult) {
		this.sqlResult = sqlResult;
	}
	public UConditionCommonQueryDialog(){
		this(null, true);
	}
    public UConditionCommonQueryDialog(Dialog owner) {
        super(owner);
        init();
    }
	
	public UConditionCommonQueryDialog(JFrame frame,boolean model){
		super(frame,model);
		init();
		
	}
	public void init(){
		
		this.setLayout(null);
		this.setTitle("输入表达式，例如:学号='200710001'");
		this.setSize(500,460);
		this.setModal(true);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		srlInput = new UConditionCommonQueryPanel();
		srlInput.initContents();
		srlInput.setDlgOwner(this);
		c.add(srlInput.getAWTComponent());
		int xo = 0, yo = 0;
		Dimension d;
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		xo = screenWidth / 2 - 500 / 2;
		yo = screenHeight / 2 - 460 / 2;
		this.setLocation(xo,yo);
		this.setVisible(true);
	}
	public void doOk(){
		sqlResult = (String)srlInput.getData();
		buttonValue = RETURN_OK;
		dispose();
		setVisible(false);
	}
}
