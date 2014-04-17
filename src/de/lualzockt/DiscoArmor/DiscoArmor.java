package de.lualzockt.DiscoArmor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.lualzockt.DiscoArmor.Utils.PluginMetrics;
import de.lualzockt.DiscoArmor.Utils.Updater;
import de.lualzockt.DiscoArmor.api.DiscoArmorApi;
import de.lualzockt.DiscoArmor.api.DiscoArmorToggleEvent;
import de.lualzockt.DiscoArmor.color.ColorManager;
import de.lualzockt.DiscoArmor.color.DiscoColor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscoArmor extends JavaPlugin
implements Runnable
{
	public static final int CURSE_PROJECT_ID = 60167;
	private static DiscoArmor instance;
	private static DiscoArmorApi api;
	private Random random;
	public Map<String, Integer> players;
	public long delay;
	public String helmet = "armor";
	public String chestplate = "armor";
	public String leggings = "armor";
	public String boots = "armor";
	public String toggleTrue = "You are now using DiscoArmor";
	public String toggleFalse = "You are now using DiscoArmor";
	public String helmet_name = "Discohelmet";
	public String chest_name = "Discochestplate";
	public String leggings_name = "Discoleggings";
	public String boots_name = "Discoboots";
	public String permissionMessage = "§cYou dont have permission.";
	public List<String> aliases;
	public static final String PREFIX = "§8[§cDiscoArmor§8]";
	public boolean updateNeeded = false;
	public boolean enableMaxTime = false;
	public boolean async = false;
	public int time;
	public int task = 0;
	public String download;
	public String version = "Unknown";
	public boolean updatecheck = true;

	public Map<String, ItemStack[]> armor = new HashMap<>();

	public static DiscoArmorApi getAPI()
	{
		if (api == null) {
			api = new DiscoArmorApi(getInstance());
		}
		return api;
	}

	public static DiscoArmor getInstance() {
		return instance;
	}

	public void checkForUpdate()
	{
		checkUpdates(null);
	}

	private void giveArmor(Player p) {
		PlayerInventory inv = p.getInventory();
		getAPI().getClass(); if (this.helmet.equals("armor")) {
			inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET), p));
		}
		getAPI().getClass(); if (this.chestplate.equals("armor")) {
			inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE), p));
		}
		getAPI().getClass(); if (this.leggings.equals("armor")) {
			inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS), p));
		}
		getAPI().getClass(); if (this.boots.equals("armor")) {
			inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS), p));
		}
		getAPI().getClass(); if (this.helmet.equals("wool")) {
			ItemStack wool = new Wool(org.bukkit.DyeColor.values()[this.random.nextInt(org.bukkit.DyeColor.values().length)]).toItemStack();
			inv.setHelmet(wool);
		}
	}

	public ItemStack colorArmor(ItemStack item, Player p) {
		if ((item.getItemMeta() instanceof LeatherArmorMeta)) {
			LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
			meta.setColor(ColorManager.random(p));
			if (item.getType() == Material.LEATHER_HELMET) {
				if (getConfig().getBoolean("random-color-names", true))
					meta.setDisplayName(ChatColor.values()[this.random.nextInt(ChatColor.values().length)] + this.helmet_name);
				else {
					meta.setDisplayName(this.helmet_name);
				}

			}
			else if (item.getType() == Material.LEATHER_CHESTPLATE) {
				if (getConfig().getBoolean("random-color-names", true))
					meta.setDisplayName(ChatColor.values()[this.random.nextInt(ChatColor.values().length)] + this.chest_name);
				else {
					meta.setDisplayName(this.chest_name);
				}
			}
			else if (item.getType() == Material.LEATHER_LEGGINGS) {
				if (getConfig().getBoolean("random-color-names", true))
					meta.setDisplayName(ChatColor.values()[this.random.nextInt(ChatColor.values().length)] + this.leggings_name);
				else {
					meta.setDisplayName(this.leggings_name);
				}

			}
			else if (item.getType() == Material.LEATHER_BOOTS) {
				if (getConfig().getBoolean("random-color-names", true))
					meta.setDisplayName(ChatColor.values()[this.random.nextInt(ChatColor.values().length)] + this.boots_name);
				else {
					meta.setDisplayName(this.boots_name);
				}

			}

			item.setItemMeta(meta);
		}

		return item;
	}

	private void loadColors() {
		ColorManager.colors.clear();
		for (String s : getConfig().getStringList("colors")) {
			Color colour = ColorManager.parse(s);
			if (colour == null) {
				continue;
			}
			ColorManager.colors.add(colour);
		}
	}

	public void loadConfig() {
		saveDefaultConfig();
		getConfig().addDefault("aliases", new ArrayList<>());
		List<String> al = getConfig().getStringList("aliases");
		if(al == null) al = new ArrayList<>();

		for (String s : al) {
			this.aliases.add(s.toLowerCase());
		}
		this.delay = 
				Long.parseLong(String.valueOf(getConfig().getInt("delay")));
		if (this.delay < 1L) {
			this.delay = 1L;
		}
		if (getConfig().isString("messages.toggle-true")) {
			this.toggleTrue = ChatColor.translateAlternateColorCodes('&', 
					getConfig().getString("messages.toggle-true", "Enabled!"));
		}
		if (getConfig().isString("messages.toggle-false")) {
			this.toggleFalse = 
					ChatColor.translateAlternateColorCodes(
							'&', 
							getConfig().getString("messages.toggle-false", 
									"Disabled!"));
		}
		this.async = getConfig().getBoolean("async", false);
		this.time = getConfig().getInt("max-time", 1);
		this.enableMaxTime = getConfig().getBoolean("enable-max-time", false);
		this.helmet = getConfig().getString("armor.helmet", "armor");
		this.chestplate = getConfig().getString("armor.chestplate", "armor");
		this.leggings = getConfig().getString("armor.leggings", "armor");
		this.boots = getConfig().getString("armor.boots", "armor");
		this.boots_name = ChatColor.translateAlternateColorCodes('&', 
				getConfig().getString("names.boots"));
		this.chest_name = ChatColor.translateAlternateColorCodes('&', 
				getConfig().getString("names.chestplate"));
		this.leggings_name = ChatColor.translateAlternateColorCodes('&', 
				getConfig().getString("names.leggings"));
		this.helmet_name = ChatColor.translateAlternateColorCodes('&', 
				getConfig().getString("names.helmet"));
		this.permissionMessage = ChatColor.translateAlternateColorCodes('&', 
				getConfig().getString("messages.permission-message"));
		this.updatecheck = getConfig().getBoolean("auto-update-check", 
				true);
		int engine = getConfig().getInt("engine-mode", 1);
		if (engine == 0) {
			ColorManager.engine = 0;
		} else if (engine == 1) {
			ColorManager.engine = 1;
		} else {
			getLogger().severe("INVALID ENGINE MODE (" + engine + ").");
			getLogger().severe("SHUTTING DOWN DISCO ARMOR");
			getConfig().set("engine-mode", Integer.valueOf(0));
			getServer().getPluginManager().disablePlugin(this);
		}
		loadColors();
	}

	public void onEnable()
	{
		instance = this;
		this.aliases = new LinkedList<>();
		this.players = new HashMap<>();
		new DiscoArmorCommand(this);
		new DiscoArmorListener(this);
		DiscoColor.setup();
		loadConfig();
		startScheduling();
		try {
			PluginMetrics m = new PluginMetrics(this);
			m.start();
		} catch (Exception ex) {
			getLogger().severe("Metrics could not be loaded.");
		}
		this.random = new Random();
		validateSettings();
		if (this.updatecheck)
			checkForUpdate();
		else
			getLogger().warning("Auto Update checking is disabled!");
	}

	public void run(){
		Iterator<String> it = this.players.keySet().iterator();
		while (it.hasNext()) {
			String s = (String)it.next();
			Player p = Bukkit.getPlayerExact(s);
			if (p == null) {
				this.players.remove(s);
			}
			else{
				if (this.enableMaxTime)
					this.players.put(s, Integer.valueOf(((Integer)this.players.get(s)).intValue() + 1));
				if (((Integer)this.players.get(s)).intValue() > this.time) {
					Player pl = Bukkit.getPlayer(s);
					toggle(pl);
				}
				else {
					giveArmor(p);
				}
			}
		}
	}

	public void startScheduling()
	{
		if (this.async)
			this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, 
					this, 0L, this.delay);
		else
			this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
					this, 0L, this.delay);
	}

	public void toggle(Player p)
	{
		if (this.players.containsKey(p.getName())) {
			p.sendMessage(this.toggleFalse);
			disable(p);
		} else {
			p.sendMessage(this.toggleTrue);
			enable(p);
		}

		Bukkit.getPluginManager().callEvent(new DiscoArmorToggleEvent(p, this.players.containsKey(p.getName())));
	}
	public void enable(Player p) {
		this.armor.put(p.getName(), p.getInventory().getArmorContents());
		giveArmor(p);
		this.players.put(p.getName(), Integer.valueOf(0));
	}

	public void disable(Player p) {
		if (this.players.containsKey(p.getName())) {
			if (this.armor.containsKey(p.getName())) {
				p.getInventory().setArmorContents((ItemStack[])this.armor.get(p.getName()));
				this.armor.remove(p.getName());
			}
			this.players.remove(p.getName());
		}
	}

	public void onDisable() {
		for (String s : this.players.keySet()) {
			Player p = Bukkit.getPlayerExact(s);
			if (p != null) {
				disable(p);
			}
		}
		super.onDisable();
	}

	private void validateSettings() {
		if (this.helmet == null) {
			api.getClass(); this.helmet = "armor";
		}
		if (this.chestplate == null) {
			api.getClass(); this.chestplate = "armor";
		}
		if (this.leggings == null) {
			api.getClass(); this.leggings = "armor";
		}
		if (this.boots == null) {
			api.getClass(); this.boots = "armor";
		}
	}

	protected static void checkUpdates(CommandSender cs)
	{
		Updater updater;
		if (cs != null) {
			updater = new Updater(getInstance(), 60167, getInstance().getFile(), Updater.UpdateType.NO_DOWNLOAD, true, cs);
		}
		else
			updater = new Updater(getInstance(), 60167, getInstance().getFile(), Updater.UpdateType.DEFAULT, true);
	}
}