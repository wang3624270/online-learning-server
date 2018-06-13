package cn.edu.sdu.uims.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImgCreator
{

    public ImgCreator()
    {
    }

    public Image getImage(int i, int j)
    {
        int k = j <= i ? i : j;
        img = new BufferedImage(k, k, 13);
        g = ((BufferedImage)img).createGraphics();
        return img;
    }

    public Graphics getGraphics()
    {
        return g;
    }

    private Image img;
    public Graphics g;
}