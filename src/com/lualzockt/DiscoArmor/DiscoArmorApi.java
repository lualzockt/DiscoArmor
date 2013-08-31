package com.lualzockt.DiscoArmor;

import java.util.InputMismatchException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class DiscoArmorApi {
	public DiscoArmorApi(DiscoArmor plugin) {
		this.plugin = plugin;
	}
	private  DiscoArmor plugin = null;
	protected  void setPlugin(DiscoArmor pl) {
	plugin  = pl;
	}
	public  final String ARMOR = "armor";
	public  final String WOOL = "wool";
	public  final String NOTHING = "none";
	public  void toggle(Player  p) {
		this.plugin.toggle(p);
	}
	public  void toggle(String n) {
		Player p = Bukkit.getPlayer(n);
		if(p == null) {
			throw new NullPointerException("The player is not online.");
		}
	}
	public  boolean isEnabledForUser(Player p) {
		return this.plugin.players.containsKey(p.getName());
	}
	public  boolean isEnabledForUser(String p) {
		return this.plugin.players.containsKey(p);
	}
	public  boolean on(Player p) {
		if(isEnabledForUser(p)) {
			return false;
		}
		this.plugin.toggle(p);
		return true;
	}
	public  boolean off(Player p) {
		if(!isEnabledForUser(p)) {
			return false;
		}
		this.plugin.toggle(p);
		return true;
	}
	public long getDelay() {
		return this.plugin.delay;
	}
	public  void setDelay(long delay) {
		if(delay <= 0) {
			throw new InputMismatchException("The delay must be bigger then 0");
		}
		this.plugin.delay = delay;
	}
	public String getHelmetType() {
		return this.plugin.helmet;
	}
	public  void setHelmetType(String helmet) {
		if(helmet.equalsIgnoreCase(ARMOR) || helmet.equalsIgnoreCase(WOOL) || helmet.equalsIgnoreCase(NOTHING))
		this.plugin.helmet = helmet;
		else
		throw new InputMismatchException("Must be a valid type!");
	}
	public String getChestplateType() {
		return this.plugin.chestplate;
	}
	public  void setChestplateType(String chestplate) {
		if(chestplate.equalsIgnoreCase(ARMOR) ||   chestplate.equalsIgnoreCase(NOTHING))
		this.plugin.chestplate = chestplate;
		else
			throw new InputMismatchException("Must be a valid type!");
	}
	public String getLeggingsType() {
		return this.plugin.leggings;
	}
	public  void setLeggingsType(String leggings) {
		if(leggings.equalsIgnoreCase(ARMOR) || leggings.equalsIgnoreCase(NOTHING))
		this.plugin.leggings = leggings;
		else
			throw new InputMismatchException("Must be a valid type!");
	}
	public String getBootsType() {
		return this.plugin.boots;
	}
	public  void setBootsType(String boots) {
		if(boots.equalsIgnoreCase(ARMOR) || boots.equalsIgnoreCase(NOTHING))
		this.plugin.boots = boots;
		else
			throw new InputMismatchException("Must be a valid type!");
	}
	public String getToggleTrueMessage() {
		return this.plugin.toggleTrue;
	}
	public  void setToggleTrueMessage(String toggleTrue) {
		this.plugin.toggleTrue = toggleTrue;
	}
	public String getToggleFalseMessage() {
		return this.plugin.toggleFalse;
	}
	public  void setToggleFalseMessage(String toggleFalse) {
		this.plugin.toggleFalse = toggleFalse;
	}
	public String getHelmetName() {
		return this.plugin.helmet_name;
	}
	public  void setHelmetName(String helmet_name) {
		this.plugin.helmet_name = helmet_name;
	}
	public String getChestName() {
		return this.plugin.chest_name;
	}
	public  void setChestName(String chest_name) {
		this.plugin.chest_name = chest_name;
	}
	public String getLeggingsName() {
		return this.plugin.leggings_name;
	}
	public  void setLeggingsName(String leggings_name) {
		this.plugin.leggings_name = leggings_name;
	}
	public String getBootsName() {
		return this.plugin.boots_name;
	}
	public  void setBootsName(String boots_name) {
		this.plugin.boots_name = boots_name;
	}
	public boolean isEnableMaxTime() {
		return this.plugin.enableMaxTime;
	}
	public  void setEnableMaxTime(boolean enableMaxTime) {
		this.plugin.enableMaxTime = enableMaxTime;
	}
}
