package braynstorm.core;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;


public class Garage {
	public static void glSetColor(float r, float g, float b) {
		glSetColor(r, g, b, 1);
	}

	public static void glSetColor(float r, float g, float b, float a) {
		GL11.glColor4f(r / 255, g / 255, b / 255, a);
	}


	/**
	 * 
	 * @param color
	 *            "#FFFFFFf" last one is alpha.
	 */
	public static void glSetColor(String color) {

		int[] rgba = convertHexToInt(color);
		glSetColor(rgba[0], rgba[1], rgba[2], rgba[3] / 100);
	}

	public static String convertFloatToHexColor(float red, float green, float blue, float alpha) {
		String hex = "#";
		hex += Integer.toHexString((int) (red));
		hex += Integer.toHexString((int) (green));
		hex += Integer.toHexString((int) (blue));
		hex += Integer.toHexString((int) ((alpha / 100) * 15));
		// System.out.println(hex);
		return hex;
	}

	public static int[] convertHexToInt(String color) {
		int[] rgba = new int[4];

		if (color.substring(0, 1).equals("#")) {


			rgba[0] = Integer.parseInt(color.substring(1, 3), 16);
			rgba[1] = Integer.parseInt(color.substring(3, 5), 16);
			rgba[2] = Integer.parseInt(color.substring(5, 7), 16);


			if (color.length() == 8) {
				rgba[3] = (Integer.parseInt(color.substring(7, 8), 16) / 15) * 100;
			} else {
				rgba[3] = 100;
			}
		}
		// System.out.println(rgba[0]);
		// System.out.println(rgba[1]);
		// System.out.println(rgba[2]);
		// System.out.println(rgba[3]);

		return rgba;
	}

	public static FloatBuffer toFloatBuffer(float[] vals) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vals.length);
		buffer.put(vals);
		buffer.flip();
		return buffer;
	}
}
