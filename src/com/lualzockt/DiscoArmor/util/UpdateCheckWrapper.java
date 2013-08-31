package com.lualzockt.DiscoArmor.util;

import java.util.logging.Logger;

import com.lualzockt.DiscoArmor.DiscoArmor;

public class UpdateCheckWrapper implements Runnable{
	private Logger log;
	private DiscoArmor plugin;
	private Thread thread;
	public UpdateCheckWrapper(Logger log, DiscoArmor plugin) {
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
		}
		thread.interrupt();
		thread = null;
		
		
	}
}
