package org.starfish;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.sdu.rmi.RmiSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.starfish.configure_model.Dimensions;
import org.starfish.constants.StarfishConstants;
import org.starfish.permi_dimension.PermissionDim;
import org.starfish.sf_auth.StarfishMenuManager;
import org.starfish.web_security.SecurityCheckUtils;

import com.zaxxer.hikari.HikariDataSource;

public class Starfish implements ApplicationContextAware {
	public static Logger logger = Logger.getLogger(Starfish.class);

	public static Starfish starfish;
	public static Dimensions rootDimensions;
	private String configurePath;
	private String pageAccessConfigurePlace;// Page access configure information
											// can be stored into database or
											// file.
	private String codeForCheckUrlInSpringSecurity;// Some operations should be
													// done in Spring Security.
													// We add several classes in
													// Spring Security, and when
													// a request comes, these
													// classes will call code
													// segment indicated by
													// 'codeForCheckUrlInSpringSecurity'
													// to check user authority
													// to access url.

	private Object urlFilterBean = null;
	private Method urlFilterMethod = null;

	private HikariDataSource dataSource;

	public static JdbcTemplate jdbcTemplate;

	public void init() {

	}

	public Starfish() {
		starfish = this;

		InputStream in = Starfish.class.getResourceAsStream("/starfish/dimensions.xml");

		try {
			SAXReader sb = new SAXReader();
			Document doc = (Document) sb.read(in);

			rootDimensions = new Dimensions(null);
			rootDimensions.currentElement = doc.getRootElement();
			rootDimensions.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getConfigurePath() {
		return configurePath;
	}

	public void setConfigurePath(String configurePath) {
		this.configurePath = configurePath;

	}

	public static void evaluateUserPermissions(HttpSession session) {
		if (session.getAttribute(StarfishConstants.userPermission) == null) {
			session.setAttribute(StarfishConstants.userPermission, new PermissionDim());
		}
		PermissionDim permissionDim = (PermissionDim) session.getAttribute(StarfishConstants.userPermission);
		permissionDim.setDimensionName(StarfishConstants.dimenssionRoot);
		rootDimensions.walkUserPermissionDims(session);

	}

	public static void evaluateUserPermissions(RmiSession session) {
		if (session.getAttribute(StarfishConstants.userPermission) == null) {
			session.setAttribute(StarfishConstants.userPermission, new PermissionDim());
		}
		PermissionDim permissionDim = (PermissionDim) session.getAttribute(StarfishConstants.userPermission);
		permissionDim.setDimensionName(StarfishConstants.dimenssionRoot);
		//rootDimensions.walkUserPermissionDims(session);
		if (rootDimensions != null)
			rootDimensions.walkUserPermissionDims(session);

	}

	public static void evaluateUserPermissions(Session session) {

		if (session.getUserProperties().get(StarfishConstants.userPermission) == null) {
			session.getUserProperties().put(StarfishConstants.userPermission, new PermissionDim());
		}
		PermissionDim permissionDim = (PermissionDim) session.getUserProperties().get(StarfishConstants.userPermission);
		permissionDim.setDimensionName(StarfishConstants.dimenssionRoot);
		rootDimensions.walkUserPermissionDims(session);

	}

	public String getPageAccessConfigurePlace() {
		return pageAccessConfigurePlace;
	}

	public void setPageAccessConfigurePlace(String pageAccessConfigurePlace) {
		this.pageAccessConfigurePlace = pageAccessConfigurePlace;
	}

	public HikariDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(HikariDataSource dataSource) {
		this.dataSource = dataSource;
		if (jdbcTemplate == null)
			jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public String getCodeForCheckUrlInSpringSecurity() {
		return codeForCheckUrlInSpringSecurity;
	}

	public void setCodeForCheckUrlInSpringSecurity(String codeForCheckUrlInSpringSecurity) {
		this.codeForCheckUrlInSpringSecurity = codeForCheckUrlInSpringSecurity;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (codeForCheckUrlInSpringSecurity != null) {
			int i;
			String beanId = codeForCheckUrlInSpringSecurity.substring(0, codeForCheckUrlInSpringSecurity.indexOf('.'));
			urlFilterBean = applicationContext.getBean(beanId);
			String methodName = codeForCheckUrlInSpringSecurity
					.substring(codeForCheckUrlInSpringSecurity.indexOf('.') + 1);
			try {
				Method me = urlFilterBean.getClass().getMethod(methodName, ServletRequest.class);
				urlFilterMethod = me;

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SecurityCheckUtils.urlFilterBean = urlFilterBean;
			SecurityCheckUtils.urlFilterMethod = urlFilterMethod;
		}
		// ////////////////////////////////////////////
		// Menu resource
		new StarfishMenuManager();

	}

}
