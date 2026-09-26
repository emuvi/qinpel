package br.com.pointel.wiz_jarch.flow;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A highly capable logging wrapper around SLF4J that also embeds runtime flow control signals.
 * Components utilizing Pace can be externally paused or stopped mid-execution. 
 * Heavy tasks periodically call {@link #waitIfPausedAndThrowIfStopped()} to hook into this lifecycle.
 */
public class Pace {

    private final Logger logger;

    private volatile boolean paused = false;
    private volatile boolean stopped = false;
    private volatile boolean closed = false;

    /**
     * Constructs a Pace tracker bound to an SLF4J Logger.
     *
     * @param logger the underlying SLF4J logger implementation
     */
    public Pace(Logger logger) {
        this.logger = logger;
    }

    /**
     * Signals the background process to suspend execution.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Signals the background process to resume execution.
     */
    public void resume() {
        paused = false;
    }

    /**
     * Signals the background process to permanently stop execution.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Marks the logger/tracker as closed.
     */
    public void close() {
        closed = true;
    }

    /**
     * @return true if the tracker is currently paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @return true if the tracker has been permanently stopped
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * @return true if the tracker has been closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * A blocking checkpoint. If the tracker is paused, the calling thread will sleep
     * in a loop until resumed. If the tracker is stopped, it forcefully throws an exception
     * to abort the current flow.
     *
     * @throws Exception if the user has triggered a stop signal
     */
    public void waitIfPausedAndThrowIfStopped() throws Exception {
        while (paused) {
            Thread.sleep(100);
        }
        if (stopped) {
            throw new Exception("Pace was stopped by the user.");
        }
    }

    /**
     * A shorter alias for {@link #waitIfPausedAndThrowIfStopped()}.
     *
     * @throws Exception if the user has triggered a stop signal
     */
    public void check() throws Exception {
        waitIfPausedAndThrowIfStopped();
    }

    /**
     * A blocking checkpoint. If the tracker is paused, the calling thread will sleep
     * in a loop until resumed.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void waitIfPaused() throws InterruptedException {
        while (paused) {
            Thread.sleep(100);
        }
    }

    /**
     * Forcefully throws an exception if the tracker is stopped.
     *
     * @throws Exception if the user has triggered a stop signal
     */
    public void throwIfStopped() throws Exception {
        if (stopped) {
            throw new Exception("Pace was stopped by the user.");
        }
    }

    /**
     * Forcefully throws an exception if the tracker is closed.
     *
     * @throws Exception if the tracker has been closed
     */
    public void throwIfClosed() throws Exception {
        if (closed) {
            throw new Exception("Pace was closed.");
        }
    }

    /**
     * Sleeps for the specified duration, periodically checking for pause/stop signals.
     * This ensures the sleep can be interrupted by a stop signal or paused.
     *
     * @param millis the duration to sleep in milliseconds
     * @throws Exception if stopped during sleep
     */
    public void sleep(long millis) throws Exception {
        if (millis <= 0) {
            waitIfPausedAndThrowIfStopped();
            return;
        }
        long end = System.currentTimeMillis() + millis;
        while (System.currentTimeMillis() < end) {
            waitIfPausedAndThrowIfStopped();
            long remaining = end - System.currentTimeMillis();
            if (remaining > 0) {
                Thread.sleep(Math.min(100, remaining));
            }
        }
    }

    /**
     * Resets the paused, stopped, and closed states to false.
     */
    public void reset() {
        paused = false;
        stopped = false;
        closed = false;
    }

    /**
     * Toggles the paused state.
     */
    public void togglePause() {
        paused = !paused;
    }

    /**
     * @return the underlying SLF4J logger instance
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return the name of the underlying logger
     */
    public String getName() {
        return logger.getName();
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public void trace(String msg) {
        logger.trace(msg);
    }

    public void trace(String format, Object arg) {
        logger.trace(format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        logger.trace(format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        logger.trace(format, arguments);
    }

    public void trace(String msg, Throwable t) {
        logger.trace(msg, t);
    }

    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg) {
        logger.trace(marker, msg);
    }

    public void trace(Marker marker, String format, Object arg) {
        logger.trace(marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        logger.trace(marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... argArray) {
        logger.trace(marker, format, argArray);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        logger.trace(marker, msg, t);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(String format, Object arg) {
        logger.debug(format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        logger.debug(format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    public void debug(String msg, Throwable t) {
        logger.debug(msg, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        logger.debug(marker, msg);
    }

    public void debug(Marker marker, String format, Object arg) {
        logger.debug(marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        logger.debug(marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... arguments) {
        logger.debug(marker, format, arguments);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        logger.debug(marker, msg, t);
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(String format, Object arg) {
        logger.info(format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        logger.info(format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    public void info(String msg, Throwable t) {
        logger.info(msg, t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        logger.info(marker, msg);
    }

    public void info(Marker marker, String format, Object arg) {
        logger.info(marker, format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        logger.info(marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... arguments) {
        logger.info(marker, format, arguments);
    }

    public void info(Marker marker, String msg, Throwable t) {
        logger.info(marker, msg, t);
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(String format, Object arg) {
        logger.warn(format, arg);
    }

    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }

    public void warn(String format, Object arg1, Object arg2) {
        logger.warn(format, arg1, arg2);
    }

    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        logger.warn(marker, msg);
    }

    public void warn(Marker marker, String format, Object arg) {
        logger.warn(marker, format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        logger.warn(marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... arguments) {
        logger.warn(marker, format, arguments);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        logger.warn(marker, msg, t);
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(String format, Object arg) {
        logger.error(format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        logger.error(format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

    public void error(String msg, Throwable t) {
        logger.error(msg, t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        logger.error(marker, msg);
    }

    public void error(Marker marker, String format, Object arg) {
        logger.error(marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        logger.error(marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... arguments) {
        logger.error(marker, format, arguments);
    }

    public void error(Marker marker, String msg, Throwable t) {
        logger.error(marker, msg, t);
    }

}
