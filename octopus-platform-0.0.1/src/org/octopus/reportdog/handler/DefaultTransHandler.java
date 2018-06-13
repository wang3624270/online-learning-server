package org.octopus.reportdog.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.module.CellConfig;
import org.octopus.reportdog.module.CellInstance;
import org.octopus.reportdog.module.DocBrick_P;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.RowConfig;
import org.octopus.reportdog.module.impl.DocStructure;
import org.octopus.reportdog.state.BlockState;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransHandler implements TansHandlerI {

	public void transformData(RmiRequest request, RmiResponse response) {
		DocStructure docStructure = (DocStructure) request
				.get(ReportdogConstants.DOC_STRUCTURE);
		DocDescription docDescription = (DocDescription) request
				.get(ReportdogConstants.DOC_DESCRIPTION);
		docDescription.setBackgroundImagePath(((DocStructure) docStructure)
				.getBackgroundImagePath());

		DefaultDataForm midForm = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY);
		List<String> reservedWords = (List<String>) request
				.get(ReportdogConstants.RESERVED_WORDS_KEY);
		if (docStructure.getPageFooter() != null)
			docDescription.setPageFooter(docStructure.getPageFooter()
					.transToBasicLatticeElement());
		if (docStructure.getPaperInfo() != null)
			docDescription.setPaperInfo(docStructure.getPaperInfo()
					.transToBasicLatticeElement());

		List paragraphModelConfigs = docStructure.getBrick_PList();

		DocBrick_P docBrick_P;
		DocLattice_P docLattice_P;
		String content, type;
		List<BlockState> blockStateList;
		int i;
		try {
			List<String> hideKeys = null;
			if (request.get(ReportdogConstants.HIDE_PARAGRAPH_KEY) != null)
				hideKeys = (List) request
						.get(ReportdogConstants.HIDE_PARAGRAPH_KEY);
			for (i = 0; i < paragraphModelConfigs.size(); i++) {
				docBrick_P = (DocBrick_P) paragraphModelConfigs.get(i);

				if (hideKeys != null && hideKeys.contains(docBrick_P.getName()))
					continue;
				if (docBrick_P.onlyShowInLastPage.equals("true")
						&& request.get(ReportdogConstants.NOW_PAGE_NUM_KEY) != null
						&& request.get(ReportdogConstants.TOTAL_PAGE_COUNT) != null) {
					if (Integer.parseInt(request.get(
							ReportdogConstants.NOW_PAGE_NUM_KEY).toString()) + 1 != Integer
							.parseInt(request.get(
									ReportdogConstants.TOTAL_PAGE_COUNT)
									.toString()))
						continue;
				}
				docLattice_P = new DocLattice_P();
				docLattice_P.setName(docBrick_P.getName());
				docLattice_P.setType(docBrick_P.getType());
				docLattice_P.setTemplateName(docBrick_P.getTemplateName());
				docLattice_P.setNewPage(docBrick_P.getNewPage());
				type = docBrick_P.getType();
				docDescription.addP(docLattice_P);
				if (type.equals("text") || type.equals("number")) {
					content = docBrick_P.getContent();
					docLattice_P.setTemplateName(docBrick_P.getTemplateName());
					docLattice_P.setContent(RequestUtils.keywordReplace(
							content, midForm));
					docLattice_P.setTextHeight(docBrick_P.getTextHeight());
					docLattice_P.setType(type);
				} else if (type.equals("table")
						|| type.equals("embedMainTable")) {

					if (reservedWords == null || reservedWords.size() == 0) {
						this.tableProcess(docBrick_P, docLattice_P, midForm,
								request);
					} else {
						this.tableProcess(docBrick_P, docLattice_P, midForm,
								reservedWords, request);

					}
				} else if (type.equals("image")) {
					content = docBrick_P.getContent();
					docLattice_P.setContent(content);
				} else if (type.equals("block")) {
					blockStateList = docBrick_P.getBlockStateList();
					blockProcess(docLattice_P, blockStateList, midForm);
				} else if (type.equals("formatText")) {
					if (reservedWords == null || reservedWords.size() == 0) {
						this.tableProcess(docBrick_P, docLattice_P, midForm,
								request);
					} else {
						this.tableProcess(docBrick_P, docLattice_P, midForm,
								reservedWords, request);

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DefaultTransHandler中数据注入模型错误,请检查页面配置.");
		}
	}

	// /////////////////////////////////////////////////////////
	public void blockProcess(DocLattice_P paragraphInstance,
			List<BlockState> blockStateList, DefaultDataForm midForm) {
		int i;
		List<BlockState> theList = new ArrayList();
		BlockState aState;
		for (i = 0; i < blockStateList.size(); i++) {
			aState = new BlockState();
			aState.setName(blockStateList.get(i).getName());
			aState.setTemplateName(blockStateList.get(i).getTemplateName());
			aState.setType(blockStateList.get(i).getType());
			if (aState.type.equals("text"))
				aState.setContent(RequestUtils.keywordReplace(blockStateList
						.get(i).getContent(), midForm));
			theList.add(aState);
		}
		paragraphInstance.setBlockStateList(theList);
	}

	// ///////////////////////////////////////////////////////
	public void tableProcess(DocBrick_P paragraphConfig,
			DocLattice_P paragraphInstance, DefaultDataForm midForm,
			RmiRequest request) {
		List<RowConfig> rowConfigs;
		RowConfig rowConfig;
		CellInstance cellInstance;
		int i, j, m, n, s, row = -1, col = 0;
		List<CellConfig> cellConfigs;
		String id;
		List<DefaultDataForm> dataList;
		DefaultDataForm element;
		CellConfig cellConfig;
		float widths[] = null;
		rowConfigs = paragraphConfig.getRowList();
		if (rowConfigs != null && rowConfigs.size() > 0) {
			// ///////////////////////////////////////
			List<String> idList = new ArrayList();
			if (paragraphConfig.getRowArrangement() == 1) {
				// //////////////////////
				// ////////////////////////
				for (i = 0; i < rowConfigs.size(); i++) {
					if (!idList.contains(rowConfigs.get(i).getId()))
						idList.add(rowConfigs.get(i).getId());
				}
				for (i = 0; i < idList.size(); i++) {
					{
						if (midForm.get(idList.get(i)) instanceof DefaultDataForm) {
							dataList = new ArrayList();
							dataList.add((DefaultDataForm) midForm.get(idList
									.get(i)));
						} else
							dataList = (List<DefaultDataForm>) midForm
									.get(idList.get(i));
						if (dataList != null && dataList.size() > 0)
							for (s = 0; s < dataList.size(); s++)

								for (n = 0; n < rowConfigs.size(); n++) {
									rowConfig = rowConfigs.get(n);
									cellConfigs = rowConfig.getCellList();

									row++;
									element = dataList.get(s);
									if (col < cellConfigs.size()) {
										col = cellConfigs.size();
										widths = this.getTableCellWidth(
												cellConfigs, col);
									}
									for (m = 0; m < cellConfigs.size(); m++) {

										cellConfig = cellConfigs.get(m);

										cellInstance = this
												.CellInstanceProcess(row, m,
														rowConfig.getHeight(),
														cellConfig, element,
														request);
										if (cellConfig.getOmitEmpty().equals(
												"true")
												&& cellConfig.getType().equals(
														"")
												&& (cellInstance.getContent() == null || cellInstance
														.getContent().length() == 0))
											continue;
										paragraphInstance
												.addCellInstance(cellInstance);

									}
									if (rowConfig.getPrintOnlyOnce() == 1)
										break;

								}

						// /////////////////////////
					}
					// ////////////////////////
					// ////////////////////////////////
				}

			} else {
				for (i = 0; i < rowConfigs.size(); i++) {
					rowConfig = (RowConfig) rowConfigs.get(i);
					id = rowConfig.getId();
					cellConfigs = rowConfig.getCellList();
					if (id != null && !id.equals("")) {
						if (midForm.get(rowConfig.getId()) instanceof DefaultDataForm) {
							dataList = new ArrayList();
							dataList.add((DefaultDataForm) midForm
									.get(rowConfig.getId()));
						} else
							dataList = (List<DefaultDataForm>) midForm
									.get(rowConfig.getId());
						if (dataList != null && dataList.size() > 0) {
							for (j = 0; j < dataList.size(); j++) {
								row++;
								element = dataList.get(j);
								if (col < cellConfigs.size()) {
									col = cellConfigs.size();
									widths = this.getTableCellWidth(
											cellConfigs, col);
								}
								for (m = 0; m < cellConfigs.size(); m++) {

									cellConfig = cellConfigs.get(m);
									cellInstance = this.CellInstanceProcess(
											row, m, rowConfig.getHeight(),
											cellConfig, element, request);
									if (cellConfig.getOmitEmpty()
											.equals("true")
											&& cellConfig.getType().equals("")
											&& (cellInstance.getContent() == null || cellInstance
													.getContent().length() == 0))
										continue;
									paragraphInstance
											.addCellInstance(cellInstance);

								}
								if (rowConfig.getPrintOnlyOnce() == 1)
									break;
							}
						}

						// /////////////////////////
					} else {
						row++;
						if (col < cellConfigs.size()) {
							col = cellConfigs.size();
							widths = this.getTableCellWidth(cellConfigs, col);
						}
						for (m = 0; m < cellConfigs.size(); m++) {
							cellConfig = cellConfigs.get(m);

							cellInstance = this.CellInstanceProcess(row, m,
									rowConfig.getHeight(), cellConfig, midForm,
									request);
							if (cellConfig.getOmitEmpty().equals("true")
									&& cellConfig.getType().equals("")
									&& (cellInstance.getContent() == null || cellInstance
											.getContent().length() == 0))
								continue;
							paragraphInstance.addCellInstance(cellInstance);

						}
					}
				}

			}
		}
		paragraphInstance.setRows(row);
		paragraphInstance.setCols(col);
		paragraphInstance.setWidth(widths);
		paragraphInstance.setTableBorderWidth(paragraphConfig
				.getTableBorderWidth());
		paragraphInstance.setTableWidthPercentage(paragraphConfig
				.getTableWidthPercentage());
		paragraphInstance.setNewPageAction(paragraphConfig.getNewPageAction());
		paragraphInstance
				.setHeaderRowCount(paragraphConfig.getHeaderRowCount());
		paragraphInstance.setSplitRows(paragraphConfig.isSplitRows());
		if (paragraphConfig.getColCount() != null
				&& Integer.parseInt(paragraphConfig.getColCount()) >= paragraphInstance
						.getCols()) {
			paragraphInstance.setCols(Integer.parseInt(paragraphConfig
					.getColCount()));
			paragraphInstance.setWidth(convertWidth(
					paragraphConfig.getWidths(),
					Integer.parseInt(paragraphConfig.getColCount())));

		}
		if (paragraphConfig.getRowCount() != null
				&& Integer.parseInt(paragraphConfig.getRowCount()) >= paragraphInstance
						.getRows()) {
			paragraphInstance.setRows(Integer.parseInt(paragraphConfig
					.getRowCount()));
			paragraphInstance.setHeight(convertWidth(
					paragraphConfig.getHeights(),
					Integer.parseInt(paragraphConfig.getRowCount())));
		}
	}

	private float[] convertWidth(String widthsString, int colNum) {
		if (widthsString == null)
			return null;
		String[] a = widthsString.split(",");
		float[] width = new float[a.length];
		int i;
		for (i = 0; i < a.length; i++) {
			width[i] = Float.parseFloat(a[i]);
		}

		return width;
	}

	public void tableProcess(DocBrick_P paragraphConfig,
			DocLattice_P paragraphInstance, DefaultDataForm midForm,
			List<String> reservedWords, RmiRequest request) {
		List<RowConfig> rowConfigs;
		RowConfig rowConfig;
		CellInstance cellInstance;
		int i, j, m, row = -1, col = 0, tmpcol = 0;
		List<CellConfig> cellConfigs;
		String id;
		List<DefaultDataForm> dataList;
		DefaultDataForm element;
		CellConfig cellConfig;
		float widths[] = null;
		rowConfigs = paragraphConfig.getRowList();
		if (rowConfigs != null && rowConfigs.size() > 0) {
			for (i = 0; i < rowConfigs.size(); i++) {
				rowConfig = (RowConfig) rowConfigs.get(i);
				id = rowConfig.getId();
				cellConfigs = rowConfig.getCellList();
				if (id != null && !id.equals("")) {
					dataList = (List<DefaultDataForm>) midForm.get(rowConfig
							.getId());
					if (dataList != null && dataList.size() > 0) {
						for (j = 0; j < dataList.size(); j++) {
							row++;
							element = dataList.get(j);
							tmpcol = this.computeTmpCol(cellConfigs,
									reservedWords);
							if (col < tmpcol) {
								col = tmpcol;
								this.getTableCellWidth(cellConfigs, col,
										reservedWords);
							}
							int nowCol = 0;
							String str, str1;
							for (m = 0; m < cellConfigs.size(); m++) {

								cellConfig = cellConfigs.get(m);
								str = cellConfig.getContent();
								str1 = "";
								if (str.length() > 0 && str.charAt(0) == '$')
									str1 = str.substring(2, str.length() - 1);
								if (reservedWords.contains(str)
										|| reservedWords.contains(str1)) {
									cellInstance = this.CellInstanceProcess(
											row, nowCol, rowConfig.getHeight(),
											cellConfig, element, request);
									paragraphInstance
											.addCellInstance(cellInstance);
									nowCol++;
								}
							}
						}
					}
				} else {
					row++;
					tmpcol = this.computeTmpCol(cellConfigs, reservedWords);
					if (col < tmpcol) {
						col = tmpcol;
						this.getTableCellWidth(cellConfigs, col, reservedWords);
					}
					int nowCol = 0;
					for (m = 0; m < cellConfigs.size(); m++) {
						cellConfig = cellConfigs.get(m);
						if (reservedWords.contains(cellConfig.getContent())) {
							cellInstance = this.CellInstanceProcess(row,
									nowCol, rowConfig.getHeight(), cellConfig,
									midForm, request);
							paragraphInstance.addCellInstance(cellInstance);
							nowCol++;
						}
					}
				}

			}
		}
		paragraphInstance.setRows(row);
		paragraphInstance.setCols(col);
		paragraphInstance.setWidth(widths);
		paragraphInstance.setTableBorderWidth(paragraphConfig
				.getTableBorderWidth());
		paragraphInstance.setTableWidthPercentage(paragraphConfig
				.getTableWidthPercentage());
		paragraphInstance.setNewPageAction(paragraphConfig.getNewPageAction());
		paragraphInstance
				.setHeaderRowCount(paragraphConfig.getHeaderRowCount());
		paragraphInstance.setSplitRows(paragraphConfig.isSplitRows());
	}

	public float[] getTableCellWidth(List<CellConfig> cellConfigs, int col) {
		float[] widths = new float[col];
		int m;
		String widthstr;
		CellConfig cellConfig;
		for (m = 0; m < cellConfigs.size(); m++) {
			cellConfig = cellConfigs.get(m);
			widthstr = cellConfig.getWidth();
			if (widthstr != null) {
				widths[m] = Float.parseFloat(widthstr);
			} else {
				widths[m] = 50;
			}
		}
		return widths;
	}

	public float[] getTableCellWidth(List<CellConfig> cellConfigs, int col,
			List<String> reservedWords) {
		float[] widths = new float[col];
		int m, n = 0;
		CellConfig cellConfig;
		String widthstr;
		for (m = 0; m < cellConfigs.size(); m++) {
			cellConfig = cellConfigs.get(m);
			if (reservedWords.contains(cellConfig.getContent())) {
				widthstr = cellConfig.getWidth();
				if (widthstr != null) {
					widths[n] = Float.parseFloat(widthstr);
				} else {
					widths[n] = 50;
				}
				n++;
			}
		}
		return widths;
	}

	public int computeTmpCol(List<CellConfig> cellConfigs,
			List<String> reservedWords) {
		int m, tmpcol = 0;
		CellConfig cellConfig;
		for (m = 0; m < cellConfigs.size(); m++) {
			cellConfig = cellConfigs.get(m);
			if (reservedWords.contains(cellConfig.getContent())) {
				tmpcol++;
			}
		}
		return tmpcol;
	}

	public CellInstance CellInstanceProcess(int row, int col, String height,
			CellConfig cellConfig, DefaultDataForm element, RmiRequest request) {
		CellInstance cellInstance = new CellInstance();
		String rowspanstr = cellConfig.getRowspan();
		if (rowspanstr != null && !rowspanstr.equals("")) {
			if (rowspanstr.charAt(0) == '$') {
				rowspanstr = rowspanstr.substring(rowspanstr.indexOf('{') + 1,
						rowspanstr.indexOf('}'));
				rowspanstr = element.get(rowspanstr).toString();
			}

			// rowspanstr = RequestUtils
			// .getGlobalVariable(
			// (HashMap) request
			// .get(ReportdogConstants.GLOBAL_VARIABLE_MAP),
			// rowspanstr).toString();
			cellInstance.setRowspan(Integer.parseInt(rowspanstr));
		} else {
			cellInstance.setRowspan(1);
		}
		String colspanstr = cellConfig.getColspan();
		if (colspanstr != null && !colspanstr.equals("")) {
			if (colspanstr.charAt(0) == '$')
				colspanstr = RequestUtils
						.getGlobalVariable(
								(HashMap) request
										.get(ReportdogConstants.GLOBAL_VARIABLE_MAP),
								colspanstr).toString();

			cellInstance.setColspan(Integer.parseInt(colspanstr));
		} else {
			cellInstance.setColspan(1);
		}
		if (cellConfig.getHeight().equals("-1"))
			cellInstance.setHeight(height);
		else
			cellInstance.setHeight(cellConfig.getHeight());
		cellInstance.setCol(col);
		cellInstance.setRow(row);
		cellInstance.setWidth(cellConfig.getWidth());
		cellInstance.setTemplateName(cellConfig.getTemplateName());
		cellInstance.setUnderline(cellConfig.getUnderline());
		cellInstance.setLineLeading(cellConfig.getLineLeading());
		cellInstance.setIsFixedHeight(cellConfig.getIsFixedHeight());
		cellInstance.setIsComplexProcess(cellConfig.getIsComplexProcess());
		cellInstance.setBorderLineStyle(cellConfig.getBorderLineStyle());
		cellInstance.setDelayEvaluate(cellConfig.getDelayEvaluate());
		cellInstance.setIsUnicodeProcess(cellConfig.getIsUnicodeProcess());
		cellInstance.setIsHtmlProcess(cellConfig.getIsHtmlProcess());
		cellInstance.setShowBorder(cellConfig.getShowBorder());
		cellInstance.setNewPage(cellConfig.getNewPage());

		if (cellConfig.getAlign() != null)
			cellInstance.setHAlign(Integer.parseInt(cellConfig.getAlign()));

		if (cellConfig.getHalign() != null)
			cellInstance.setHAlign(Integer.parseInt(cellConfig.getHalign()));
		else
			cellInstance.setHAlign(0);

		if (cellConfig.getValign() != null)
			cellInstance.setVAlign(Integer.parseInt(cellConfig.getValign()));
		else
			cellInstance.setVAlign(5);

		cellInstance.setType(cellConfig.getType());
		if (cellConfig.getType().equals("image")) {
			cellInstance.setStreamValue(RequestUtils.getStreamInf(
					cellConfig.getContent(), element));
			cellInstance.setContentVariableStr(cellConfig.getContent());
		} else if (cellConfig.getType().equals("childTable")
				&& cellConfig.getIsChildTableTemplate() == 1) {
			String str = cellConfig.getContent();
			String var = str.substring(str.indexOf('|'));
			str = str.substring(0, str.indexOf('|'));

			var = var.substring(var.indexOf('{') + 1, var.indexOf('}'));
			DocStructure modelModuleConfig = (DocStructure) request
					.get(ReportdogConstants.DOC_STRUCTURE);
			List paragraphModelConfigs = modelModuleConfig.getBrick_PList();

			DefaultDataForm midForm = (DefaultDataForm) request
					.get(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY);
			int i;
			DocBrick_P paragraphConfig;
			DocLattice_P paragraphInstance = new DocLattice_P();

			for (i = 0; i < paragraphModelConfigs.size(); i++) {

				paragraphConfig = (DocBrick_P) paragraphModelConfigs.get(i);
				if (paragraphConfig.getName().equals(str)) {
					paragraphInstance = new DocLattice_P();
					paragraphInstance.setName(paragraphConfig.getName());
					paragraphInstance.setType(paragraphConfig.getType());
					paragraphInstance.setTemplateName(paragraphConfig
							.getTemplateName());
					if (element.get(var) instanceof List) {
						DefaultDataForm tempForm = new DefaultDataForm();
						tempForm.setName(var);
						tempForm.addTopDataFormOrList(element.get(var));
						this.tableProcess(paragraphConfig, paragraphInstance,
								tempForm, request);
					} else
						this.tableProcess(paragraphConfig, paragraphInstance,
								(DefaultDataForm) element.get(var), request);

					break;
				}

			}

			cellInstance.setEmbedParagraphInstance(paragraphInstance);

		} else if (cellConfig.getType().equals("childTable")
				&& cellConfig.getIsChildTableTemplate() == 0) {
			String str = cellConfig.getContent();
			DocStructure modelModuleConfig = (DocStructure) request
					.get(ReportdogConstants.DOC_STRUCTURE);
			List paragraphModelConfigs = modelModuleConfig.getBrick_PList();
			// PageModelModuleInstance modelModuleInstance =
			// (PageModelModuleInstance) request
			// .get(ImportAndExportConstants.PAGE_MODEL_MODULE_INSTANCE_KEY);
			DefaultDataForm midForm = (DefaultDataForm) request
					.get(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY);
			int i;
			DocBrick_P paragraphConfig;
			DocLattice_P paragraphInstance = new DocLattice_P();

			for (i = 0; i < paragraphModelConfigs.size(); i++) {

				paragraphConfig = (DocBrick_P) paragraphModelConfigs.get(i);
				if (paragraphConfig.getName().equals(str)) {
					paragraphInstance = new DocLattice_P();
					paragraphInstance.setName(paragraphConfig.getName());
					paragraphInstance.setType(paragraphConfig.getType());
					paragraphInstance.setTemplateName(paragraphConfig
							.getTemplateName());
					this.tableProcess(paragraphConfig, paragraphInstance,
							(DefaultDataForm) midForm, request);
					break;
				}
			}

			cellInstance.setEmbedParagraphInstance(paragraphInstance);

		} else {
			cellInstance.setContent(RequestUtils.keywordReplace(
					cellConfig.getContent(), element));
			cellInstance.setContentVariableStr(cellConfig.getContent());
		}

		return cellInstance;
	}

	public CellInstance CellInstanceProcess(int row, int col, String height,
			CellConfig cellConfig, DefaultDataForm element,
			List<String> reservedWords, RmiRequest request) {
		CellInstance cellInstance = new CellInstance();
		String rowspanstr = cellConfig.getRowspan();
		if (rowspanstr != null && !rowspanstr.equals("")) {
			cellInstance.setRowspan(Integer.parseInt(rowspanstr));
		} else {
			cellInstance.setRowspan(1);
		}
		String colspanstr = cellConfig.getColspan();
		if (colspanstr != null && !colspanstr.equals("")) {
			if (colspanstr.indexOf(0) == '$')
				colspanstr = RequestUtils
						.getGlobalVariable(
								(HashMap) request
										.get(ReportdogConstants.GLOBAL_VARIABLE_MAP),
								colspanstr).toString();

			cellInstance.setColspan(Integer.parseInt(colspanstr));
		} else {
			cellInstance.setColspan(1);
		}
		if (cellConfig.getHeight().equals("-1"))
			cellInstance.setHeight(height);
		else
			cellInstance.setHeight(cellConfig.getHeight());
		cellInstance.setCol(col);
		cellInstance.setRow(row);
		cellInstance.setWidth(cellConfig.getWidth());
		cellInstance.setTemplateName(cellConfig.getTemplateName());
		cellInstance.setUnderline(cellConfig.getUnderline());
		cellInstance.setLineLeading(cellConfig.getLineLeading());
		cellInstance.setIsFixedHeight(cellConfig.getIsFixedHeight());
		cellInstance.setIsComplexProcess(cellConfig.getIsComplexProcess());
		cellInstance.setBorderLineStyle(cellConfig.getBorderLineStyle());
		cellInstance.setDelayEvaluate(cellConfig.getDelayEvaluate());
		cellInstance.setIsUnicodeProcess(cellConfig.getIsUnicodeProcess());
		cellInstance.setIsHtmlProcess(cellConfig.getIsHtmlProcess());
		cellInstance.setShowBorder(cellConfig.getShowBorder());
		cellInstance.setNewPage(cellConfig.getNewPage());
		if (cellConfig.getAlign() != null)
			cellInstance.setHAlign(Integer.parseInt(cellConfig.getAlign()));

		if (cellConfig.getHalign() != null)
			cellInstance.setHAlign(Integer.parseInt(cellConfig.getHalign()));
		else
			cellInstance.setHAlign(0);

		if (cellConfig.getValign() != null)
			cellInstance.setVAlign(Integer.parseInt(cellConfig.getValign()));
		else
			cellInstance.setVAlign(5);
		cellInstance.setType(cellConfig.getType());
		if (cellConfig.getType().equals("image")) {
			cellInstance.setStreamValue(RequestUtils.getStreamInf(
					cellConfig.getContent(), element));
			cellInstance.setContentVariableStr(cellConfig.getContent());
		} else if (cellConfig.getType().equals("childTable")
				&& cellConfig.getIsChildTableTemplate() == 1) {
			String str = cellConfig.getContent();
			String var = str.substring(str.indexOf('|'));
			str = str.substring(0, str.indexOf('|'));

			var = var.substring(var.indexOf('{'), var.indexOf('}'));
			DocStructure modelModuleConfig = (DocStructure) request
					.get(ReportdogConstants.DOC_STRUCTURE);
			List paragraphModelConfigs = modelModuleConfig.getBrick_PList();
			// PageModelModuleInstance modelModuleInstance =
			// (PageModelModuleInstance) request
			// .get(ImportAndExportConstants.PAGE_MODEL_MODULE_INSTANCE_KEY);
			DefaultDataForm midForm = (DefaultDataForm) request
					.get(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY);
			int i;
			DocBrick_P paragraphConfig;
			DocLattice_P paragraphInstance = new DocLattice_P();

			for (i = 0; i < paragraphModelConfigs.size(); i++) {

				paragraphConfig = (DocBrick_P) paragraphModelConfigs.get(i);
				if (paragraphConfig.getName().equals(str)) {
					paragraphInstance = new DocLattice_P();
					paragraphInstance.setName(paragraphConfig.getName());
					paragraphInstance.setType(paragraphConfig.getType());
					paragraphInstance.setTemplateName(paragraphConfig
							.getTemplateName());
					this.tableProcess(paragraphConfig, paragraphInstance,
							(DefaultDataForm) midForm.get(var), request);
					break;
				}

			}

			cellInstance.setEmbedParagraphInstance(paragraphInstance);

		} else if (cellConfig.getType().equals("childTable")
				&& cellConfig.getIsChildTableTemplate() == 0) {
			String str = cellConfig.getContent();
			DocStructure modelModuleConfig = (DocStructure) request
					.get(ReportdogConstants.DOC_STRUCTURE);
			List paragraphModelConfigs = modelModuleConfig.getBrick_PList();
			// PageModelModuleInstance modelModuleInstance =
			// (PageModelModuleInstance) request
			// .get(ImportAndExportConstants.PAGE_MODEL_MODULE_INSTANCE_KEY);
			DefaultDataForm midForm = (DefaultDataForm) request
					.get(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY);
			int i;
			DocBrick_P paragraphConfig;
			DocLattice_P paragraphInstance = new DocLattice_P();

			for (i = 0; i < paragraphModelConfigs.size(); i++) {

				paragraphConfig = (DocBrick_P) paragraphModelConfigs.get(i);
				if (paragraphConfig.getName().equals(str)) {
					paragraphInstance = new DocLattice_P();
					paragraphInstance.setName(paragraphConfig.getName());
					paragraphInstance.setType(paragraphConfig.getType());
					paragraphInstance.setTemplateName(paragraphConfig
							.getTemplateName());
					this.tableProcess(paragraphConfig, paragraphInstance,
							(DefaultDataForm) midForm, request);
					break;
				}
			}

			cellInstance.setEmbedParagraphInstance(paragraphInstance);

		} else {
			cellInstance.setContent(RequestUtils.keywordReplace(
					cellConfig.getContent(), element));
			cellInstance.setContentVariableStr(cellConfig.getContent());

		}
		return cellInstance;
	}
}
