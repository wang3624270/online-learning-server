package cn.edu.sdu.uims.pi;

public interface ImageDataDriverI {
	Object getImageData(Integer id);
	void putImageDate(Integer id, Object obj);
	boolean isExistImageData(Integer id);
}
