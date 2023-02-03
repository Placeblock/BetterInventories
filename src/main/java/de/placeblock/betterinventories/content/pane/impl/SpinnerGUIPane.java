package de.placeblock.betterinventories.content.pane.impl;

import de.placeblock.betterinventories.builder.content.GUIItemBuilder;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import io.schark.design.texts.Texts;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SpinnerGUIPane extends SimpleGUIPane {
    private static final List<Vector2d> DELTA_POSITIONS = List.of(new Vector2d(1, 1), new Vector2d(1, 0),
            new Vector2d(1, -1), new Vector2d(0, -1), new Vector2d(-1, -1), new Vector2d(-1, 0),
            new Vector2d(-1, 1), new Vector2d(0, 1));
    private static final Vector2d CENTER_POSITION = new Vector2d(1, 1);
    private static final ItemStack FIRST_ITEM = new ItemBuilder(Texts.PREFIX_RAW, Material.LIGHT_BLUE_STAINED_GLASS_PANE).build();
    private static final ItemStack SECOND_ITEM = new ItemBuilder(Texts.PREFIX_RAW, Material.CYAN_STAINED_GLASS_PANE).build();
    private static final ItemStack THIRD_ITEM = new ItemBuilder(Texts.PREFIX_RAW, Material.BLUE_STAINED_GLASS_PANE).build();
    private int startIndex = 0;
    private BukkitTask spinTask;

    public SpinnerGUIPane(GUI gui) {
        super(gui, new Vector2d(3, 3));
    }

    public void start() {
        this.spinTask = Bukkit.getScheduler().runTaskTimer(this.getGui().getPlugin(), () -> {
            for (int i = 0; i < 8; i++) {
                int deltaIndex = (this.startIndex+i)%8;
                Vector2d position = CENTER_POSITION.add(DELTA_POSITIONS.get(deltaIndex));
                ItemStack item = i < 2 ? FIRST_ITEM : i < 4 ? SECOND_ITEM : THIRD_ITEM;
                this.setSectionAt(position, new GUIItemBuilder(this.getGui()).item(item).build());
            }
            this.getGui().update();
            this.startIndex = Util.modulo(this.startIndex - 1, 8);
        }, 0, 2);
    }

    public void stop() {
        if (this.spinTask != null) {
            this.spinTask.cancel();
        }
    }
}
