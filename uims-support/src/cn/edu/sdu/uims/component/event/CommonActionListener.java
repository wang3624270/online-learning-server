package cn.edu.sdu.uims.component.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CommonActionListener implements ActionListener {
	
	private List listenerList=new ArrayList();

	public void addListener(ActionListener listener) {
		listenerList.add(listener);
	}



	public void removeListener(ActionListener listener) {
		listenerList.remove(listener);
	}



	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i;
		int size=listenerList.size();
		ActionListener listener;
		for(i=0;i<size;i++){
			listener=(ActionListener)listenerList.get(i);
			listener.actionPerformed(e);
		}
		
		
	}
	

}


