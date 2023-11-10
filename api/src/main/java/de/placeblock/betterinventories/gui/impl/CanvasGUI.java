package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.util.InventoryTypeMapper;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Used for creating GUIs that only have one canvas e.g. HopperGUIs and a fixed size.
 * Bear in mind that for creating Chest Inventories you should use {@link ChestGUI}
 */
@SuppressWarnings("unused")
public class CanvasGUI extends BaseCanvasGUI<SimpleGUIPane> {
    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param height The height of the GUI
     */
    @Deprecated(forRemoval = true)
    public CanvasGUI(Plugin plugin, TextComponent title, int height, boolean removeItems) {
        super(plugin, title, InventoryType.CHEST, removeItems);
        this.setCanvas(new SimpleGUIPane.Builder(this).size(new Vector2d(9, height)).autoSize(false).build());
    }

    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     */
    @Deprecated(forRemoval = true)
    public CanvasGUI(Plugin plugin, TextComponent title, InventoryType type, boolean removeItems) {
        super(plugin, title, type, removeItems);
        this.setCanvas(new SimpleGUIPane.Builder(this).size(InventoryTypeMapper.getSize(type)).autoSize(false).build());
    }


    public static class Builder<P extends JavaPlugin> extends BaseCanvasGUI.Builder<Builder<P>, CanvasGUI, SimpleGUIPane, P> {
        private int height = 3;
        private InventoryType type = InventoryType.CHEST;

        public Builder(P plugin) {
            super(plugin);
        }

        public Builder<P> type(InventoryType type) {
            this.type = type;
            return this.self();
        }

        public Builder<P> height(int height) {
            this.height = height;
            return this.self();
        }

        @Override
        public CanvasGUI build() {
            if (this.type == InventoryType.CHEST) {
                return new CanvasGUI(this.getPlugin(), this.getTitle(), this.height, this.isRemoveItems());
            } else {
                return new CanvasGUI(this.getPlugin(), this.getTitle(), this.type, this.isRemoveItems());
            }
        }

        @Override
        protected Builder<P> self() {
            return this;
        }
    }
}
