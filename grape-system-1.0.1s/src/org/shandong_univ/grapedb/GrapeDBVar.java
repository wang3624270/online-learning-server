package org.shandong_univ.grapedb;

public class GrapeDBVar {
	public static ThreadLocal<String> threadCurrentGrapeDBKey = new ThreadLocal<String>();

	public static void deriveKey(String key) {
		threadCurrentGrapeDBKey.set(key);
	}

	public static void clearDeriveKey() {
		threadCurrentGrapeDBKey.set(null);
	}
}