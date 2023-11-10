package de.placeblock.betterinventories;

import de.placeblock.betterinventories.gui.impl.textinput.TextInputGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

// With gui.showPlayer(Player) you can show the GUI to a player!
@SuppressWarnings("unused")
public class TextInputExample extends TextInputGUI {

    public TextInputExample(Plugin plugin, Player player) {
        // Several constructors available
        super(plugin,
                Component.text("GUI Title"), // The title of the GUI
                true,
                player,
                "Start Text", // The text to start with
                (text, abort) -> true, // Gets called if the player submits the text or closes the inventory
                System.out::println, // Gets called while typing
                Component::text);

        // You don't have to call update() on this one!
    }

    // Can be used optionally (Preferred method when deriving TextInputGUI)
    @Override
    public boolean onFinish(String text, boolean abort) {
        System.out.println(text);
        return true;
    }
    @Override
    public void onUpdate(String text) {
        System.out.println(text);
    }
}
