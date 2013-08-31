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

import com.lualzockt.DiscoArmor.util.Metrics;
import com.lualzockt.DiscoArmor.util.UpdateCheckWrapper;

public class DiscoArmor extends JavaPlugin implements Runnable{
	
	private DiscoArmorApi api;
	public DiscoArmorApi api() {
		if(api == null) {
			api = new DiscoArmorApi(this);
		}
		return api;
	}
	protected Map<String,Integer> players;
	protected Map<String, ItemStack[]> oldarmor = new HashMap<String, ItemStack[]>();
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
	String permissionMessage = "§cYou dont have permission.";
	List<String> aliases;
	public static final String PREFIX  = "§8[§cDiscoArmor§8]";
	public boolean updateNeeded = false;
	protected boolean enableMaxTime = false;
	protected boolean async = false;
	protected int time;
	protected int task = 0; // The id of the Scheduler Task
	protected void startScheduling() {
		if(async) {
			task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, this, 0,delay);
			}else {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this, 0, delay);
			}
		
	}
	protected void loadConfig(){
		saveDefaultConfig();
		getConfig().addDefault("aliases", new ArrayList<String>());
		List<String> al = getConfig().getStringList("aliases");
		for(String s : al) {
			aliases.add(s.toLowerCase());
		}
		delay = Long.parseLong(String.valueOf(getConfig().getInt("delay")));
		if(delay < 1){
				delay = 1;
		}
		if(getConfig().isString("messages.toggle-true")) {
			toggleTrue = ChatColor.translateAlternateColorCodes('&',getConfig().getString("messages.toggle-true"));
		}
		if(getConfig().isString("messages.toggle-false")) {
			toggleFalse = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.toggle-false"));
		}
		async = getConfig().getBoolean("async");
		time = getConfig().getInt("max-time");
		enableMaxTime = getConfig().getBoolean("enable-max-time");
		helmet= getConfig().getString("armor.helmet");
		chestplate = getConfig().getString("armor.chestplate");
		leggings = getConfig().getString("armor.leggings");
		boots = getConfig().getString("armor.boots");
		boots_name =  ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.boots"));
		chest_name =   ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.chestplate"));
		leggings_name =   ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.leggings"));
		helmet_name =   ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.helmet"));
		permissionMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("permission-message"));
	}
	@Override
	public void onEnable() {
		api();
		aliases = new LinkedList<String>();
		players = new HashMap<String,Integer>();
		oldarmor = new HashMap<String, ItemStack[]>();
		getCommand("discoarmor").setExecutor(new DiscoArmorCommand(this));
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		loadConfig();
		startScheduling();
		try {
			Metrics m = new Metrics(this);
			m.start();
		}catch(Exception ex) {
			
		}
		
		if(helmet == null) {
			helmet = api.ARMOR;
		}
		if(chestplate == null) {
			chestplate = api.ARMOR;
		}
		if(leggings == null) {
			leggings = api.ARMOR;
		}
		if(boots == null) {
			boots = api.ARMOR;
		}
		this.checkForUpdate();
	}
	public void checkForUpdate() {
		new UpdateCheckWrapper(this.getLogger(), this);
	}
	protected void toggle(Player p) {
		if(players.containsKey(p.getName())) {
			players.remove(p.getName());
			p.sendMessage(toggleFalse);
			p.getInventory().setBoots(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setHelmet(null);
			if(oldarmor.containsKey(p.getName())) {
				ItemStack[] oa = oldarmor.get(p.getName());
				p.getInventory().setHelmet(oa[0]);
				p.getInventory().setChestplate(oa[1]);
				p.getInventory().setLeggings(oa[2]);
				p.getInventory().setBoots(oa[3]);
				oldarmor.remove(p.getName());
			}
			return;
		}
		else {
			try {
				ItemStack[] oa =  {
						p.getInventory().getHelmet(),p.getInventory().getChestplate(),p.getInventory().getLeggings(),p.getInventory().getBoots() 
				};
				oldarmor.put(p.getName(), oa);
			}catch(Exception ex) {
				
			}
			if(helmet.equalsIgnoreCase("armor")) {
				ItemStack item1 = new ItemStack(Material.LEATHER_HELMET,1);
				ItemMeta meta1 = item1.getItemMeta();
				meta1.setDisplayName(helmet_name);
				item1.setItemMeta(meta1);
				p.getInventory().setHelmet(item1);
			}
			if(chestplate.equalsIgnoreCase("armor")) {
				ItemStack item2 = new ItemStack(Material.LEATHER_CHESTPLATE,1);
				ItemMeta meta2 = item2.getItemMeta();
				meta2.setDisplayName(chest_name);
				item2.setItemMeta(meta2);
				p.getInventory().setChestplate(item2);
			if(leggings.equalsIgnoreCase("armor")) {
				ItemStack item3 = new ItemStack(Material.LEATHER_LEGGINGS,1);
				ItemMeta meta3 = item3.getItemMeta();
				meta3.setDisplayName(leggings_name);
				item3.setItemMeta(meta3);
				p.getInventory().setLeggings(item3);
			}
				
			if(boots.equalsIgnoreCase("armor")) {
				ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS,1);
				ItemMeta meta4 = item4.getItemMeta();
				meta4.setDisplayName(boots_name);
				item4.setItemMeta(meta4);
			
				p.getInventory().setBoots(item4);
			}
				
				
				players.put(p.getName(),0);
				
				p.sendMessage(toggleTrue);
			}	
		
		}
	

	}
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		players.clear();
		
	}
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
					toggle(pl);
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
	
	
}
