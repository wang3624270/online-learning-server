package cn.edu.sdu.uims.util;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.print.PrintService;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.frame.UClientFrame;

public class FilePrintUtil {
	public static void printPDFFile(byte[] streamByte, boolean isSilentPrint)
			throws Exception {
		String password = "";

		String printerName = null;

		PDDocument document = null;
		try {
			InputStream is = new ByteArrayInputStream(streamByte);
			document = PDDocument.load(is);
			if (document.isEncrypted()) {
				document.decrypt(password);
			}
			PrinterJob printJob = PrinterJob.getPrinterJob();

			if (printerName != null) {
				PrintService[] printService = PrinterJob.lookupPrintServices();
				boolean printerFound = false;
				for (int i = 0; !printerFound && i < printService.length; i++) {
					if (printService[i].getName().indexOf(printerName) != -1) {
						printJob.setPrintService(printService[i]);
						printerFound = true;
					}
				}
			}
			if (isSilentPrint) {
				document.silentPrint(printJob);
			} else {
				document.print(printJob);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

	}

	public static void printPDFFile(InputStream stream, boolean isSilentPrint)
			throws Exception {
		String password = "";

		String printerName = null;

		PDDocument document = null;
		try {
			document = PDDocument.load(stream);
			if (document.isEncrypted()) {
				document.decrypt(password);
			}
			PrinterJob printJob = PrinterJob.getPrinterJob();

			if (printerName != null) {
				PrintService[] printService = PrinterJob.lookupPrintServices();
				boolean printerFound = false;
				for (int i = 0; !printerFound && i < printService.length; i++) {
					if (printService[i].getName().indexOf(printerName) != -1) {
						printJob.setPrintService(printService[i]);
						printerFound = true;
					}
				}
			}
			if (isSilentPrint) {
				document.silentPrint(printJob);
			} else {
				document.print(printJob);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

	}

	// /////////////////////
	public static String mergePDFFileStreamToOneFile(List<byte[]> byteList) {
		File tmpfile = null;
		try {
			tmpfile = File.createTempFile("tmp", ".pdf");

			FileOutputStream fos = new FileOutputStream(tmpfile);
			ByteArrayOutputStream byteStream = mergePDFFileStreamToStream(byteList);
			fos.write(byteStream.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpfile.getPath();
	}

	public static ByteArrayOutputStream mergePDFFileStreamToStream(
			List<byte[]> byteList) {
		try {
			if (byteList == null || byteList.size() == 0)
				return new ByteArrayOutputStream();
			ByteArrayOutputStream aStream = new ByteArrayOutputStream();
			InputStream is = new ByteArrayInputStream(byteList.get(0));
			Document document = new Document(new PdfReader(is).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, aStream);
			PdfImportedPage page;
			document.open();
			int i, n, j;
			PdfReader reader;
			for (i = 0; i < byteList.size(); i++) {
				reader = new PdfReader(
						new ByteArrayInputStream(byteList.get(i)));
				n = reader.getNumberOfPages();
				for (j = 1; j <= n; j++) {
					document.newPage();
					page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			document.close();
			return aStream;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new ByteArrayOutputStream();
	}

	public static byte[] mergePDFFileStream(List<byte[]> byteList) {
		ByteArrayOutputStream aStream = mergePDFFileStreamToStream(byteList);
		return aStream.toByteArray();
	}

	// /////////////////////

	public static void saveFile(byte[] dataArray, String fileName)
			throws Exception {

		ByteArrayInputStream bin = new ByteArrayInputStream(dataArray);
		JFileChooser fc = new JFileChooser("选择要保存的目录");
		fc.setApproveButtonText("确定");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int intRetVal = fc.showOpenDialog(UClientFrame.getInstance());
		if (intRetVal == JFileChooser.APPROVE_OPTION) {
			File file = new File(fc.getSelectedFile().getPath());
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(null, "您选择的不是文件夹，请重新选择!");
				return;
			} else {
				String filePath = fc.getSelectedFile().getPath() + "\\"
						+ fileName;

				File newFile = new File(filePath);
				try {
					/*	RandomAccessFile  raf = new RandomAccessFile(newFile, "rw");
					FileChannel fic = raf.getChannel();
					 FileLock fl = fic.tryLock();
					 if(!fl.isValid()) {
					     
					 }*/
					FileOutputStream fos = new FileOutputStream(newFile);
					fos.write(dataArray);
					fos.close();
					JOptionPane.showMessageDialog(null, fileName + "下载成功.");
				} catch(FileNotFoundException fe){
					 JOptionPane.showMessageDialog(null, "另一个程序正在使用此文件，请关闭后再重试");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void mergePDFFileStreamToOneFileCS(List<byte[]> byteList) {
		File tmpfile = null;
		try {
			tmpfile = GetFile.getSaveFile("pdf");

			FileOutputStream fos = new FileOutputStream(tmpfile);
			ByteArrayOutputStream byteStream = mergePDFFileStreamToStream(byteList);
			fos.write(byteStream.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) throws Exception{
		FileInputStream in;
		List<byte[]> aList = new ArrayList<byte[]>();
		byte [] a;
		int length;
		File f;
		f = new File("c:\\test\\1.pdf");
		length = (int)f.length();
		a = new byte[length];
		in = new FileInputStream(f);
		in.read(a);
		in.close();
		aList.add(a);
		f = new File("c:\\test\\2.pdf");
		length = (int)f.length();
		a = new byte[length];
		in = new FileInputStream(f);
		in.read(a);
		in.close();
		aList.add(a);
		FileOutputStream fos = new FileOutputStream("c:\\test\\t.pdf");
		ByteArrayOutputStream byteStream = mergePDFFileStreamToStream(aList);
		fos.write(byteStream.toByteArray());
		fos.flush();
		fos.close();
	}
}