package com.css.common.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jiming.jing on 2020/3/28.
 */
public class VCodeUtil {

    private static final int WIDTH = 80;//验证码长度
    private static final int HEIGHT = 30;//验证码高度
    private static final int LENGTH = 5;//验证码位数

    /**
     * 获取验证码
     */
    public static BufferedImage getVCodeImage(char[] rands){
        // 创建内存图象并获得其图形上下文
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        drawBackground(graphics);
        drawRands(graphics, rands);

        // 结束图像的绘制过程，完成图像
        graphics.dispose();

        return image;
    }

    /**
     * 生产随机码
     */
    public static char[] generateVCode() {
        // 定义验证码的字符表
        //String chars = "abcdefghijkmnopqrstuvwxyz234567890";
        String chars = "1234567890";
        char[] rands = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            int rand = (int) (Math.random() * chars.length());
            rands[i] = chars.charAt(rand);
        }

        return rands;
    }

    /**
     *  画出验证码
     */
    private static Graphics drawRands(Graphics graphics, char[] rands) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 25));

        // 在不同的坐标输出验证码的每个字符
        for (int i = 0; i < rands.length; i++) {
            graphics.drawString(String.valueOf(rands[i]), 13*i + 6, 24);
        }

        return graphics;
    }

    /**
     * 画背景
     */
    private static Graphics drawBackground(Graphics graphics) {
        graphics.setColor(new Color(0xDCDCDC));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // 随机产生120个干扰点
        for (int i = 0; i < 120; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);

            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);

            graphics.setColor(new Color(red, green, blue));
            graphics.drawOval(x, y, 1, 0);
        }

        // 加两条干扰线
        graphics.drawLine(0, 5, 90, 5);
        graphics.drawLine(0, 20, 90, 20);

        return graphics;
    }
}
