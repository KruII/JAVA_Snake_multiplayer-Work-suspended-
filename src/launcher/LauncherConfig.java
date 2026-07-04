package launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class LauncherConfig {
    private static final String DEFAULT_GAME_ID = "snake";

    private final int width;
    private final int height;
    private final boolean fullscreen;
    private final int monitorIndex;
    private final String gameId;

    public LauncherConfig(int width, int height, boolean fullscreen, int monitorIndex, String gameId) {
        this.width = width;
        this.height = height;
        this.fullscreen = fullscreen;
        this.monitorIndex = monitorIndex;
        this.gameId = gameId == null || gameId.trim().length() == 0 ? DEFAULT_GAME_ID : gameId;
    }

    public static File getOptionsFile() {
        String appData = System.getenv("APPDATA");
        File directory;

        if (appData != null && appData.trim().length() > 0) {
            directory = new File(appData, ".Game");
        } else {
            directory = new File(System.getProperty("user.home"), ".Game");
        }

        return new File(directory, "options.");
    }

    public static LauncherConfig load() {
        File file = getOptionsFile();

        try {
            Scanner scanner = new Scanner(file);
            int width = scanner.hasNextLine() ? Integer.parseInt(scanner.nextLine()) : 1280;
            int height = scanner.hasNextLine() ? Integer.parseInt(scanner.nextLine()) : 720;
            boolean fullscreen = scanner.hasNextLine() && Boolean.parseBoolean(scanner.nextLine());
            int monitorIndex = scanner.hasNextLine() ? Integer.parseInt(scanner.nextLine()) : 0;
            String gameId = scanner.hasNextLine() ? scanner.nextLine() : DEFAULT_GAME_ID;
            scanner.close();

            return new LauncherConfig(width, height, fullscreen, monitorIndex, gameId);
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            return new LauncherConfig(1280, 720, false, 0, DEFAULT_GAME_ID);
        }
    }

    public void save() {
        File file = getOptionsFile();
        File directory = file.getParentFile();

        if (directory != null) {
            directory.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(width + "\n");
            writer.write(height + "\n");
            writer.write(fullscreen + "\n");
            writer.write(monitorIndex + "\n");
            writer.write(gameId + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public int getMonitorIndex() {
        return monitorIndex;
    }

    public String getGameId() {
        return gameId;
    }
}
