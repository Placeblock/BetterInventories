package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.function.Function;

/**
 * A {@link GUIButton} which returns back to an {@link GUI}.
 * Material is set to {@link Material#RED_STAINED_GLASS_PANE}
 */
@SuppressWarnings("unused")
public class BackGUIButton extends SwitchGUIButton {
    /**
     * Creates a new BackGUIButton
     * @param gui The GUI
     * @param targetGUI The GUI to be opened on click
     * @param title The title of the Button
     * @param cooldown The cooldown of the Button. Applied to the material, not the Button alone.
     * @param sound The sound that is played when pressing that button
     * @param permission The permission required to press this button
     */
    protected BackGUIButton(GUI gui, int cooldown, Sound sound, String permission, Function<Player, GUI> targetGUI, TextComponent title) {
        super(gui, new ItemBuilder(title, Material.RED_STAINED_GLASS_PANE).build(), cooldown, sound, permission, targetGUI);
    }

    /**
     * Abstract Builder for creating {@link BackGUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link GUIButton} that is built
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends BackGUIButton> extends SwitchGUIButton.AbstractBuilder<B, P> {
        private TextComponent title = Component.text("Zurück").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false);

        /**
         * Sets the title attribute
         * @param title The title of the {@link BackGUIButton}
         * @return Itself
         */
        public B title(TextComponent title) {
            this.title = title;
            return this.self();
        }

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        protected AbstractBuilder(GUI gui) {
            super(gui);
        }
    }

    /**
     * Builder for creating {@link BackGUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, BackGUIButton> {
        /**
         * Creates a new Builder
         * @param gui The gui the new Buttons belong to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public BackGUIButton build() {
            return new BackGUIButton(this.getGui(), this.getCooldown(), this.getSound(),
                    this.getPermission(), this.getTargetGUI(), this.getTitle());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
