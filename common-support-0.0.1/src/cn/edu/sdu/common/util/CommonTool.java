package cn.edu.sdu.common.util;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import com.google.gson.Gson;



public class CommonTool {
	// private static final int String = 0;

	public static String getCheckCode() {
		String str = "";
		for (int i = 0; i < 4; i++) {
			str += (int) (Math.random() * 10);
		}
		return str;
	}
	
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
	
	  public static String filterEmoji(String source) {
	      //我觉得昵称里放emoji很蠢。我也不打算存储他。不过如果 有人在视频评论 发布活动里用emoji 那他就真太可怕了
	        StringBuilder buf = null;
	        int len = source.length();
	        for (int i = 0; i < len; i++) {
	            char codePoint = source.charAt(i);
	            if (isEmojiCharacter(codePoint)) {
	                if (buf == null) {
	                    buf = new StringBuilder(source.length());
	                }
	                buf.append(codePoint);
	            }
	        }
	        if (buf == null) {
	            return source;
	        } else {
	            if (buf.length() == len) {
	                buf = null;
	                return source;
	            } else {
	                return buf.toString();
	            }
	        }
	    }
	
   public static Object getJsonByRequest(HttpServletRequest request,Class<?>  c) {
	BufferedReader br;
	String str, wholeStr = "";
	try {
		br = request.getReader();
		while ((str = br.readLine()) != null) {
			wholeStr += str;
		 }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return new Gson().fromJson(wholeStr, c);
   }

	public static Map getNodeMap(Object data, String errMsg) {
		Map ret = new HashMap();
		if (errMsg != null) {
			ret.put("re", -1);
			ret.put("data", errMsg);
		} else {
			ret.put("re", 1);
			ret.put("data", data);
		}
		return ret;
	}

	public static Map getNodeMapOk(String msg) {
		Map ret = new HashMap();
		ret.put("re", 1);
		ret.put("data", msg);
		return ret;
	}
	public static Map getNodeMapError(String msg) {
		Map ret = new HashMap();
		ret.put("re", -1);
		ret.put("data", msg);
		return ret;
	}
	
	public static Map getNodeMapReLogin(String msg) {
		Map ret = new HashMap();
		ret.put("re", 0);
		ret.put("data", msg);
		return ret;
	}

	public static void sendSecurityCodeToPhone(final String phoneNumber, final String num) {

		final String corp_id = "hy6550";
		final String corp_pwd = "mm2289";
		final String corp_service = "1069003256550";
		final String corp_msg_id = "";
		final String ext = "";

		final String url = "http://sms.cloud.hbsmservice.com:8080/sms_send2.do";
		final String contentType = "application/x-www-form-urlencoded:charset=GBK";

		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			// httpPost.addHeader("Content-Type", contentType);
			httpPost.addHeader("contentType", contentType);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("corp_id", corp_id));
			nvps.add(new BasicNameValuePair("corp_pwd", corp_pwd));
			nvps.add(new BasicNameValuePair("corp_service", corp_service));
			nvps.add(new BasicNameValuePair("mobile", phoneNumber));
			nvps.add(new BasicNameValuePair("msg_content", num));
			nvps.add(new BasicNameValuePair("corp_msg_id", corp_msg_id));
			nvps.add(new BasicNameValuePair("ext", ext));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps,"gb2312");
			httpPost.setEntity(entity);
			response = httpclient.execute(httpPost);
			System.out.println(response.getStatusLine());
			InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			String retString = sb.toString();
			System.out.println(retString);
			HttpEntity entity2 = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	


	@SuppressWarnings("finally")
	public static Map wechatPay(Map<Object, Object> info) {
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String trade_type = "APP";
		String spbill_create_ip = "123.12.12.123";
		info.put("trade_type", trade_type);
		info.put("spbill_create_ip", spbill_create_ip);
		String appid = (String) info.get("app_id");
		String body = (String) info.get("body");
		String mch_id = (String) info.get("mch_id");
		String nonce_str = (String) info.get("nonce_str");
		String notify_url = (String) info.get("notify_url");
		String out_trade_no = (String) info.get("out_trade_no");
		Integer total_fee = (Integer) info.get("total_fee");
		
		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
		map.put("appid", appid);
		map.put("body", body);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		map.put("notify_url", notify_url);
		map.put("out_trade_no", out_trade_no);
		map.put("spbill_create_ip", spbill_create_ip);
		map.put("total_fee", total_fee);
		map.put("trade_type", trade_type);
		
		String sign = creatSign(map);

		String formData = "<xml>\n";
		formData += "<appid>" + appid + "</appid>\n"; // appid
		formData += "<body>" + body + "</body>\n"; // 商品或支付单简要描述
		formData += "<mch_id>" + mch_id + "</mch_id>\n"; // 商户号
		formData += "<nonce_str>" + nonce_str + "</nonce_str>\n"; // 随机字符串，不长于32位
		formData += "<notify_url>"+notify_url+"</notify_url>\n"; // 支付成功后微信服务器通过POST请求通知这个地址
		formData += "<out_trade_no>" + out_trade_no + "</out_trade_no>\n"; // 订单号
		formData += "<total_fee>" + total_fee + "</total_fee>\n"; // 金额
		formData += "<trade_type>" + trade_type + "</trade_type>\n"; // APP
		formData += "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>\n"; //
		formData += "<sign>" + sign + "</sign>\n";
		formData += "</xml>";

		System.out.println(formData);
		InputStream in = null;
		OutputStreamWriter out = null;
		LinkedHashMap<Object, Object> retMap = new LinkedHashMap<Object, Object>();
		try {
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-type", "text/xml");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestMethod("POST");
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(new String(formData.getBytes())); 
			out.flush();
			// 获取返回的数据
			in = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			String strXml = sb.toString();
			System.out.println(strXml);
			Document document = DocumentHelper.parseText(strXml);
			Element root = document.getRootElement();
			Iterator iter = root.elementIterator();
			String prepayid = null;
			while (iter.hasNext()) {
				Element ele = (Element) iter.next();
				switch (ele.getName()) {
				case "prepay_id":
					prepayid = (String) ele.getData();
					break;
/*				case "nonce_str":
					tempMap.put("noncestr", ele.getData());
					break;
				case "sign":
					tempMap.put("sign", ele.getData());
					break;*/
				};
			}
			
			
			retMap.put("appid", "wx9068ac0e88c09e7a");
			retMap.put("noncestr", createNonceStr());
			retMap.put("package", "Sign=WXPay");
			retMap.put("partnerid", "1485755962");
			retMap.put("prepayid", prepayid);
			System.out.println(prepayid);
			Long time = System.currentTimeMillis()/1000;
			retMap.put("timestamp", String.valueOf(time));
			String retSign = creatSignLower(retMap);
			System.out.println(retSign);
			retMap.put("sign", retSign);
			
			return retMap;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return retMap;
		}

	}
	
	public static String createNonceStr() {
		return String.valueOf(Math.random()).substring(2,15);
	}

	public static String creatSign(Map<Object, Object> parameters) {

		final String Key = "s6Th4Ge1Em1Xt5Nc5Bt7Xd8Ny0Jg9Qg4";
		StringBuffer sb = new StringBuffer();
		Set st = parameters.entrySet();
		Iterator it = st.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !v.equals("") && !k.equals("sign") && !k.equals("key")) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + Key);
		System.out.println("字符串拼接后是：" + sb.toString());
		String sign = Md5Util.GetMD5Code(sb.toString()).toUpperCase();
		return sign;
	}

	public static String creatSignLower(Map<Object, Object> parameters) {

		final String Key = "s6Th4Ge1Em1Xt5Nc5Bt7Xd8Ny0Jg9Qg4";
		StringBuffer sb = new StringBuffer();
		Set st = parameters.entrySet();
		Iterator it = st.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !v.equals("") && !k.equals("sign") && !k.equals("key")) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + Key);
		System.out.println("字符串拼接后是：" + sb.toString());
		String sign = Md5Util.GetMD5Code(sb.toString());
		return sign;
	}
	
	public static String getArraysSql(Integer a[]) {
		String sql = a[0].toString();
		for (int i = 1; i < a.length; i++) {
			sql += "," + a[i];
		}
		return sql;
	}

	public static String getNextNum(String prefix, String num) {
		if (num == null || num.length() == 0) {
			return prefix + "0001";
		}
		String str = num.substring(num.length() - 4, num.length());
		int c;
		if (str.charAt(0) == '0') {
			if (str.charAt(1) == '0') {
				if (str.charAt(2) == '0') {
					c = str.charAt(3) - '0';
				} else {
					c = (str.charAt(2) - '0') * 10 + str.charAt(3) - '0';
				}
			} else {
				c = (str.charAt(1) - '0') * 100 + (str.charAt(2) - '0') * 10 + str.charAt(3) - '0';
			}
		} else {
			c = (str.charAt(0) - '0') * 1000 + (str.charAt(1) - '0') * 100 + (str.charAt(2) - '0') * 10 + str.charAt(3)
					- '0';
		}
		c++;
		if (c < 10) {
			return prefix + "000" + c;
		} else if (c < 100) {
			return prefix + "00" + c;
		} else if (c < 1000) {
			return prefix + "0" + c;
		} else {
			return prefix + +c;
		}
	}
	
	public static String getNextNum2(String prefix, String num) {
		if (num == null || num.length() == 0) {
			return prefix + "01";
		}
		String str = num.substring(num.length() - 2, num.length());
		int c;
		if (str.charAt(0) == '0') {
			c = str.charAt(1) - '0';
		} else {
			c = (str.charAt(0) - '0') * 10 + str.charAt(1) - '0';
		}
		c++;
		if (c < 10) {
			return prefix + "0" + c;
		} else {
			return prefix + c;
		}
	}
	
	public static String getHttpPort(String proprttiesConfig) {
		Properties currentCfg = new Properties();
		try {
//			currentCfg.load(CommonTool.class.getResourceAsStream("fs.cfg.properties"));
			currentCfg.load(CommonTool.class.getResourceAsStream(proprttiesConfig));
			String ipAddress = (String) currentCfg.get("ipAddress");
			String wwwPort = (String) currentCfg.get("wwwPort");
			String str = ipAddress + ":" + wwwPort;
			System.out.println(str);
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	 public static  String getUserInfo(String encryptedData,String sessionKey,String iv){
	        // 被加密的数据
	        byte[] dataByte = Base64.decode(encryptedData.getBytes());
	        // 加密秘钥
	        byte[] keyByte = Base64.decode(sessionKey.getBytes());
	        // 偏移量
	        byte[] ivByte = Base64.decode(iv.getBytes());
	        try {
	               // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
	            int base = 16;
	            if (keyByte.length % base != 0) {
	                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
	                byte[] temp = new byte[groups * base];
	                Arrays.fill(temp, (byte) 0);
	                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
	                keyByte = temp;
	            }
	            // 初始化
	            Security.addProvider(new BouncyCastleProvider());
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
	            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
	            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
	            parameters.init(new IvParameterSpec(ivByte));
	            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
	            byte[] resultByte = cipher.doFinal(dataByte);
	            if (null != resultByte && resultByte.length > 0) {
	                String result = new String(resultByte, "UTF-8");
	                return result;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	        return null;
	    }
	
}
