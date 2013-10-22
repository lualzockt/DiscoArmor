package com.lualzockt.DiscoArmor.util;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

import com.lualzockt.DiscoArmor.DiscoArmor;

public class UpdateCheckWrapper implements Runnable{
	private Logger log;
	private DiscoArmor plugin;
	private Thread thread;
	private CommandSender  p = null;
	public UpdateCheckWrapper(Logger log, DiscoArmor plugin) {
		this.plugin = plugin;
		this.log = log;
		thread = new Thread(this);
		thread.start();
	}
	public UpdateCheckWrapper(Logger log, DiscoArmor plugin, CommandSender sender) {
		this.p = sender;
		this.plugin = plugin;
		this.log = log;
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		this.log.info("Checking for updates...");
		UpdateChecker uc = new UpdateChecker(this.plugin);
		uc.updateInformation();
		this.plugin.updateNeeded = uc.updateNeeded();
		if(uc.updateNeeded()) {
			this.log.info("A new update is avaible for download. Grap the new version (" + uc.getVersion() + ") here:   " +  uc.getLink());		
			if(p != null) {
				p.sendMessage(DiscoArmor.PREFIX + " §6 A new update has been released. ");
				p.sendMessage(DiscoArmor.PREFIX + " §6 Download version §f" + uc.getVersion() + " §6 here: §f" + uc.getLink());
			}
		}else {
			if(p != null) {
				p.sendMessage(DiscoArmor.PREFIX + " §6 You are using the newest version of DiscoArmor :) ");
			}
		}
		this.plugin.version = uc.getVersion();
		this.plugin.download = uc.getLink();
		
		thread.interrupt();
		thread = null;
		
		
	}
}
