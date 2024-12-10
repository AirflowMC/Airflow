package me.glicz.airflow.console;

import net.minecraft.server.dedicated.DedicatedServer;
import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

import java.nio.file.Paths;

public class AirflowConsole extends SimpleTerminalConsole {
    private final DedicatedServer server;

    public AirflowConsole(DedicatedServer server) {
        this.server = server;
    }

    @Override
    protected LineReader buildReader(LineReaderBuilder builder) {
        return super.buildReader(builder
                .appName("Airflow")
                .variable(LineReader.HISTORY_FILE, Paths.get(".console_history"))
        );
    }

    @Override
    protected boolean isRunning() {
        return !server.isStopped() && server.isRunning();
    }

    @Override
    protected void runCommand(String command) {
        server.handleConsoleInput(command, server.createCommandSourceStack());
    }

    @Override
    protected void shutdown() {
        server.halt(false);
    }
}
