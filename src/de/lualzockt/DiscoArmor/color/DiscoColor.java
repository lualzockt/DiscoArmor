package de.lualzockt.DiscoArmor.color;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Color;


public class DiscoColor
{
  private static Map<String, Color> colors = new HashMap();

  public static Color getColor(String s) {
    return (Color)colors.get(s.toUpperCase());
  }

  public static String getName(Color color) {
    for (String n : colors.keySet()) {
      Color c = (Color)colors.get(n);
      if (color == c) {
        return n;
      }
    }
    return null;
  }

  public static void setup() {
    colors.clear();
    colors.put("WHITE", Color.fromRGB(16777215));
    colors.put("BLACK", Color.fromRGB(0));
    colors.put("RED", Color.fromRGB(16711680));
    colors.put("BLUE", Color.fromRGB(255));
    colors.put("AQUA", Color.AQUA);
    colors.put("ORANGE", Color.ORANGE);
    colors.put("PURPLE", Color.PURPLE);
    colors.put("MAROON", Color.MAROON);
    colors.put("PURPLE", Color.PURPLE);
    colors.put("GREEN", Color.fromRGB(16776960));
    colors.put("TEAL", Color.TEAL);
    colors.put("GOLD", Color.fromRGB(13938487));
    colors.put("OLIVE", Color.OLIVE);
    colors.put("SILVER", Color.fromRGB(12632256));
    colors.put("PINK", Color.fromRGB(16711886));
  }
}