package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentGridBag extends UGroupComponent {
	private GridBagLayout gl;
	GridBagConstraints gbc;
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			gl= new GridBagLayout();
			gbc = new GridBagConstraints();
			container.setLayout(gl); 
		}
//		 gbc.gridx = elementTemplate.gridx; 
//		 gbc.gridy = elementTemplate.gridy; 
		 gbc.gridwidth = elementTemplate.gridwidth; 
		 gbc.gridheight = elementTemplate.gridheight; 
		 gbc.weightx = elementTemplate.weightx; 
		 gbc.weighty = elementTemplate.weighty; 
		 gbc.fill = elementTemplate.fill;
		 gbc.anchor  = elementTemplate.anchor;
		 gl.setConstraints(com.getAWTComponent(), gbc);
		 container.add(com.getAWTComponent());
		comnum++;
	}

}
