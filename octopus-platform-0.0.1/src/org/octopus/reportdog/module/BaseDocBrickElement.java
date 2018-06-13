package org.octopus.reportdog.module;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

public class BaseDocBrickElement {
	// Add this class in 2015.1.28.
	// The document description system of report dog is confused now. We hope to
	// use some universal and standardized architecture to describe document
	// structure. And Html format is a good choice. The current description
	// system should changed and update gradually. But report dog has been
	// used for several years since 2009, and most document templates are build
	// based on old description system. So, from now on, new features(new
	// tag, new properties...) for description system are implemented in html
	// style. In other words, all new tags added to description system are html
	// style tag. And we hope that these tags are compatible with html document
	// as much as possible. Some details of tag(such as align left,
	// align right, border width...) should be describe with CSS-like format.
	// For example, tags such as <sometag value="${xxx}"
	// style="text-align:right" /> will be more and more frequently in the
	// future.
	private Element currentElement;

	public void parse() {
		parseProperites();
	}

	public Element getCurrentElement() {
		return currentElement;
	}

	public void setCurrentElement(Element currentElement) {
		this.currentElement = currentElement;
	}

	public void parseProperites() {
		int i;
		PropertyDescriptor pd;
		Method method;
		List propList = this.getCurrentElement().attributes();
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						this.getClass());
				method = pd.getWriteMethod();
				// method.invoke(pageModelModuleConfig,
				// ((DefaultAttribute) propList.get(i)).getValue());
				transData(method, this, pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void transData(Method method, Object object, String type,
			String content) {
		try {

			if (type.equals("float"))
				method.invoke(object, Float.parseFloat(content));
			else if (type.equals("int") || type.equals("java.lang.Integer"))
				method.invoke(object, Integer.parseInt(content));
			else if (type.equals("boolean"))
				method.invoke(object, Boolean.parseBoolean(content));
			else
				method.invoke(object, content);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseDocLatticeElement transToBasicLatticeElement() {
		return new BaseDocLatticeElement();
	}
}