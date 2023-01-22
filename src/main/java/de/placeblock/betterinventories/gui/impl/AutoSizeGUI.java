package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class AutoSizeGUI extends GUI {
    private final PaginatorGUIPane paginator;
    private int lastUpdateRows = 1;
    private final int maxHeight;

    public AutoSizeGUI(Plugin plugin, TextComponent title, boolean repeat, int maxHeight) {
        super(plugin, title, InventoryType.CHEST);
        this.maxHeight = Math.max(2, Math.min(6, maxHeight));
        this.paginator = new PaginatorGUIPane(this, 9, 1, repeat);
    }

    public void addItem(GUIItem item) {
        this.addItem(item, true);
    }

    public void addItem(GUIItem item, boolean update) {
        this.paginator.addItem(item);
        if (update) {
            this.update();
        }
    }

    public void addItems(Collection<GUIItem> items) {
        for (GUIItem item : items) {
            this.addItem(item, false);
        }
        this.update();
    }

    @Override
    public void update() {
        this.updateHeight();
        super.update();
    }

    private void updateHeight() {
        int slots = this.paginator.getItems().size();
        int rows = Math.min(this.maxHeight, (int) Math.ceil(slots/9F));
        if (this.lastUpdateRows != rows) {
            this.lastUpdateRows = rows;
            this.paginator.setHeight(rows);
            this.reloadViews();
        }
    }

    @Override
    public int getSize() {
        return this.paginator.getSlots();
    }

    @Override
    public List<ItemStack> render() {
        return this.paginator.render();
    }

    @Override
    public GUISection getClickedSection(int slot) {
        return this.paginator.getSectionAt(slot);
    }
}
