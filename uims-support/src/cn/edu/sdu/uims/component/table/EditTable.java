package cn.edu.sdu.uims.component.table;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

class EditTable extends JTable {
    public EditTable(TableMap map) {
        super(map);
    }

    public boolean editCellAt(int row, int column, EventObject e) {
        if (cellEditor != null && !cellEditor.stopCellEditing()) {
            return false;
        }

        if (row < 0 || row >= getRowCount() || column < 0
                || column >= getColumnCount()) {
            return false;
        }

        if (!isCellEditable(row, column))
            return false;

        /*
         * if (editorRemover == null) { KeyboardFocusManager fm =
         * KeyboardFocusManager.getCurrentKeyboardFocusManager();
         * editorRemover = new CellEditorRemover(fm);
         * fm.addPropertyChangeListener("permanentFocusOwner",
         * editorRemover); }
         */

        DefaultCellEditor editor = (DefaultCellEditor)getCellEditor(row, column);
        if (editor != null && editor.isCellEditable(e)) {
            editorComp = prepareEditor(editor, row, column);
            if (editorComp == null) {
                removeEditor();
                return false;
            }
            editorComp.setBounds(getCellRect(row, column, false));
            add(editorComp);
            editorComp.validate();

            setCellEditor(editor);
            setEditingRow(row);
            setEditingColumn(column);
            editor.addCellEditorListener(this);
            
            Component c = editor.getComponent();
            if(c instanceof JTextComponent){
                ((JTextField)(editor.getComponent())).
                    setAlignmentX(Component.RIGHT_ALIGNMENT);
                ((JTextField)(editor.getComponent())).selectAll();
            }
            
            return true;
        }
        return false;
    }
}
