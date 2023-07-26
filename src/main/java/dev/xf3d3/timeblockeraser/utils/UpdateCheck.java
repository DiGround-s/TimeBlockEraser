package dev.xf3d3.timeblockeraser.utils;

import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import net.william278.desertwell.util.UpdateChecker;
import net.william278.desertwell.util.Version;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class UpdateCheck {
    private final TimeBlockEraser plugin;

    public UpdateCheck(@NotNull TimeBlockEraser plugin) {
        this.plugin = plugin;
    }

    private Version getVersion() {
        return Version.fromString(plugin.getDescription().getVersion(), "-");
    }

    @NotNull
    private UpdateChecker getUpdateChecker() {
        return UpdateChecker.builder()
                .currentVersion(getVersion())
                .endpoint(UpdateChecker.Endpoint.MODRINTH)
                .resource("O5OQXCl8")
                .build();
    }

    public void checkForUpdates() {
        getUpdateChecker().check().thenAccept(checked -> {
            if (!checked.isUpToDate()) {
                plugin.log(Level.WARNING, "A new version of TimeBlockEraser is available: v"
                        + checked.getLatestVersion() + " (running v" + getVersion() + ")");
            }
        });
    }
}
