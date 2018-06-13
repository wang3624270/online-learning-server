package org.octopus.reportdog.document.element.pdfElement;

import java.awt.image.BufferedImage;

import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;

import org.octopus.reportdog.document.ExportObjectOperator;
import org.octopus.reportdog.document.element.ReportElement;

public class AbsoluteImage implements ReportElement {

	byte[] contentBytes = null;
	float coordinateX = 5;
	float coordibnateY = 5;

	@Override
	public void renderElement(ExportObjectOperator ud) {
		Document doc = (Document) ud.getDocOperator();

		String x, y, height, width;
		Image image = null;
		try {
			image = com.itextpdf.text.Image.getInstance(contentBytes);

			// height = template.sHeight;
			// width = template.sWidth;

			// image.setAbsolutePosition(coordinateX, coordibnateY);

			// if (height != null && width != null) {
			// image.scaleAbsolute(Float.parseFloat(width), Float
			// .parseFloat(height));
			// }
			doc.add(image);
		} catch (Exception e) {
		}
	}

	@Override
	public void injectContent(Object ob, HashMap paraMap) {
		// TODO Auto-generated method stub

		if (paraMap == null || paraMap.get("mediaType") == null)

			contentBytes = (byte[]) ob;
		else if (paraMap.get("mediaType").equals("bufferedImage")) {
			BufferedImage tempImage = (BufferedImage) ob;
			contentBytes = ((DataBufferByte) tempImage.getData()
					.getDataBuffer()).getData();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(tempImage, "jpg", baos);
				baos.flush();
				contentBytes = baos.toByteArray();
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}