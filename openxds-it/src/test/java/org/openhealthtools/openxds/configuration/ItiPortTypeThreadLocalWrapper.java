package org.openhealthtools.openxds.configuration;

import java.util.function.Supplier;

public abstract class ItiPortTypeThreadLocalWrapper<T> {
    private ThreadLocal<T> port;
    private Supplier<T> supplier;

    public ItiPortTypeThreadLocalWrapper(Supplier<T> supplier) {
        this.supplier = supplier;
        this.port = new ThreadLocal<>();
    }

    protected T getPort() {
        if(port.get() == null) {
            port.set(supplier.get());
        }
        return port.get();
    }
}
