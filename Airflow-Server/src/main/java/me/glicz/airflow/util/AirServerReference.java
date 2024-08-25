package me.glicz.airflow.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.util.ServerReference;

public class AirServerReference implements ServerReference {
    private AirServer server;

    @Override
    public AirServer getServer() {
        return server;
    }

    public void setServer(Supplier<AirServer> supplier) {
        Preconditions.checkState(this.server == null, "Server is already set");
        this.server = supplier.get();
    }
}
