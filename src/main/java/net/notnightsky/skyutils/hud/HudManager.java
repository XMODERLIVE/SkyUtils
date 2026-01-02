package net.notnightsky.skyutils.hud;

import net.notnightsky.skyutils.hud.component.HudEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class HudManager {

    private final static HudManager INSTANCE = new HudManager();

    public static HudManager getInstance() {
        return INSTANCE;
    }

    private final Map<Identifier, HudEntry> entries;
    private final MinecraftClient client;

    private HudManager() {
        this.entries = new LinkedHashMap<>();
        client = MinecraftClient.getInstance();
    }

    public void refreshAllBounds() {
        for (HudEntry entry : getEntries()) {
            entry.onBoundsUpdate();
        }
    }

    public HudManager add(HudEntry entry) {
        entries.put(entry.getId(), entry);
        return this;
    }

    public List<HudEntry> getEntriesSorted() {
        List<HudEntry> entryList = getEntries();
        entryList.sort(Comparator.comparing(hudEntry -> hudEntry.getId().toString()));
        return entryList;
    }

    public List<HudEntry> getEntries() {
        if (entries.size() > 0) {
            return new ArrayList<>(entries.values());
        }
        return Collections.emptyList();
    }

    public List<HudEntry> getMoveableEntries() {
        if (entries.size() > 0) {
            return entries.values().stream().filter((entry) -> entry.isEnabled()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public HudEntry get(Identifier identifier) {
        return entries.get(identifier);
    }

    public void render(DrawContext context, float tickDelta) {
        for (HudEntry hud : getEntries()) {
            if (hud.isEnabled()) {
                hud.render(context, tickDelta);
            }
        }
    }
}