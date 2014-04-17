package de.lualzockt.DiscoArmor;


import java.util.Iterator;

import de.lualzockt.DiscoArmor.Utils.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class DiscoArmorListener extends DiscoArmorManager
  implements Listener
{
  public DiscoArmorListener(DiscoArmor plugin)
  {
    super(plugin);
  }

  @EventHandler
  public void onCmd(PlayerCommandPreprocessEvent e) {
    if (getPlugin().aliases.contains(e.getMessage().replace("/", "")
      .toLowerCase()))
      e.setMessage("/da toggle");
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    Player p = e.getEntity();
    if (getPlugin().players.containsKey(p.getName())) {
      getPlugin().players.remove(p.getName());
      Iterator it = e.getDrops().iterator();
      while (it.hasNext()) {
        ItemStack i = (ItemStack)it.next();
        if (i == null)
          continue;
        String m = i.getItemMeta().getDisplayName();
        if (m == null)
          continue;
        if ((!m.equals(getPlugin().boots_name)) && 
          (!m.equals(getPlugin().helmet_name)) && 
          (!m.equals(getPlugin().chest_name)) && 
          (!m.equals(getPlugin().leggings_name))) continue;
        it.remove();
      }

      if (getPlugin().armor.containsKey(p.getName()))
      {
        ItemStack[] armor = (ItemStack[])getPlugin().armor.get(p.getName());
        for (ItemStack item : armor) {
          if (item == null) {
            continue;
          }
          e.getDrops().add(item);
        }
        getPlugin().armor.remove(p.getName());
      }
    }
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    if (getPlugin().players.containsKey(p.getName())) {
      ItemStack i = e.getCurrentItem();
      if (i == null) {
        return;
      }
      if (Utils.isArmor(e.getSlotType(), e.getSlot()))
        e.setCancelled(true);
    }
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    if (getPlugin().players.containsKey(e.getPlayer().getName())) {
      Player p = e.getPlayer();
      getPlugin().players.remove(e.getPlayer().getName());
      if (getPlugin().armor.containsKey(p.getName())) {
        ItemStack[] oa = (ItemStack[])getPlugin().armor.get(p.getName());
        p.getInventory().setArmorContents(oa);
        getPlugin().armor.remove(p.getName());
      }
    }
  }

  @EventHandler
  public void onKick(PlayerKickEvent e) {
    if ((getPlugin().players.containsKey(e.getPlayer().getName())) && 
      (getPlugin().players.containsKey(e.getPlayer().getName()))) {
      Player p = e.getPlayer();
      getPlugin().players.remove(e.getPlayer().getName());
      if (getPlugin().armor.containsKey(p.getName())) {
        ItemStack[] oa = (ItemStack[])getPlugin().armor.get(p.getName());
        p.getInventory().setArmorContents(oa);
        getPlugin().armor.remove(p.getName());
      }
    }
  }

  @EventHandler
  public void onTeleport(PlayerTeleportEvent e)
  {
    Player p = e.getPlayer();
    if ((getPlugin().players.containsKey(p.getName())) && (e.getFrom().getWorld() != e.getTo().getWorld())) {
      getPlugin().players.remove(p.getName());
      getPlugin().disable(p);
    }
  }

  public void setup()
  {
    getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
  }
}