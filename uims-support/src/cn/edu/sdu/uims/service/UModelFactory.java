package cn.edu.sdu.uims.service;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import cn.edu.sdu.uims.graph.model.GraphModel2D;

public class UModelFactory {
	private HashMap<String, GraphModel2D> graphModel2DMap = new HashMap<String, GraphModel2D>();
	private HashMap<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
	private static UModelFactory instance = new UModelFactory();
	public static UModelFactory getInstance(){
		return instance;
	}
	public HashMap<String, GraphModel2D> getGraphModel2DMap() {
		return graphModel2DMap;
	}
	public HashMap<String, BufferedImage> getImageMap() {
		return imageMap;
	}

	
}
