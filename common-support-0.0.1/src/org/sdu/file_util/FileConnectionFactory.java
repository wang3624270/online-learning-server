package org.sdu.file_util;

import java.io.IOException;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

/**
 * 
 * @author JING Ming
 *
 */
public class FileConnectionFactory {
	private static String CONFIG_FILE_LOCATION = "/fs.cfg.properties";

	// private static final Hashtable<String,FileConnection> threadLocal = new
	// Hashtable<String,FileConnection>();

	public static FileConnection currentConnection(String connName) {
		FileConnection conn = null;
		if (conn == null || !conn.isOpen()) {
			FileSource fs = null;
			try {
				fs = ConnectionFactory.lookup(connName);
			} catch (NameNotFoundException e) {
			}
			try {
				if (fs == null) {
					fs = ConnectionFactory.bind(connName, new ConnectionParam(
							CONFIG_FILE_LOCATION));
				}
				conn = fs.getConnection(connName);
 
				// threadLocal.put(connName, conn);
			} catch (NameAlreadyBoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	// public static void closeConnection(String connector) {
	// FileConnection conn = threadLocal.get(connector);
	// threadLocal.put(connector, null);
	// if (conn != null) {
	// try {
	// conn.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	private FileConnectionFactory() {

	}

}
