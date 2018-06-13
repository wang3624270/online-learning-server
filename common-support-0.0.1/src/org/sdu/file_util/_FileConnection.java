package org.sdu.file_util;

import java.io.IOException;
import java.lang.reflect.*;

/**
 * 
 * @author JING Ming
 * 
 */
public class _FileConnection implements InvocationHandler {

	private final static String CLOSE_METHOD_NAME = "close";
	private final static String IS_OPEN_METHOD_NAME = "isOpen";

	private FileConnection conn = null;
	// 数据库的忙状态
	private boolean inUse = false;
	// 用户最后一次访问该连接方法的时间
	private long lastAccessTime = System.currentTimeMillis();

	_FileConnection(FileConnection conn, boolean inUse) {
		this.conn = conn;
		this.inUse = inUse;
	}

	public FileConnection getConnection() {
		FileConnection fileConnection = (FileConnection) Proxy
				.newProxyInstance(conn.getClass().getClassLoader(), conn
						.getClass().getInterfaces(), this);
		return fileConnection;
	}

	public boolean isOpen() {
		return conn.isOpen();
	}

	void close() throws IOException {
		conn.close();
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object obj = null;
		// 判断是否调用了close的方法，如果调用close方法则把连接置为无用状态
		if (CLOSE_METHOD_NAME.equals(method.getName())) {
			setInUse(false);
		} else if (!isInUse()) {
			if (IS_OPEN_METHOD_NAME.equals(method.getName())) {
				return false;
			}
			throw new IOException(" the connection " + conn.toString()
					+ " has closed");
		} else
			obj = method.invoke(conn, args);
		// 设置最后一次访问时间，以便及时清除超时的连接
		lastAccessTime = System.currentTimeMillis();
		return obj;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}
}
