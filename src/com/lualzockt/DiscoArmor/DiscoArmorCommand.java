package com.lualzockt.DiscoArmor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lualzockt.DiscoArmor.util.UpdateCheckWrapper;

public class DiscoArmorCommand extends DiscoArmorManager implements CommandExecutor {
	public DiscoArmorCommand(DiscoArmor plugin) {
		super(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
			if(args.length  >= 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					if (cs.hasPermission("discoarmor.reload")) {
						this.getPlugin().players.clear();
						this.getPlugin().reloadConfig();
						this.getPlugin().loadConfig();
						cs.sendMessage(this.getPlugin().PREFIX + " DiscoArmor has been reloaded.");
					}else {
						cs.sendMessage("§cYou don't have permission to reload the config.");
					}
				}else if(args[0].equalsIgnoreCase("info")) {
					if(cs.hasPermission("discoarmor.info")) {
						cs.sendMessage(this.getPlugin().PREFIX + " §6Name: §f" + this.getPlugin().getDescription().getName());
						cs.sendMessage(this.getPlugin().PREFIX + " §6Author: §f" + this.getPlugin().getDescription().getAuthors().get(0));
						cs.sendMessage(this.getPlugin().PREFIX + " §6Current Version: §f " + this.getPlugin().getDescription().getVersion());
						cs.sendMessage(this.getPlugin().PREFIX + " §6Latest Version: §f" + this.getPlugin().version);
						if(this.getPlugin().updatecheck) {
							if(this.getPlugin().updateNeeded) {
							cs.sendMessage(this.getPlugin().PREFIX + " §6Update: §f§fDownload here: " + this.getPlugin().download);
							}else {
							cs.sendMessage(this.getPlugin().PREFIX + " §6Update: §fNo Update avaible!");
							}
						}else {
							cs.sendMessage(this.getPlugin().PREFIX + "§4Update checking is disabled!");
						
					}
						
						cs.sendMessage(this.getPlugin().PREFIX + " §6Bukkit Page: §fhttp://dev.bukkit.org/bukkit-this.getOwningPlugin()s/discoarmor");
					}else {
						cs.sendMessage("§cYou do not have permission.");
					}
				}else if (args[0].equalsIgnoreCase("help")) {
					if(cs.hasPermission("discoarmor.help")) {
						cs.sendMessage(this.getPlugin().PREFIX +" §7Help of DiscoArmor");
						cs.sendMessage("§c/da toggle [player]§8 - Disable and enable the DiscoArmor-Mode.");
						cs.sendMessage("§c/da reload§8 - Reloads the Config.");
						cs.sendMessage("§c/da info§8- Get info about the this.getOwningPlugin().");
						cs.sendMessage("§c/da update§8- Checks for updates.");
					}else {
						cs.sendMessage("§cYou do not have permission.");
					}
					
				}else if(args[0].equalsIgnoreCase("update")) {
					if(cs.hasPermission("discoarmor.update")) {
						if(this.getPlugin().updatecheck) {
							cs.sendMessage(this.getPlugin().PREFIX + " §6Checking for Updates...");
						new UpdateCheckWrapper(this.getPlugin().getLogger(), this.getPlugin(), cs);
						}else {
							cs.sendMessage(this.getPlugin().PREFIX + "§4Update checking is disabled!");
						}
						
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
								this.getPlugin().toggle(p);
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
								this.getPlugin().toggle(p);
								p.sendMessage("§8DiscoMode toggled by " + cs.getName());
								p.sendMessage(this.getPlugin().toggleTrue);
								boolean t = this.getPlugin().players.containsKey(p.getName());
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
	@Override
	public void setup() {
		this.getPlugin().getCommand("discoarmor").setExecutor(this);
		
	}
}
