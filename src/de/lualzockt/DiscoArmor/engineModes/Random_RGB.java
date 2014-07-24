package de.lualzockt.DiscoArmor.engineModes;

import de.lualzockt.DiscoArmor.DiscoArmor;

import org.bukkit.Color;
import org.bukkit.entity.Player;

public class Random_RGB implements EngineMode{

	@Override
	public Color color(Player p) {
		return Color.fromRGB(DiscoArmor.random.nextInt(255), DiscoArmor.random.nextInt(255), DiscoArmor.random.nextInt(255));
	}

	@Override
	public String getName() {
		return "RandomRGB";
	}

	@Override
	public String getAuthor() {
		return "LualZockt";
	}

	@Override
	public String getDescription() {
		return "Colors will be randomly chosen from the rgb range";
	}

}
