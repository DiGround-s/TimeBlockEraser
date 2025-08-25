package dev.xf3d3.timeblockeraser.config;

import net.william278.annotaml.YamlComment;
import net.william278.annotaml.YamlFile;
import net.william278.annotaml.YamlKey;

import java.util.List;
import java.util.Map;

/**
 * Plugin settings, read from config.yml
 */
@SuppressWarnings("unused")
@YamlFile(header = """
        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
        ┃    TimeBlockEraser Config    ┃
        ┃      Developed by xF3d3      ┃
        ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
        ┃
        ┗╸ Information: https://modrinth.com/plugin/timeblockeraser""")
public class Settings {

    @YamlComment("Enabled Worlds")
    @YamlKey("enabled-worlds")
    private List<String> enabledWorlds = List.of("BoxPvP", "MyWorld");

    @YamlComment("Enable bypass permission")
    @YamlKey("bypass-perm")
    private boolean bypass = true;

    @YamlComment("Enable WorldGuard hook")
    @YamlKey("worldguard-hook")
    private boolean flag = false;

    @YamlComment("Set here the blocks you want to delete and the time in seconds! [NOTE: The blocks name can be different between versions]")
    @YamlKey("blocks")
    private Map<String, Integer> blocks = Map.of(
            "OBSIDIAN", 10,
            "COBWEB", 30
    );

    @YamlComment("Set here the entities you want to delete and the time in seconds! [NOTE: The entities name can be different between versions] \nAvailable entities: armor stands, boats, minecarts, end crystals")
    @YamlKey("entities")
    private Map<String, Integer> entities = Map.of(
            "ENDER_CRYSTAL", 10
    );

    @YamlComment("Make ALL placed blocks temporary - overrides individual block settings when enabled")
    @YamlKey("all_blocks.enabled")
    private boolean allBlocksEnabled = false;

    @YamlKey("all_blocks.time")
    private int allBlocksTime = 20;

    public boolean enableBypass() {
        return bypass;
    }

    public boolean getWorldGuardHook() {
        return flag;
    }

    public List<String> getWorlds() {
        return enabledWorlds;
    }

    public Map<String, Integer> getBlocks() {
        return blocks;
    }

    public Map<String, Integer> getEntities() {
        return entities;
    }

    public boolean isAllBlocksEnabled() {
        return allBlocksEnabled;
    }

    public int getAllBlocksTime() {
        return allBlocksTime;
    }
}
