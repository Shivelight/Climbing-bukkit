package io.github.cwkfr.climbing.listener;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import io.github.cwkfr.climbing.Settings;

public class PlayerMove implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();

    Material climbingBlock = Material.getMaterial(Settings.get().getString("block"));
    Material north = player.getLocation().getBlock().getRelative(BlockFace.NORTH).getType();
    Material east = player.getLocation().getBlock().getRelative(BlockFace.EAST).getType();
    Material south = player.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType();
    Material west = player.getLocation().getBlock().getRelative(BlockFace.WEST).getType();

    PotionEffectType effectType =
        PotionEffectType.getByName(Settings.get().getString("effect.type"));
    int effectLevel = Settings.get().getInt("effect.level");
    int effectDuration = (Settings.get().getInt("effect.duration") * 20);
    double upSpeed = Settings.get().getDouble("upSpeed");
    double downSpeed = Settings.get().getDouble("downSpeed");

    // Find climbing blocks around player
    if (north.equals(climbingBlock) || east.equals(climbingBlock) || south.equals(climbingBlock)
        || west.equals(climbingBlock)) {

      // If player is sneaking, go downward and avoid fall damage
      if (player.isSneaking()) {
        player.setVelocity(new Vector(0, downSpeed, 0));
        player.setFallDistance(0);
        return;
      } else { // climb up the block and add effect to player
        player.setVelocity(new Vector(0, upSpeed, 0));
        player.addPotionEffect(new PotionEffect(effectType, effectDuration, effectLevel));
      }
    }
  }
}
