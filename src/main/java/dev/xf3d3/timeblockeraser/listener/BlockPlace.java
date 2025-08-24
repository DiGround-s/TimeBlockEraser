package dev.xf3d3.timeblockeraser.listener;

import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;

public class BlockPlace implements Listener {
    private final TimeBlockEraser plugin;

    public BlockPlace(@NotNull TimeBlockEraser plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        final List<String> worlds = plugin.getSettings().getWorlds();

        // Check if the world is enabled
        if (!worlds.contains(event.getBlockPlaced().getWorld().getName())) {
            return;
        }

        Block mainBlock = event.getBlockPlaced();
        Material material = mainBlock.getType();
        Player player = event.getPlayer();

        // Check if the player has the bypass permission
        if (plugin.getSettings().enableBypass() && player.hasPermission("timeblockeraser.bypass")) {
            return;
        }

        // Check WorldGuard flag
        if (
                plugin.getSettings().getWorldGuardHook() &&
                        !plugin.getWorldGuardHook().checkFlag(player.getLocation(), plugin.getWorldGuardHook().getFlag("block"))
        ) {
            // TODO: remove debug
            plugin.getLogger().log(Level.WARNING, "not removing block because of worldguard flag");
            return;
        }

        // Check if all_blocks is enabled
        if (plugin.getSettings().getAllBlocks().isEnabled()) {
            plugin.runLaterAt(mainBlock.getLocation(), () -> mainBlock.setType(Material.AIR), plugin.getSettings().getAllBlocks().getTime());
            return;
        }

        // Check individual block configurations
        for (String key : plugin.getSettings().getBlocks().keySet()) {
            Material block = Material.getMaterial(key);

            // Check if the block should be removed
            if (block == null || material != block) {
                continue;
            }

            plugin.runLaterAt(mainBlock.getLocation(), () -> mainBlock.setType(Material.AIR), plugin.getSettings().getBlocks().get(key));
            break;
        }
    }
}
