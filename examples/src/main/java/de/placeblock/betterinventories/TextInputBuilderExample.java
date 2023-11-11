package de.placeblock.betterinventories;

import de.placeblock.betterinventories.gui.impl.textinput.TextInputGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Example for using Builders
 */
@SuppressWarnings("unused")
public class TextInputBuilderExample {

    public TextInputBuilderExample(JavaPlugin plugin, Player player) {
        // We can not only create GUIs by extending them, but also through builders!
        // This gets called while the player is typing
        TextInputGUI textInputGUI = new TextInputGUI.Builder<>(plugin, player)
                .title(Component.text("Inventory Title")) // Title of the inventory
                .removeItems(false) /* We don't need this feature here. For more Information visit the JavaDoc */
                .text("New Name") // This sets the text currently in the anvil
                .onUpdate(System.out::println)
                .onFinish((text, aborted) -> { // This gets called when the player submits
                    System.out.println("Player " + player.getName() + " submitted: " + text);
                    return true; // We want to close the inventory
                })
                .build();
        textInputGUI.showPlayer(player); // Show the inventory to the player

    }

}
