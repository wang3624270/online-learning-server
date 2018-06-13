package cn.edu.sdu.uims.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;

public class UVImageRandom extends UVImage {
	private Image imgs[] = null;
	public final static int MAX_NUM =1;
	private Random r = new Random();
	private int rw = 100, rh = 50;

	public UVImageRandom() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int i;
		String path = "d:\\wk\\run\\stamp\\";
		String ext = ".jpg";
		String sName;
		imgs = new Image[MAX_NUM];
		try {
			for (i = 0; i < MAX_NUM; i++) {
				sName = path + (1) + ext;
				imgs[i] = toolkit.getImage(sName);
				imgs[i] = UVUtil.transParency(imgs[i], 0, 255, 255, 255);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, UMatrix m) {
		if (imgs == null)
			return;
		int xt, yt, nt;
		super.draw(g, m);
		URect rs = new URect(x, y, w, h);
		URect rd;
		rd = m.logicToView(rs);
		xt = (int) ((r.nextFloat() - 0.5) * rw);
		yt = (int) ((r.nextFloat() - 0.5) * rh);
		nt = (int) (r.nextFloat() * MAX_NUM) % MAX_NUM;
		g.drawImage(imgs[nt], rd.x + xt, rd.y + yt, rd.w, rd.h, null);
		// g.drawImage(imgs[0], rd.x, rd.y, rd.w, rd.h, component);
		// g.drawImage(imgs[nt],50,50,100,100,null);

	}

	public Object getData() {
		return null;
	}

	public void setData(Object obj) {
	}
}
