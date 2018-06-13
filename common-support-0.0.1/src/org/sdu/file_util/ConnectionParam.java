package org.sdu.file_util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * to parse the configuration file "fs.properties"
 * 
 * @author JING Ming
 * 
 */
public class ConnectionParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static HashMap<String, Properties> cfgMap = new HashMap<String, Properties>();
	private static Properties currentCfg = new Properties();

	public static final String DEFAULT_CONNECTION = "default";
	public static final String URL = "url";// 地址后缀
	public static final String PORT = "port";// 端口后缀
	public static final String USERNAME = "username";// 用户名后缀
	public static final String PASSWORD = "password";// 密码后缀
	public static final String LOCALPATH = "localpath";// 本地地址
	public static final String SPACEPATH = "spacepath";// 本地地址
	public static final String HTTPPATH = "httppath";	//新闻地址
	public static final String ROOTPATH = "rootpath";// FTP地址
	public static final String ENCODING = "encoding";
	public static final String MIN_CONNECTION = "min_connection";
	public static final String MAX_CONNECTION = "max_connection";
	public static final String TIMEOUT_VALUE = "timeout_value";
	// the waiting time before the next try
	public static final String WAIT_TIME = "wait_time";
	// use local file cache or not while reading or writing file
	public static final String CACHE = "cache";
	// the local directory to write the temporary file
	public static final String CACHE_PATH = "cache_path";

	public static final String IDLE_TEST_PERIOD = "idle_test_period";

	public final String LOCAL = "local";

	private final int DEFAULT_PORT = 21;
	private final String DEFAULT_ROOTPATH = "/";
	private final int DEFAULT_MIN_CONNECTION = 10;
	private final int DEFAULT_MAX_CONNECTION = 100;
	private final long DEFAULT_TIMEOUT_VALUE = 60000 * 30;// 30 minutes
	private final long DEFAULT_WAIT_TIME = 30000;// 30 seconds
	private final boolean DEFAULT_CACHE = false;
	private final String DEFAULT_CACHE_PATH = "C:/";
	private final long DEFAULT_IDLE_TEST_PERIOD = 60000;// 1 minutes
	private final String DEFAULT_ENCODING = "gbk";

	public ConnectionParam(String configFileLocation) throws IOException {
		Properties cfg = null;
		if (cfgMap.get(configFileLocation) == null) {
			cfgMap.put(configFileLocation, new Properties());
			cfg = cfgMap.get(configFileLocation);
			initCfg(cfg);
			try {
				cfg.load(ConnectionParam.class
						.getResourceAsStream(configFileLocation));
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			}

		} else
			cfg = cfgMap.get(configFileLocation);
		currentCfg = cfg;

	}

	private void initCfg(Properties cfg) {
		cfg.setProperty(PORT, String.valueOf(DEFAULT_PORT));
		cfg.setProperty(ROOTPATH, DEFAULT_ROOTPATH);
		cfg.setProperty(SPACEPATH, DEFAULT_ROOTPATH);
		cfg.setProperty(MIN_CONNECTION, String.valueOf(DEFAULT_MIN_CONNECTION));
		cfg.setProperty(MAX_CONNECTION, String.valueOf(DEFAULT_MAX_CONNECTION));
		cfg.setProperty(TIMEOUT_VALUE, String.valueOf(DEFAULT_TIMEOUT_VALUE));
		cfg.setProperty(WAIT_TIME, String.valueOf(DEFAULT_WAIT_TIME));
		cfg.setProperty(CACHE, String.valueOf(DEFAULT_CACHE));
		cfg.setProperty(CACHE_PATH, DEFAULT_CACHE_PATH);
		cfg.setProperty(IDLE_TEST_PERIOD,
				String.valueOf(DEFAULT_IDLE_TEST_PERIOD));
		cfg.setProperty(ENCODING, DEFAULT_ENCODING);
	}

	public String getString(String key) {
		String str = getCurrentCfg().getProperty(key);
		if (str == null) {
			str = "";
		} else {
			str = str.trim();
		}
		return str;
	}

	private Properties getCurrentCfg() {
		if (currentCfg == null) {
			currentCfg = new Properties();
			initCfg(currentCfg);

		}
		return currentCfg;
	}

	public int getInt(String key) {
		String value = getCurrentCfg().getProperty(key);
		return Integer.parseInt(value);
	}

	public long getLong(String key) {
		String value = getCurrentCfg().getProperty(key);
		return Long.parseLong(value);
	}

	public boolean getBoolean(String key) {
		String value = getCurrentCfg().getProperty(key);
		return Boolean.parseBoolean(value);
	}

	public boolean isLocal() {
		return LOCAL.equalsIgnoreCase(getCurrentCfg().getProperty(URL));
	}

}
