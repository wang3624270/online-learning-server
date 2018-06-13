package cn.edu.sdu.uims.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanel;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UMatrixUtil;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public class UVPanel extends UPanel implements UVElementI{
	protected int x, y, w, h;
	private List  list = new ArrayList();
	private UFormI form;
	public UVPanel() {
		this(new UPanelTemplate());
	}

	public UVPanel(UPanelTemplate panelTemplate) {
		super(panelTemplate);
	}

	public void init() {
		UFormI form = null;
		initComponents();
		form = initFormData();
		setData(form);
		initContents();
		setParameterData(null, form, null);

	}

	
	
	public void setShellBounds(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
    public void draw(Graphics g, UMatrix m) {
        UVElementI ge;
        int i ;
//        MatrixStack.push(m);
        UMatrix mt = makeTransMatrix();
        UMatrix ms = new UMatrix();
        UMatrixUtil.multi(m.m, mt.m, ms.m);
        for(i = 0; i < list.size();i++){
			ge = (UVElementI)list.get(i);
			ge.draw(g, ms);	
        }
    }
	public void addComponent(UComponentI com,int layout) {
		list.add(com);
	}
	public UMatrix makeTransMatrix(){
		return new UMatrix(x,y);
	}
	public UFormI initFormData() {
		UFormI form = null;
//		UHandlerTemplate ruleTemplate;
//		try {
//			if (panelTemplate.handlerName != null) {
//				ruleTemplate = (UHandlerTemplate) UFactory.getModelSession().getTemplate(
//						UConstants.MAPKEY_HANDLER, panelTemplate.handlerName);
//				form = ruleTemplate.instance.initFormData();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return form;
	}


	public void setParameterData(HashMap map, UFormI form, UComponentI father) {
		UVElementI obj;
		int i;
		for (i = 0; i < list.size(); i++) {
			obj = (UVElementI)list.get(i);
			obj.setParameterData(map,form,father);
		}
	}

	public void drawBox(Graphics g, UMatrix m) {
		// TODO Auto-generated method stub
		
	}


	public URect getBox() {
		// TODO Auto-generated method stub
		return null;
	}

	public void shift(int dx, int dy) {
		// TODO Auto-generated method stub
		
	}

	public int getSelectStatus(UPoint p) {
		// TODO Auto-generated method stub
		return -1;
	}

}
