package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
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
     */
    public BackGUIButton(GUI gui, int cooldown, Sound sound, String permission, Function<Player, GUI> targetGUI, TextComponent title) {
        super(gui, new ItemBuilder(title, Material.RED_STAINED_GLASS_PANE).build(), cooldown, sound, permission, targetGUI);
    }

    public static class Builder extends AbstractBuilder<Builder, BackGUIButton> {
        private TextComponent title = Component.text("Zurück").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false);

        public Builder(GUI gui) {
            super(gui);
        }

        public Builder title(TextComponent title) {
            this.title = title;
            return this;
        }

        @Override
        public BackGUIButton build() {
            return new BackGUIButton(this.getGui(), this.getCooldown(), this.getSound(),
                    this.getPermission(), this.getTargetGUI(), this.title);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
