package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

@Getter
public class ChestGUI extends CanvasGUI implements Sizeable {
    @Setter
    private int maxHeight;

    public ChestGUI(Plugin plugin, TextComponent title, int maxHeight) {
        super(plugin, title, 0);
        this.maxHeight = maxHeight;
    }

    @Override
    public void update() {
        Vector2d oldSize = this.canvas.getSize();
        this.canvas.updateSizeRecursive(this.getMaxSize());
        Vector2d newSize = this.canvas.getSize();
        if (oldSize != newSize) {
            this.reloadViews();
        }
        super.update();
    }

    @Override
    public Vector2d getMaxSize() {
        return new Vector2d(9, this.maxHeight);
    }
}
