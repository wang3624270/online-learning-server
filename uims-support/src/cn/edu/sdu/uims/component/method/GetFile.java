package cn.edu.sdu.uims.component.method;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * <p>Title: </p>
 * <p>Description: Display a dialog to let user select a file to open or save in</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class GetFile {
  static File file;

  /**
   * Let user choose a file to open
   * @return File
   */
  public static File getOpenFile() {
	  return getOpenFile((Component)null);
  }
  
  public static File getOpenFile(Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));

    int r = chooser.showOpenDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    file= chooser.getSelectedFile();
    return file;
  }

  /**
   * Let user choose a specified-type file
   * @param fileType String specify the file type to open
   * @return File
   */
  public static File getOpenFile(String fileType){
	  return getOpenFile(fileType,(Component)null);
  }
  
  public static File getOpenFile(String fileType,Component parent) {
    final String filetype = fileType.toLowerCase();
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));

    chooser.setFileFilter(new
                          javax.swing.filechooser.FileFilter() {
      public boolean accept(File fileToSave) {
        String name = fileToSave.getName().toLowerCase();
        return name.endsWith("." + filetype.toLowerCase())
            || fileToSave.isDirectory();
      }

      public String getDescription() {
        return filetype + " files";
      }
    });

    int r = chooser.showOpenDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    File tmpFile = chooser.getSelectedFile();
    String name = tmpFile.getAbsolutePath();
    if (name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
      return tmpFile;
    }
    else{
      file = new File(name + "." + fileType);
      return file;
    }
  }

  /**
   * Let user choose a specified-type file
   * and the choose dialog with a specified titie
   * @param fileType String specify the file type to open
   * @param title String the dialog's title
   * @return File
   */
  public static File getOpenFile(String fileType, String title) {
	  return getOpenFile(fileType, title,(Component)null);
  }

  public static File getOpenFile(String fileType, String title,Component parent) {
    final String filetype = fileType.toLowerCase();
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setDialogTitle(title);

    chooser.setFileFilter(new
                          javax.swing.filechooser.FileFilter() {
      public boolean accept(File fileToSave) {
        String name = fileToSave.getName().toLowerCase();
        return name.endsWith("." + filetype.toLowerCase())
            || fileToSave.isDirectory();
      }

      public String getDescription() {
        return filetype + " files";
      }
    });

    int r = chooser.showOpenDialog(null);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    File tmpFile = chooser.getSelectedFile();
    String name = tmpFile.getAbsolutePath();
    if (name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
      return tmpFile;
    }
    else{
      file = new File(name + "." + fileType);
      return file;
    }
  }

  /**
   * Let user choose a file to save in
   * @return File
   */
  public static File getSaveFile() {
	  return getSaveFile((Component) null); 
  }
  public static File getSaveFile(Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));

    int r = chooser.showSaveDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    return chooser.getSelectedFile();
  }

  /**
   * Let user choose a specified-type file to save in
   * @param fileType String
   * @return File
   */
  public static File getSaveFile(String fileType) {
	  return getSaveFile(fileType,(Component) null);
  }
  public static File getSaveFile(String fileType,Component parent) {
    final String filetype = fileType.toLowerCase();
    JFileChooser chooser = new JFileChooser();
 //   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setCurrentDirectory(new File("."));

    chooser.setFileFilter(new
                          javax.swing.filechooser.FileFilter() {
      public boolean accept(File fileToSave) {
        String name = fileToSave.getName().toLowerCase();
        return name.endsWith("." + filetype.toLowerCase())
            || fileToSave.isDirectory();
      }

      public String getDescription() {
        return filetype + " files";
      }
    });

    int r = chooser.showSaveDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    File tmpFile = chooser.getSelectedFile();
    String name = tmpFile.getAbsolutePath();
    if (name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
      return tmpFile;
    }
    else
      return new File(name + "." + fileType);

  }

  /**
   * Let user choose a specified-type file to save in
   * and the choose dialog with a specified titie
   * @param fileType String
   * @param title String Dialog's title
   * @return File
   */
  public static File getSaveFile(String fileType, String title) {
	  return  getSaveFile(fileType,title,(Component)null);
	  
  }
  public static File getSaveFile(String fileType, String title,Component parent) {
    final String filetype = fileType.toLowerCase();
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setDialogTitle(title);

    chooser.setFileFilter(new
                          javax.swing.filechooser.FileFilter() {
      public boolean accept(File fileToSave) {
        String name = fileToSave.getName().toLowerCase();
        return name.endsWith("." + filetype.toLowerCase())
            || fileToSave.isDirectory();
      }

      public String getDescription() {
        return filetype + " files";
      }
    });

    int r = chooser.showSaveDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    File tmpFile = chooser.getSelectedFile();
    String name = tmpFile.getAbsolutePath();
    if (name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
      return tmpFile;
    }
    else
      return new File(name + "." + fileType);
  }

  public static File getSaveFile(String fileType, String title, String fileName) {
	  return  getSaveFile(fileType,title,fileName,(Component)null);
	  
  }
  public static File getSaveFile(String fileType, String title, String fileName,Component parent) {
	    final String filetype = fileType.toLowerCase();
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File("."));
	    chooser.setDialogTitle(title);
	    chooser.setSelectedFile(new File(fileName));

	    chooser.setFileFilter(new
	                          javax.swing.filechooser.FileFilter() {
	      public boolean accept(File fileToSave) {
	        String name = fileToSave.getName().toLowerCase();
	        return name.endsWith("." + filetype.toLowerCase())
	            || fileToSave.isDirectory();
	      }

	      public String getDescription() {
	        return filetype + " files";
	      }
	    });

	    int r = chooser.showSaveDialog(parent);
	    if (r != JFileChooser.APPROVE_OPTION)
	      return null;

	    File tmpFile = chooser.getSelectedFile();
	    String name = tmpFile.getAbsolutePath();
	    if (name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
	      return tmpFile;
	    }
	    else
	      return new File(name + "." + fileType);
	  }
  
  public static File getDirName() {
	  return getDirName((Component) null);
  }
  public static File getDirName(Component parent) {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setCurrentDirectory(new File("."));

	    int r = chooser.showSaveDialog(parent);
	    if (r != JFileChooser.APPROVE_OPTION)
	      return null;
	    return chooser.getSelectedFile();

	  }
  
 //----------------以下是选择多个文件的方法------赵鹏2007.7.26
  /**
   * Let user choose a file to open
   * @return File
   */
  public static File[] getOpenFiles(){
	  	return getOpenFiles((Component) null);
  }
  public static File[] getOpenFiles(Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setMultiSelectionEnabled(true);
    chooser.setCurrentDirectory(new File("."));

    int r = chooser.showOpenDialog(null);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    return chooser.getSelectedFiles();
  }

  /**
   * Let user choose a specified-type file
   * @param fileType String specify the file type to open
   * @return File
   */
  public static File[] getOpenFiles(String fileType) {
	   return getOpenFiles(fileType,(Component) null); 
	  }
  public static File[] getOpenFiles(String fileType,Component parent) {
   return getOpenFiles(fileType,"");
  }

  /**
   * Let user choose a specified-type file
   * and the choose dialog with a specified titie
   * @param fileType String specify the file type to open
   * @param title String the dialog's title
   * @return File
   */
  public static File[] getOpenFiles(String fileType, String title) {
	  return getOpenFiles(fileType,title,(Component) null);
  }
  public static File[] getOpenFiles(String fileType, String title,Component parent) {
    final String filetype = fileType.toLowerCase();
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setDialogTitle(title);
    chooser.setMultiSelectionEnabled(true);
    chooser.setFileFilter(new
                          javax.swing.filechooser.FileFilter() {
      public boolean accept(File fileToSave) {
        String name = fileToSave.getName().toLowerCase();
        return name.endsWith("." + filetype.toLowerCase())
            || fileToSave.isDirectory();
      }

      public String getDescription() {
        return filetype + " files";
      }
    });

    int r = chooser.showOpenDialog(null);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    File[] tmpFile = chooser.getSelectedFiles();
    for(int i=0;i<tmpFile.length;i++){
	    String name = tmpFile[i].getAbsolutePath();
	    if (!name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
	    	tmpFile[i] = new File(name + "." + fileType);
	    }
    }
    return tmpFile;
  }

  /**
   * Let user choose a file to save in
   * @return File
   */
  public static File[] getSaveFiles() {
	  return getSaveFiles((Component)null);
  }
  public static File[] getSaveFiles(Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setMultiSelectionEnabled(true);
    int r = chooser.showSaveDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    return chooser.getSelectedFiles();
  }

  /**
   * Let user choose a specified-type file to save in
   * @param fileType String
   * @return File
   */
  public static File[] getSaveFiles(String fileType) {
	  return getSaveFiles(fileType,(Component)null);
  }
  public static File[] getSaveFiles(String fileType,Component parent) {
	return  getSaveFiles(fileType,"");

  }

  /**
   * Let user choose a specified-type file to save in
   * and the choose dialog with a specified titie
   * @param fileType String
   * @param title String Dialog's title
   * @return File
   */
  public static File[] getSaveFiles(String fileType, String title){
	  return  getSaveFiles(fileType, title,null); 
  }
  public static File[] getSaveFiles(String fileType, String title,Component parent) {
    final String filetype = fileType.toLowerCase();
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setDialogTitle(title);
    chooser.setMultiSelectionEnabled(true);
    chooser.setFileFilter(new
                          javax.swing.filechooser.FileFilter() {
      public boolean accept(File fileToSave) {
        String name = fileToSave.getName().toLowerCase();
        return name.endsWith("." + filetype.toLowerCase())
            || fileToSave.isDirectory();
      }

      public String getDescription() {
        return filetype + " files";
      }
    });

    int r = chooser.showSaveDialog(parent);
    if (r != JFileChooser.APPROVE_OPTION)
      return null;

    File[] tmpFile = chooser.getSelectedFiles();
    for(int i=0;i<tmpFile.length;i++){
	    String name = tmpFile[i].getAbsolutePath();
	    if (!name.toLowerCase().endsWith("." + fileType.toLowerCase())) {
	    	tmpFile[i] = new File(name + "." + fileType);
	    }
    }
    return tmpFile;
  }
  public static void main(String []args){
	  GetFile.getOpenFile();
  }
}
