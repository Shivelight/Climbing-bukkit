package io.github.cwkfr.climbing;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.cwkfr.climbing.listener.PlayerMove;

public class Climbing extends JavaPlugin {

  public void onEnable() {

    Settings.setup(this);
    Settings.get().addDefault("block", "LOG");
    Settings.get().addDefault("effect.type", "HUNGER");
    Settings.get().addDefault("effect.level", 2);
    Settings.get().addDefault("effect.duration", 3);
    Settings.get().addDefault("upSpeed", 0.2);
    Settings.get().addDefault("downSpeed", -0.1);
    Settings.get().addDefault("messages.nopermission", "&cYou don't have permission!");
    Settings.get().options().copyDefaults(true);
    Settings.save();

    getServer().getPluginManager().registerEvents(new PlayerMove(), this);
    getLogger().info("Climbing has been enabled!");

  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (cmd.getName().equalsIgnoreCase("climb")) {
      if (sender.isOp() || sender.hasPermission("climbing.admin")) {
        if (args.length == 0) {
          sender.sendMessage(ChatColor.GOLD + "--- [Climbing] ---");
          sender.sendMessage(ChatColor.YELLOW + "Block: " + Settings.get().getString("block"));
          sender.sendMessage(
              ChatColor.YELLOW + "Effect Type: " + Settings.get().getString("effect.type"));
          sender.sendMessage(
              ChatColor.YELLOW + "Effect Level: " + Settings.get().getInt("effect.level"));
          sender.sendMessage(
              ChatColor.YELLOW + "Effect Duration: " + Settings.get().getInt("effect.duration"));
          sender.sendMessage(ChatColor.YELLOW + "Up Speed: " + Settings.get().getDouble("upSpeed")
              + " || Down Speed: " + Settings.get().getDouble("downSpeed"));
          sender.sendMessage(ChatColor.YELLOW + "/climb reload - Reload the configration");

          return true;
        }
        if (args.length > 0) {
          if (args[0].equalsIgnoreCase("reload")) {
            Settings.reload();
            sender.sendMessage(ChatColor.GOLD + "[Climbing] Reloaded.");
            return true;
          }
        }
      }

      sender.sendMessage(parseColor(Settings.get().getString("messages.nopermission")));
      return true;
    }

    return true;
  }

  public String parseColor(String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
}
