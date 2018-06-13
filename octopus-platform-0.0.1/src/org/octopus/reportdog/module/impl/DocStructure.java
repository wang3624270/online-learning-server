package org.octopus.reportdog.module.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.module.DocBrick_P;
import org.octopus.reportdog.module.DocBrick_PageFooter;
import org.octopus.reportdog.module.DocBrick_Paper;
import org.octopus.reportdog.module.PageConfig;

public class DocStructure {

	private String name;
	private String handler;
	private String pageModel = "singlePage";
	public HashMap<String, PageConfig> pageConfigs;
	private List<DocBrick_P> p_List;
	private String backgroundImagePath;
	private String pageNumberDes = "normal";

	// Indicate some information of page footer.
	private DocBrick_PageFooter pageFooter = null;
	private DocBrick_Paper paperInfo = null;

	public DocStructure() {
		super();
		this.pageConfigs = new HashMap();
		this.p_List = new ArrayList();
	}

	public DocStructure(String name) {
		super();
		this.name = name;
		this.p_List = new ArrayList();
	}

	public void addBrick_P(DocBrick_P p) {
		// TODO Auto-generated method stub
		p_List.add(p);

	}

	public List getBrick_PList() {
		return p_List;
	}

	public DocBrick_P findBrick_P(String name) {
		// TODO Auto-generated method stub

		int i;
		for (i = 0; i < p_List.size(); i++) {
			if (((DocBrick_P) p_List.get(i)).getName().equals(name))
				return (DocBrick_P) p_List.get(i);
		}
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}

	public void SetHandler(String handler) {
		this.handler = handler;
	}

	public void setPageModel(String pageModel) {
		this.pageModel = pageModel;
	}

	public String getPageModel() {
		return pageModel;
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	public DocBrick_PageFooter getPageFooter() {
		return pageFooter;
	}

	public void setPageFooter(DocBrick_PageFooter pageFooter) {
		this.pageFooter = pageFooter;
	}

	public DocBrick_Paper getPaperInfo() {
		return paperInfo;
	}

	public void setPaperInfo(DocBrick_Paper paperInfo) {
		this.paperInfo = paperInfo;
	}
 
}
