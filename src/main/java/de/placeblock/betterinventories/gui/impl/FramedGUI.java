package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.content.SimpleGUIPaneBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.content.item.impl.BackGUIButton;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import io.schark.design.texts.Texts;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Placeblock
 */
@Getter
public class FramedGUI extends CanvasGUI<SimpleGUIPane> {
    public static final ItemStack EASTER_BORDER_ITEM = new ItemBuilder(Texts.noItalic(Texts.PREFIX_RAW), Material.CYAN_STAINED_GLASS_PANE).build();
    public static final int MAX_CLICK_DELAY = 400;
    protected final SimpleGUIPane frame;
    protected final SimpleGUIPane firstFrameBorder;
    protected final SimpleGUIPane lastFrameBorder;

    protected final boolean vertical;

    // FOR EASTER EGG
    private long lastClick = 0;
    private int clicks = 0;

    public FramedGUI(Plugin plugin, TextComponent title, int height, GUI backInventory) {
        this(plugin, title, height, backInventory, false);
    }

    public FramedGUI(Plugin plugin, TextComponent title, int height, GUI backInventory, boolean vertical) {
        super(plugin, title, InventoryType.CHEST);
        this.vertical = vertical;
        if (!this.vertical && height < 3) {
            throw new IllegalArgumentException("Expected minimum size of 3 but got " + height + " in FramedGUI");
        }

        this.frame = new SimpleGUIPane(this, new Vector2d(9, height));
        this.firstFrameBorder = this.getFrameBorder();
        this.lastFrameBorder = this.getFrameBorder();
        this.setBackInventory(backInventory);
        this.setFrameBorders(this.firstFrameBorder, this.lastFrameBorder);

        Vector2d canvasSize = this.vertical ? new Vector2d(this.frame.getWidth() - 2, height) : new Vector2d(this.frame.getWidth(), height - 2);
        this.setCanvas(new SimpleGUIPane(this, canvasSize));
        this.frame.setSectionAt(this.vertical ? new Vector2d(1, 0) : new Vector2d(0, 1), this.getCanvas());
    }

    private void setFrameBorders(SimpleGUIPane firstFrameBorder, SimpleGUIPane lastFrameBorder) {
        this.frame.setSectionAt(new Vector2d(0, 0), firstFrameBorder);
        if (this.vertical) {
            this.frame.setSectionAt(new Vector2d(this.frame.getWidth()-1, 0), lastFrameBorder);
        } else {
            this.frame.setSectionAt(new Vector2d(0, this.frame.getHeight()-1), lastFrameBorder);
        }
    }

    public void setBackInventory(GUI backInventory) {
        if (backInventory != null) {
            this.lastFrameBorder.setSectionAt(this.lastFrameBorder.getSlots()-1, new BackGUIButton(this, () -> backInventory));
        }
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
            pane.addSection(this.getFillGUIItem());
        }
        return pane;
    }

    public GUIButton getFillGUIItem() {
        return new FrameBorderItem(this) {
            // FOR EASTER EGG
            @Override
            public void onClick(Player player, int slot) {
                long time = System.currentTimeMillis();
                if (time-FramedGUI.this.lastClick > MAX_CLICK_DELAY) {
                    FramedGUI.this.clicks = 0;
                } else {
                    FramedGUI.this.clicks++;
                    if (FramedGUI.this.clicks > 3) {
                        FramedGUI.this.easterAnimation(slot);
                        FramedGUI.this.clicks = 0;
                    }
                }
                FramedGUI.this.lastClick = time;
            }
        };
    }

    private abstract static class FrameBorderItem extends GUIButton {
        public FrameBorderItem(FramedGUI gui) {
            super(gui, PLACEHOLDER_ITEM);
        }
    }

    // FOR EASTER EGG
    private void easterAnimation(int slotStart) {
        this.moveAnimation(this.frame.slotToVector(slotStart), EASTER_BORDER_ITEM);
        Bukkit.getScheduler().runTaskLater(this.getPlugin(), () -> this.moveAnimation(this.frame.slotToVector(slotStart), PLACEHOLDER_ITEM), 4);
    }

    private void moveAnimation(Vector2d start, ItemStack item) {
        int startIndex = this.vertical ? start.getY() : start.getX();
        final int[] index = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                Set<FrameBorderItem> animationItems = FramedGUI.this.getNextButtons(startIndex, index[0]);
                for (FrameBorderItem animationItem : animationItems) {
                    animationItem.setItem(item);
                }
                FramedGUI.this.update();
                index[0]++;
                if (index[0] == 13) {
                    this.cancel();
                }
            }
        }.runTaskTimer(this.getPlugin(), 0, 1);
    }

    @NotNull
    private Set<FrameBorderItem> getNextButtons(int index, int delta) {
        Vector2d previous = this.vertical ? new Vector2d(0, index - delta) : new Vector2d(index - delta, 0);
        Vector2d next = this.vertical ? new Vector2d(0, index + delta) : new Vector2d(index + delta, 0);
        GUISection firstNext = this.firstFrameBorder.getSectionAt(previous);
        GUISection firstPrevious = this.firstFrameBorder.getSectionAt(next);
        GUISection lastNext = this.lastFrameBorder.getSectionAt(previous);
        GUISection lastPrevious = this.lastFrameBorder.getSectionAt(next);
        Set<FrameBorderItem> nextSections = new HashSet<>();
        if (firstNext instanceof FrameBorderItem borderItem) nextSections.add(borderItem);
        if (firstPrevious instanceof FrameBorderItem borderItem) nextSections.add(borderItem);
        if (lastNext instanceof FrameBorderItem borderItem) nextSections.add(borderItem);
        if (lastPrevious instanceof FrameBorderItem borderItem) nextSections.add(borderItem);
        return nextSections;
    }
}
