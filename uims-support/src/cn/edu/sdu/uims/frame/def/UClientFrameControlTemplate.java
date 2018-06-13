package cn.edu.sdu.uims.frame.def;

import java.util.List;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UClientFrameControlTemplate extends UTemplate {
	public String bgColorName;
	public int align = UConstants.ALIGNMENT_TOP;
	public int width = 1600;
	public int height = 160;
	public int titleHeight = 20;
	public String toolBarNames;
	public String windowToolBarName;
	public List<ULabelAttribute> labelList;
	public List<UImageAttribute> imageList;
	public List<UElementTemplate> comList;
}
