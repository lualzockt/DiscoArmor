package com.lualzockt.DiscoArmor;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Color;

public class DiscoColor{
	private static Map<String,Color> colors = new HashMap<String,Color>();
	
	public static Color getColor(String s) {
		return colors.get(s.toUpperCase());
	}
	
	public static String getName(Color color) {
		for(String n : colors.keySet()) {
			Color c = colors.get(n);
			if(color == c) {
				return n;
			}
		}
		return null;
	}
	
	public static void setup()  {
		colors.clear();
		colors.put("WHITE", Color.fromRGB(0xFFFFFF));
		colors.put("BLACK", Color.fromRGB(0x000000));
		colors.put("RED", Color.fromRGB(0xFF0000));
		colors.put("BLUE", Color.fromRGB(0x0000FF));
		colors.put("AQUA", Color.AQUA);
		colors.put("ORANGE", Color.ORANGE);
		colors.put("PURPLE", Color.PURPLE);
		colors.put("MAROON", Color.MAROON);
		colors.put("PURPLE", Color.PURPLE);
		colors.put("GREEN", Color.fromRGB(0xFFFF00));
		colors.put("TEAL", Color.TEAL);
		colors.put("GOLD", Color.fromRGB(0xD4AF37));
		colors.put("OLIVE", Color.OLIVE);
		colors.put("SILVER", Color.fromRGB(0xC0C0C0));
		colors.put("PINK", Color.fromRGB(0xff00ce));
	}
}
