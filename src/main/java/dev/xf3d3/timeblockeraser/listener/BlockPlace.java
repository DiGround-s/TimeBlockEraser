package dev.xf3d3.timeblockeraser.listener;

import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        for (String key : plugin.getSettings().getBlocks().keySet()) {
            Material block = Material.getMaterial(key);

            if (block == null || material != block)
                continue;

            plugin.runLater(() -> mainBlock.setType(Material.AIR), plugin.getSettings().getBlocks().get(key));
        }
    }
}
