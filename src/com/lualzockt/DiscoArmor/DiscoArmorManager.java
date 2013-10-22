package com.lualzockt.DiscoArmor;


public abstract class DiscoArmorManager {
	private DiscoArmor plugin;
	public DiscoArmor getPlugin() {
		return this.plugin;
	}
	public boolean isEnabled() {
		return this.plugin != null && this.plugin.isEnabled();
	}
	public abstract void setup();
	public DiscoArmorManager(DiscoArmor plugin) {
		this.plugin = plugin;
		this.setup();
	}
}