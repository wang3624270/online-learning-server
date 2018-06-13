package cn.edu.sdu.uims.trans;


import java.awt.Point;
import java.io.Serializable;

public class UMatrix implements Serializable{
	public static final int NUM = 4;
	public double m[][],rm[][]= new double[NUM][NUM];

	public static void copy(double a[][], double b[][]) {
		int i, j;
		for (i = 0; i < a.length; i++)
			for (j = 0; j < a[i].length; j++)
				b[i][j] = a[i][j];
	}

	public static void copy(UMatrix a, UMatrix b) {
		int i, j;
		for (i = 0; i < a.m.length; i++)
			for (j = 0; j < a.m[i].length; j++)
				b.m[i][j] = a.m[i][j];
		b.InversMatrix();
	}

	public  static void multi(double a[][], double b[][], double c[][]) {
		int i, j, k;
		int n = a.length;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				c[i][j] = 0;
				for (k = 0; k < n; k++)
					c[i][j] += a[i][k] * b[k][j];
			}
		}
	}

	public  static void multi(UMatrix a, UMatrix b, UMatrix c) {
		int i, j, k;
		int n = a.m.length;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				c.m[i][j] = 0;
				for (k = 0; k < n; k++)
					c.m[i][j] += a.m[i][k] * b.m[k][j];
			}
		}
		c.InversMatrix();
	}

	
	public UMatrix() {
		m = new double[][] { { 1, 0, 0,0 }, { 0, 1, 0,0 }, { 0, 0, 1,0 },{0,0,0,1} };
		InversMatrix();
	}

	public UMatrix(double xo, double yo) {
		m = new double[][] { { 1, 0,0, xo }, { 0, 1,0, yo }, { 0, 0, 1,0 } , { 0, 0, 0,1 }};
		InversMatrix();
	}

	public UMatrix(UFPoint p) {
		m = new double[][] { { 1, 0, 0,p.x }, { 0, 1, 0,p.y }, { 0, 0, 1,0 }, { 0, 0, 0,1 } };
		InversMatrix();
	}

	public void shift(double dx, double dy) {
		double mt[][] = { { 1, 0, 0,dx }, { 0, 1, 0,dy }, { 0, 0, 1,0 }, { 0, 0, 0,1 } };
		double mt1[][] = new double[NUM][NUM];
		UMatrixUtil.copy(m, mt1);
		UMatrixUtil.multi(mt1, mt, m);
		InversMatrix();
	}

	public void scale(double sx, double sy) {
		double mt[][] = { { sx, 0, 0 ,0}, { 0, sy, 0,0 }, { 0, 0,1, 0 }, { 0, 0,0, 1 } };
		double mt1[][] = new double[NUM][NUM];
		UMatrixUtil.copy(m, mt1);
		UMatrixUtil.multi(mt1, mt, m);
		InversMatrix();
	}

	public void roate(double a) {
		double d = (double) (Math.PI * a / 180);
		double mt[][] = { { (double) Math.cos(d), (double) Math.sin(d), 0, 0 },
				{ -(double) Math.sin(d), (double) Math.cos(d), 0,0 },{ 0, 0,1, 0 }, { 0, 0,0, 1 } };
		double mt1[][] = new double[NUM][NUM];
		copy(m, mt1);
		multi(mt1, mt, m);
		InversMatrix();
	}

	public UMatrix(double dx, int dy) {
		super();
		m = new double[][] { { 1, 0, 0, dx }, { 0, 1, 0, dy }, { 0, 0, 1,0 }, { 0, 0, 0,1 } };
		InversMatrix();
	}

	public double GetM3Val(double a00, double a01, double a02, double a10,
			double a11, double a12, double a20, double a21, double a22) {
		double x;
		x = a00 * (a11 * a22 - a21 * a12) - a10 * (a01 * a22 - a21 * a02) + a20
				* (a01 * a12 - a11 * a02);
		return x;
	}

	public double GetMatrixVal() {
		double x;
		x = m[0][0]
				* GetM3Val(m[1][1], m[1][2], m[1][3], m[2][1], m[2][2],
						m[2][3], m[3][1], m[3][2], m[3][3])
				- m[1][0]
				* GetM3Val(m[0][1], m[0][2], m[0][3], m[2][1], m[2][2],
						m[2][3], m[3][1], m[3][2], m[3][3])
				+ m[2][0]
				* GetM3Val(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2],
						m[1][3], m[3][1], m[3][2], m[3][3])
				- m[3][0]
				* GetM3Val(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2],
						m[1][3], m[2][1], m[2][2], m[2][3]);
		return x;
	}

	public void InversMatrix() {
		double da;
		da = GetMatrixVal();
		rm[0][0] = GetM3Val(m[1][1], m[1][2], m[1][3], m[2][1], m[2][2],
				m[2][3], m[3][1], m[3][2], m[3][3])
				/ da;
		rm[1][0] = -GetM3Val(m[1][0], m[1][2], m[1][3], m[2][0], m[2][2],
				m[2][3], m[3][0], m[3][2], m[3][3])
				/ da;
		rm[2][0] = GetM3Val(m[1][0], m[1][1], m[1][3], m[2][0], m[2][1],
				m[2][3], m[3][0], m[3][1], m[3][3])
				/ da;
		rm[3][0] = -GetM3Val(m[1][0], m[1][1], m[1][2], m[2][0], m[2][1],
				m[2][2], m[3][0], m[3][1], m[3][2])
				/ da;

		rm[0][1] = -GetM3Val(m[0][1], m[0][2], m[0][3], m[2][1], m[2][2],
				m[2][3], m[3][1], m[3][2], m[3][3])
				/ da;
		rm[1][1] = GetM3Val(m[0][0], m[0][2], m[0][3], m[2][0], m[2][2],
				m[2][3], m[3][0], m[3][2], m[3][3])
				/ da;
		rm[2][1] = -GetM3Val(m[0][0], m[0][1], m[0][3], m[2][0], m[2][1],
				m[2][3], m[3][0], m[3][1], m[3][3])
				/ da;
		rm[3][1] = GetM3Val(m[0][0], m[0][1], m[0][2], m[2][0], m[2][1],
				m[2][2], m[3][0], m[3][1], m[3][2])
				/ da;

		rm[0][2] = GetM3Val(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2],
				m[1][3], m[3][1], m[3][2], m[3][3])
				/ da;
		rm[1][2] = -GetM3Val(m[0][0], m[0][2], m[0][3], m[1][0], m[1][2],
				m[1][3], m[3][0], m[3][2], m[3][3])
				/ da;
		rm[2][2] = GetM3Val(m[0][0], m[0][1], m[0][3], m[1][0], m[1][1],
				m[1][3], m[3][0], m[3][1], m[3][3])
				/ da;
		rm[3][2] = -GetM3Val(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1],
				m[1][2], m[3][0], m[3][1], m[3][2])
				/ da;

		rm[0][3] = -GetM3Val(m[0][1], m[0][2], m[0][3], m[1][1], m[1][2],
				m[1][3], m[2][1], m[2][2], m[2][3])
				/ da;
		rm[1][3] = GetM3Val(m[0][0], m[0][2], m[0][3], m[1][0], m[1][2],
				m[1][3], m[2][0], m[2][2], m[2][3])
				/ da;
		rm[2][3] = -GetM3Val(m[0][0], m[0][1], m[0][3], m[1][0], m[1][1],
				m[1][3], m[2][0], m[2][1], m[2][3])
				/ da;
		rm[3][3] = GetM3Val(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1],
				m[1][2], m[2][0], m[2][1], m[2][2])
				/ da;
	}
	public UPoint logicToView(UPoint p) {
		int x, y;
		x = (int) (m[0][0] * p.x + m[0][1] * p.y + m[0][3]);
		y = (int) (m[1][0] * p.x + m[1][1] * p.y + m[1][3]);
		return new UPoint(x, y);
	}
	public  UPoint logicToView(UFPoint p) {
		int xt, yt;
		xt = (int) (m[0][0] * p.x + m[0][1] * p.y + m[0][3]);
		yt = (int) (m[1][0] * p.x + m[1][1] * p.y + m[1][3]);
		return new UPoint(xt, yt);
	}
	public  UPoint logicToView(double x,double y) {
		int xt, yt;
		xt = (int) (m[0][0] * x + m[0][1] * y + m[0][3]);
		yt = (int) (m[1][0] * x + m[1][1] * y + m[1][3]);
		return new UPoint(xt, yt);
	}
	public  UPoint logicToView(double x,double y,double z) {
		int xt, yt;
		xt = (int) (m[0][0] * x + m[0][1] * y + m[0][3]);
		yt = (int) (m[1][0] * x + m[1][1] * y + m[1][3]);
		return new UPoint(xt, yt);
	}
	public  Point logicToViewD(double x,double y,double z) {
		double xt, yt;
		xt = m[0][0] * x + m[0][1] * y + m[0][3];
		yt = m[1][0] * x + m[1][1] * y + m[1][3];
		Point p  = new Point();
		p.x = (int)xt;
		p.y = (int)yt;
//		p.setX((int)xt);
//		p.setY((int)yt);
//		p.setZ(0.0);
		return p;
	}

	public  URect logicToView(UFRect r) {
		int x1, y1, x2, y2;
		x1 = (int) (m[0][0] * r.x + m[0][1] * r.y + m[0][3]);
		y1 = (int) (m[1][0] * r.x + m[1][1] * r.y + m[1][3]);
		x2 = (int) (m[0][0] * (r.x + r.w) + m[0][1] * (r.y + r.h) + m[0][3]);
		y2 = (int) (m[1][0] * (r.x + r.w) + m[1][1] * (r.y + r.h) + m[1][3]);
		return new URect(x1, y1, x2-x1, y2-y1);
	}
	public  URect logicToView(double fx0, double fy0, double fx1, double fy1) {
		int x1, y1, x2, y2;
		x1 = (int) (m[0][0] * fx0 + m[0][1] * fy0 + m[0][3]);
		y1 = (int) (m[1][0] * fx0 + m[1][1] * fy0 + m[1][3]);
		x2 = (int) (m[0][0] * (fx1) + m[0][1] * (fy1) + m[0][3]);
		y2 = (int) (m[1][0] * (fx1) + m[1][1] * (fy1) + m[1][3]);
		return new URect(x1, y1, x2-x1, y2-y1);
	}
	public  URect logicToView(URect r) {
		int x1, y1, x2, y2;
		x1 = (int) (m[0][0] * r.x + m[0][1] * r.y + m[0][3]);
		y1 = (int) (m[1][0] * r.x + m[1][1] * r.y + m[1][3]);
		x2 = (int) (m[0][0] * (r.x + r.w) + m[0][1] * (r.y + r.h) + m[0][3]);
		y2 = (int) (m[1][0] * (r.x + r.w) + m[1][1] * (r.y + r.h) + m[1][3]);
		return new URect(x1, y1, x2-x1, y2-y1);
	}
	public UPoint [] logicToView(UFPoint [] fpt) {
		UPoint [] pt = new UPoint[fpt.length];
		for(int i = 0; i < pt.length;i++){
			pt[i] = logicToView(fpt[i]);
		}
		return pt;
	}
	public  UFPoint viewToLogic(UPoint p) {
		double xt, yt;
		xt =  (rm[0][0] * p.x + rm[0][1] * p.y + rm[0][3]);
		yt =  (rm[1][0] * p.x + rm[1][1] * p.y + rm[1][3]);
		return new UFPoint(xt, yt);
	}
	public  UFPoint viewToLogic(UFPoint p) {
		double xt, yt;
		xt =  (rm[0][0] * p.x + rm[0][1] * p.y + rm[0][3]);
		yt =  (rm[1][0] * p.x + rm[1][1] * p.y + rm[1][3]);
		return new UFPoint(xt, yt);
	}
}
