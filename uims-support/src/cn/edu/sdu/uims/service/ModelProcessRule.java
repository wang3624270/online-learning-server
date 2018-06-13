package cn.edu.sdu.uims.service;

/**
 * 
 * use the load image to initial the building information.
 * 
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.sdu.file_util.FileUtility;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.data.GraphModelDataProcessorI;
import cn.edu.sdu.uims.graph.model.data.xml.XmlGraphModelDataProcessor;

@RestController
public class ModelProcessRule  {

	private String getGraphContentObject(String templatename) {
		return UimsFactory.getSysConfigInfoProcessorI().getGraphContentObject(templatename);
	}

	public void clearGraphModel2DObject() {
		UModelFactory.getInstance().getGraphModel2DMap().clear();
	}

	public GraphModelI getGraphModel2DObject(String name) {
		GraphModel2D o = (GraphModel2D) UModelFactory.getInstance()
				.getGraphModel2DMap().get(name);
		// if (o == null) {
		o = loadGraphModel2DFormDB(name);
		UModelFactory.getInstance().getGraphModel2DMap().put(name, o);
		// }
		return o;
	}



	/**
	 * 解析xml的方法
	 * 
	 * @param filePath
	 *            load Graph2D
	 * @param preFixData表示编号前缀
	 *            ：平台编码+业务编码+模型编码类型码
	 * @return
	 */
	private GraphModel2D loadGraphModel2DFormDB(String name) {
		GraphModel2D graphModel2D = null;
		String content, num;
		String configuration = getGraphContentObject(name);
		if (configuration != null) {
			graphModel2D = new GraphModel2D();
			GraphModelDataProcessorI graphDataProcessor = new XmlGraphModelDataProcessor(
					graphModel2D);
			graphModel2D.setGraphDataProcessor(graphDataProcessor);
			graphModel2D.setName(name);
//			num = (String) graph.getNum();
//			graphModel2D.setNum(num);
			graphModel2D.setGraphData(configuration);
//			graphModel2D.setTimestamp(graph.getTimestamp());
			return graphModel2D;
		}

		return null;
	}


	public GraphModel2D newTempGraphModel2DObject() {
		GraphModel2D gm2d = null;
		String numString = getCreateGraphModelNum(UConstants.MODEL_TYPE_GRAPH,
				UConstants.MODEL_TYPE_GRAPH_PRINT);
		String modelName = numString;
		gm2d = newGraphModel2DObject(modelName, null, null);
		return gm2d;
	}

	String getCreateGraphModelNum(String name, String name1) {
		return "001";
	}
	public GraphModel2D newTempGraphModel2DObject(
			GraphModelDataProcessorI graph2DElementProcessorI, Object content) {
		GraphModel2D gm2d = null;
		String numString = getCreateGraphModelNum(UConstants.MODEL_TYPE_GRAPH,
				UConstants.MODEL_TYPE_GRAPH_PRINT);
		String modelName = numString;
		gm2d = newGraphModel2DObject(modelName, graph2DElementProcessorI,
				content);
		return gm2d;
	}

	public GraphModel2D newGraphModel2DObject(String name,
			GraphModelDataProcessorI graphDataProcessor, Object content) {
		GraphModel2D gm2d = null;
		gm2d = new GraphModel2D();
		gm2d.setName(name);
		if (graphDataProcessor != null) {
			graphDataProcessor.setGraphModelI(gm2d);
			gm2d.setGraphDataProcessor(graphDataProcessor);
			gm2d.setGraphData(content);
		} else {
			gm2d.getInitDefaultGraph();
		}
		UModelFactory.getInstance().getGraphModel2DMap().put(name, gm2d);
		return gm2d;
	}

	public BufferedImage getImageByName(String name) {

		BufferedImage img = UModelFactory.getInstance().getImageMap().get(name);
		if (img == null) {
			img = getImageByNameFromFtp(name);
			if (img != null) {
				UModelFactory.getInstance().getImageMap().put(name, img);
			}
		}
		return img;

	}

	public BufferedImage getImageByNameFromFtp(String name) {
		BufferedImage img = null;
		File file = new File("temp");

		try {
			OutputStream output = new FileOutputStream(file);
			FileUtility.downloadFile(output, "/graphimage/" + name);
			img = ImageIO.read(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
}
