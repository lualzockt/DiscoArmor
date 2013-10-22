package com.lualzockt.DiscoArmor;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;

public final class ColorUtils {
	public static int engine = 0;
	public static final List<Color> colors = new LinkedList<Color>();
	
	public static final int WHITE = 0xFFFFFF;
	public static final int BLACK = 0x000000;
	public static final int RED = 0xFF0000;
	public static final int BLUE = 0x0000FF;
	public static final int GREEN = 0x00FF00;
	public static final int YELLOW = 0xFFFF00;			
	public static Color random() {
		Random r = new Random();
		if(engine == 0) {
			return Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		}
		else if(engine == 1)  {
			return ColorUtils.colors.get(r.nextInt(colors.size()));
		}
		return Color.fromRGB(WHITE);
	}
	public static Color parse(String s) {
		Color color = DiscoColor.getColor(s);
		if(color != null) {
			return color;
		}
		String[] colors = s.split(",");
		if(colors.length != 3){
			DiscoArmor.getInstance().getLogger().warning("Invalid color: " + s);
			return null;
		}
		try {
			int r = Integer.parseInt(colors[0]);
			int g = Integer.parseInt(colors[1]);
			int b = Integer.parseInt(colors[2]);
			return Color.fromRGB(r,g,b);
		}catch(NumberFormatException ex) {
			DiscoArmor.getInstance().getLogger().warning("Invalid color: " + s);
			return null;
		}
	}
}
