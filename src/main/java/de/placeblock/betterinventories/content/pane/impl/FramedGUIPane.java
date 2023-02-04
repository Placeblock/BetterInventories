package de.placeblock.betterinventories.content.pane.impl;

import de.placeblock.betterinventories.builder.content.GUIPaneBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.content.item.impl.BackGUIButton;
import de.placeblock.betterinventories.content.item.impl.FrameBorderGUIItem;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.FramedGUI;
import de.placeblock.betterinventories.util.Vector2d;
import io.schark.design.texts.Texts;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Author: Placeblock
 */
public class FramedGUIPane extends SimpleGUIPane {
    public static final ItemStack EASTER_BORDER_ITEM = new ItemBuilder(Texts.noItalic(Texts.PREFIX_RAW), Material.CYAN_STAINED_GLASS_PANE).build();

    private Supplier<GUI> backGUI;

    protected SimpleGUIPane firstFrameBorder;
    protected SimpleGUIPane lastFrameBorder;
    @Getter
    private final SimpleGUIPane frame;

    protected final boolean vertical;


    public FramedGUIPane(GUI gui, Vector2d size, boolean vertical, Supplier<GUI> backGUI) {
        super(gui, size, new Vector2d(FramedGUI.FRAMED_GUI_MAX_WIDTH, FramedGUI.FRAMED_GUI_MAX_HEIGHT),
                vertical ? FramedGUI.FRAMED_GUI_VERTICAL_MIN : FramedGUI.FRAMED_GUI_HORIZONTAL_MIN, true);
        this.backGUI = backGUI;
        this.vertical = vertical;
        this.setBackGUI(backGUI);
        Vector2d canvasSize = this.vertical ? new Vector2d(this.getWidth() - 2, this.getHeight()) : new Vector2d(this.getWidth(), this.getHeight() - 2);
        int maxHeight = this.vertical ? FramedGUI.FRAMED_GUI_FRAME_VERTICAL_MAX_HEIGHT : FramedGUI.FRAMED_GUI_FRAME_HORIZONTAL_MAX_HEIGHT;
        Vector2d frameMinSize = new Vector2d(canvasSize.getX(), 1);
        Vector2d frameMaxSize = new Vector2d(canvasSize.getX(), maxHeight);
        this.frame = new SimpleGUIPane(this.getGui(), canvasSize, frameMaxSize, frameMinSize, true);
        this.setSectionAt(this.vertical ? new Vector2d(1, 0) : new Vector2d(0, 1), this.getFrame());
        this.setFrameBorders();
    }

    public void setBackGUI(Supplier<GUI> backGUI) {
        this.backGUI = backGUI;
    }

    protected void updateBackGUI() {
        if (this.backGUI != null) {
            this.lastFrameBorder.setSectionAt(this.lastFrameBorder.getSlots()-1,
                    new BackGUIButton(this.getGui(), this.backGUI));
        }
    }

    @Override
    public void prerender() {
        this.prerenderChildren();
        this.repositionFrameBorders();
        if (this.isAutoSize()) {
            this.updateSize();
        }
    }

    @Override
    public void onUpdateSize() {
        this.setFrameBorders();
    }

    private void setFrameBorders() {
        this.removeSection(this.firstFrameBorder);
        this.removeSection(this.lastFrameBorder);
        this.firstFrameBorder = this.getFrameBorder();
        this.lastFrameBorder = this.getFrameBorder();
        this.updateBackGUI();
        this.repositionFrameBorders();
    }

    private void repositionFrameBorders() {
        this.removeSection(this.firstFrameBorder);
        this.removeSection(this.lastFrameBorder);
        this.setSectionAt(new Vector2d(0, 0), this.firstFrameBorder);
        if (this.vertical) {
            this.setSectionAt(new Vector2d(this.getWidth()-1, 0), this.lastFrameBorder);
        } else {
            this.setSectionAt(new Vector2d(0, this.frame.getHeight()+1), this.lastFrameBorder);
        }
    }

    private SimpleGUIPane getFrameBorder() {
        SimpleGUIPane pane;
        GUI gui = this.getGui();
        Vector2d frameBorderSize = this.vertical ? new Vector2d(1, this.getHeight()) : new Vector2d(this.getWidth(), 1);
        pane =  new GUIPaneBuilder(gui).size(frameBorderSize).build();
        for (int i = 0; i < pane.getSlots(); i++) {
            pane.addSection(new FrameBorderGUIItem(this.getGui()) {
                @Override
                protected void onActivate(int slot) {
                    FramedGUIPane.this.playEasterAnimation(slot);
                }
            });
        }
        return pane;
    }




    // FOR EASTER EGG

    public void playEasterAnimation(int slotStart) {
        this.moveAnimation(this.slotToVector(slotStart), EASTER_BORDER_ITEM);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> this.moveAnimation(this.slotToVector(slotStart), GUI.PLACEHOLDER_ITEM), 4);
    }

    private void moveAnimation(Vector2d start, ItemStack item) {
        int startIndex = this.vertical ? start.getY() : start.getX();
        final int[] index = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                Set<FrameBorderGUIItem> animationItems = FramedGUIPane.this.getNextButtons(startIndex, index[0]);
                for (FrameBorderGUIItem animationItem : animationItems) {
                    animationItem.setItem(item);
                }
                FramedGUIPane.this.getGui().update();
                index[0]++;
                if (index[0] == 13) {
                    this.cancel();
                }
            }
        }.runTaskTimer(this.getGui().getPlugin(), 0, 1);
    }

    @NotNull
    private Set<FrameBorderGUIItem> getNextButtons(int index, int delta) {
        Vector2d previous = this.vertical ? new Vector2d(0, index - delta) : new Vector2d(index - delta, 0);
        Vector2d next = this.vertical ? new Vector2d(0, index + delta) : new Vector2d(index + delta, 0);
        GUISection firstNext = this.firstFrameBorder.getSectionAt(previous);
        GUISection firstPrevious = this.firstFrameBorder.getSectionAt(next);
        GUISection lastNext = this.lastFrameBorder.getSectionAt(previous);
        GUISection lastPrevious = this.lastFrameBorder.getSectionAt(next);
        Set<FrameBorderGUIItem> nextSections = new HashSet<>();
        if (firstNext instanceof FrameBorderGUIItem borderItem) nextSections.add(borderItem);
        if (firstPrevious instanceof FrameBorderGUIItem borderItem) nextSections.add(borderItem);
        if (lastNext instanceof FrameBorderGUIItem borderItem) nextSections.add(borderItem);
        if (lastPrevious instanceof FrameBorderGUIItem borderItem) nextSections.add(borderItem);
        return nextSections;
    }
}
