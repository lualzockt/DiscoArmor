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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.lualzockt.DiscoArmor.util.Metrics;
import com.lualzockt.DiscoArmor.util.UpdateCheckWrapper;

public class DiscoArmor extends JavaPlugin implements Runnable{
	private static DiscoArmor instance;
	private static DiscoArmorApi api;

	public static DiscoArmorApi api() {
		if(api == null) {
			api = new DiscoArmorApi(getInstance());
		}
		return api;
	}
	protected static DiscoArmor getInstance() {
		return instance;
	}
	public String version = "Unknown";
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
	public String download;
	private Random r;
	
	private PlayerListener listener;
	public boolean updatecheck = true;
	private DiscoArmorCommand command;
	@SuppressWarnings("deprecation")
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
		this.async = getConfig().getBoolean("async");
		this.time = getConfig().getInt("max-time");
		this.enableMaxTime = getConfig().getBoolean("enable-max-time");
		this.helmet= getConfig().getString("armor.helmet");
		this.chestplate = getConfig().getString("armor.chestplate");
		this.leggings = getConfig().getString("armor.leggings");
		this.boots = getConfig().getString("armor.boots");
		this.boots_name =  ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.boots"));
		this.chest_name =   ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.chestplate"));
		this.leggings_name =   ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.leggings"));
		this.helmet_name =   ChatColor.translateAlternateColorCodes('&',getConfig().getString("names.helmet"));
		this.permissionMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.permission-message"));
		this.updatecheck = this.getConfig().getBoolean("auto-update-check", true);
		int engine = this.getConfig().getInt("engine-mode");
		if(engine == 0) {
			ColorUtils.engine = 0;
		}else if(engine == 1) {
			ColorUtils.engine = 1;
		}else {
			this.getLogger().severe("INVALID ENGINE MODE (" + engine + ").");
			this.getLogger().severe("SHUTTING DOWN DISCO ARMOR");
			this.getConfig().set("engine-mode", 0);
			this.getServer().getPluginManager().disablePlugin(this);
		}
		this.loadColors();
		
	}
	private void loadColors() {
		ColorUtils.colors.clear();
		for(String s : this.getConfig().getStringList("colors"))  {
			Color colour = ColorUtils.parse(s);
			if(colour == null)
				continue;
			
			ColorUtils.colors.add(colour);
		}
	}
	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		this.instance = this;
		this.aliases = new LinkedList<String>();
		this.players = new HashMap<String,Integer>();
		this.oldarmor = new HashMap<String, ItemStack[]>();
		this.command = new DiscoArmorCommand(this);
		this.listener = new PlayerListener(this);
		this.loadConfig(); // Load config
		this.startScheduling(); // Start color change task
		DiscoColor.setup();
		try { // Start Metrics
			Metrics m = new Metrics(this);
			m.start();
		}catch(Exception ex) {
			this.getLogger().severe("Metrics could not be loaded.");
		}
		this.r = new Random();
		this.validateSettings();
		if(this.updatecheck) {
			this.checkForUpdate(); // Checks for Updates
		}else {
			this.getLogger().warning("Auto Update checking is disabled!");
		}
		
	}
	private void validateSettings() {
		if(this.helmet == null) {
			this.helmet = api.ARMOR;
		}
		if(this.chestplate == null) {
			this.chestplate = api.ARMOR;
		}
		if(this.leggings == null) {
			this.leggings = api.ARMOR;
		}
		if(this.boots == null) {
			this.boots = api.ARMOR;
		}
	}
	public void checkForUpdate() {
		new UpdateCheckWrapper(this.getLogger(), this);
	}
	protected void toggle(Player p) {
		if(players.containsKey(p.getName())) { // Toggle to false
			players.remove(p.getName());
			p.sendMessage(toggleFalse);
			p.getInventory().setBoots(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setHelmet(null);
			if(oldarmor.containsKey(p.getName())) { // Give the player his old armor back
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
				ItemStack[] oa =  {
						p.getInventory().getHelmet(),p.getInventory().getChestplate(),p.getInventory().getLeggings(),p.getInventory().getBoots() 
				};
				oldarmor.put(p.getName(), oa);
				players.put(p.getName(),0);
				this.giveArmor(p);
				p.sendMessage(toggleTrue);
	
		}
	

	}
	
	private void giveArmor(Player p) {
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
		}
	}
	private void colorArmor(Player p ) {
		if(this.helmet.equalsIgnoreCase("armor")) {
			if(p.getInventory().getHelmet() == null)
			{
				ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS,1);
				ItemMeta meta4 = item4.getItemMeta();
				meta4.setDisplayName(boots_name);
				item4.setItemMeta(meta4);
				p.getInventory().setLeggings(item4);
			}
				LeatherArmorMeta meta1 = (LeatherArmorMeta) p.getInventory().getHelmet().getItemMeta(); 
				meta1.setColor(ColorUtils.random()); // Randomize Color
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
				meta2.setColor(ColorUtils.random());
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
				meta3.setColor(ColorUtils.random());
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
				meta4.setColor(ColorUtils.random());
				p.getInventory().getBoots().setItemMeta(meta4);
				
			}
			
	}
	@Override
	public void run() {
		for(String s : players.keySet()){
			Player p = Bukkit.getPlayerExact(s);
			if(p == null) {
				players.remove(s); // Remove players if they are no longer online
				continue;
			}
			try {
				if(this.enableMaxTime) {
				this.players.put(s, players.get(s) + 1);
				}
				if(this.players.get(s) > time) {
					Player pl = Bukkit.getPlayer(s);
					toggle(pl);
					continue;
				}
				this.colorArmor(p);
				
			}catch (Exception ex){
				p.chat("/da toggle");
				ex.printStackTrace();
				Bukkit.getScheduler().cancelTask(task);
			}
			
			
		}
		
	}
	
	
}
