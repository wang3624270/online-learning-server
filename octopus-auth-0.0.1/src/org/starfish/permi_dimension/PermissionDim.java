package org.starfish.permi_dimension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PermissionDim {
	public List<String> getPermiItems() {
		return permiItems;
	}

	public void setPermiItems(List<String> permiItems) {
		this.permiItems = permiItems;
	}

	private String dimensionName;
	private HashMap<String, PermissionDim> childDims;
	private List<String> permiItems;

	public PermissionDim() {
		// permiMap = new HashMap<String, String>();
		childDims = new HashMap<String, PermissionDim>();
		permiItems = new ArrayList<String>();
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public void addDimensionItem(String dimItem) {
		permiItems.add(dimItem);
	}

	public void addChildDimension(PermissionDim childDim) {
		if (childDim != null)
			childDims.put(childDim.getDimensionName(), childDim);
	}

	public PermissionDim getPermissionDim(String dimensionName) {
		// Temporary suppose that root permission dimension only have one child
		// dimension layer.
		HashMap<String, PermissionDim> perMap = new HashMap<String, PermissionDim>();
		Set set = childDims.keySet();
		Iterator it = set.iterator();
		PermissionDim dim;
		while (it.hasNext()) {
			dim = childDims.get(it.next());
			if (dim.getDimensionName().equals(dimensionName)) {
				return dim;
			}
		}
		return null;
	}

}