package com.lualzockt.DiscoArmor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscoArmor extends JavaPlugin{
	

	public Map<String,Integer> players;
	public Map<String, ItemStack[]> oldarmor = new HashMap<String, ItemStack[]>();
	long delay;
	String helmet = "armor";
	String chestplate = "armor";
	String leggings = "armor";
	String boots = "armor";
	String toggleTrue = "You are now using DiscoArmor";
	String toggleFalse = "You are now using DiscoArmor";
	String helmet_name = "Discohelmet";
	String chest_name = "Discochestplate";
	String leggings_name = "Discoleggings";
	String boots_name = "Discoboots";
	List<String> aliases;

	boolean enableMaxTime = false;
	int time;
	int task = 0; // The id of the Scheduler Task
	public void startScheduling() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for(String s : players.keySet()){
					Player p = Bukkit.getPlayerExact(s);
					if(p == null) {
						players.remove(s); // Remove players if they are no longer online
						continue;
					}
					Random r = new Random();
					try {
						if(enableMaxTime) {
						players.put(s, players.get(s) + 1);
						}
						if(players.get(s) > time) {
							Player pl = Bukkit.getPlayer(s);
							pl.chat("/da toggle");
							continue;
						}
						if(helmet.equalsIgnoreCase("armor")) {
							if(p.getInventory().getHelmet() == null)
							{
								ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS,1);
								ItemMeta meta4 = item4.getItemMeta();
								meta4.setDisplayName(boots_name);
								item4.setItemMeta(meta4);
							
								p.getInventory().setLeggings(item4);
							}
								LeatherArmorMeta meta1 = (LeatherArmorMeta) p.getInventory().getHelmet().getItemMeta(); 
								meta1.setColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256))); // Randomize Color
								p.getInventory().getHelmet().setItemMeta(meta1);
						}else if(helmet.equalsIgnoreCase("wool")) {
							p.getInventory().setHelmet(new ItemStack(Material.WOOL,1,(byte) (r.nextInt(14) + 1)));
						}
						if(chestplate.equalsIgnoreCase("armor")) {
								if(p.getInventory().getChestplate() == null)
								{
									ItemStack item2 = new ItemStack(Material.LEATHER_CHESTPLATE,1);
									ItemMeta meta2 = item2.getItemMeta();
									meta2.setDisplayName(chest_name);
									item2.setItemMeta(meta2);
									p.getInventory().setChestplate(item2);
								}
								LeatherArmorMeta meta2 = (LeatherArmorMeta) p.getInventory().getChestplate().getItemMeta();
								meta2.setColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
								p.getInventory().getChestplate().setItemMeta(meta2);
							}
						if(leggings.equalsIgnoreCase("armor")) {
								if(p.getInventory().getLeggings() == null)
								{
									ItemStack item3 = new ItemStack(Material.LEATHER_CHESTPLATE,1);
									ItemMeta meta3 = item3.getItemMeta();
									meta3.setDisplayName(leggings_name);
									item3.setItemMeta(meta3);
								
									p.getInventory().setLeggings(item3);
								}
								LeatherArmorMeta meta3 = (LeatherArmorMeta) p.getInventory().getLeggings().getItemMeta();
								meta3.setColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
								p.getInventory().getLeggings().setItemMeta(meta3);
							}
						if(boots.equalsIgnoreCase("armor")) {
								if(p.getInventory().getBoots() == null) 
								{
									ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS,1);
									ItemMeta meta4 = item4.getItemMeta();
									meta4.setDisplayName(boots_name);
									item4.setItemMeta(meta4);
								
									p.getInventory().setBoots(item4);
								}
								LeatherArmorMeta meta4 = (LeatherArmorMeta) p.getInventory().getBoots().getItemMeta();
								meta4.setColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
								p.getInventory().getBoots().setItemMeta(meta4);
								
							}
							
						
					}catch (Exception ex){
						p.chat("/da toggle");
					}
					
					
				}
				
			}
			
		}, 0, delay);
		
	}
	public void loadConfig(){
		this.saveDefaultConfig();
		getConfig().addDefault("aliases", new ArrayList<String>());
		List<String> al = getConfig().getStringList("aliases");
		for(String s : al) {
			aliases.add(s.toLowerCase());
		}
		this.delay = Long.parseLong(String.valueOf(this.getConfig().getInt("delay")));
		if(this.delay < 1){
				this.delay = 1;
		}
		if(this.getConfig().isString("messages.toggle-true")) {
			this.toggleTrue = ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("messages.toggle-true"));
		}
		if(this.getConfig().isString("messages.toggle-false")) {
			this.toggleFalse = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.toggle-false"));
		}
		this.time = this.getConfig().getInt("max-time");
		this.enableMaxTime = this.getConfig().getBoolean("enable-max-time");
		this.helmet= this.getConfig().getString("armor.helmet");
		this.chestplate = this.getConfig().getString("armor.chestplate");
		this.leggings = this.getConfig().getString("armor.leggings");
		this.boots = this.getConfig().getString("armor.boots");
		this.boots_name =  ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("names.boots"));
		this.chest_name =   ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("names.chestplate"));
		this.leggings_name =   ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("names.leggings"));
		this.helmet_name =   ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("names.helmet"));
		
	}
	@Override
	public void onEnable() {
		aliases = new LinkedList<String>();
		players = new HashMap<String,Integer>();
		oldarmor = new HashMap<String, ItemStack[]>();
		this.getCommand("discoarmor").setExecutor(new DiscoArmorCommand(this));
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		loadConfig();
		startScheduling();
	}
	public void toggle(Player p) {
		if(players.containsKey(p.getName())) {
			players.remove(p.getName());
			p.sendMessage(this.toggleFalse);
			p.getInventory().setBoots(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setHelmet(null);
			if(this.oldarmor.containsKey(p.getName())) {
				ItemStack[] oa = this.oldarmor.get(p.getName());
				p.getInventory().setHelmet(oa[0]);
				p.getInventory().setChestplate(oa[1]);
				p.getInventory().setLeggings(oa[2]);
				p.getInventory().setBoots(oa[3]);
				this.oldarmor.remove(p.getName());
			}
			return;
		}
		else {
			try {
				ItemStack[] oa =  {
						p.getInventory().getHelmet(),p.getInventory().getChestplate(),p.getInventory().getLeggings(),p.getInventory().getBoots() 
				};
				this.oldarmor.put(p.getName(), oa);
			}catch(Exception ex) {
				
			}
			if(helmet.equalsIgnoreCase("armor")) {
				ItemStack item1 = new ItemStack(Material.LEATHER_HELMET,1);
				ItemMeta meta1 = item1.getItemMeta();
				meta1.setDisplayName(this.helmet_name);
				item1.setItemMeta(meta1);
				p.getInventory().setHelmet(item1);
			}
			if(chestplate.equalsIgnoreCase("armor")) {
				ItemStack item2 = new ItemStack(Material.LEATHER_CHESTPLATE,1);
				ItemMeta meta2 = item2.getItemMeta();
				meta2.setDisplayName(this.chest_name);
				item2.setItemMeta(meta2);
				p.getInventory().setChestplate(item2);
			if(leggings.equalsIgnoreCase("armor")) {
				ItemStack item3 = new ItemStack(Material.LEATHER_LEGGINGS,1);
				ItemMeta meta3 = item3.getItemMeta();
				meta3.setDisplayName(this.leggings_name);
				item3.setItemMeta(meta3);
				p.getInventory().setLeggings(item3);
			}
				
			if(boots.equalsIgnoreCase("armor")) {
				ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS,1);
				ItemMeta meta4 = item4.getItemMeta();
				meta4.setDisplayName(this.boots_name);
				item4.setItemMeta(meta4);
			
				p.getInventory().setBoots(item4);
			}
				
				
				players.put(p.getName(),0);
				
				p.sendMessage(this.toggleTrue);
			}	
		
		}
	

	}
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		this.players.clear();
		
	}
	
}
