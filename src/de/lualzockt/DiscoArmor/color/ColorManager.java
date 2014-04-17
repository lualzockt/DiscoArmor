package de.lualzockt.DiscoArmor.color;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.lualzockt.DiscoArmor.DiscoArmor;
import de.lualzockt.DiscoArmor.api.ArmorColorEvent;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;


public final class ColorManager
{
  public static int engine = 0;
  public static final List<Color> colors = new LinkedList();

  private static Color getRandom()
  {
    Random r = new Random();
    if (engine == 0)
      return Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    if (engine == 1) {
      return (Color)colors.get(r.nextInt(colors.size()));
    }
    return Color.fromRGB(16777215);
  }

  public static Color random(Player p)
  {
    ArmorColorEvent e = new ArmorColorEvent(p, getRandom());
    Bukkit.getPluginManager().callEvent(e);
    return e.getColor();
  }

  public static Color parse(String s)
  {
    Color color = DiscoColor.getColor(s);
    if (color != null) {
      return color;
    }
    String[] colors = s.split(",");
    if (colors.length != 3) {
      DiscoArmor.getInstance().getLogger().warning("Invalid color: " + s);
      return null;
    }
    try {
      int r = Integer.parseInt(colors[0]);
      int g = Integer.parseInt(colors[1]);
      int b = Integer.parseInt(colors[2]);
      return Color.fromRGB(r, g, b);
    } catch (NumberFormatException ex) {
      DiscoArmor.getInstance().getLogger().warning("Invalid color: " + s);
    }return null;
  }
}