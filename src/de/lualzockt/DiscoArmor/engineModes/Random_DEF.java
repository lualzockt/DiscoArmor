package de.lualzockt.DiscoArmor.engineModes;

import de.lualzockt.DiscoArmor.DiscoArmor;
import de.lualzockt.DiscoArmor.color.ColorManager;

import org.bukkit.Color;
import org.bukkit.entity.Player;

public class Random_DEF implements EngineMode{

	@Override
	public Color color(Player p) {
		return ColorManager.colors.get(DiscoArmor.random.nextInt(ColorManager.colors.size()));
	}

	@Override
	public String getName() {
		return "Random Defined";
	}

	@Override
	public String getAuthor() {
		return "LualZockt";
	}

	@Override
	public String getDescription() {
		return "Colors will be randomly selected from the defined colors";
	}

}
