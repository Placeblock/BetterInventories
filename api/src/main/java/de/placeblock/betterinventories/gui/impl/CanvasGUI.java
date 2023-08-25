package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.gui.CanvasGUIBuilder;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.util.InventoryTypeMapper;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Used for creating GUIs that only have one canvas e.g. HopperGUIs and a fixed size.
 * Bear in mind that for creating Chest Inventories you should use {@link ChestGUI}
 * <br>
 * Builder: {@link CanvasGUIBuilder}
 */
@SuppressWarnings("unused")
public class CanvasGUI extends BaseCanvasGUI<SimpleGUIPane> {
    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param height The height of the GUI
     */
    public CanvasGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, InventoryType.CHEST, new Vector2d(9, height));
    }

    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param height The height of the GUI
     * @param registerDefaultHandlers Whether to register default-handlers
     */
    public CanvasGUI(Plugin plugin, TextComponent title, int height, boolean registerDefaultHandlers) {
        this(plugin, title, InventoryType.CHEST, new Vector2d(9, height), registerDefaultHandlers);
    }

    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     */
    public CanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        this(plugin, title, type, InventoryTypeMapper.getSize(type));
    }

    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     * @param registerDefaultHandlers Whether to register default-handlers
     */
    public CanvasGUI(Plugin plugin, TextComponent title, InventoryType type, boolean registerDefaultHandlers) {
        this(plugin, title, type, InventoryTypeMapper.getSize(type), registerDefaultHandlers);
    }

    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     * @param size The size of the GUI
     */
    protected CanvasGUI(Plugin plugin, TextComponent title, InventoryType type, Vector2d size) {
        this(plugin, title, type, size, true);
    }

    /**
     * Creates a new CanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     * @param size The size of the GUI
     */
    protected CanvasGUI(Plugin plugin, TextComponent title, InventoryType type, Vector2d size, boolean registerDefaultHandlers) {
        super(plugin, title, type, registerDefaultHandlers);
        this.setCanvas(new SimpleGUIPane(this, size, size, false));
    }
}
