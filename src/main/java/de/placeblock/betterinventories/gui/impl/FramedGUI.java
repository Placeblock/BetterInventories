package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.content.SimpleGUIPaneBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.content.item.impl.SwitchGUIButton;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Author: Placeblock
 */
@Getter
public class FramedGUI extends CanvasGUI<SimpleGUIPane> {
    public static final ItemStack FRAME_BORDER_ITEM = new ItemBuilder(Component.empty(), Material.BLUE_STAINED_GLASS_PANE).build();
    protected final SimpleGUIPane frame;
    protected final SimpleGUIPane firstFrameBorder;
    protected final SimpleGUIPane lastFrameBorder;
    protected final boolean vertical;

    protected FramedGUI(Plugin plugin, TextComponent title, int height, GUI backInventory, boolean vertial) {
        super(plugin, title, InventoryType.CHEST, new Vector2d(9, height));
        this.vertical = vertial;
        if (height < 3) {
            throw new IllegalArgumentException("Expected minimum size of 3 but got " + height + " in FramedGUI");
        }
        this.frame = new SimpleGUIPane(this, new Vector2d(9, height));
        this.frame.setSectionAt(this.vertical ? new Vector2d(1, 0) : new Vector2d(0, 1), this.getCanvas());
        this.lastFrameBorder = this.getFrameBorder();
        this.frame.setSectionAt(new Vector2d(), this.lastFrameBorder);
        this.firstFrameBorder = this.getFrameBorder();
        if (backInventory != null) {
            this.firstFrameBorder.setSectionAt(this.firstFrameBorder.getSlots()-1, new SwitchGUIButton(this, backInventory));
        }
        if (this.vertical) {
            this.frame.setSectionAt(new Vector2d(this.frame.getWidth()-1, 0), this.firstFrameBorder);
        } else {
            this.frame.setSectionAt(new Vector2d(0, this.frame.getHeight()-1), this.firstFrameBorder);
        }
    }

    @Override
    SimpleGUIPane createCanvas(Vector2d size) {
        return new SimpleGUIPane(this, new Vector2d(size.getX(), size.getY()-2));
    }

    @Override
    public int getSize() {
        return this.frame.getSlots();
    }

    @Override
    public List<ItemStack> renderContent() {
        return this.frame.render();
    }

    @Override
    public GUISection getClickedSection(int slot) {
        return this.frame.getSectionAt(slot);
    }

    private SimpleGUIPane getFrameBorder() {
        SimpleGUIPane pane;
        if (this.vertical) {
            pane =  new SimpleGUIPaneBuilder(this).size(new Vector2d(1, this.frame.getHeight())).build();
        } else {
            pane = new SimpleGUIPaneBuilder(this).size(new Vector2d(this.frame.getWidth(), 1)).build();
        }
        for (int i = 0; i < pane.getSlots(); i++) {
            pane.addSection(new GUIItem(this, FRAME_BORDER_ITEM));
        }
        return pane;
    }
}
