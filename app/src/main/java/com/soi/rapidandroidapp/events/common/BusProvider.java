package com.soi.rapidandroidapp.events.common;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
 * such as through injection directly into interested classes.
 */
public final class BusProvider {
    public static final String BUS_TAG = BusProvider.class.getSimpleName();
    private static final Bus BUS = new MainThreadBus(new Bus());

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

    /**
     * This bus CAN be called from all threads and it will call subscribe methods on the MAIN thread.
     */
    public static class MainThreadBus extends Bus {
        private final Bus mBus;
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        public MainThreadBus(final Bus bus) {
            if (bus == null) {
                throw new NullPointerException("bus must not be null");
            }
            mBus = bus;
        }

        @Override
        public void register(Object obj) {
            mBus.register(obj);
        }

        @Override
        public void unregister(Object obj) {
            mBus.unregister(obj);
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                mBus.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBus.post(event);
                    }
                });
            }
        }
    }
}