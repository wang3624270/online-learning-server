package cn.edu.sdu.uims.progress;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import cn.edu.sdu.uims.base.UScrollPane;

public class ProgressBarAndInfoPanel extends ProgressPanel{
	private JTextArea taskOutput;

	public ProgressBarAndInfoPanel() {
		super();
		init();
	}
	public void init(){
		super.init();
		taskOutput = new JTextArea(40, 70);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);
		
		add(new UScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));	
	}
	public void addOutInfo(String str,String spn){
		taskOutput.append(str + spn);
	}
	public void addOutInfo(String str){
		taskOutput.append(str+"\n");
	}
	public void setOutInfo(String str){
		taskOutput.setText(str);
	}
	public void clearText(){
		taskOutput.setText("");
	}



}
