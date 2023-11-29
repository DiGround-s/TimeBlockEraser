package dev.xf3d3.timeblockeraser.hooks;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class WorldGuard {

    private final TimeBlockEraser plugin;

    public WorldGuard(@NotNull TimeBlockEraser plugin) {
        this.plugin = plugin;
    }

    private final HashMap<String, StateFlag> FLAGS = new HashMap<>(); {
        FLAGS.put("block", new StateFlag("block-erase", true));
        FLAGS.put("entity", new StateFlag("entity-erase", true));
    }

    public StateFlag getFlag(String FlagName) { return FlagName != null ? FLAGS.getOrDefault(FlagName.toLowerCase(), null) : null; }

    public void registerFlags() {
        FlagRegistry flagRegistry = com.sk89q.worldguard.WorldGuard.getInstance().getFlagRegistry();

        for(Map.Entry<String, StateFlag> flag : FLAGS.entrySet()) {
            try {
                flagRegistry.register(flag.getValue());

            } catch (FlagConflictException | IllegalStateException e) {
                Flag<?> registeredFlag = flagRegistry.get(flag.getKey());

                if (registeredFlag instanceof StateFlag) FLAGS.put(flag.getKey(), (StateFlag) registeredFlag);
            }
        }

    }

    public boolean checkFlag(Location Location, StateFlag Flag) {
        try {
            return com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(Location)).testState(null, Flag);
        } catch (Throwable e) {
            plugin.getLogger().log(Level.SEVERE, "error while checking WorldGuard flag value", e);
        }

        return true;
    }

}
