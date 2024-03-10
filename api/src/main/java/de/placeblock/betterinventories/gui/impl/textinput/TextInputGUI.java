package de.placeblock.betterinventories.gui.impl.textinput;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.PlayerGUI;
import de.placeblock.betterinventories.gui.impl.BaseAnvilGUI;
import de.placeblock.betterinventories.nms.TextInputPacketListener;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * GUI for getting Text Input
 */
public class TextInputGUI extends BaseAnvilGUI implements PlayerGUI<Player> {
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
     * @param onUpdate Is called whenever the player types something
     * @param titleConverter Is called to convert the current text to the title of the submit item
     * @param removeItems Whether to remove loose items on close.
     *                   The first player that closes the gui gets the items
     */
    protected TextInputGUI(Plugin plugin, TextComponent title, boolean removeItems,
                        Player player, String text,
                        FinishConsumer onFinish,
                        Consumer<String> onUpdate,
                        Function<String, TextComponent> titleConverter) {
        super(plugin, title, removeItems);
        this.player = player;
        this.currentText = text;
        this.onFinish = onFinish;
        this.onUpdate = onUpdate;
        this.titleConverter = titleConverter;
        this.setInputItem();
        this.setResultButton();
        this.update();
        this.packetListener = GUI.NMS_BRIDGE.getTextInputPacketListener(this.player, this::updateText);
        this.packetListener.register();
    }

    /**
     * Sets the input-item into the GUI
     */
    private void setInputItem() {
        TextComponent title = Component.text(this.currentText);
        ItemStack inputItem = new ItemBuilder(title, INPUT_MATERIAL).build();
        GUIButton inputButton = new GUIButton.Builder(this)
                .itemStack(inputItem).build();
        this.setInputItem(inputButton);
    }

    /**
     * Sets the result-button into the GUI
     */
    private void setResultButton() {
        TextComponent title = this.titleConverter.apply(this.currentText);
        ItemStack resultItem = new ItemBuilder(title, RESULT_MATERIAL).build();
        GUIButton resultButton = new GUIButton.Builder(this)
                .itemStack(resultItem)
                .onClick(cd -> this.finish(false)).build();
        this.setResultItem(resultButton);
    }

    /**
     * Is called by the {@link TextInputPacketListener} to update the text
     * @param text The new text
     */
    public void updateText(String text) {
        if (this.onUpdate != null) {
            this.onUpdate.accept(text);
        }
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
        super.onClose(player);
    }

    /**
     * Is called to finish text-input
     * @param abort Whether the player aborted, e.g. by closing the Inventory
     */
    private void finish(boolean abort) {
        if (!this.closed) {
            this.closed = true;
            if ((this.onFinish == null || (this.onFinish.accept(this.currentText, abort)) &&
                this.onFinish(this.currentText, abort)) || abort) {
                this.packetListener.unregister();
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

    /**
     * Abstract Builder for creating {@link TextInputGUI}
     * @param <B> The Builder that implements this one
     * @param <G> The GUI that is built
     * @param <P> The plugin that uses this builder
     */
    @Getter(AccessLevel.PROTECTED)
    @SuppressWarnings("unused")
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, G, P>, G extends TextInputGUI, P extends JavaPlugin> extends BaseAnvilGUI.AbstractBuilder<B, G, P> {
        private final Player player;
        private String text = "";
        private FinishConsumer onFinish = (t, a) -> false;
        private Consumer<String> onUpdate = (t) -> {};
        private Function<String, TextComponent> titleConverter = Component::text;

        /**
         * Creates a new Builder
         * @param plugin The plugin that uses this builder
         * @param player The player this GUI belongs to
         */
        public AbstractBuilder(P plugin, Player player) {
            super(plugin);
            this.player = player;
        }

        /**
         * Sets the text attribute
         * @param text The text that is shown in the anvil at the beginning
         * @return Itself
         */
        public B text(String text) {
            this.text = text;
            return this.self();
        }

        /**
         * Sets the onFinish attribute
         * @param onFinish Is executed if the text is submitted
         * @return Itself
         */
        public B onFinish(FinishConsumer onFinish) {
            this.onFinish = onFinish;
            return this.self();
        }

        /**
         * Sets the onUpdate attribute
         * @param onUpdate Is executed if the text is updated
         * @return Itself
         */
        public B onUpdate(Consumer<String> onUpdate) {
            this.onUpdate = onUpdate;
            return this.self();
        }

        /**
         * Sets the titleConverter attribute
         * @param titleConverter Is called to convert the current text to the title of the submit item
         * @return Itself
         */
        public B titleConverter(Function<String, TextComponent> titleConverter) {
            this.titleConverter = titleConverter;
            return this.self();
        }
    }

    /**
     * Builder for creating {@link TextInputGUI}
     */
    @SuppressWarnings("unused")
    public static class Builder<P extends JavaPlugin> extends AbstractBuilder<Builder<P>, TextInputGUI, P> {
        /**
         * Creates a new Builder
         *
         * @param plugin The plugin that uses this builder
         * @param player The player this GUI belongs to
         */
        public Builder(P plugin, Player player) {
            super(plugin, player);
        }

        @Override
        public TextInputGUI build() {
            return new TextInputGUI(this.getPlugin(), this.getTitle(), this.isRemoveItems(), this.getPlayer(),
                    this.getText(), this.getOnFinish(), this.getOnUpdate(), this.getTitleConverter());
        }

        @Override
        protected Builder<P> self() {
            return this;
        }
    }
}
