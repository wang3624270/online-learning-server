package cn.edu.sdu.uims.component.panel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;

import cn.edu.sdu.uims.base.UScrollPane;

public class SizeBarScrollPane extends UScrollPane {
    public SizeBarScrollPane() {
        super();
        this.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
    }
    
    public SizeBarScrollPane(Component com) {
        super(com);
        getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
        getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
    }
    
    public SizeBarScrollPane(JTable com) {
        super(com);
        getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
        getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
    }
   
   
    public SizeBarScrollPane(int verticalSize, int horizontalSize) {
        super();
        this.getVerticalScrollBar().setPreferredSize(
                new Dimension(verticalSize, verticalSize));
        this.getHorizontalScrollBar().setPreferredSize(
                new Dimension(horizontalSize, horizontalSize));
    }

}
