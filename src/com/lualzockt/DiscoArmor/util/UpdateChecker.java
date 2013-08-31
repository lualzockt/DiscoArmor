package com.lualzockt.DiscoArmor.util;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lualzockt.DiscoArmor.DiscoArmor;

public class UpdateChecker {

	private URL filesFeed;
	private String version;
	private String link;
	private DiscoArmor plugin;
	private boolean needUpdate = false;

	public UpdateChecker(DiscoArmor plugin){
		this.plugin = plugin;
		try {
			this.filesFeed = new URL("http://dev.bukkit.org/bukkit-plugins/discoarmor/files.rss");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(this.filesFeed == null) {
			plugin.getLogger().info("[ERR] Could not check for updates");
			return;
		}

		try {
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

			Node latestFile = document.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();

			this.version = children.item(1).getTextContent().replace("DiscoArmor ", "");
			this.link = children.item(3).getTextContent();

			String cur_ver = plugin.getDescription().getVersion();
			if(!cur_ver.equals(this.version))
				this.needUpdate = true;


		} catch (Exception e) {			
			plugin.getLogger().info("[ERR] Could not check for updates: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean updateNeeded(){
		return needUpdate;
	}

	public String getVersion(){
		return this.version;
	}

	public String getLink(){
		return this.link;
	}

	public void updateInformation(){
		try {
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

			Node latestFile = document.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();

			this.version = children.item(1).getTextContent().replace("DiscoArmor ", "");
			this.link = children.item(3).getTextContent();

			String cur_ver = plugin.getDescription().getVersion();
			if(!cur_ver.equals(this.version))
				this.needUpdate = true;


		} catch (Exception e) {			
			plugin.getLogger().info("[ERR] Could not check for updates: " + e.getMessage());
			e.printStackTrace();
		}
	}

}