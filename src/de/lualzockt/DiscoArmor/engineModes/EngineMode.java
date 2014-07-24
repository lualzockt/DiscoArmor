package de.lualzockt.DiscoArmor.engineModes;

import org.bukkit.Color;
import org.bukkit.entity.Player;

public interface EngineMode{
	public Color color(Player p);
	public String getName();
	public String getAuthor();
	public String getDescription();
	
	
}