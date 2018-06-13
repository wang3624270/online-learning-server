package cn.edu.sdu.uims.def;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class ValidationRuleSet extends RuleSetBase{
	public void addRuleInstances(Digester digester) {
		
		digester.addObjectCreate("form-validation/formset/form",
		"cn.edu.sdu.uims.def.UValidationForm",
		"className");
		digester.addSetProperties("form-validation/formset/form");
		digester.addSetNext("form-validation/formset/form",
		"addFormConfig",
		"cn.edu.sdu.uims.def.UValidationForm");

		digester.addObjectCreate("form-validation/formset/form/field",
		"cn.edu.sdu.uims.def.UValidationField",
        "className");
		digester.addSetProperties("form-validation/formset/form/field");
		digester.addSetNext("form-validation/formset/form/field",
        "addFieldConfig",
		"cn.edu.sdu.uims.def.UValidationField");
	}
}
