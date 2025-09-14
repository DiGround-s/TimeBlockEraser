package dev.xf3d3.timeblockeraser.utils;

import com.tcoded.folialib.impl.PlatformScheduler;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface TaskRunner {
    TimeBlockEraser plugin = TimeBlockEraser.getPlugin();

    default void runLater(@NotNull Runnable runnable, long delay) {
        getScheduler().runLater(runnable, delay * 20);
    }

    default void runLaterAt(@NotNull Location location, @NotNull Runnable runnable, long delay) {
        getScheduler().runAtLocationLater(location, runnable, delay * 20);
    }

    default void runLaterAtEntity(@NotNull Entity entity, @NotNull Runnable runnable, long delay) {
        getScheduler().runAtEntityLater(entity, runnable, delay * 20);
    }

    default void runSync(@NotNull Consumer<WrappedTask> runnable) {
        getScheduler().runNextTick(runnable);
    }



    @NotNull
    PlatformScheduler getScheduler();
}
