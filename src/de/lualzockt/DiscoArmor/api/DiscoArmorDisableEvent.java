package de.lualzockt.DiscoArmor.api;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class DiscoArmorDisableEvent extends PlayerEvent{

	/*
	 * Called when DiscoArmor is disabled for the specified player
	 */
	
	public DiscoArmorDisableEvent(Player who) {
		super(who);
	}
	private static HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
