package de.lualzockt.DiscoArmor;

import de.lualzockt.DiscoArmor.Utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DiscoArmorModifyCommand extends CommandAbstract{

	@Override
	public void execute(Player p, Command cmd, String[] args) {
		if(args.length == 0) {
			p.sendMessage(DiscoArmor.PREFIX + "DiscoArmor Settings Modification");
			Utils.sendJSONChat(p, "/dam delay <delay>","/dam delay <delay>" , false);
			Utils.sendJSONChat(p, "/dam engineMode <id>","/dam engineMode <id>", false);
			return;
		}
		if(args[0].equalsIgnoreCase("help")) {
			this.execute(p, cmd, new String[] {});
		}else if(args[0].equalsIgnoreCase("delay")) {
			if(args.length == 2) {
				if(Utils.isInt(args[1])) {
					int i = Integer.parseInt(args[1]);
					if(i < 1) {
						p.sendMessage("§cMust be a numeric value greater than 0");
					}else {
						DiscoArmor.getAPI().setDelay(i, true);
						p.sendMessage(DiscoArmor.PREFIX + "Delay has been set to §7" + i);
					}
				}else {
					p.sendMessage("§cMust be a numeric value greater than 0");
				}
			}else {
				p.sendMessage("§c/dam delay <delay>");
			}
		}else if(args[0].equalsIgnoreCase("engineMode")) {
			if(args.length == 2) {
				if(Utils.isInt(args[1])) {
					int i = Integer.parseInt(args[1]);
					if(i < 1) {
						p.sendMessage("§cMust be a numeric value greater than 0");
					}else {
						DiscoArmor.getAPI().setDelay(i, true);
						p.sendMessage(DiscoArmor.PREFIX + "Delay has been set to §7" + i);
					}
				}else {
					p.sendMessage("§cInvalid engine mode");
				}
			}else {
				p.sendMessage("§c/dam engineMode <id>");
			}
		}
	}

	@Override
	public void execute(ConsoleCommandSender cs, Command cmd, String[] args) {
		
	}

	@Override
	public void execute(CommandSender cs, Command cmd, String[] args) {
		
		
	}



}
