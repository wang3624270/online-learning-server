package org.sdu.file_util;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

/**
 * 
 * @author JING Ming
 * 
 */
public class FileSourceImpl extends TimerTask implements FileSource {

	private ConnectionParam param = null;
	private List<_FileConnection> conns = new ArrayList<_FileConnection>();

	private String connName;

	// private Hashtable<String, List> connsMap = new Hashtable<String, List>();
 
	public FileSourceImpl(ConnectionParam param) {
		this.param = param;
	}

	// public void initConnection() {
	// if (!param.isLocal()) {
	// for (int i = conns.size(); i < param
	// .getInt(ConnectionParam.MIN_CONNECTION); i++) {
	// FileConnection fileConn = FtpManager.getConnection(param);
	// _FileConnection _conn = new _FileConnection(fileConn, false);
	// synchronized (conns) {
	// conns.add(_conn);
	// }
	// }
	// }
	// }

	/**
	 * 根据connector名称连接FTP
	 * 
	 * @author CJY
	 * @param connector
	 */
	public void initConnection(String connName) {
		this.connName = connName;
		if (connName.equals("default")||connName.equals("space")) {
			if (!param.getString(ConnectionParam.URL).equals("local")) {
				for (int i = conns.size(); i < param
						.getInt(ConnectionParam.MIN_CONNECTION); i++) {
				}
			}
		} else {
			if (!param.getString(connName + "_url").equals("local")) {
				for (int i = conns.size(); i < param
						.getInt(ConnectionParam.MIN_CONNECTION); i++) {
				}
			}
		}
	}

	public void stop() throws IOException {
		Iterator<_FileConnection> iter = conns.iterator();
		while (iter.hasNext()) {
			_FileConnection _conn = iter.next();
			_conn.close();
		}
	}

	public void close() {
		Iterator<_FileConnection> iter = conns.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
	}

	 public FileConnection getConnection() throws IOException {
		if (param.isLocal()) {
			return getLocalFileConnection();
		} else {
			return getFtpFileConnection();
		}
	}

	public FileConnection getConnection(String connName) throws IOException {
		if (connName.equals("default")) {
			if (param.getString(ConnectionParam.URL).equals("local")) {
				return getLocalFileConnection();
			} else {
				return getFtpFileConnection();
			}
		} else if(connName.equals("space")){
			return new LocalFileConnection(param,"space");
		} else if(connName.equals("http")){
			return new LocalFileConnection(param,"news");
		}else {
			if (param.getString(connName + "_url").equals("local")) {
				return getLocalFileConnection();
			} else {
				return getFtpFileConnection(connName);
			}
		}
	}

	protected FileConnection getLocalFileConnection() {
		return new LocalFileConnection(param);
	}

	protected FileConnection getFtpFileConnection() throws IOException {

		FileConnection conn = getFreeConnection(0);
		if (conn == null) {
			if (getConnectionCount(ConnectionParam.DEFAULT_CONNECTION) >= param
					.getInt(ConnectionParam.MAX_CONNECTION)) {
				conn = getFreeConnection(param
						.getLong(ConnectionParam.WAIT_TIME));
			} else {
			}
		}
		return conn;
	}

	protected FileConnection getFtpFileConnection(String connName)
			throws IOException {
		FileConnection conn = getFreeConnection(0);
		if (conn == null) {
			if (getConnectionCount(connName) >= param
					.getInt(ConnectionParam.MAX_CONNECTION)) {
				conn = getFreeConnection(param
						.getLong(ConnectionParam.WAIT_TIME));
			} else {
			}
		}
		return conn;
	}

	protected synchronized FileConnection getFreeConnection(long nTimeout)
			throws IOException {
		FileConnection conn = null;
		Iterator<_FileConnection> iter = conns.iterator();
		while (iter.hasNext()) {
			_FileConnection _conn = (_FileConnection) iter.next();
			if (!_conn.isInUse()&&_conn.isOpen()) {
				conn = _conn.getConnection();
				_conn.setInUse(true);
				break;
			}
		}
		if (conn == null && nTimeout > 0) {
			try {
				Thread.sleep(nTimeout);
			} catch (Exception e) {
			}
			conn = getFreeConnection(0);
			if (conn == null)
				throw new IOException("No available connection!");
		}
		return conn;
	}

	private int getConnectionCount(String connName) {
		return conns.size();
	}

	public synchronized void run() {
//		printConnectInfo();
		detectTimeout();
		detectInvalidation();
		initConnection(connName);
	}

	private void printConnectInfo() {
		Iterator<_FileConnection> iter = conns.iterator();
		System.out.println("当前连接数为: "+conns.size());
		int i = 1;
		while (iter.hasNext()) {
			_FileConnection _conn = (_FileConnection) iter.next();
			System.out.println("第 " + i + " 个连接信息： "+String.valueOf(Calendar.getInstance().getTimeInMillis()-_conn.getLastAccessTime()+" "+_conn.isInUse())+" "+_conn.isOpen());
//			System.out.println("最后使用时间：" + _conn.getLastAccessTime());
//			System.out.println("是否在使用：" + _conn.isInUse());
//			System.out.println("是否已经打开:" + _conn.isOpen());
			i++;
		}
	}

	/**
	 * detect the timeout connection in use, then set the connection unused to
	 * collection the connection
	 */
	private void detectTimeout() {
		Iterator<_FileConnection> iter = conns.iterator();
		while (iter.hasNext()) {
			_FileConnection _conn = iter.next();
			if (_conn.isInUse()) {
				long lt = Calendar.getInstance().getTimeInMillis()
						- _conn.getLastAccessTime();
				if (lt >= param.getLong(ConnectionParam.TIMEOUT_VALUE)) {
					try {
						_conn.getConnection().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * detect the invalid connection, then remove the connection from the
	 * connections list
	 */
	private void detectInvalidation() {
		Iterator<_FileConnection> iter = conns.iterator();
		while (iter.hasNext()) {
			_FileConnection _conn = (_FileConnection) iter.next();
			if (!_conn.isInUse() && !_conn.isOpen()) {
				try {
					_conn.getConnection().close();
					_conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				iter.remove();
			}
		}
	}
}
