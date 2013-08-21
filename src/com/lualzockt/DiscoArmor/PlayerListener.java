package com.lualzockt.DiscoArmor;


import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener{
	private DiscoArmor plugin;
	public PlayerListener(DiscoArmor discoArmor) {
		this.plugin = discoArmor;
	}
	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		if(plugin.aliases.contains(e.getMessage().replace("/", "").toLowerCase())) {
			e.setMessage("/da toggle");
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(plugin.players.containsKey(e.getEntity().getName())) {
			plugin.players.remove(e.getEntity().getName());
			plugin.oldarmor.remove(e.getEntity().getName());
			Iterator<ItemStack> it = e.getDrops().iterator();
			while(it.hasNext()) {
				ItemStack i = it.next();
				if(i == null) continue;
				String m = i.getItemMeta().getDisplayName();
				if(m == null) continue;
				if(m.equals(plugin.boots_name) || m.equals(plugin.helmet_name) || m.equals(plugin.chest_name) || m.equals(plugin.leggings_name) ){
					it.remove();
				}
			}
		}
		
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(!plugin.players.containsKey(p.getName()))return;
		ItemStack i = e.getCurrentItem();
		if(i == null) {
			return;
		}
		if(e.getSlotType().equals(SlotType.ARMOR)) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		if(plugin.players.containsKey(e.getPlayer().getName())) {
			plugin.players.remove(e.getPlayer().getName());
			try {
				e.getPlayer().getInventory().setArmorContents(plugin.oldarmor.get(e.getPlayer().getName()));
			}catch(Exception ex) {
				
			}
			plugin.oldarmor.remove(e.getPlayer().getName());
		}
	}
	@EventHandler
	public void onKick(PlayerKickEvent e){
		if(plugin.players.containsKey(e.getPlayer().getName())) {
			plugin.oldarmor.remove(e.getPlayer().getName());
			plugin.players.remove(e.getPlayer().getName());
		}
	}

}
