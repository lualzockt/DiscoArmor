package de.lualzockt.DiscoArmor.engineModes;

import org.bukkit.Color;
import org.bukkit.entity.Player;

public class EngineModeBuilder {
	private String description = "None";
	private String name = "Unnamed";
	private String author = "Unspecified";
	private IEngine engine = new IEngine() {
		
		@Override
		public Color color(Player p) {
			return Color.WHITE;
		}
	};
	
	
	public EngineModeBuilder(String description, String name, String author,IEngine engine) {
		if(description != null)
		this.description = description;
		if(name != null)
		this.name = name;
		if(author != null)
		this.author = author;
		if(engine != null)
		this.engine = engine;
	}


	public String description() {
		return this.description;
	}
	public EngineModeBuilder  description(String s) {
		return new EngineModeBuilder(s, name, author, engine);
	}
	public String name() {
		return this.name;
	}
	public EngineModeBuilder name(String s) {
		return new EngineModeBuilder(this.description, s,this.author, engine);
	}
	public String author() {
		return this.author;
	}
	public EngineModeBuilder author(String author) {
		return new EngineModeBuilder(description,name, author, engine);
	}
	public IEngine engine() {
		return engine;
	}
	public EngineModeBuilder engine(IEngine engine) {
		return new EngineModeBuilder(description, name, author, engine);
	}
	public EngineMode build() {
		return new EngineMode() {
			
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public String getDescription() {
				return description;
			}
			
			@Override
			public String getAuthor() {
				return author;
			}
			
			@Override
			public Color color(Player p) {
				return engine.color(p);
			}
		};
	}
	
}
