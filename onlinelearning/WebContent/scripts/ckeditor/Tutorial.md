CKEditor配置指南：
1.每个发布的工程(如gradms)web.xml下面配置servlet如下：
	<servlet>
		<servlet-name>CKEditorUploader</servlet-name>
		<servlet-class>com.topwellsoft.lemon.filebrowser.GeneralFileUploadServlet</servlet-class>
		<init-param>
			<param-name>baseDir</param-name>
			<param-value>web_upload_files/</param-value>
		</init-param>
		<init-param>
			<param-name>enabled</param-name>
			<param-value>true</param-value>
		</init-param>
		<init-param>
			<param-name>AllowedExtensionsFile</param-name>
			<param-value>doc|docx|xls|txt|xlsx</param-value>
		</init-param>
		<init-param>
			<param-name>DeniedExtensionsFile</param-name>
			<param-value>
				php|php3|php5|phtml|asp|aspx|ascx|jsp|cfm|cfc|pl|bat|exe|dll|reg|cgi
			</param-value>
		</init-param>
		<init-param>
			<param-name>AllowedExtensionsImage</param-name>
			<param-value>jpg|gif|jpeg|png|bmp</param-value>
		</init-param>
		<init-param>
			<param-name>DeniedExtensionsImage</param-name>
			<param-value></param-value>
		</init-param>
		<init-param>
			<param-name>AllowedExtensionsFlash</param-name>
			<param-value>swf|fla</param-value>
		</init-param>
		<init-param>
			<param-name>DeniedExtensionsFlash</param-name>
			<param-value></param-value>
		</init-param>
		<load-on-startup>-1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>CKEditorUploader</servlet-name>
		<url-pattern>/lemon/fileUpload</url-pattern>
	</servlet-mapping>
	<servlet>
		<servlet-name>CKEditorDownloader</servlet-name>
		<servlet-class>com.topwellsoft.lemon.filebrowser.GeneralFileDownloadServlet</servlet-class>
		<load-on-startup>-1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>CKEditorDownloader</servlet-name>
		<url-pattern>/lemon/fileDownload</url-pattern>
	</servlet-mapping>
	2.每个发布的工程(如gradms)的sysinfo.properties添加信息如下：
	webserver=http://localhost:8080/gradms/(注：此添加为本地测试)
	或者
	webserver=http://27.223.2.83:8088/(注：此添加正式服务器)
	3.每个发布的工程(如gradms)的fs.cfg.properties添加信息如下(注：localpath的值物理盘符下面必须要有FTPROOT这个文件夹)：
	
	Required
	url=local
	localpath=D\:/FTPROOT/
	
	
	
	