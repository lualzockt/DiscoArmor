package com.lualzockt.DiscoArmor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscoArmorCommand implements CommandExecutor {
	private DiscoArmor plugin;
	public DiscoArmorCommand(DiscoArmor discoArmor) {
		this.plugin = discoArmor;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if(args.length  >= 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("discoarmor.reload")) {
						plugin.players.clear();
						plugin.reloadConfig();
						plugin.loadConfig();
						sender.sendMessage(plugin.PREFIX + " DiscoArmor has been reloaded.");
					}else {
						sender.sendMessage("§cYou don't have permission to reload the config.");
					}
				}else if(args[0].equalsIgnoreCase("info")) {
					sender.sendMessage(plugin.PREFIX + " §7You are using DiscoArmor v. "+ plugin.getDescription().getVersion() + " by LualZockt.");
				}else if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(plugin.PREFIX +" §7Help of DiscoArmor");
					sender.sendMessage("§c/da toggle§8 - Disable and enable the DiscoArmor-Mode.");
					sender.sendMessage("§c/da reload§8 - Reloads the Config.");
					sender.sendMessage("§c/da info§8- Get info about the plugin.");
				}else if(args[0].equalsIgnoreCase("toggle")) {
						
						if(!(sender instanceof Player)) {
							sender.sendMessage("§cYou have to be a Player!");
							return true;
						}
						Player p = (Player) sender;
						if(p.hasPermission("discoarmor.toggle") || p.hasPermission("da.toggle")) {
							plugin.toggle(p);
						}
						else {
							p.sendMessage("§cYou don't have permission to do this.");
						}
				} else if(args[0].equalsIgnoreCase("options")) {
					sender.sendMessage("§4§k@@§2Coming Soon!§4§@@");
				}
				else {
				     sender.sendMessage("§cUnknow Command! §8Type /da help for help.");
				}
			}else {
				sender.sendMessage("§cType /da help for help.");
			}
		return true;
			
	}
}
