package de.lualzockt.DiscoArmor.Utils;

import org.bukkit.event.inventory.InventoryType;

public class Utils{
  public static boolean isArmor(InventoryType.SlotType st, int slot){
    return (st == InventoryType.SlotType.ARMOR) || (slot > 99);
  }
}
