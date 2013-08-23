package com.lualzockt.DiscoArmor;

import java.util.InputMismatchException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Api {
	private static DiscoArmor plugin = null;
	protected static void setPlugin(DiscoArmor pl) {
	plugin  = pl;
	}
	public static final String ARMOR = "armor";
	public static final String WOOL = "wool";
	public static final String NOTHING = "none";
	public static void toggle(Player  p) {
		plugin.toggle(p);
	}
	public static void toggle(String n) {
		Player p = Bukkit.getPlayer(n);
		if(p == null) {
			throw new NullPointerException("The player is not online.");
		}
	}
	public static boolean isEnabledForUser(Player p) {
		return plugin.players.containsKey(p.getName());
	}
	public static boolean isEnabledForUser(String p) {
		return plugin.players.containsKey(p);
	}
	public static boolean on(Player p) {
		if(isEnabledForUser(p)) {
			return false;
		}
		plugin.toggle(p);
		return true;
	}
	public static boolean off(Player p) {
		if(!isEnabledForUser(p)) {
			return false;
		}
		plugin.toggle(p);
		return true;
	}
	public long getDelay() {
		return plugin.delay;
	}
	public static void setDelay(long delay) {
		if(delay <= 0) {
			throw new InputMismatchException("The delay must be bigger then 0");
		}
		plugin.delay = delay;
	}
	public String getHelmetType() {
		return plugin.helmet;
	}
	public static void setHelmetType(String helmet) {
		if(helmet.equalsIgnoreCase(ARMOR) || helmet.equalsIgnoreCase(WOOL) || helmet.equalsIgnoreCase(NOTHING))
		plugin.helmet = helmet;
		else
		throw new InputMismatchException("Must be a valid type!");
	}
	public String getChestplateType() {
		return plugin.chestplate;
	}
	public static void setChestplateType(String chestplate) {
		if(chestplate.equalsIgnoreCase(ARMOR) ||   chestplate.equalsIgnoreCase(NOTHING))
		plugin.chestplate = chestplate;
		else
			throw new InputMismatchException("Must be a valid type!");
	}
	public String getLeggingsType() {
		return plugin.leggings;
	}
	public static void setLeggingsType(String leggings) {
		if(leggings.equalsIgnoreCase(ARMOR) || leggings.equalsIgnoreCase(NOTHING))
		plugin.leggings = leggings;
		else
			throw new InputMismatchException("Must be a valid type!");
	}
	public String getBootsType() {
		return plugin.boots;
	}
	public static void setBootsType(String boots) {
		if(boots.equalsIgnoreCase(ARMOR) || boots.equalsIgnoreCase(NOTHING))
		plugin.boots = boots;
		else
			throw new InputMismatchException("Must be a valid type!");
	}
	public String getToggleTrueMessage() {
		return plugin.toggleTrue;
	}
	public static void setToggleTrueMessage(String toggleTrue) {
		plugin.toggleTrue = toggleTrue;
	}
	public String getToggleFalseMessage() {
		return plugin.toggleFalse;
	}
	public static void setToggleFalseMessage(String toggleFalse) {
		plugin.toggleFalse = toggleFalse;
	}
	public String getHelmetName() {
		return plugin.helmet_name;
	}
	public static void setHelmetName(String helmet_name) {
		plugin.helmet_name = helmet_name;
	}
	public String getChestName() {
		return plugin.chest_name;
	}
	public static void setChestName(String chest_name) {
		plugin.chest_name = chest_name;
	}
	public String getLeggingsName() {
		return plugin.leggings_name;
	}
	public static void setLeggingsName(String leggings_name) {
		plugin.leggings_name = leggings_name;
	}
	public String getBootsName() {
		return plugin.boots_name;
	}
	public static void setBootsName(String boots_name) {
		plugin.boots_name = boots_name;
	}
	public boolean isEnableMaxTime() {
		return plugin.enableMaxTime;
	}
	public static void setEnableMaxTime(boolean enableMaxTime) {
		plugin.enableMaxTime = enableMaxTime;
	}
}
