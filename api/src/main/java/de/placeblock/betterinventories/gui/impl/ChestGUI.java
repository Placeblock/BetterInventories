package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Implementation of {@link BaseChestGUI} using SimpleGUIPane that auto resizes
 */
public class ChestGUI extends BaseChestGUI<SimpleGUIPane> {

    /**
     * Creates a new ChestGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param minHeight The minimum height of the GUI
     * @param maxHeight The maximum height of the GUI
     */
    @Deprecated(forRemoval = true)
    public ChestGUI(Plugin plugin, TextComponent title, boolean removeItems, int minHeight, int maxHeight) {
        super(plugin, title, removeItems, minHeight, maxHeight);
        this.setCanvas(new SimpleGUIPane.Builder(this).adoptMinMax(this).autoSize(true).build());
    }


    public static class Builder<P extends JavaPlugin> extends BaseChestGUI.Builder<Builder<P>, ChestGUI, SimpleGUIPane, P> {

        public Builder(P plugin) {
            super(plugin);
        }

        public Builder<P> height(int height) {
            this.minHeight(height);
            this.maxHeight(height);
            return this.self();
        }

        @Override
        public ChestGUI build() {
            return new ChestGUI(this.getPlugin(), this.getTitle(), this.isRemoveItems(),
                    this.getMinHeight(), this.getMaxHeight());
        }

        @Override
        protected Builder<P> self() {
            return this;
        }
    }
}
