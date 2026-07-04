package main;

import java.awt.Rectangle;

public final class SnakeGrid {
    public static final int COLUMNS = 40;
    public static final int ROWS = 30;
    public static final int HUD_HEIGHT = 48;
    public static final int PADDING = 24;

    private static int cellSize = 20;
    private static int offsetX = 0;
    private static int offsetY = HUD_HEIGHT;

    private SnakeGrid() {
    }

    public static void recalculate(int width, int height) {
        int availableWidth = Math.max(COLUMNS, width - PADDING * 2);
        int availableHeight = Math.max(ROWS, height - PADDING * 2 - HUD_HEIGHT);

        cellSize = Math.max(1, Math.min(availableWidth / COLUMNS, availableHeight / ROWS));

        int boardWidth = cellSize * COLUMNS;
        int boardHeight = cellSize * ROWS;

        offsetX = Math.max(0, (width - boardWidth) / 2);
        offsetY = HUD_HEIGHT + Math.max(0, (height - HUD_HEIGHT - boardHeight) / 2);
    }

    public static Rectangle cellRect(int gridX, int gridY) {
        return new Rectangle(
                offsetX + gridX * cellSize,
                offsetY + gridY * cellSize,
                cellSize,
                cellSize
        );
    }

    public static Rectangle boardRect() {
        return new Rectangle(offsetX, offsetY, cellSize * COLUMNS, cellSize * ROWS);
    }

    public static int getCellSize() {
        return cellSize;
    }

    public static int getOffsetX() {
        return offsetX;
    }

    public static int getOffsetY() {
        return offsetY;
    }

    public static int clampX(int x) {
        return Math.max(0, Math.min(COLUMNS - 1, x));
    }

    public static int clampY(int y) {
        return Math.max(0, Math.min(ROWS - 1, y));
    }
}
