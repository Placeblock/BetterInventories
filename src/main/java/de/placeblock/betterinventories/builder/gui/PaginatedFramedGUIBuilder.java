package de.placeblock.betterinventories.builder.gui;

import org.bukkit.plugin.Plugin;


public class PaginatedFramedGUIBuilder extends BaseFramedGUIBuilder<PaginatedFramedGUI, PaginatedFramedGUIBuilder>{
    private Integer maxHeight = 6;
    private boolean autoSize = false;

    public PaginatedFramedGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    public PaginatedFramedGUIBuilder maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public PaginatedFramedGUIBuilder autoSize() {
        this.autoSize = true;
        this.height(1);
        return this;
    }

    @Override
    public PaginatedFramedGUI build() {
        super.build();
        return new PaginatedFramedGUI(this.getPlugin(),
                this.getTitle(),
                this.getHeight(),
                this.maxHeight,
                this.getBackGUI(),
                this.isVertical(),
                this.autoSize);
    }
}
