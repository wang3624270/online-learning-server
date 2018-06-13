package org.sdu.file_util;

import java.io.IOException;
/**
 * 
 * @author JING Ming
 *
 */
public interface FileSource {
//	FileConnection getConnection() throws IOException;
	
	FileConnection getConnection(String name) throws IOException;

}
