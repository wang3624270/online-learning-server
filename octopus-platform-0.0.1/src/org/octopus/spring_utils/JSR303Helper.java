package org.octopus.spring_utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

public class JSR303Helper<T> {

	public JSR303Helper() {
	}

	public List<ConstraintViolation> validForm(Object ob) {

		Set<ConstraintViolation<T>> constraintViolations = ((ValidatorFactory) SpringContextHelper
				.getBean("validator")).getValidator().validate((T) ob);
		Iterator it = constraintViolations.iterator();
		List<ConstraintViolation> list = new ArrayList<ConstraintViolation>();
		while (it.hasNext()) {
			ConstraintViolation ob1 = (ConstraintViolation) it.next();
			list.add(ob1);
		}

		return list;
	}
}