package br.com.pointel.wiz_jarch.mage;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import java.awt.Desktop;
import java.io.File;
import java.net.InetAddress;
import java.net.URI;

/**
 * Native integration utility providing OS detection, process insights,
 * hardware profiling, desktop integration, and native JNA bindings.
 * <p>
 * This class abstracts complex system-level calls, granting easy access to memory limits, 
 * active window tracking, and cross-platform (Windows, Linux, macOS) process management.
 * </p>
 */
public class WizNative {

    private static final String OS_NAME_PROPERTY = "os.name";

    /**
     * Checks if the underlying operating system is a Windows variant.
     *
     * @return {@code true} if running on Windows, {@code false} otherwise
     */
    public static boolean isWin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("win"));
    }

    /**
     * Checks if the underlying operating system is a Unix/Linux variant.
     *
     * @return {@code true} if running on Linux, Unix, or AIX, {@code false} otherwise
     */
    public static boolean isLin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0);
    }

    /**
     * Checks if the underlying operating system is macOS.
     *
     * @return {@code true} if running on macOS, {@code false} otherwise
     */
    public static boolean isMac() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("mac"));
    }

    // =========================================================================
    // SYSTEM AND PROCESS INFORMATION
    // =========================================================================

    /**
     * Retrieves the process ID (PID) of the currently running Java Virtual Machine.
     *
     * @return the process ID mapped from the active {@link ProcessHandle}
     */
    public static long getPID() {
        return ProcessHandle.current().pid();
    }

    /**
     * Retrieves the raw operating system name property exactly as defined by the JVM.
     *
     * @return the OS name string mapped from {@link System#getProperty(String)}
     */
    public static String getOsName() {
        return System.getProperty(OS_NAME_PROPERTY);
    }

    /**
     * Retrieves the underlying operating system version.
     *
     * @return the OS version string
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Retrieves the operating system architecture payload (e.g., amd64, x86, aarch64).
     *
     * @return the OS architecture string
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Retrieves the username of the account actively executing the application.
     *
     * @return the system username string
     */
    public static String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * Retrieves the home directory path of the active user.
     *
     * @return the absolute path string to the user's home directory
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Retrieves the current working directory from which the application was launched.
     *
     * @return the user directory path string
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Resolves the local network hostname of the host machine.
     * <p>
     * If the resolution via {@link InetAddress} fails, it gracefully falls back to returning {@code "localhost"}.
     * </p>
     *
     * @return the active hostname string
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "localhost";
        }
    }

    // =========================================================================
    // HARDWARE AND MEMORY
    // =========================================================================

    /**
     * Retrieves the exact number of logical processors currently available to the Java Virtual Machine.
     *
     * @return the number of available processors exposed by the {@link Runtime}
     */
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Retrieves the total amount of instantly accessible free memory in the Java Virtual Machine.
     *
     * @return the free memory allocation defined in bytes
     */
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * Retrieves the total amount of memory currently reserved and allocated to the Java Virtual Machine.
     *
     * @return the total memory footprint defined in bytes
     */
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * Retrieves the maximum absolute ceiling of memory that the Java Virtual Machine will attempt to use.
     *
     * @return the maximum threshold limit defined in bytes
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * Calculates the real-time amount of memory actively being used by the JVM footprint.
     *
     * @return the used memory calculation defined in bytes
     */
    public static long getUsedMemory() {
        return getTotalMemory() - getFreeMemory();
    }

    // =========================================================================
    // DESKTOP INTEGRATION
    // =========================================================================

    /**
     * Requests the native operating system to open a specified web {@link URI} in the default internet browser.
     * <p>
     * This method safely verifies the {@link Desktop} availability and fails silently if desktop integration is unsupported.
     * </p>
     *
     * @param url the destination URL to open
     */
    public static void openUrl(String url) {
        if (url == null || url.isBlank()) return;
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            // Silent fallback
        }
    }

    /**
     * Requests the native operating system to open a local {@link File} leveraging its default associated desktop application.
     * <p>
     * This method safely verifies the {@link Desktop} availability and fails silently if desktop integration is unsupported.
     * </p>
     *
     * @param file the target file to execute/open
     */
    public static void openFile(File file) {
        if (file == null || !file.exists()) return;
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            // Silent fallback
        }
    }

    // =========================================================================
    // JNA / ACTIVE WINDOW (NATIVE)
    // =========================================================================

    /**
     * Retrieves the title of the currently focused/active window operating globally on the host operating system.
     * <p>
     * Leverages native JNA framework bindings strictly tailored and routed for Windows, Linux, and macOS platforms.
     * </p>
     *
     * @return the title of the active window, or an empty string if unresolvable or inaccessible
     * @throws Exception if native library loading or interrogation explicitly fails
     * @throws UnsupportedOperationException if the host OS is unsupported
     */
    public static String getActiveWindowTitle() throws Exception {
        if (isWin()) {
            return getWinActiveWindowTitle();
        } else if (isLin()) {
            return getLinActiveWindowTitle();
        } else if (isMac()) {
            return getMacActiveWindowTitle();
        } else {
            throw new UnsupportedOperationException("Not supported on this OS.");
        }
    }

    /**
     * Fetches the active window title natively on Windows utilizing {@code user32.dll} bindings.
     *
     * @return the focused window title string
     */
    private static String getWinActiveWindowTitle() {
        WinUser32 user32 = WinUser32.getInstance();
        Pointer hWnd = user32.GetForegroundWindow();
        char[] windowText = new char[1024];
        user32.GetWindowTextW(hWnd, windowText, 512);
        return Native.toString(windowText);
    }

    /**
     * Fetches the active window title natively on Linux/Unix operating systems mapping {@code X11} bindings.
     *
     * @return the focused window title string
     * @throws Exception if X11 native communication structurally fails
     */
    private static String getLinActiveWindowTitle() throws Exception {
        LinX11 x11 = LinX11.getInstance();
        LinX11.Display display = x11.XOpenDisplay(null);
        if (display == null) {
            throw new UnsupportedOperationException("Cannot open X display");
        }
        try {
            LinX11.Window window = x11.XDefaultRootWindow(display);
            LinX11.WindowByReference activeWindow = new LinX11.WindowByReference();
            x11.XGetInputFocus(display, activeWindow, new int[1]);
            if (activeWindow.getValue() == null || activeWindow.getValue().longValue() == 0) {
                return "";
            }

            String windowTitle = getWindowProperty(display, activeWindow.getValue(), "_NET_WM_NAME");
            if (windowTitle == null || windowTitle.isEmpty()) {
                windowTitle = getWindowProperty(display, activeWindow.getValue(), "WM_NAME");
            }
            return windowTitle != null ? windowTitle : "";
        } finally {
            x11.XCloseDisplay(display);
        }
    }

    /**
     * Helper protocol to securely extract precise window properties routed from an X11 Atom context.
     *
     * @param display  the active X11 display connection pointer
     * @param window   the targeted X11 window pointer
     * @param property the internal property key to query
     * @return the resolved string value payload of the property, or {@code null} if missing/unreadable
     */
    private static String getWindowProperty(LinX11.Display display, LinX11.Window window, String property) {
        LinX11 x11 = LinX11.getInstance();
        LinX11.Atom propertyAtom = x11.XInternAtom(display, property, false);
        LinX11.AtomByReference actualType = new LinX11.AtomByReference();
        int[] actualFormat = new int[1];
        NativeLongByReference nitems = new NativeLongByReference();
        NativeLongByReference bytesAfter = new NativeLongByReference();
        PointerByReference prop = new PointerByReference();

        LinX11.Atom anyPropertyTypeAtom = x11.XInternAtom(display, "ANY_PROPERTY_TYPE", true);
        int status = x11.XGetWindowProperty(display, window, propertyAtom, new NativeLong(0), new NativeLong(1024),
                false, anyPropertyTypeAtom, actualType, actualFormat, nitems, bytesAfter, prop);
        if (status == LinX11.Success && prop.getValue() != null) {
            String value = prop.getValue().getString(0, "UTF-8");
            x11.XFree(prop.getValue());
            return value;
        }
        return null;
    }

    /**
     * Fetches the active window title natively on macOS utilizing {@code CoreGraphics} bindings.
     *
     * @return the focused window title string
     * @throws Exception if CoreGraphics interrogation structurally fails
     */
    private static String getMacActiveWindowTitle() throws Exception {
        MacCoreGraphics instance = MacCoreGraphics.getInstance();
        MacCoreGraphics.CGWindowID[] windowArray = instance.CGWindowListCopyWindowInfo(MacCoreGraphics.kCGWindowListOptionOnScreenOnly | MacCoreGraphics.kCGWindowListExcludeDesktopElements, MacCoreGraphics.kCGNullWindowID);
        if (windowArray == null || windowArray.length == 0) {
            return "";
        }
        for (MacCoreGraphics.CGWindowID window : windowArray) {
            String title = instance.CGWindowTitle(window);
            if (title != null && !title.isEmpty()) {
                return title;
            }
        }
        return "";
    }

    /**
     * Internal JNA integration bindings mapping the Windows {@code user32.dll} API.
     */
    private interface WinUser32 extends StdCallLibrary {

        /**
         * Loads and securely returns the localized User32 instance.
         */
        static WinUser32 getInstance() {
            if (isWin()) {
                return Native.load("user32", WinUser32.class, W32APIOptions.DEFAULT_OPTIONS);
            } else {
                throw new UnsupportedOperationException("This method is only supported on Windows OS.");
            }
        }

        Pointer GetForegroundWindow();

        int GetWindowTextW(Pointer hWnd, char[] lpString, int nMaxCount);

    }

    /**
     * Internal JNA integration bindings mapping the Linux/Unix {@code libX11.so} API.
     */
    private interface LinX11 extends X11 {
        
         /**
          * Loads and securely returns the localized X11 instance.
          */
         static LinX11 getInstance() {
            if (isLin()) {
                return Native.load("X11", LinX11.class);
            } else {
                throw new UnsupportedOperationException("This method is only supported on Linux OS.");
            }
        }

        int Success = 0;

        int XGetInputFocus(Display display, WindowByReference focus_return, int[] revert_to_return);

        int XGetWindowProperty(Display display, Window w, Atom property, NativeLong long_offset, NativeLong long_length,
                              boolean delete, Atom req_type, AtomByReference actual_type_return, int[] actual_format_return,
                              NativeLongByReference nitems_return, NativeLongByReference bytes_after_return,
                              PointerByReference prop_return);

        Atom XInternAtom(Display display, String atom_name, boolean only_if_exists);

        int XFree(Pointer data);

        /**
         * Internal JNA by-reference pointer wrapper abstracting an X11 Window.
         */
        class WindowByReference extends ByReference {

            public WindowByReference() {
                super(Native.POINTER_SIZE);
            }

            public Window getValue() {
                Pointer p = getPointer().getPointer(0);
                if (p == null) return null;
                return new Window(Pointer.nativeValue(p));
            }

        }

        /**
         * Internal JNA by-reference pointer wrapper abstracting an X11 Atom context.
         */
        class AtomByReference extends ByReference {

            public AtomByReference() {
                super(Native.POINTER_SIZE);
            }

            public Atom getValue() {
                Pointer p = getPointer().getPointer(0);
                if (p == null) return null;
                return new Atom(Pointer.nativeValue(p));
            }

        }
    }

    /**
     * Internal JNA integration bindings mapping the macOS {@code CoreGraphics} framework API.
     */
    private interface MacCoreGraphics extends com.sun.jna.Library {

        /**
         * Loads and securely returns the localized CoreGraphics instance.
         */
        static MacCoreGraphics getInstance() {
            if (isMac()) {
                return Native.load("CoreGraphics", MacCoreGraphics.class);
            } else {
                throw new UnsupportedOperationException("This method is only supported on Mac OS.");
            }
        }

        int kCGWindowListOptionOnScreenOnly = 1;
        int kCGWindowListExcludeDesktopElements = 16;
        int kCGNullWindowID = 0;

        CGWindowID[] CGWindowListCopyWindowInfo(int option, int relativeToWindow);

        String CGWindowTitle(CGWindowID window);

        /**
         * Internal JNA specific wrapper providing mapping for macOS Window ID tracking.
         */
        class CGWindowID extends com.sun.jna.IntegerType {
            public CGWindowID() { super(4); }
            public CGWindowID(long value) { super(4, value); }
        }

    }

}
