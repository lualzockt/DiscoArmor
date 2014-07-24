package de.lualzockt.DiscoArmor.engineModes;

import java.util.ArrayList;
import java.util.List;

import de.lualzockt.DiscoArmor.DiscoArmor;

import org.bukkit.Color;
import org.bukkit.entity.Player;

public class Random_RBW implements EngineMode{

	private static List<Color> colors = new ArrayList<>();
	static {
		colors.add(Color.RED);
		colors.add(Color.ORANGE);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.AQUA);
		colors.add(Color.TEAL);
		colors.add(Color.PURPLE);
	}
	@Override
	public Color color(Player p) {
		return colors.get(DiscoArmor.random.nextInt(colors.size()));
	}

	@Override
	public String getName() {
		return "Random Rainbow";
	}

	@Override
	public String getAuthor() {
		return "LualZockt";
	}

	@Override
	public String getDescription() {
		return "Colors will be randomly selected from the rainbow colors";
	}

}
