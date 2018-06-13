package org.octopus.reportdog.module;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.xml.sax.Attributes;

public class PanelModelConfigRuleSet extends RuleSetBase {

	public void addRuleInstances(Digester digester) {
		// TODO Auto-generated method stub
		digester.addSetProperties("panels/panel");
		digester.addObjectCreate("panels/panel/page",
				"org.octopus.reportdog.module.PageConfig",
				"className");
		digester.addSetProperties("panels/panel/page");
		digester.addSetNext("panels/panel/page", "addPageConfig",
				"org.octopus.reportdog.module.PageConfig");
		digester.addRule("panels/panel/page/arg-property",
				new AddPanelModelPropertyRule());
		

	}
	final class AddPanelModelPropertyRule extends Rule {

		public AddPanelModelPropertyRule() {
			super();
		}

		public void begin(String namespace, String name, Attributes attributes)
				throws Exception {
			PageConfig dsc = (PageConfig) digester.peek();
			dsc.addProperty(attributes.getValue("property"), attributes
					.getValue("value"));
		}

	}

}
