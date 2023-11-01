package de.placeblock.betterinventories.content.pane.impl.transfer;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
@SuppressWarnings("unused")
public class TransferGUIPane extends SimpleItemGUIPane {
    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param size The size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public TransferGUIPane(GUI gui, Vector2d size, boolean autoSize) {
        this(gui, size, size, true);
    }
    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public TransferGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        this(gui, minSize, maxSize, true);
    }

    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public TransferGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
        /*this.registerInteractionHandler(new InteractionHandler() {
            @Override
            public boolean onClick(InventoryClickEvent event) {
                System.out.println(event.getAction());
                System.out.println(event.getRawSlot());
                System.out.println(event.getSlot());
                System.out.println(event.getHotbarButton());
                System.out.println(event.getCurrentItem());
                return false;
            }
            @Override
            public boolean onDrag(InventoryDragEvent event) {
                System.out.println(event);
                return false;
            }
        });*/
    }


    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

}
