package cn.edu.sdu.uims.component.panel;

import cn.edu.sdu.uims.frame.UFrameConstants;

public class SuperTextPanel extends UOldPanelBase{
	public SuperTextPanel(){
		super();
		this.setToolActions(new String[]{UFrameConstants.TOOL_ITEM_SAVE,UFrameConstants.TOOL_ITEM_ADD,
				UFrameConstants.TOOL_ITEM_OPEN,UFrameConstants.TOOL_ITEM_MODIFY,UFrameConstants.TOOL_ITEM_DELETE,
				UFrameConstants.TOOL_ITEM_QUERY});
	}
	
}
