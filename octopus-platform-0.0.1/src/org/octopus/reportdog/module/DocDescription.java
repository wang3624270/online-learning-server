package org.octopus.reportdog.module;

import java.util.ArrayList;
import java.util.List;

public class DocDescription {

	private String name;
	private List p_List = new ArrayList();
	private String backgroundImagePath;

	private DocLattice_PageFooter pageFooter = null;
	private DocLattice_Paper paperInfo = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getp_List() {
		return p_List;
	}

	public void setp_List(List p_List) {
		this.p_List = p_List;
	}

	public void addP(DocLattice_P paragraphInstance) {
		this.p_List.add(paragraphInstance);
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	public DocLattice_PageFooter getPageFooter() {
		return pageFooter;
	}

	public void setPageFooter(DocLattice_PageFooter pageFooter) {
		this.pageFooter = pageFooter;
	}

	public DocLattice_Paper getPaperInfo() {
		return paperInfo;
	}

	public void setPaperInfo(DocLattice_Paper paperInfo) {
		this.paperInfo = paperInfo;
	}

}
