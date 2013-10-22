package com.lualzockt.DiscoArmor;


import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener extends DiscoArmorManager implements Listener{
	public PlayerListener(DiscoArmor plugin) {
		super(plugin);
	}
	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		if(getPlugin().aliases.contains(e.getMessage().replace("/", "").toLowerCase())) {
			e.setMessage("/da toggle");
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(getPlugin().players.containsKey(p.getName())) {
			getPlugin().players.remove(p.getName());
			Iterator<ItemStack> it = e.getDrops().iterator();
			while(it.hasNext()) {
				ItemStack i = it.next();
				if(i == null) continue;
				String m = i.getItemMeta().getDisplayName();
				if(m == null) continue;
				if(m.equals(getPlugin().boots_name) || m.equals(getPlugin().helmet_name) || m.equals(getPlugin().chest_name) || m.equals(getPlugin().leggings_name) ){
					it.remove();
				}
			}
			if(this.getPlugin().oldarmor.containsKey(p.getName())) {
				this.getPlugin().oldarmor.remove(p.getName());
				ItemStack[] armor = this.getPlugin().oldarmor.get(p.getName());
				for(ItemStack item : armor) {
					if(item == null) {
						continue;
					}
					e.getDrops().add(item);
				}
			}
		}
		
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(!getPlugin().players.containsKey(p.getName()))return;
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
		if(getPlugin().players.containsKey(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			getPlugin().players.remove(e.getPlayer().getName());
			if(getPlugin().oldarmor.containsKey(p.getName())) {
				ItemStack[] oa = getPlugin().oldarmor.get(p.getName());
				p.getInventory().setHelmet(oa[0]);
				p.getInventory().setChestplate(oa[1]);
				p.getInventory().setLeggings(oa[2]);
				p.getInventory().setBoots(oa[3]);
				getPlugin().oldarmor.remove(p.getName());
			}
			
		}
	}
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		ItemStack i = e.getItemDrop().getItemStack();
		if(i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
			String name = i.getItemMeta().getDisplayName();
			if(name.equalsIgnoreCase(getPlugin().chest_name)||name.equalsIgnoreCase(getPlugin().boots_name)||name.equalsIgnoreCase(getPlugin().helmet_name)||name.equalsIgnoreCase(getPlugin().leggings_name) ) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onKick(PlayerKickEvent e){
		if(getPlugin().players.containsKey(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			if(getPlugin().oldarmor.containsKey(p.getName())) {
				ItemStack[] oa = getPlugin().oldarmor.get(p.getName());
				p.getInventory().setHelmet(oa[0]);
				p.getInventory().setChestplate(oa[1]);
				p.getInventory().setLeggings(oa[2]);
				p.getInventory().setBoots(oa[3]);
				getPlugin().oldarmor.remove(p.getName());
			}
			getPlugin().players.remove(e.getPlayer().getName());
		}
	}														
	@Override
	public void setup() {
		this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
	}
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
	
		if(this.getPlugin().players.containsValue(e.getPlayer().getName())) {
			
			Bukkit.getScheduler().runTaskLater(this.getPlugin(), new Runnable() {
				public void run() {
					
				}
			}, 10L);
		}
	}
}
