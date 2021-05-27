package com.peteralbus.service.image_detection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class image_drawbox {

    public static void main(String[] args) throws IOException {
        return;
    }
    public static String Draw(float y1, float x1, float y2, float x2, String file) throws IOException {
        BufferedImage image = ImageIO.read(new File(file));
        Graphics g = image.getGraphics();
        Graphics2D g2 = (Graphics2D)g; //g是Graphics对象

        float h = image.getHeight();
        float w = image.getWidth();
        g2.setStroke(new BasicStroke(3.0f));
        g2.setColor(Color.RED);//画笔颜色
        float x = x1 * w;
        float y = y1 * h;
        float wid = (x2 - x1) * w;
        float hei = (y2 - y1) * h;
        g2.drawRect((int) x, (int) y, (int) wid, (int) hei);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
        String outfile = file.substring(0, file.length()-4) + "_detection.jpg";
        FileOutputStream out = new FileOutputStream(outfile);//输出图片的地址
        ImageIO.write(image, "jpeg", out);
        return outfile;
    }
}