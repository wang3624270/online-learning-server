package org.sdu.file_util;

import java.util.Hashtable;
import java.util.Timer;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

/**
 * 
 * @author JING Ming
 * 
 */
public class ConnectionFactory {
	
	static Hashtable<String, FileSourceImpl> connectionPools = null;
	static {
		connectionPools = new Hashtable<String, FileSourceImpl>(2, 0.75F);
	}
	static Hashtable<String, Timer> checkConnTimers = null;
	static {
		checkConnTimers = new Hashtable<String, Timer>(2, 0.75F);
	}

	public static FileSource lookup(String fileSource)
			throws NameNotFoundException {
		Object fs = null;
		fs = connectionPools.get(fileSource);
		if (fs == null || !(fs instanceof FileSource))
			throw new NameNotFoundException(fileSource);
		return (FileSource) fs;
	}

	public static FileSource bind(String name, ConnectionParam param)
			throws NameAlreadyBoundException {
		FileSourceImpl source = null;
		try {
			lookup(name);
			throw new NameAlreadyBoundException(name);
		} catch (NameNotFoundException e) {
			source = new FileSourceImpl(param);
			source.initConnection(name);
			connectionPools.put(name, source);
			Timer checkConnTimer = checkConnTimers.get(name);
			if (checkConnTimer == null) {
				checkConnTimer = new Timer();
				checkConnTimers.put(name, checkConnTimer);
			}
			long checkPeriod = param.getLong(ConnectionParam.IDLE_TEST_PERIOD);
			checkConnTimer.schedule(source, checkPeriod, checkPeriod);
		}
		return source;
	}

	public static FileSource rebind(String name, ConnectionParam param)
			throws NameAlreadyBoundException {
		try {
			unbind(name);
		} catch (Exception e) {
		}
		return bind(name, param);
	}

	public static void unbind(String name) throws NameNotFoundException {
		FileSource fileSource = lookup(name);
		if (fileSource instanceof FileSourceImpl) {
			FileSourceImpl fsi = (FileSourceImpl) fileSource;
			try {
				fsi.stop();
				fsi.close();
			} catch (Exception e) {
			} finally {
				fsi = null;
			}
		}
		connectionPools.remove(name);
		Timer checkConnTimer = checkConnTimers.get(name);
		if (checkConnTimer != null) {
			checkConnTimer.cancel();
			checkConnTimers.remove(name);
		}
	}

}
