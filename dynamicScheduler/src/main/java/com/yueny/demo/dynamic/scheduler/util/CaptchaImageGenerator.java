package com.yueny.demo.dynamic.scheduler.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码生成
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 上午12:37:41
 *
 */
public class CaptchaImageGenerator {
	// 随机产生字符数量
	private int charSize = 4;
	// 图片上的文字
	private String code = "";

	// 字体
	private final Font font = new Font("Times New Roman", Font.PLAIN, 18);
	// 图片高
	private int height = 26;
	// 干扰线数量
	private int lineSize = 10;
	private final Random random = new Random();

	// 随机产生的字符串
	private final String randString = "012EFGHI789ABCDJ56KLMNPQR34STUVWXYZ";

	// 图片宽
	private int width = 80;

	public CaptchaImageGenerator() {
	}

	// 设置产生的字符数量 , 默认为4
	public CaptchaImageGenerator charSize(final int charSize) {
		if (charSize < 0) {
			throw new IllegalArgumentException("charSize < 0");
		}
		this.charSize = charSize;
		return this;
	}

	// 获取图片中生成的字符内容
	public String code() {
		return this.code;
	}

	/**
	 * 生成图片
	 *
	 * @return BufferedImage instance
	 */
	public BufferedImage generate() {

		final BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		final Graphics g = image.getGraphics();
		g.fillRect(0, 0, width, height);

		g.setFont(font);
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drewLine(g);
		}

		for (int i = 1; i <= charSize; i++) {
			drewString(g, i);
		}

		g.dispose();
		return image;
	}

	/*
	 * 获取随机的字符
	 */
	public String getRandomString(final int num) {
		return String.valueOf(randString.charAt(num));
	}

	// 设置图片的高度
	public CaptchaImageGenerator height(final int height) {
		if (height < 0) {
			throw new IllegalArgumentException("height < 0");
		}
		this.height = height;
		return this;
	}

	// 设置干扰线数量, 默认为10
	public CaptchaImageGenerator lineSize(final int lineSize) {
		if (lineSize < 0) {
			throw new IllegalArgumentException("lineSize < 0");
		}
		this.lineSize = lineSize;
		return this;
	}

	// 设置图片的宽度
	public CaptchaImageGenerator width(final int width) {
		if (width < 0) {
			throw new IllegalArgumentException("width < 0");
		}
		this.width = width;
		return this;
	}

	/*
	 * 绘制干扰线
	 */
	private void drewLine(final Graphics g) {
		final int x = random.nextInt(width);
		final int y = random.nextInt(height);
		final int xl = random.nextInt(13);
		final int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/*
	 * 绘制字符串
	 */
	private void drewString(final Graphics g, final int i) {
		g.setFont(font);
		g.setColor(new Color(random.nextInt(101), random.nextInt(200), random
				.nextInt(121)));
		final String rand = String.valueOf(getRandomString(random
				.nextInt(randString.length())));
		this.code += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(final int fc, final int bc) {
		final int r = fc + random.nextInt(bc - fc - 16);
		final int g = fc + random.nextInt(bc - fc - 14);
		final int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}
}