package cn.edu.sdu.uims.component.event;

import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;
import java.util.EventObject;

import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionListener;

public interface UEventListener extends ActionListener, ItemListener,
		ChangeListener, MouseListener, MouseMotionListener, KeyListener,
		AdjustmentListener, WindowListener, ListSelectionListener,TableModelListener, TreeSelectionListener, GraphEventListener, CalendarEventListener,SelectObjectEventListener,FocusListener, UTimerEventListener,GroupComponentEventListener {
		void processEvent(EventObject e, String eventName, String cmd);
}
