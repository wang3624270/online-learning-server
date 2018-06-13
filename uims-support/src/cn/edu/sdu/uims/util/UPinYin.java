package cn.edu.sdu.uims.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.uims.constant.UConstants;

public class UPinYin {

		Connection con = null;

		public UPinYin() {
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			String wordPath = System.getProperty("user.dir");
			wordPath += "\\" + UConstants.WordName;
			String dbUrl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="
					+ wordPath;

			try {
				con = DriverManager.getConnection(dbUrl, "", "");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public List<String> exePinYin(String str) {

			if (isCharacters(str)) {
				List<String> wordList = queryPinYin(str);
				if (wordList.size() != 0)
					return wordList;
				else
					return null;
			} else {
				addHanZi(str);
				return null;
			}
		}

		public List<String> queryPinYin(String args) {
			try {
				args.toLowerCase();
				Statement state = con
						.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
				String sql = "select * from words where pinyin = '" + args
						+ "' order by frequency desc";
				ResultSet rs = state.executeQuery(sql);
				List<String> wordList = new ArrayList<String>();
				if (rs != null) {
					while (rs.next()) {
						wordList.add(rs.getString("word"));
					}
					rs.close();
					return wordList;
				} else
					return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public boolean addHanZi(String str) { // ֱ�����뺺��ʱ�����ô˺���
			try {
				con.setAutoCommit(false);
				Statement state = con
						.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
				String fc = getBeginCharacter(str);
				if (fc == null) // �����ȡ�ַ������ĸ����������ַ��Ǻ��֣�����false��
					return false;
				else { // ��ѯ����ĺ����Ƿ��������ݿ��У�����ڣ�frequency��1������ڣ����뵽��ݿ�
					String sq = "select * from words where word = '" + str + "'";
					ResultSet rs = state.executeQuery(sq);
					if (rs.next()) {
						rs.close();
						String s = "update words set frequency = frequency + 1 where word = '"
								+ str + "'";
						state.executeUpdate(s);

					} else {
						rs.close();
						String sql = "insert into words(pinyin,word,frequency) values ('"
								+ fc + "','" + str + "',1)";
						state.executeUpdate(sql);
					}
				}
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
					con.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
					return false;
				}
				return false;
			}
			return true;
		}

		public boolean updateFrequency(String str) { // ��������ĸ��ѡ�ж�Ӧ��ĳ����飬�Դ˴����frequency+1
			// ʱ�����ô˺���
			try {
				con.setAutoCommit(false);
				Statement state = con
						.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
				String sq = "select * from words where word = '" + str + "'";
				ResultSet rs = state.executeQuery(sq);
				rs.close();
				String s = "update words set frequency = frequency + 1 where word = '"
						+ str + "'";
				state.executeUpdate(s);
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
					con.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();

					return false;
				}
				return false;
			}
			return true;
		}

		public static boolean isCharacters(String str) { // �ж��ַ��Ƿ�����ĸ
			int len = str.length();
			char c;
			for (int i = 0; i < len; i++) {
				c = str.charAt(i);
				if (!(c <= 'Z' && c >= 'A') && !(c <= 'z' && c >= 'a')) {
					return false;
				}
			}
			return true;
		}

		public static int compare(String str1, String str2) {
			int result = 0;
			String m_s1 = null;
			String m_s2 = null;
			try {
				m_s1 = new String(str1.getBytes(_FromEncode_), _ToEncode_);
				m_s2 = new String(str2.getBytes(_FromEncode_), _ToEncode_);
			} catch (Exception e) {
				return str1.compareTo(str2);
			}
			result = chineseCompareTo(m_s1, m_s2);
			return result;
		}

		public static int getCharCode(String s) {
			if (s == null && s.equals(""))
				return -1;
			byte b[] = s.getBytes();
			int value = 0;
			for (int i = 0; i < b.length && i <= 2; i++)
				value = value * 100 + b[i];

			return value;
		}

		public static int chineseCompareTo(String s1, String s2) {
			int len1 = s1.length();
			int len2 = s2.length();
			int n = Math.min(len1, len2);
			for (int i = 0; i < n; i++) {
				int s1_code = getCharCode(s1.charAt(i) + "");
				int s2_code = getCharCode(s2.charAt(i) + "");
				if (s1_code * s2_code < 0)
					return Math.min(s1_code, s2_code);
				if (s1_code != s2_code)
					return s1_code - s2_code;
			}

			return len1 - len2;
		}

		public static String getBeginCharacter(String res) { // ȡ��������ĸ
			String a = res;
			String result = "";
			for (int i = 0; i < a.length(); i++) {
				String current = a.substring(i, i + 1);
				if (compare(current, "\u554A") < 0) {
					return null;
				} else if (compare(current, "\u554A") >= 0
						&& compare(current, "\u5EA7") <= 0)
					if (compare(current, "\u531D") >= 0)
						result = result + "z";
					else if (compare(current, "\u538B") >= 0)
						result = result + "y";
					else if (compare(current, "\u6614") >= 0)
						result = result + "x";
					else if (compare(current, "\u6316") >= 0)
						result = result + "w";
					else if (compare(current, "\u584C") >= 0)
						result = result + "t";
					else if (compare(current, "\u6492") >= 0)
						result = result + "s";
					else if (compare(current, "\u7136") >= 0)
						result = result + "r";
					else if (compare(current, "\u671F") >= 0)
						result = result + "q";
					else if (compare(current, "\u556A") >= 0)
						result = result + "p";
					else if (compare(current, "\u54E6") >= 0)
						result = result + "o";
					else if (compare(current, "\u62FF") >= 0)
						result = result + "n";
					else if (compare(current, "\u5988") >= 0)
						result = result + "m";
					else if (compare(current, "\u5783") >= 0)
						result = result + "l";
					else if (compare(current, "\u5580") >= 0)
						result = result + "k";
					else if (compare(current, "\u51FB") > 0)
						result = result + "j";
					else if (compare(current, "\u54C8") >= 0)
						result = result + "h";
					else if (compare(current, "\u5676") >= 0)
						result = result + "g";
					else if (compare(current, "\u53D1") >= 0)
						result = result + "f";
					else if (compare(current, "\u86FE") >= 0)
						result = result + "e";
					else if (compare(current, "\u642D") >= 0)
						result = result + "d";
					else if (compare(current, "\u64E6") >= 0)
						result = result + "c";
					else if (compare(current, "\u82AD") >= 0)
						result = result + "b";
					else if (compare(current, "\u554A") >= 0)
						result = result + "a";
			}

			return result;
		}

		public static boolean isChinese(String str) {
			String a = str;
			int i;
			String current = null;
			for (i = 0; i < a.length(); i++) {
				current = a.substring(i, i + 1);
				if (compare(current, "\u554A") >= 0
						&& compare(current, "\u5EA7") <= 0)
					return true;

			}
			return false;
		}

		private static String _FromEncode_ = "GBK";

		private static String _ToEncode_ = "GBK";

		// public static void main(String[] args) {
		// PinYin spell = new PinYin();
		// System.out.println(spell.exePinYin("z"));
		//		
		//		
		// }
}
