package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.impl.FramedGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.FramedGUI;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unchecked")
@Getter
public abstract class BaseFramedGUIBuilder<G extends FramedGUI, B extends BaseFramedGUIBuilder<G, B>> extends BaseChestGUIBuilder<FramedGUIPane, G, B> {
    private boolean vertical = false;
    private Supplier<GUI> backGUI;

    public BaseFramedGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    public B vertical() {
        this.vertical = true;
        return (B) this;
    }

    public B backGUI(Supplier<GUI> backGUI) {
        this.backGUI = backGUI;
        return (B) this;
    }
}
