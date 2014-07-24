package de.lualzockt.DiscoArmor.engineModes;


public class EngineModes {
	public static EngineMode RANDOM_DEF = new Random_DEF();
	public static EngineMode RANDOM_RGB = new Random_RGB();
	public static EngineMode RANDOM_DYE = new Random_DYE();
	public static EngineMode RANDOM_RBW = new Random_RBW();
	public static EngineModeBuilder builder() {
		return new EngineModeBuilder(null,null,null,null);
	}
}
