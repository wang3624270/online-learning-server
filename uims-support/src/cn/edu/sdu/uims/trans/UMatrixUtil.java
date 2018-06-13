package cn.edu.sdu.uims.trans;

public class UMatrixUtil {
	public static void copy(double a[][], double b[][]) {
		int i, j;
		for (i = 0; i < a.length; i++)
			for (j = 0; j < a[i].length; j++)
				b[i][j] = a[i][j];
	}

	public static void multi(double a[][], double b[][], double c[][]) {
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

}
