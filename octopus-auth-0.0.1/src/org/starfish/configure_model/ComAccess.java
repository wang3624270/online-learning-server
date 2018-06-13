package org.starfish.configure_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.starfish.configure_model.SFModel;
import org.starfish.pageside_access.Access;
import org.starfish.permi_dimension.PermissionDim;
import org.starfish.utils.StarfishUtils;

public class ComAccess extends SFModel {

	public ComAccess(SFModel parent) {
		super(parent);

	}

	public void mergeAccessList(List<Access> list) {
	}
}