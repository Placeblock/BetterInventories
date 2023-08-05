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
        super(plugin, title, maxHeight);
        this.maxHeight = maxHeight;
    }

    @Override
    public void update() {
        Vector2d oldSize = this.canvas.getSize();
        this.canvas.updateSizeRecursive(this.getMaxSize());
        int width = this.canvas.getWidth();
        this.canvas.setWidth((int) (Math.ceil(width/9F)*9));
        Vector2d newSize = this.canvas.getSize();
        if (!oldSize.equals(newSize)) {
            this.reloadViews();
        }
        super.update();
    }

    @Override
    public Vector2d getMaxSize() {
        return new Vector2d(9, this.maxHeight);
    }
}
