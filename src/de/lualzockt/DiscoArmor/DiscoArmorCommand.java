package de.lualzockt.DiscoArmor;

import org.apache.commons.lang.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscoArmorCommand extends DiscoArmorManager implements CommandExecutor {
  public DiscoArmorCommand(DiscoArmor plugin){
    super(plugin);
  }

  public boolean onCommand(CommandSender cs, Command command, String label, String[] args){
    if (args.length >= 1){
      boolean t;
      if (args[0].equalsIgnoreCase("toggle")) {
        if (args.length == 1) {
          if (!(cs instanceof Player)) {
            cs.sendMessage("§cYou have to be a Player!");
            return true;
          }
          Player p = (Player)cs;
          if ((p.hasPermission("discoarmor.toggle")) || 
            (p.hasPermission("da.toggle")))
            getPlugin().toggle(p);
          else {
            p.sendMessage("§cYou don't have permission to do this.");
          }
        }
        else if (cs.hasPermission("discoarmor.toggleothers")) {
          Player p = Bukkit.getPlayer(args[1]);
          if (p == null) {
            cs.sendMessage("§cThe player is not online.");
            return true;
          }
          getPlugin().toggle(p);
          p.sendMessage("§8DiscoMode toggled by " + cs.getName());
          p.sendMessage(getPlugin().toggleTrue);
          t = getPlugin().players.containsKey(p.getName());
          String status;
          if (t)
            status = "true";
          else {
            status = "false";
          }
          cs.sendMessage(String.format(
            "Toggled %s's DiscoMode to %s.", new Object[] { p.getName(), 
            status }));
        }
        else {
          cs.sendMessage("§cYou do not have permission to toggle others DiscoArmorMode.");
        }

      }
      else if (args[0].equalsIgnoreCase("reload")) {
        if (cs.hasPermission("discoarmor.reload")) {
          for (String s : getPlugin().players.keySet()) {
            Player pl = Bukkit.getPlayerExact(s);
            if (pl != null)
              getPlugin().disable(pl);
          }
          getPlugin().players.clear();
          getPlugin().reloadConfig();
          getPlugin().loadConfig();
          cs.sendMessage("§8[§cDiscoArmor§8] DiscoArmor has been reloaded.");
        }
        else {
          cs.sendMessage("§cYou don't have permission to reload the config.");
        }
      } else if (args[0].equalsIgnoreCase("info")) {
        if (cs.hasPermission("discoarmor.info")) {
          cs.sendMessage("§8[§cDiscoArmor§8] §6Name: §f" + 
            getPlugin().getDescription().getName());
          cs.sendMessage("§8[§cDiscoArmor§8] §6Author: §f" + 
            (String)getPlugin().getDescription().getAuthors()
            .get(0));
          cs.sendMessage("§8[§cDiscoArmor§8] §6Current Version: §f " + 
            getPlugin().getDescription().getVersion());
          cs.sendMessage("§8[§cDiscoArmor§8] §6Latest Version: §f" + 
            getPlugin().version);
          if (getPlugin().updatecheck) {
            if (getPlugin().updateNeeded)
              cs.sendMessage("§8[§cDiscoArmor§8] §6Update: §f§fDownload here: " + 
                getPlugin().download);
            else {
              cs.sendMessage("§8[§cDiscoArmor§8] §6Update: §fNo Update avaible!");
            }
          }
          else {
            cs.sendMessage("§8[§cDiscoArmor§8]§4Update checking is disabled!");
          }

          cs.sendMessage("§8[§cDiscoArmor§8] §6Bukkit Page: §fhttp://dev.bukkit.org/bukkit-plugins/discoarmor");
        }
        else {
          cs.sendMessage("§cYou do not have permission.");
        }
      } else if (args[0].equalsIgnoreCase("help")) {
        if (cs.hasPermission("discoarmor.help")) {
          cs.sendMessage("§8[§cDiscoArmor§8] §7Help of DiscoArmor");

          cs.sendMessage("§c/da toggle [player]§8 - Disable and enable the DiscoArmor-Mode.");
          cs.sendMessage("§c/da reload§8 - Reloads the config.");
          cs.sendMessage("§c/da info§8- Get info about the Plugin");
          cs.sendMessage("§c/da update§8- Checks for updates.");
        } else {
          cs.sendMessage("§cYou do not have permission.");
        }
      }
      else if (args[0].equalsIgnoreCase("update")) {
        if (cs.hasPermission("discoarmor.update")) {
          if (getPlugin().updatecheck) {
            cs.sendMessage("§8[§cDiscoArmor§8] §6Checking for Updates...");

            DiscoArmor.checkUpdates(cs);
          } else {
            cs.sendMessage("§8[§cDiscoArmor§8]§4Update checking is disabled!");
          }
        }
        else
        {
          cs.sendMessage("§cYou do not have permission.");
        }
      } else if (args[0].equalsIgnoreCase("list")) {
        if (cs.hasPermission("discoarmor.list")) {
          cs.sendMessage("§8[§cDiscoArmor§8]Players using DiscoArmor now:");

          cs.sendMessage(StringUtils.join(
            getPlugin().players.keySet(), ", "));
        } else {
          cs.sendMessage("§cYou do not have permission to execute this command.");
        }
      }
      else cs.sendMessage("§cUnknow Command! §8Type /da help for help."); 
    }
    else
    {
      cs.sendMessage("§cType /da help for help.");
    }
    return true;
  }

  public void setup()
  {
    getPlugin().getCommand("discoarmor").setExecutor(this);
  }
}