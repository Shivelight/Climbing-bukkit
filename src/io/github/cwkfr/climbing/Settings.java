package io.github.cwkfr.climbing;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {
  static FileConfiguration configFile;
  static File file;

  public static void setup(Plugin plugin) {
    Settings.file = new File(plugin.getDataFolder(), "config.yml");
    if (!Settings.file.exists()) {
      try {
        Settings.file.createNewFile();
      } catch (IOException ex) {
      }
    }
    Settings.configFile = (FileConfiguration) YamlConfiguration.loadConfiguration(Settings.file);
  }

  public static FileConfiguration get() {
    return Settings.configFile;
  }

  public static void save() {
    try {
      Settings.configFile.save(Settings.file);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
    }
  }

  public static void reload() {
    Settings.configFile = (FileConfiguration) YamlConfiguration.loadConfiguration(Settings.file);
  }

}