package de.placeblock.betterinventories.gui.impl.textinput;

import de.placeblock.betterinventories.builder.content.GUIButtonBuilder;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.PlayerGUI;
import de.placeblock.betterinventories.gui.impl.AnvilGUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * GUI for getting Text Input
 */
public class TextInputGUI extends AnvilGUI implements PlayerGUI<Player> {
    /**
     * The input-material
     */
    public static final Material INPUT_MATERIAL = Material.PAPER;

    /**
     * The result-material
     */
    public static final Material RESULT_MATERIAL = Material.LIME_DYE;

    /**
     * The player which enters text
     */
    private final Player player;

    /**
     * The packet-listener used to receive the anvil's text.
     */
    private final TextInputPacketListener packetListener;

    /**
     * Called when a player clicks on the submit-item
     */
    private final FinishConsumer onFinish;

    /**
     * Called when a player types
     */
    private final Consumer<String> onUpdate;

    /**
     * Is called to convert the current text to the title of the submit item
     */
    private final Function<String, TextComponent> titleConverter;

    /**
     * The current text the player entered
     */
    private String currentText;

    /**
     * Whether this inventory is closed (Needed for internal stuff)
     */
    private boolean closed = false;

    /**
     * Creates a new TextInputGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param player The player which enters text
     * @param text The initial Text
     * @param onFinish Is called when the player finishes renaming (by submitting or by aborting)
     */
    @SuppressWarnings("unused")
    public TextInputGUI(Plugin plugin, TextComponent title,
                        Player player, String text,
                        FinishConsumer onFinish) {
        this(plugin, title, player, text, onFinish, Component::text);
    }

    /**
     * Creates a new TextInputGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param player The player which enters text
     * @param text The initial Text
     * @param onFinish Is called when the player finishes renaming (by submitting or by aborting)
     * @param titleConverter Is called to convert the current text to the title of the submit item
     */
    public TextInputGUI(Plugin plugin, TextComponent title,
                        Player player, String text,
                        FinishConsumer onFinish,
                        Function<String, TextComponent> titleConverter) {
        this(plugin, title, player, text, onFinish, t -> {}, titleConverter);
    }

    /**
     * Creates a new TextInputGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param player The player which enters text
     * @param text The initial Text
     * @param titleConverter Is called to convert the current text to the title of the submit item
     */
    @SuppressWarnings("unused")
    public TextInputGUI(Plugin plugin, TextComponent title,
                        Player player, String text,
                        Function<String, TextComponent> titleConverter) {
        this(plugin, title, player, text, (t, a) -> true, t -> {}, titleConverter);
    }

    /**
     * Creates a new TextInputGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param player The player which enters text
     * @param text The initial Text
     * @param onFinish Is called when the player finishes renaming (by submitting or by aborting)
     * @param onUpdate Is called whenever the player types something
     * @param titleConverter Is called to convert the current text to the title of the submit item
     */
    public TextInputGUI(Plugin plugin, TextComponent title,
                        Player player, String text,
                        FinishConsumer onFinish,
                        Consumer<String> onUpdate,
                        Function<String, TextComponent> titleConverter) {
        super(plugin, title);
        this.player = player;
        this.currentText = text;
        this.onFinish = onFinish;
        this.onUpdate = onUpdate;
        this.titleConverter = titleConverter;
        this.setInputItem();
        this.setResultButton();
        this.update();
        this.packetListener = new TextInputPacketListener(this);
        this.packetListener.inject();
    }

    /**
     * Sets the input-item into the GUI
     */
    private void setInputItem() {
        TextComponent title = Component.text(this.currentText);
        ItemStack inputItem = new ItemBuilder(title, INPUT_MATERIAL).build();
        GUIButton inputButton = new GUIButtonBuilder(this)
                .item(inputItem).build();
        this.setInputItem(inputButton);
    }

    /**
     * Sets the result-button into the GUI
     */
    private void setResultButton() {
        TextComponent title = this.titleConverter.apply(this.currentText);
        ItemStack resultItem = new ItemBuilder(title, RESULT_MATERIAL).build();
        GUIButton resultButton = new GUIButtonBuilder(this)
                .item(resultItem)
                .onClick(cd -> this.finish(false)).build();
        this.setResultItem(resultButton);
    }

    /**
     * Is called by the {@link TextInputPacketListener} to update the text
     * @param text The new text
     */
    public void updateText(String text) {
        this.onUpdate.accept(text);
        this.onUpdate(text);
        this.currentText = text;
        this.setResultButton();
        this.update();
    }

    /**
     * Is called when the player closes the GUI.
     * Aborts the text-input.
     * @param player The player, who closed the GUI
     */
    @Override
    public void onClose(Player player) {
        this.finish(true);
    }

    /**
     * Is called to finish text-input
     * @param abort Whether the player aborted, e.g. by closing the Inventory
     */
    private void finish(boolean abort) {
        if (!this.closed) {
            this.closed = true;
            if ((this.onFinish.accept(this.currentText, abort) &&
                this.onFinish(this.currentText, abort)) || abort) {
                this.packetListener.uninject();
                this.player.closeInventory();
            }
        }
    }


    /**
     * Called when a player types
     * @param text The new text
     */
    @SuppressWarnings("unused")
    public void onUpdate(String text) {}

    /**
     * Called when a player clicks on the submit-item and can be overridden
     * @param text The final text
     * @param abort Whether the player aborted, e.g. by closing the Inventory
     * @return GUI closes when true is returned
     */
    @SuppressWarnings("unused")
    public boolean onFinish(String text, boolean abort) {return true;}

    /**
     * @return The player, who is entering text
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }
}
