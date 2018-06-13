package org.octopus.dim_manage.dim_org;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.octopus.dim_manage.dim_org.client_data_model.OrgInfo;
import org.octopus.dim_manage.dim_org.client_data_model.OrgListRow;
import org.octopus.dim_manage.dim_org.client_form.SubmitOrgForm;
import org.octopus.dim_manage.dim_org.jpa_model.DimOrg;
import org.octopus.web_ui.easyui.data_struct.EUTableData;
import org.octopus.web_ui.easyui.data_struct.EUTreeNodeData;
import org.sdu.rmi.RestConstants;
import org.sdu.rmi.ReturnToClientStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.starfish.DimensionHandlerI;

@Controller
@Scope("application")
public class SysOrgManageBean {

	private OrgDimension orgDimension;

	@Autowired
	@Qualifier("orgDimension")
	public void setOrgDimension(DimensionHandlerI dimHandler) {
		this.orgDimension = (OrgDimension) dimHandler;
	}

	public SysOrgManageBean() {

	}

	@RequestMapping(value = "/dimManage/sysOrgManage", method = RequestMethod.GET)
	public String sysOrgManage(HttpServletRequest request) {
		return "pages/org/octopus/dim_manage/dim_org/sysOrgManage";
	}

	@RequestMapping(value = "/dimManage/sysOrgList", method = RequestMethod.GET)
	public @ResponseBody EUTableData sysOrgList(HttpServletRequest request) {

		List<DimOrg> t1List = orgDimension.getAllItemsEntityInDimension();
		EUTableData t1 = new EUTableData();
		t1.setTotal(t1List.size());
		for (DimOrg dt1 : t1List) {
			OrgListRow t1row = new OrgListRow();
			t1row.setDimOrg(dt1);

			t1.addData(t1row);
		}
		return t1;
	}

	@RequestMapping(value = "/dimManage/newOrg", method = RequestMethod.POST)
	public @ResponseBody ReturnToClientStruct newOrg(HttpServletRequest request, @RequestBody SubmitOrgForm form) {
		ReturnToClientStruct s = new ReturnToClientStruct();

		List<String> li = form.validForMessages();

		if (li.size() > 0) {
			s.setReCode(RestConstants.ret_error);
			s.setErrorMessageList(li);
			return s;
		}
		DimOrg org = new DimOrg();
		org.setName(form.getOrgName());
		org.setCode(form.getOrgCode());
		if (form.getDlgBelongOrg() != null && form.getDlgBelongOrg().length() > 0) {
			DimOrg org1 = orgDimension
					.findOrgByCode(form.getDlgBelongOrg().substring(form.getDlgBelongOrg().indexOf("  ") + 2));
			if (org1 != null)
				org.setBelongOrgLogicId(org1.getLogicId());
		}
		org.setDescription(form.getOrgDes());
		org.setAddress(form.getOrgAddress());
		org.setLogicId(UUID.randomUUID().toString());
		orgDimension.createDimOrg(org);
		return s;
	}

	@RequestMapping(value = "/dimManage/editOrg", method = RequestMethod.POST)
	public @ResponseBody ReturnToClientStruct editOrg(HttpServletRequest request, @RequestBody SubmitOrgForm form) {

		ReturnToClientStruct s = new ReturnToClientStruct();

		List<String> li = form.validForMessages();

		if (li.size() > 0) {
			s.setReCode(RestConstants.ret_error);
			s.setErrorMessageList(li);
			return s;
		}
		String logicId = form.getLogicId();
		DimOrg org = orgDimension.getOrg(logicId);

		org.setName(form.getOrgName());
		org.setCode(form.getOrgCode());
		if (form.getDlgBelongOrg() != null && form.getDlgBelongOrg().length() > 0) {
			DimOrg org1 = orgDimension
					.findOrgByCode(form.getDlgBelongOrg().substring(form.getDlgBelongOrg().indexOf("  ") + 2));
			if (org1 != null)
				org.setBelongOrgLogicId(org1.getLogicId());
		}
		org.setDescription(form.getOrgDes());
		org.setAddress(form.getOrgAddress());
		org.setLogicId(UUID.randomUUID().toString());
		orgDimension.createDimOrg(org);
		return s;
	}

	@RequestMapping(value = "/dimManage/delOrg", method = RequestMethod.GET)
	public @ResponseBody ReturnToClientStruct delOrg(HttpServletRequest request) {
		ReturnToClientStruct s = new ReturnToClientStruct();
		try {

			String o = (String) request.getParameter("delOrgLogicIds");
			if (o == null)
				return s;
			String[] arrs = o.split(",");
			orgDimension.deleteOrg(arrs);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			s.setReCode(RestConstants.ret_error);
			return s;
		}
	}

	@RequestMapping(value = "/dimManage/getOrg", method = RequestMethod.GET)
	public @ResponseBody OrgInfo getOrg(HttpServletRequest request) {
		OrgInfo orgInfo = new OrgInfo();
		try {
			String o = (String) request.getParameter("delOrgLogicIds");
			String[] arrs = o.split(",");
			DimOrg dimOrg = orgDimension.findOrgByLogicId(arrs[0]);
			orgInfo.setDimOrg(dimOrg);
			DimOrg parentOrg = orgDimension.findOrgByLogicId(dimOrg.getBelongOrgLogicId());
			DimOrg org1 = null;
			if (parentOrg != null)
				orgInfo.setBelongOrg(parentOrg.getName() + "  " + parentOrg.getCode());
		} catch (Exception e) {
			orgInfo.setReCode(RestConstants.ret_error);
			return orgInfo;
		}
		return orgInfo;
	}

	@RequestMapping(value = "/dimManage/orgTree", method = RequestMethod.GET)
	public @ResponseBody List<EUTreeNodeData> orgTree(HttpServletRequest request) {

		EUTreeNodeData treeData = new EUTreeNodeData();
		List l=new ArrayList<EUTreeNodeData>();
		try {
			String o = (String) request.getParameter("currentOrgLogicIds");
			String[] arrs = o.split(",");
			DimOrg dimOrg = orgDimension.findOrgByLogicId(arrs[0]);
			findOrg(dimOrg, treeData);

		} catch (Exception e) {

			return l;
		}
		l.add(treeData);
		return l;
	}

	private void findOrg(DimOrg dimOrg, EUTreeNodeData treeData) {
		treeData.setName(dimOrg.getName());
		treeData.setText(dimOrg.getName());
		treeData.setId(dimOrg.getLogicId());
		List<DimOrg> orgList = orgDimension.getChildrenOrg(dimOrg.getLogicId());
		if (orgList != null) {
			treeData.setChildren(new ArrayList<EUTreeNodeData>());
			for (DimOrg org : orgList) {
				EUTreeNodeData treeData1 = new EUTreeNodeData();
				findOrg(org, treeData1);
				treeData.getChildren().add(treeData1);
			}
		}
	}
}