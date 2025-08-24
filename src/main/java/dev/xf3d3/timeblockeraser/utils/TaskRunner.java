package dev.xf3d3.timeblockeraser.utils;

import dev.xf3d3.timeblockeraser.TimeBlockEraser;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import space.arim.morepaperlib.scheduling.GracefulScheduling;
import space.arim.morepaperlib.scheduling.ScheduledTask;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface TaskRunner {
    TimeBlockEraser plugin = TimeBlockEraser.getPlugin();

    default void runAsync(@NotNull Runnable runnable) {
        final int taskId = getTasks().size();
        getTasks().put(taskId, getScheduler().asyncScheduler().run(runnable));
    }

    default <T> CompletableFuture<T> supplyAsync(@NotNull Supplier<T> supplier) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        getScheduler().asyncScheduler().run(() -> future.complete(supplier.get()));
        return future;
    }

    default int runAsyncRepeating(@NotNull Runnable runnable, long period) {
        final int taskId = getNextTaskId();
        getTasks().put(taskId, getScheduler().asyncScheduler().runAtFixedRate(runnable, getDurationTicks(0), getDurationTicks(period)));
        return taskId;
    }

    default void runLater(@NotNull Runnable runnable, long delay) {
        getScheduler().globalRegionalScheduler().runDelayed(runnable, delay * 20);
    }

    default void runLaterAt(@NotNull Location location, @NotNull Runnable runnable, long delay) {
        getScheduler().regionSpecificScheduler(location).runDelayed(runnable, delay * 20);
    }

    default void runSync(@NotNull Runnable runnable) {
        getScheduler().globalRegionalScheduler().run(runnable);
    }

    default void runSyncAt(@NotNull Location location, @NotNull Runnable runnable) {
        getScheduler().regionSpecificScheduler(location).run(runnable);
    }

    default void runSyncRepeating(@NotNull Runnable runnable, long period) {
        getScheduler().asyncScheduler().runAtFixedRate(runnable, getDurationTicks(0), getDurationTicks(period));
    }

    default void runSyncRepeatingAt(@NotNull Location location, @NotNull Runnable runnable, long period) {
        getScheduler().asyncScheduler().runAtFixedRate(runnable, getDurationTicks(0), getDurationTicks(period));
    }

    @NotNull
    GracefulScheduling getScheduler();

    @NotNull
    ConcurrentHashMap<Integer, ScheduledTask> getTasks();

    default int getNextTaskId() {
        int taskId = 0;
        while (getTasks().containsKey(taskId)) {
            taskId++;
        }
        return taskId;
    }

    default void cancelTask(int taskId) {
        if (getTasks().containsKey(taskId)) {
            getTasks().get(taskId).cancel();
            getTasks().remove(taskId);
        }
    }

    default void cancelAllTasks() {
        getScheduler().cancelGlobalTasks();
        getTasks().values().forEach(ScheduledTask::cancel);
        getTasks().clear();
    }

    @NotNull
    default Duration getDurationTicks(long ticks) {
        return Duration.of(ticks * 50, ChronoUnit.MILLIS);
    }
}
