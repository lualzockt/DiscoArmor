package com.lualzockt.DiscoArmor;

import org.bukkit.entity.Player;

public abstract class Api {
	private static DiscoArmor plugin = null;
	protected static void setPlugin(DiscoArmor pl) {
	plugin  = pl;
	}
	
	public static void toggle(Player  p) {
		plugin.toggle(p);
	}
	public static boolean enabled(Player p) {
		return plugin.players.containsKey(p.getName());
	}
	public static boolean on(Player p) {
		if(enabled(p)) {
			return false;
		}
		plugin.toggle(p);
		return true;
	}
	public static boolean off(Player p) {
		if(!enabled(p)) {
			return false;
		}
		plugin.toggle(p);
		return true;
	}
	public long getDelay() {
		return plugin.delay;
	}
	public static void setDelay(long delay) {
		plugin.delay = delay;
	}
	public String getHelmet() {
		return plugin.helmet;
	}
	public static void setHelmet(String helmet) {
		plugin.helmet = helmet;
	}
	public String getChestplate() {
		return plugin.chestplate;
	}
	public static void setChestplate(String chestplate) {
		plugin.chestplate = chestplate;
	}
	public String getLeggings() {
		return plugin.leggings;
	}
	public static void setLeggings(String leggings) {
		plugin.leggings = leggings;
	}
	public String getBoots() {
		return plugin.boots;
	}
	public static void setBoots(String boots) {
		plugin.boots = boots;
	}
	public String getToggleTrueMessage() {
		return plugin.toggleTrue;
	}
	public static void setToggleTrueMessage(String toggleTrue) {
		plugin.toggleTrue = toggleTrue;
	}
	public String getToggleFalse() {
		return plugin.toggleFalse;
	}
	public static void setToggleFalse(String toggleFalse) {
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
