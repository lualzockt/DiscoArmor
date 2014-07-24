package de.lualzockt.DiscoArmor.engineModes;

import de.lualzockt.DiscoArmor.DiscoArmor;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;


public class Random_DYE implements EngineMode{

	@Override
	public Color color(Player p) {
		return DyeColor.values()[DiscoArmor.random.nextInt(DyeColor.values().length)].getColor();
	}

	@Override
	public String getName() {
		return "RandomDYE";
	}

	@Override
	public String getAuthor() {
		return "LualZockt";
	}

	@Override
	public String getDescription() {
		return "Colors will be randomly chosen from the default minecraft dye colors";
	}

}
