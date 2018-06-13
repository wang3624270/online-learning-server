package org.octopus.reportdog.module;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.octopus.reportdog.state.MidDataMappingState;
import org.xml.sax.Attributes;

public class SourceConfigRuleSet extends RuleSetBase {

	public void addRuleInstances(Digester digester) {
		// TODO Auto-generated method stub
		digester
				.addObjectCreate(
						"source-configs/source-mappings/source-mapping",
						"org.octopus.reportdog.module.SourceMappingConfig",
						"className");
		digester
				.addSetProperties("source-configs/source-mappings/source-mapping");
		digester
				.addSetNext("source-configs/source-mappings/source-mapping",
						"addSourceMappingConfig",
						"org.octopus.reportdog.module.SourceMappingConfig");
		digester
				.addRule(
						"source-configs/source-mappings/source-mapping/source-property",
						new AddSourcePropertyRule());

		digester.addObjectCreate("source-configs/data-configs/data-config",
				"org.octopus.reportdog.module.SourceDataConfig",
				"className");
		digester.addSetProperties("source-configs/data-configs/data-config");
		digester.addSetNext("source-configs/data-configs/data-config",
				"addSourceDataConfig",
				"org.octopus.reportdog.module.SourceDataConfig");
		digester.addRule(
				"source-configs/data-configs/data-config/arg-property",
				new AddSourceDataPropertyRule());

	}

	final class AddSourcePropertyRule extends Rule {

		public AddSourcePropertyRule() {
			super();
		}

		public void begin(String namespace, String name, Attributes attributes)
				throws Exception {
			SourceMappingConfig dsc = (SourceMappingConfig) digester.peek();
			String property = attributes.getValue("property");
			String value = attributes.getValue("value");
			String handler = attributes.getValue("handler");
			String id = attributes.getValue("id");
			MidDataMappingState midDataMappingState = new MidDataMappingState();
			midDataMappingState.setProperty(property);
			midDataMappingState.setValue(value);
			midDataMappingState.setId(id);
			midDataMappingState.setHandler(handler);
			dsc.addProperty(property, midDataMappingState);
		}
	}

	final class AddSourceDataPropertyRule extends Rule {

		public AddSourceDataPropertyRule() {
			super();
		}

		public void begin(String namespace, String name, Attributes attributes)
				throws Exception {
			SourceDataConfig dsc = (SourceDataConfig) digester.peek();
			dsc.addProperty(attributes.getValue("property"), attributes
					.getValue("value"));
		}

	}

}
