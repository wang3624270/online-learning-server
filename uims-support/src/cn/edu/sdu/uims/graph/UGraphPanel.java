package cn.edu.sdu.uims.graph;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import cn.edu.sdu.uims.component.panel.SuperPanel;


public class UGraphPanel extends SuperPanel {
		
	    protected  JPopupMenu popupMenu = null;
	    protected int mouseX, mouseY;
		public UGraphPanel(){
			super();
		}
		public void init(){
			initPopMenu();			
		}
		public void paint(Graphics g){
			super.paint(g);
			draw(g);
		}
		public void draw(Graphics g){
		}
		public void drawBox(Graphics g, int x, int y, int w, int h ){
			g.drawRect(x, y,w,h);
		}
		public void displyPopMenu(){
	        popupMenu.show(this,mouseX, mouseY);		
		}
		public void setSelectRect(int x, int y){
			
		}
		public void initPopMenu(){
		   popupMenu = new JPopupMenu();
		    addMouseListener(new MouseAdapter() {
		            public void mousePressed(MouseEvent e) {
		                // popupMenu.setVisible(false);
		                if (e.getButton() == MouseEvent.BUTTON3) {           	
		                     mouseX = e.getX();
		                     mouseY = e.getY();
		                    displyPopMenu();
		                }
		                else {
		                	setSelectRect(e.getX(), e.getY());               	
		                }
		                // else popupMenu.setVisible(false);
		            }
		        });
		}
}
