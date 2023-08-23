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
    public static final Material INPUT_MATERIAL = Material.PAPER;
    public static final Material RESULT_MATERIAL = Material.LIME_DYE;

    private final Player player;
    private final TextInputPacketListener packetListener;
    private final FinishConsumer onFinish;
    private final Consumer<String> onUpdate;
    private final Function<String, TextComponent> titleConverter;
    private String currentText;
    private boolean closed = false;

    /**
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

    private void setInputItem() {
        TextComponent title = Component.text(this.currentText);
        ItemStack inputItem = new ItemBuilder(title, INPUT_MATERIAL).build();
        GUIButton inputButton = new GUIButtonBuilder(this)
                .item(inputItem).build();
        this.setInputItem(inputButton);
    }

    private void setResultButton() {
        TextComponent title = this.titleConverter.apply(this.currentText);
        ItemStack resultItem = new ItemBuilder(title, RESULT_MATERIAL).build();
        GUIButton resultButton = new GUIButtonBuilder(this)
                .item(resultItem)
                .onClick(cd -> this.finish(false)).build();
        this.setResultItem(resultButton);
    }

    public void updateText(String text) {
        this.onUpdate.accept(text);
        this.onUpdate(text);
        this.currentText = text;
        this.setResultButton();
        this.update();
    }

    @Override
    public void onClose(Player player) {
        this.finish(true);
    }

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

    @SuppressWarnings("unused")
    public void onUpdate(String text) {}
    @SuppressWarnings("unused")
    public boolean onFinish(String text, boolean abort) {return true;}

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
