package com.lualzockt.DiscoArmor;

import org.bukkit.Bukkit;
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
	public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
			if(args.length  >= 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					if (cs.hasPermission("discoarmor.reload")) {
						plugin.players.clear();
						plugin.reloadConfig();
						plugin.loadConfig();
						cs.sendMessage(plugin.PREFIX + " DiscoArmor has been reloaded.");
					}else {
						cs.sendMessage("§cYou don't have permission to reload the config.");
					}
				}else if(args[0].equalsIgnoreCase("info")) {
					if(cs.hasPermission("discoarmor.info")) {
						cs.sendMessage(plugin.PREFIX + " §7You are using DiscoArmor v. "+ plugin.getDescription().getVersion() + " by LualZockt.");

					}else {
						cs.sendMessage("§cYou do not have permission.");
					}
				}else if (args[0].equalsIgnoreCase("help")) {
					if(cs.hasPermission("discoarmor.help")) {
						cs.sendMessage(plugin.PREFIX +" §7Help of DiscoArmor");
						cs.sendMessage("§c/da toggle [player]§8 - Disable and enable the DiscoArmor-Mode.");
						cs.sendMessage("§c/da reload§8 - Reloads the Config.");
						cs.sendMessage("§c/da info§8- Get info about the plugin.");
					}else {
						cs.sendMessage("§cYou do not have permission.");
					}
					
				}else if(args[0].equalsIgnoreCase("toggle")) {
						if(args.length == 1) {
							if(!(cs instanceof Player)) {
								cs.sendMessage("§cYou have to be a Player!");
								return true;
							}
							Player p = (Player) cs;
							if(p.hasPermission("discoarmor.toggle") || p.hasPermission("da.toggle")) {
								plugin.toggle(p);
							}
							else {
								p.sendMessage("§cYou don't have permission to do this.");
							}
						}else {
							if(cs.hasPermission("discoarmor.toggleothers")) {
								Player p = Bukkit.getPlayer(args[1]);
								if(p == null) {
									cs.sendMessage("§cThe player is not online.");
									return true;
								}
								plugin.toggle(p);
								p.sendMessage("§8DiscoMode toggled by " + cs.getName());
								p.sendMessage(plugin.toggleTrue);
								boolean t = plugin.players.containsKey(p.getName());
								String status;
								if(t) {
									status = "true";
								}else {
									status = "false";
								}
								cs.sendMessage(String.format("Toggled %s's DiscoMode to %s.", p.getName(), status));
								
								
							}else {
								cs.sendMessage("§cYou do not have permission to toggle others DiscoArmorMode.");
							}
						}
						
				} else if(args[0].equalsIgnoreCase("options")) {
					cs.sendMessage("§4§k@@§2Coming Soon!§4k§@@");
				}
				else {
				     cs.sendMessage("§cUnknow Command! §8Type /da help for help.");
				}
			}else {
				cs.sendMessage("§cType /da help for help.");
			}
		return true;
			
	}
}
