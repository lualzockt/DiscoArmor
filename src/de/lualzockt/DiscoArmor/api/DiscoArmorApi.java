package de.lualzockt.DiscoArmor.api;

import java.util.InputMismatchException;

import de.lualzockt.DiscoArmor.DiscoArmor;
import de.lualzockt.DiscoArmor.DiscoArmorManager;
import de.lualzockt.DiscoArmor.EngineMode;
import de.lualzockt.DiscoArmor.color.ColorManager;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public final class DiscoArmorApi extends DiscoArmorManager
{
  public final String ARMOR = "armor";
  public final String WOOL = "wool";
  public final String NOTHING = "none";

  public DiscoArmorApi(DiscoArmor plugin)
  {
    super(plugin);
  }

  public void toggle(Player p)
  {
    getPlugin().toggle(p);
  }

  public boolean isColorRegistered(Color c) {
    return ColorManager.colors.contains(c);
  }

  public void registerColor(Color c) {
    ColorManager.colors.add(c);
  }

  public int getEngineMode() {
    return ColorManager.engine;
  }

  public void setEngineMode(int type) {
    if (EngineMode.isValid(type))
      ColorManager.engine = type;
    else
      throw new InputMismatchException("Invalid Engine Mode!");
  }

  public void toggle(String n)
  {
    Player p = Bukkit.getPlayer(n);
    if (p == null) {
      throw new NullPointerException("The player is not online.");
    }
    getPlugin().toggle(p);
  }

  public boolean isEnabledForUser(Player p) {
    return getPlugin().players.containsKey(p.getName());
  }

  public boolean isEnabledForUser(String p) {
    return getPlugin().players.containsKey(p);
  }

  public boolean setEnabledForUser(Player p, boolean t) {
    if (t) {
      getPlugin().players.put(p.getName(), Integer.valueOf(0));
    }
    else if (getPlugin().players.containsKey(p.getName())) {
      getPlugin().players.remove(p.getName());
    }

    return t;
  }

  public ItemStack[] getOldArmor(Player p) {
    if (getPlugin().armor.containsKey(p.getName())) {
      return (ItemStack[])getPlugin().armor.get(p.getName());
    }
    return null;
  }

  public void setOldArmor(Player p, ItemStack[] items)
  {
    getPlugin().armor.put(p.getName(), items);
  }

  public long getDelay() {
    return getPlugin().delay;
  }

  public void setDelay(long delay) {
    if (delay <= 0L) {
      throw new InputMismatchException("The delay must be bigger then 0");
    }
    getPlugin().delay = delay;
  }

  public String getHelmetType() {
    return getPlugin().helmet;
  }

  public void setHelmetType(String helmet) {
    if ((helmet.equalsIgnoreCase("armor")) || (helmet.equalsIgnoreCase("wool")) || 
      (helmet.equalsIgnoreCase("none")))
      getPlugin().helmet = helmet;
    else
      throw new InputMismatchException("Must be a valid type!");
  }

  public String getChestplateType() {
    return getPlugin().chestplate;
  }

  public void setChestplateType(String chestplate) {
    if ((chestplate.equalsIgnoreCase("armor")) || 
      (chestplate.equalsIgnoreCase("none")))
      getPlugin().chestplate = chestplate;
    else
      throw new InputMismatchException("Must be a valid type!");
  }

  public String getLeggingsType() {
    return getPlugin().leggings;
  }

  public void setLeggingsType(String leggings) {
    if ((leggings.equalsIgnoreCase("armor")) || 
      (leggings.equalsIgnoreCase("none")))
      getPlugin().leggings = leggings;
    else
      throw new InputMismatchException("Must be a valid type!");
  }

  public String getBootsType() {
    return getPlugin().boots;
  }

  public void setBootsType(String boots) {
    if ((boots.equalsIgnoreCase("armor")) || (boots.equalsIgnoreCase("none")))
      getPlugin().boots = boots;
    else
      throw new InputMismatchException("Must be a valid type!");
  }

  public String getToggleTrueMessage() {
    return getPlugin().toggleTrue;
  }

  public void setToggleTrueMessage(String toggleTrue) {
    getPlugin().toggleTrue = toggleTrue;
  }

  public String getToggleFalseMessage() {
    return getPlugin().toggleFalse;
  }

  public void setToggleFalseMessage(String toggleFalse) {
    getPlugin().toggleFalse = toggleFalse;
  }

  public String getHelmetName() {
    return getPlugin().helmet_name;
  }

  public void setHelmetName(String helmet_name) {
    getPlugin().helmet_name = helmet_name;
  }

  public String getChestName() {
    return getPlugin().chest_name;
  }

  public void setChestName(String chest_name) {
    getPlugin().chest_name = chest_name;
  }

  public String getLeggingsName() {
    return getPlugin().leggings_name;
  }

  public void setLeggingsName(String leggings_name) {
    getPlugin().leggings_name = leggings_name;
  }

  public String getBootsName() {
    return getPlugin().boots_name;
  }

  public void setBootsName(String boots_name) {
    getPlugin().boots_name = boots_name;
  }

  public boolean isEnableMaxTime() {
    return getPlugin().enableMaxTime;
  }

  public void setEnableMaxTime(boolean enableMaxTime) {
    getPlugin().enableMaxTime = enableMaxTime;
  }

  public void setup()
  {
  }
}