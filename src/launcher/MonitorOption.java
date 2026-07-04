package launcher;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MonitorOption {
    private final int index;
    private final GraphicsDevice device;
    private final Rectangle bounds;

    public MonitorOption(int index, GraphicsDevice device) {
        this.index = index;
        this.device = device;
        this.bounds = device.getDefaultConfiguration().getBounds();
    }

    public static List<MonitorOption> pobierzMonitory() {
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        List<MonitorOption> monitory = new ArrayList<MonitorOption>();

        for (int i = 0; i < devices.length; i++) {
            monitory.add(new MonitorOption(i, devices[i]));
        }

        return monitory;
    }

    public List<Dimension> pobierzRozdzielczosci() {
        Map<String, Dimension> unique = new LinkedHashMap<String, Dimension>();
        DisplayMode[] modes = device.getDisplayModes();

        for (int i = 0; i < modes.length; i++) {
            DisplayMode mode = modes[i];
            int width = mode.getWidth();
            int height = mode.getHeight();

            if (width < 640 || height < 480) {
                continue;
            }

            String key = width + "x" + height;
            unique.put(key, new Dimension(width, height));
        }

        if (unique.isEmpty()) {
            unique.put(bounds.width + "x" + bounds.height, new Dimension(bounds.width, bounds.height));
        }

        return new ArrayList<Dimension>(unique.values());
    }

    public int getIndex() {
        return index;
    }

    public GraphicsDevice getDevice() {
        return device;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        return "Monitor " + (index + 1) + " (" + bounds.width + "x" + bounds.height + ")";
    }
}
