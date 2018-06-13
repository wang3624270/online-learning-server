package cn.edu.sdu.uims.component.groupcomponent;

import cn.edu.sdu.uims.component.USplitPane;

public class UGropuComponentDiv extends UGroupComponent {
	protected USplitPane splitPane = null;
	protected USplitPane splitPane1 = null;

	public void addComponentAfter() {
		if (splitPane != null) {
			splitPane.ResetComponentsMinSize();
			splitPane.resetToPreferredSizes();
		}
		if (splitPane1 != null) {
			splitPane1.ResetComponentsMinSize();
			splitPane1.resetToPreferredSizes();
		}
	}
}
