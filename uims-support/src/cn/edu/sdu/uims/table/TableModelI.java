package cn.edu.sdu.uims.table;

public interface TableModelI {
	Object [] getColumnTitles();
	Object [] getRowTitles();
	Object [][] getDataObjects();
}
