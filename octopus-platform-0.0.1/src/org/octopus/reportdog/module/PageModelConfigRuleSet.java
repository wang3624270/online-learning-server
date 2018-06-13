package org.octopus.reportdog.module;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.octopus.reportdog.state.BlockState;
import org.xml.sax.Attributes;

public class PageModelConfigRuleSet extends RuleSetBase {

	public void addRuleInstances(Digester digester) {
		// TODO Auto-generated method stub
		digester.addObjectCreate("page-models/page-model/paragraph",
				"org.octopus.reportdog.module.DocBrick_P", "className");
		digester.addSetProperties("page-models/page-model/paragraph");
		digester.addSetNext("page-models/page-model/paragraph", "addBrick_P",
				"org.octopus.reportdog.module.DocBrick_P");

		digester.addRule("page-models/page-model/paragraph/arg-property",
				new AddPageModelPropertyRule());

		digester.addObjectCreate("page-models/page-model/paragraph/tr",
				"org.octopus.reportdog.module.RowConfig", "className");
		digester.addSetProperties("page-models/page-model/paragraph/tr");
		digester.addSetNext("page-models/page-model/paragraph/tr",
				"addRowConfig", "org.octopus.reportdog.module.RowConfig");

		digester.addObjectCreate("page-models/page-model/paragraph/tr/td",
				"org.octopus.reportdog.module.CellConfig", "className");
		digester.addSetProperties("page-models/page-model/paragraph/tr/td");
		digester.addSetNext("page-models/page-model/paragraph/tr/td",
				"addCellConfig",
				"org.octopus.reportdog.module.CellConfig");

	}

	final class AddPageModelPropertyRule extends Rule {

		public AddPageModelPropertyRule() {
			super();
		}

		public void begin(String namespace, String name, Attributes attributes)
				throws Exception {
			DocBrick_P dsc = (DocBrick_P) digester.peek();
			String content = attributes.getValue("content");
			String blockName = attributes.getValue("name");
			String type = attributes.getValue("type");
			String templateName = attributes.getValue("templateName");
			BlockState blockState = new BlockState();
			blockState.setContent(content);
			blockState.setName(blockName);
			blockState.setType(type);
			blockState.setTemplateName(templateName);
			dsc.addBlockState(blockState);
		}

	}

}
