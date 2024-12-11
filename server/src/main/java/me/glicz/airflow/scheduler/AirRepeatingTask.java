package me.glicz.airflow.scheduler;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.scheduler.RepeatingTask;
import me.glicz.airflow.api.scheduler.Task;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AirRepeatingTask extends AirTask implements RepeatingTask {
    private final int interval;
    private boolean afterDelay;

    AirRepeatingTask(Plugin plugin, Consumer<Task> action, boolean async, int delay, int interval, int id) {
        super(plugin, action, async, delay, id);
        this.interval = interval;
    }

    @Override
    public int getInterval() {
        return this.interval;
    }

    @Override
    void tick() {
        tick++;

        if (!afterDelay && tick >= getDelay()) {
            afterDelay = true;
            run();
            return;
        }

        if (afterDelay && tick % interval == 0) {
            run();
        }
    }

    static class Builder extends AirTask.Builder implements RepeatingTask.Builder {
        private int interval;

        Builder(AirScheduler scheduler, Plugin plugin, Consumer<Task> action) {
            super(scheduler, plugin, action);
        }

        @Override
        public RepeatingTask.@NotNull Builder interval(int ticks) {
            this.interval = ticks;
            return this;
        }

        @Override
        AirTask build(int id) {
            return new AirRepeatingTask(plugin, action, async, delay, interval, id);
        }

        @Override
        public RepeatingTask.@NotNull Builder async() {
            return (RepeatingTask.Builder) super.async();
        }

        @Override
        public RepeatingTask.@NotNull Builder delay(int ticks) {
            return (Builder) super.delay(ticks);
        }

        @Override
        public @NotNull RepeatingTask schedule() {
            return (RepeatingTask) super.schedule();
        }
    }
}
