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
 * �Զ�����֤��
 * 
 * @author smallpawpaw
 * @version 1.0.0
 */
public class Imgcode {

	/** ���������Զ�����֤��������ļ� */
	private static Properties prop;

	private static Graphics2D graphics2d;

	private static Graphics graphics;

	/** ��֤������ */
	private static String content;

	/** ��֤��ͼƬ�ĳ� */
	private static int width;

	/** ��֤��ͼƬ�ĸ� */
	private static int height;

	/** ����ɫ */
	private static String bgColor;

	/** �߿���ɫ */
	private static String borderColor;

	/** �Ƿ�ʹ�ñ߿� */
	private static String useBorder;

	/** ��֤������ */
	private static String type;

	/** ��֤���ַ��� */
	private static int length;

	/** �����С */
	private static int fontSize;

	/** ���� */
	private static String fontFamily;

	/** ������ɫ */
	private static String fontColor;

	/** ��ʼ�ַ�����߿��� */
	private static int startSpace;

	/** ������ */
	private static int space;

	/** ������б�ǶȾ���ֵ */
	private static int angle;

	/** ��֤��������� */
	private static String interfereType;

	/** ��֤�������ɫ */
	private static String interfereColor;

	/** �������� */
	private static int interfereNum;

	/** ���ŵ��С����������Ϊdotʱ��Ч */
	private static int interfereRadius;

	private Imgcode() {
	}

	/**
	 * ��ʼ����֤��������ò���
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
	 * �����Զ�����֤��ͼƬ
	 * 
	 * @return String ��֤������ BufferedImage ��֤��ͼƬ
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
	 * ���ñ���ɫ
	 */
	private static void setBg() {
		String[] bg = bgColor.split(",");
		graphics.setColor(new Color(Integer.parseInt(bg[0]), Integer.parseInt(bg[1]), Integer.parseInt(bg[2])));
		graphics.fillRect(0, 0, width, height);
		graphics.clipRect(0, 0, width, height);
	}

	/**
	 * ���ñ߿�
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
	 * �����ַ�����֤������
	 */
	private static void setContent() {
		content = "";
		Random random = new Random();
		/** ��֤��������Դ */
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

		/** ������֤�� */
		if ("number".equals(type)) {
			for (int i = 0; i < length; i++) {
				content += res[52 + random.nextInt(10)];
			}
		}

		/** ��ĸ��֤�� */
		if ("letter".equals(type)) {
			for (int i = 0; i < length; i++) {
				content += res[random.nextInt(52)];
			}
		}

		/** �����֤�� */
		if ("blend".equals(type)) {
			for (int i = 0; i < length; i++) {
				content += res[random.nextInt(62)];
			}
		}
	}

	/**
	 * ������֤���ַ���ʽ
	 */
	private static void setStyle() {
		Random random = new Random();
		if (fontColor.length() > 0) {
			/** ָ��ɫ���� */
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
			/** ���ɫ���� */
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
	 * ���ø���Ч��
	 */
	private static void setInterfere() {
		Random random = new Random();
		int x1, y1, x2, y2;
		if (interfereColor.length() > 0) {
			String[] interfere = interfereColor.split(",");
			graphics.setColor(new Color(Integer.parseInt(interfere[0]), Integer.parseInt(interfere[1]),
					Integer.parseInt(interfere[2])));
			/** ָ��ɫ�ĸ����� */
			if ("line".equals(interfereType)) {
				for (int i = 0; i < interfereNum; i++) {
					x1 = random.nextInt(width);
					y1 = random.nextInt(height);
					x2 = random.nextInt(width);
					y2 = random.nextInt(height);
					graphics.drawLine(x1, y1, x2, y2);
				}
			} else if ("dot".equals(interfereType)) {
				/** ָ��ɫ�ĸ��ŵ� */
				for (int i = 0; i < interfereNum; i++) {
					x1 = random.nextInt(width);
					y1 = random.nextInt(height);
					graphics.fillOval(x1, y1, interfereRadius, interfereRadius);
				}
			}
		} else {
			/** ���ɫ�ĸ����� */
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
				/** ���ɫ���ŵ� */
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
	 * ��ȡ���ɫ
	 * 
	 * @return �������ɫ
	 */
	private static Color getRandomColor() {
		Random random = new Random();
		Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		return color;
	}
}