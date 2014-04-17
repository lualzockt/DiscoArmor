package de.lualzockt.DiscoArmor.api;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;


public final class DiscoArmorToggleEvent extends PlayerEvent
{
  private boolean enabled;
  private static HandlerList handlers = new HandlerList();

  public DiscoArmorToggleEvent(Player who, boolean enabled) { super(who);
  }

  public boolean willBeEnabled()
  {
    return this.enabled;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}