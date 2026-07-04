package launcher;

import java.util.ArrayList;
import java.util.List;

public final class GameEntry {
    private final String id;
    private final String title;
    private final String description;

    public GameEntry(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static List<GameEntry> domyslneGry() {
        List<GameEntry> gry = new ArrayList<GameEntry>();
        gry.add(new GameEntry("snake", "Snake", "Klasyczny Snake na logicznej siatce"));
        return gry;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return title + " - " + description;
    }
}
