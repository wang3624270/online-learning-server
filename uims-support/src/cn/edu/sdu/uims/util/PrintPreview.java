package cn.edu.sdu.uims.util;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PrintPreview extends JFrame implements ActionListener, ChangeListener,
		ItemListener {
	JButton print = new JButton("打印"), printThisPage = new JButton(
			"打印当前页"), cancel = new JButton("关闭");
	Pageable pg = null;
	double scale = 1.0;
	JSlider slider = new JSlider();
	Page page[] = null;
	JComboBox jcb = new JComboBox();
	CardLayout cl = new CardLayout();
	JPanel p = new JPanel(cl);
	JButton back = new JButton("<<"), forward = new JButton(">>");

	public PrintPreview(Pageable pg) {
		super("打印预览");
		this.pg = pg;
		createPreview();
	}

	public PrintPreview(final Printable pr, final PageFormat p) {
		super("打印预览");
		this.pg = new Pageable() {
			public int getNumberOfPages() {
				Graphics g = new java.awt.image.BufferedImage(2, 2,
						java.awt.image.BufferedImage.TYPE_INT_RGB)
						.getGraphics();
				int n = 0;
				try {
					while (pr.print(g, p, n) == pr.PAGE_EXISTS)
						n++;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return n;
			}

			public PageFormat getPageFormat(int x) {
				return p;
			}

			public Printable getPrintable(int x) {
				return pr;
			}
		};
		createPreview();
	}

	private void createPreview() {
		page = new Page[pg.getNumberOfPages()];
		FlowLayout fl = new FlowLayout();
		PageFormat pf = pg.getPageFormat(0);
		Dimension size = new Dimension((int) pf.getPaper().getWidth(), (int) pf
				.getPaper().getHeight());
		if (pf.getOrientation() == PageFormat.PORTRAIT)
			size = new Dimension(size.height, size.width);
		JPanel temp = null;
		for (int i = 0; i < page.length; i++) {
			jcb.addItem("" + (i + 1));
			page[i] = new Page(i, size);
			p.add("" + (i + 1), new JScrollPane(page[i]));
		}
		setTopPanel();
		this.getContentPane().add(p, "Center");
		Dimension d = this.getToolkit().getScreenSize();
		this.setSize(d.width, d.height - 60);
		slider.setSize(this.getWidth() / 2, slider.getPreferredSize().height);
		this.setVisible(true);
		page[jcb.getSelectedIndex()].refreshScale();
	}

	private void setTopPanel() {
		FlowLayout fl = new FlowLayout();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel topPanel = new JPanel(gbl), temp = new JPanel(fl);
		slider.setBorder(new TitledBorder("放缩比例"));
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinimum(0);
		slider.setMaximum(500);
		slider.setValue(100);
		slider.setMinorTickSpacing(20);
		slider.setMajorTickSpacing(100);
		slider.addChangeListener(this);
		back.addActionListener(this);
		forward.addActionListener(this);
		back.setEnabled(false);
		forward.setEnabled(page.length > 1);
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbl.setConstraints(slider, gbc);
		topPanel.add(slider);
		temp.add(back);
		temp.add(jcb);
		temp.add(forward);
		temp.add(cancel);
		temp.add(print);
		temp.add(printThisPage);
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		gbl.setConstraints(temp, gbc);
		topPanel.add(temp);
		print.addActionListener(this);
		printThisPage.addActionListener(this);
		cancel.addActionListener(this);
		jcb.addItemListener(this);
		print.setMnemonic('P');
		cancel.setMnemonic('C');
		printThisPage.setMnemonic('U');
		this.getContentPane().add(topPanel, "North");
	}

	public void itemStateChanged(ItemEvent ie) {
		cl.show(p, (String) jcb.getSelectedItem());
		page[jcb.getSelectedIndex()].refreshScale();
		back.setEnabled(jcb.getSelectedIndex() == 0 ? false : true);
		forward.setEnabled(jcb.getSelectedIndex() == jcb.getItemCount() - 1 ? false
				: true);
		this.validate();
	}

	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if (o == print) {
			try {
				PrinterJob pj = PrinterJob.getPrinterJob();
				PageFormat pf = pg.getPageFormat(0);
//				int temp = pf.getOrientation();
//				pf.setOrientation(pf.PORTRAIT);    //临时修改 为了打印学生证
				pj.defaultPage(pf);
				pj.setPageable(pg);
				if (pj.printDialog())
					pj.print();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.toString(),
						"打印错误", 1);
			}
		} else if (o == printThisPage)
			printCurrentPage();
		else if (o == back) {
			jcb.setSelectedIndex(jcb.getSelectedIndex() == 0 ? 0 : jcb
					.getSelectedIndex() - 1);
			if (jcb.getSelectedIndex() == 0)
				back.setEnabled(false);
		} else if (o == forward) {
			jcb.setSelectedIndex(jcb.getSelectedIndex() == jcb.getItemCount() - 1 ? 0
					: jcb.getSelectedIndex() + 1);
			if (jcb.getSelectedIndex() == jcb.getItemCount() - 1)
				forward.setEnabled(false);
		} else if (o == cancel)
			this.dispose();
	}

	public void printCurrentPage() {
		try {
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.defaultPage(pg.getPageFormat(0));
			pj.setPrintable(new PsuedoPrintable());
			javax.print.attribute.HashPrintRequestAttributeSet pra = new javax.print.attribute.HashPrintRequestAttributeSet();
			if (pj.printDialog(pra))
				pj.print(pra);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.toString(),
					"打印错误", 1);
		}
	}

	public void stateChanged(ChangeEvent ce) {
		double temp = (double) slider.getValue() / 100.0;
		if (temp == scale)
			return;
		if (temp == 0)
			temp = 0.01;
		scale = temp;
		page[jcb.getSelectedIndex()].refreshScale();
		this.validate();
	}

	class Page extends JLabel {
		final int n;
		final PageFormat pf;
		java.awt.image.BufferedImage bi = null;
		Dimension size = null;

		public Page(int x, Dimension size) {
			this.size = size;
			bi = new java.awt.image.BufferedImage(size.width, size.height,
					java.awt.image.BufferedImage.TYPE_INT_RGB);
			n = x;
			pf = pg.getPageFormat(n);
			Graphics g = bi.getGraphics();
			Color c = g.getColor();
			g.setColor(Color.white);
			g.fillRect(0, 0, (int) pf.getWidth(), (int) pf.getHeight());
			g.setColor(c);
			try {
				g.clipRect(0, 0, (int) pf.getWidth(), (int) pf.getHeight());
				pg.getPrintable(n).print(g, pf, n);
			} catch (Exception ex) {
			}
			this.setIcon(new ImageIcon(bi));
		}

		public void refreshScale() {
			if (scale != 1.0)
				this.setIcon(new ImageIcon(bi.getScaledInstance(
						(int) (size.width * scale),
						(int) (size.height * scale), bi.SCALE_FAST)));
			else
				this.setIcon(new ImageIcon(bi));
			this.validate();
		}
	}

	class PsuedoPrintable implements Printable {
		public int print(Graphics g, PageFormat fmt, int index) {
			if (index > 0)
				return Printable.NO_SUCH_PAGE;
			int n = jcb.getSelectedIndex();
			try {
				return pg.getPrintable(n).print(g, fmt, n);
			} catch (Exception ex) {
			}
			return Printable.PAGE_EXISTS;
		}
	}
}