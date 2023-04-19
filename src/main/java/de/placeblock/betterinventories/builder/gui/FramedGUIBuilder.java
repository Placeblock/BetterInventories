package de.placeblock.betterinventories.builder.gui;

import org.bukkit.plugin.Plugin;


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
