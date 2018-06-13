package org.octopus.reportdog.module.impl;

import java.util.HashMap;

import org.octopus.reportdog.module.DocBrick_Paper;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.PageConfig;

public class DocWrapper {

	private String name;

	private String paper;

	private String documentHandlerName = "defaultDocumentHandler";
	private HashMap pageConfigs;
	private String type = "";
	private DocBrick_Paper paperInfo = null;

	private String orientation;

	private HashMap pageInstances = new HashMap();

	public DocWrapper() {
		super();
		pageConfigs = new HashMap();
	}

	public DocWrapper(String name) {
		super();
		this.name = name;
		pageConfigs = new HashMap();
	}

	public void addPageConfig(PageConfig pageConfig) {

		pageConfigs.put(pageConfig.getName(), pageConfig);
	}

	public PageConfig findPageConfig(String name) {
		return (PageConfig) pageConfigs.get(name);
	}

	public HashMap getPageConfigs() {
		return pageConfigs;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// //////////////////////////////////////////////////

	public String getDocumentHandlerName() {
		return this.documentHandlerName;
	}

	public void setDocumentHandlerName(String documentHandlerName) {
		this.documentHandlerName = documentHandlerName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public DocBrick_Paper getPaperInfo() {
		if (paperInfo == null) {
			paperInfo = new DocBrick_Paper();
			paperInfo.setType(paper);
		}

		return paperInfo;
	}

	public void setPaperInfo(DocBrick_Paper paperInfo) {
		this.paperInfo = paperInfo;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	// ////////////////////////////////////////
	public HashMap getPageInstances() {
		return pageInstances;
	}

	public void setPageInstances(HashMap pageInstances) {
		this.pageInstances = pageInstances;
	}

	public void addPageInstance(DocDescription pageModelModuleInstance) {
		// this.pageModelModuleInstance = pageModelModuleInstance;
		pageInstances.put("1", pageModelModuleInstance);

	}

	public void addPageInstance(String key,
			DocDescription pageModelModuleInstance) {
		// this.pageModelModuleInstance = pageModelModuleInstance;
		pageInstances.put(key, pageModelModuleInstance);

	}

	public DocDescription getPageInstance(String key) {
		return (DocDescription) pageInstances.get(key);
		// return this.pageModelModuleInstance;
	}
}
