package de.lualzockt.DiscoArmor.api;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public final class ArmorColorEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private final Player p;
  private Color c;

  public ArmorColorEvent(Player p, Color c)
  {
    this.p = p;
    this.c = c;
  }

  public void setColor(Color c) {
    this.c = c;
  }
  public Color getColor() {
    return this.c;
  }
  public Player getPlayer() {
    return this.p;
  }
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}