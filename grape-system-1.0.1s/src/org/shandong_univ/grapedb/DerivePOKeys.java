package org.shandong_univ.grapedb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DerivePOKeys {

	Queue<String> seqs;

	public DerivePOKeys() {
		seqs = new LinkedList<String>();

	}

	public void addKey(String key) {
		seqs.add(key);
	}

	public String removeKey() {
		return seqs.remove();
	}
}