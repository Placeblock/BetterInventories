package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.gui.impl.FramedGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public class FramedGUIBuilder extends BaseFramedGUIBuilder<FramedGUI, FramedGUIBuilder> {
    public FramedGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public FramedGUI build() {
        super.build();
        return new FramedGUI(this.getPlugin(),
                this.getTitle(),
                this.getHeight(),
                this.getBackGUI(),
                this.isVertical());
    }
}
