package dev.xf3d3.timeblockeraser.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import dev.xf3d3.timeblockeraser.utils.Utils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandAlias("timeblockeraser")
public class MainCommand extends BaseCommand {
    private final TimeBlockEraser plugin;

    public MainCommand(@NotNull TimeBlockEraser plugin) {
        this.plugin = plugin;
    }

    @Default
    @Subcommand("about")
    @CommandCompletion("@nothing")
    @CommandPermission("timeblockeraser.about")
    public void about(@NotNull CommandSender sender) {
        sender.sendMessage(Utils.Color("&3~~~~~~~~~~ &6&nTimeBlockEraser&r &3~~~~~~~~~~"));
        sender.sendMessage(Utils.Color("&3Version: &6" + plugin.getDescription().getVersion()));
        sender.sendMessage(Utils.Color("&3Author: &6" + plugin.getDescription().getAuthors()));
        sender.sendMessage(Utils.Color("&3Description: &6" + plugin.getDescription().getDescription()));
        sender.sendMessage(Utils.Color("&3Website: "));
        sender.sendMessage(Utils.Color("&6" + plugin.getDescription().getWebsite()));
        sender.sendMessage(Utils.Color("&3~~~~~~~~~~ &6&nTimeBlockEraser&r &3~~~~~~~~~~"));
    }

    @Subcommand("reload")
    @CommandCompletion("@nothing")
    @CommandPermission("timeblockeraser.reload")
    public void reloadSubcommand(CommandSender sender) {
        sender.sendMessage(Utils.Color("&3Reloading the plugin..."));

        plugin.runAsync(() -> {
            plugin.loadConfigs();
            plugin.runSync(() -> sender.sendMessage(Utils.Color("&3Plugin reloaded successfully!")));
        });
    }
}
