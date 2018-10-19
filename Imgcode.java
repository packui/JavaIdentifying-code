package com.github.smallpawpaw.imgcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * 自定义验证码
 * 
 * @author smallpawpaw
 * @version 1.0.0
 */
public class Imgcode {

	/** 用来加载自定义验证码的配置文件 */
	private static Properties prop;

	private static Graphics2D graphics2d;

	private static Graphics graphics;

	/** 验证码内容 */
	private static String content;

	/** 验证码图片的长 */
	private static int width;

	/** 验证码图片的高 */
	private static int height;

	/** 背景色 */
	private static String bgColor;

	/** 边框颜色 */
	private static String borderColor;

	/** 是否使用边框 */
	private static String useBorder;

	/** 验证码类型 */
	private static String type;

	/** 验证码字符数 */
	private static int length;

	/** 字体大小 */
	private static int fontSize;

	/** 字体 */
	private static String fontFamily;

	/** 字体颜色 */
	private static String fontColor;

	/** 起始字符距离边框间距 */
	private static int startSpace;

	/** 字体间距 */
	private static int space;

	/** 字体倾斜角度绝对值 */
	private static int angle;

	/** 验证码干扰类型 */
	private static String interfereType;

	/** 验证码干扰颜色 */
	private static String interfereColor;

	/** 干扰数量 */
	private static int interfereNum;

	/** 干扰点大小，干扰类型为dot时生效 */
	private static int interfereRadius;

	private Imgcode() {
	}

	/**
	 * 初始化验证码各项配置参数
	 */
	private static void init() {
		width = Integer.parseInt(prop.getProperty("width"));
		height = Integer.parseInt(prop.getProperty("height"));
		bgColor = prop.getProperty("bgColor");
		borderColor = prop.getProperty("borderColor");
		useBorder = prop.getProperty("useBorder");

		type = prop.getProperty("type");
		length = Integer.parseInt(prop.getProperty("length"));
		fontSize = Integer.parseInt(prop.getProperty("fontSize"));
		fontFamily = prop.getProperty("fontFamily");
		fontColor = prop.getProperty("fontColor");

		startSpace = Integer.parseInt(prop.getProperty("startSpace"));
		space = Integer.parseInt(prop.getProperty("space"));
		angle = Integer.parseInt(prop.getProperty("angle"));

		interfereType = prop.getProperty("interfereType");
		interfereNum = Integer.parseInt(prop.getProperty("interfereNum"));
		interfereColor = prop.getProperty("interfereColor");
		interfereRadius = Integer.parseInt(prop.getProperty("interfereRadius"));
	}

	/**
	 * 生成自定义验证码图片
	 * 
	 * @return String 验证码内容 BufferedImage 验证码图片
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Map<String, BufferedImage> createImageCode(Properties properties)
			throws FileNotFoundException, IOException {
		prop = properties;
		init();
		Map<String, BufferedImage> map = new HashMap<>();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = image.getGraphics();
		graphics2d = (Graphics2D) graphics;
		setBg();
		setBorder();
		setContent();
		setStyle();
		setInterfere();
		map.put(content, image);
		return map;
	}

	/**
	 * 设置背景色
	 */
	private static void setBg() {
		String[] bg = bgColor.split(",");
		graphics.setColor(new Color(Integer.parseInt(bg[0]), Integer.parseInt(bg[1]), Integer.parseInt(bg[2])));
		graphics.fillRect(0, 0, width, height);
		graphics.clipRect(0, 0, width, height);
	}

	/**
	 * 设置边框
	 */
	private static void setBorder() {
		if ("true".equals(useBorder)) {
			String[] border = borderColor.split(",");
			graphics.setColor(
					new Color(Integer.parseInt(border[0]), Integer.parseInt(border[1]), Integer.parseInt(border[2])));
			graphics.drawRect(0, 0, width - 1, height - 1);
		}
	}

	/**
	 * 设置字符串验证码内容
	 */
	private static void setContent() {
		content = "";
		Random random = new Random();
		/** 验证码内容资源 */
		String[] res = new String[62];
		int count = -1;
		for (char j = 'A'; j <= 'Z'; j++) {
			res[++count] = "" + j;
		}
		for (char j = 'a'; j <= 'z'; j++) {
			res[++count] = "" + j;
		}
		for (int j = 0; j <= 9; j++) {
			res[++count] = "" + j;
		}

		/** 数字验证码 */
		if ("number".equals(type)) {
			for (int i = 0; i < length; i++) {
				content += res[52 + random.nextInt(10)];
			}
		}

		/** 字母验证码 */
		if ("letter".equals(type)) {
			for (int i = 0; i < length; i++) {
				content += res[random.nextInt(52)];
			}
		}

		/** 混合验证码 */
		if ("blend".equals(type)) {
			for (int i = 0; i < length; i++) {
				content += res[random.nextInt(62)];
			}
		}
	}

	/**
	 * 设置验证码字符样式
	 */
	private static void setStyle() {
		Random random = new Random();
		if (fontColor.length() > 0) {
			/** 指定色字体 */
			String[] font = fontColor.split(",");
			graphics2d.setColor(
					new Color(Integer.parseInt(font[0]), Integer.parseInt(font[1]), Integer.parseInt(font[2])));
			for (int i = 0; i < content.length(); i++) {
				double theta = (random.nextInt(2 * angle) - angle) * Math.PI / 180;
				graphics2d.setFont(new Font(fontFamily, Font.BOLD, fontSize));
				graphics2d.rotate(theta, startSpace, height);
				graphics2d.drawString(String.valueOf(content.charAt(i)), startSpace, fontSize);
				graphics2d.rotate(-theta, startSpace, height);
				startSpace += space;
			}
		} else {
			/** 随机色字体 */
			for (int i = 0; i < content.length(); i++) {
				graphics2d.setColor(getRandomColor());
				double theta = (random.nextInt(2 * angle) - angle) * Math.PI / 180;
				graphics2d.setFont(new Font(fontFamily, Font.BOLD, fontSize));
				graphics2d.rotate(theta, startSpace, height);
				graphics2d.drawString(String.valueOf(content.charAt(i)), startSpace, fontSize);
				graphics2d.rotate(-theta, startSpace, height);
				startSpace += space;
			}
		}
	}

	/**
	 * 设置干扰效果
	 */
	private static void setInterfere() {
		Random random = new Random();
		int x1, y1, x2, y2;
		if (interfereColor.length() > 0) {
			String[] interfere = interfereColor.split(",");
			graphics.setColor(new Color(Integer.parseInt(interfere[0]), Integer.parseInt(interfere[1]),
					Integer.parseInt(interfere[2])));
			/** 指定色的干扰线 */
			if ("line".equals(interfereType)) {
				for (int i = 0; i < interfereNum; i++) {
					x1 = random.nextInt(width);
					y1 = random.nextInt(height);
					x2 = random.nextInt(width);
					y2 = random.nextInt(height);
					graphics.drawLine(x1, y1, x2, y2);
				}
			} else if ("dot".equals(interfereType)) {
				/** 指定色的干扰点 */
				for (int i = 0; i < interfereNum; i++) {
					x1 = random.nextInt(width);
					y1 = random.nextInt(height);
					graphics.fillOval(x1, y1, interfereRadius, interfereRadius);
				}
			}
		} else {
			/** 随机色的干扰线 */
			if ("line".equals(interfereType)) {
				for (int i = 0; i < interfereNum; i++) {
					graphics.setColor(getRandomColor());
					x1 = random.nextInt(width);
					y1 = random.nextInt(height);
					x2 = random.nextInt(width);
					y2 = random.nextInt(height);
					graphics.drawLine(x1, y1, x2, y2);
				}
			} else if ("dot".equals(interfereType)) {
				/** 随机色干扰点 */
				for (int i = 0; i < interfereNum; i++) {
					graphics.setColor(getRandomColor());
					x1 = random.nextInt(width);
					y1 = random.nextInt(height);
					graphics.fillOval(x1, y1, interfereRadius, interfereRadius);
				}
			}
		}
	}

	/**
	 * 获取随机色
	 * 
	 * @return 返回随机色
	 */
	private static Color getRandomColor() {
		Random random = new Random();
		Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		return color;
	}
}