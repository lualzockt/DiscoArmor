package de.lualzockt.DiscoArmor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CommandAbstract extends DiscoArmorManager implements CommandExecutor{

	public CommandAbstract() {
		super(DiscoArmor.getInstance());
	}
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label,String[] args) {
		if(cs instanceof Player) {
			this.execute((Player) cs, cmd, args); 
		}else if(cs instanceof ConsoleCommandSender) {
			this.execute((ConsoleCommandSender)cs, cmd, args);
		}
		this.execute(cs, cmd, args);
		return true;
	}
	public abstract void execute(Player p, Command cmd,String[] args);
	public abstract void execute(ConsoleCommandSender cs, Command cmd, String[] args);
	public abstract void execute(CommandSender cs, Command cmd, String[] args);
} 
