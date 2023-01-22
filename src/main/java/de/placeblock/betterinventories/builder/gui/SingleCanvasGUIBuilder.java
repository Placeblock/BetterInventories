package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.gui.impl.SingleCanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SingleCanvasGUIBuilder extends BaseGUIBuilder<SingleCanvasGUI, SingleCanvasGUIBuilder>{
    private Integer height;

    public SingleCanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    public SingleCanvasGUIBuilder height(int height) {
        this.height = height;
        return this;
    }

    protected int getHeight() {
        if (this.height == null && this.getType() == null) {
            throw new IllegalStateException("Height is null in builder");
        }
        return this.height;
    }


    @Override
    public SingleCanvasGUI build() {
        if (this.height != null) {
            return new SingleCanvasGUI(this.getPlugin(), this.getTitle(), this.getHeight());
        } else {
            return new SingleCanvasGUI(this.getPlugin(), this.getTitle(), this.getType());
        }
    }
}
