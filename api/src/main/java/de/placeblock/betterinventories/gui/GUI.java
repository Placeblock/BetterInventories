package de.placeblock.betterinventories.gui;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.listener.GUIItemListener;
import de.placeblock.betterinventories.gui.listener.GUIListener;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


/**
 * Root class for GUIs
 * Can be used to create any sort of GUI that can be rendered to a list
 * e.g. Chest, Hopper, Furnace, Anvil, Brewing.
 * However, for most of these examples there exist better methods for creating a GUI already.
 */
@Getter
@SuppressWarnings("unused")
public abstract class GUI {
    /**
     * The plugin
     */
    private final Plugin plugin;

    /**
     * The title of the GUI
     */
    private TextComponent title;

    /**
     * The current Views of the GUI
     */
    private final List<GUIView> views = new ArrayList<>();

    /**
     * The type of the GUI
     */
    private final InventoryType type;

    /**
     * The current rendered content of the GUI
     */
    private List<ItemStack> content = new ArrayList<>();

    /**
     * Listener for gui management
     */
    private final GUIListener guiListener;
    /**
     * Listener for item management
     */
    private final GUIItemListener itemListener;

    /**
     * Whether to try to remove Items from the inventory on close.
     * The first player that closes the gui gets the items
     */
    private final boolean removeItems;

    /**
     * Creates a new GUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     * @param removeItems Whether to remove loose items on close.
     *                   The first player that closes the gui gets the items
     */
    public GUI(Plugin plugin, TextComponent title, InventoryType type, boolean removeItems) {
        this.plugin = plugin;
        this.type = type;
        this.title = title;
        this.guiListener = new GUIListener(this);
        this.itemListener = new GUIItemListener(this);
        this.removeItems = removeItems;
    }

    /**
     * Creates a new Bukkit Inventory for the GUI when implemented
     * @return The Bukkit Inventory
     */
    public abstract Inventory createBukkitInventory();


    //  VIEW AND PLAYER MANAGEMENT

    /**
     * Shows the GUI to a player.
     * @param player The Player
     * @return The newly created GUIView
     */
    @SuppressWarnings("UnusedReturnValue")
    public GUIView showPlayer(Player player) {
        if (this.views.size() == 0) {
            PluginManager pluginManager = this.plugin.getServer().getPluginManager();
            pluginManager.registerEvents(this.guiListener, this.plugin);
            pluginManager.registerEvents(this.itemListener, this.plugin);
        }
        if (this.getPlayers().contains(player)) return null;
        GUIView view = new GUIView(player, this.createBukkitInventory());
        view.update(this.content);
        this.views.add(view);
        return view;
    }

    /**
     * @return All players, which can see the GUI
     */
    public List<Player> getPlayers() {
        return this.views.stream().map(GUIView::getPlayer).toList();
    }

    /**
     * Returns the GUIView for the according Inventory
     * @param inventory The Inventory
     * @return The GUIView
     */
    public GUIView getView(Inventory inventory) {
        for (GUIView view : this.views) {
            if (view.getInventory().equals(inventory)) {
                return view;
            }
        }
        return null;
    }
    /**
     * Returns the GUIView of the player
     * @param player The Player
     * @return The GUIView
     */
    public GUIView getView(Player player) {
        for (GUIView view : this.views) {
            if (view.getPlayer().equals(player)) {
                return view;
            }
        }
        return null;
    }

    /**
     * @return The amount of slots this GUI has
     */
    public abstract int getSlots();

    /**
     * Renders the GUI on a list
     * @return The List
     */
    protected abstract List<ItemStack> renderContent();

    /**
     * Searches the GUISection recursively. The SearchData is filled recursively.
     * @param searchData The searchData that contains all needed information
     */
    public abstract void searchSection(SearchData searchData);

    /**
     * Used to provide items to GUIPanes. The amount of the ItemStack
     * will be modified if a pane accepted an ItemStack.
     * @param itemStack The ItemStack that is provided.
     */
    public abstract void provideItem(ItemStack itemStack);


    //  UPDATE AND RENDERING

    /**
     * Updates the GUI, renders the GUI and updates the Views
     */
    public void update() {
        this.render();
        this.updateViews();
    }

    /**
     * Renders the GUI
     */
    private void render() {
        this.content = this.renderContent();
    }

    /**
     * Updates the content of all Views
     */
    private void updateViews() {
        for (GUIView view : this.views) {
            view.update(this.content);
        }
    }

    /**
     * Reloads all Views (Removes all Players and adds all Players).
     * Needed when resizing the GUI or changing the GUI's title
     */
    protected void reloadViews() {
        List<Player> players = this.getPlayers();
        List<GUIView> views = new ArrayList<>(this.getViews());
        for (GUIView view : views) {
            this.removePlayer(view);
        }
        for (Player player : players) {
            this.showPlayer(player);
        }
    }

    /**
     * Updates the title of the inventory
     * @param title The new title
     */
    public void updateTitle(TextComponent title) {
        this.title = title;
        this.reloadViews();
    }


    //  HANDLE GUI REMOVAL

    /**
     * Removes a player without closing the Inventory of the Player
     * @param view The View of the Player
     */
    public void removePlayer(GUIView view) {
        Player player = view.getPlayer();
        if (this.removeItems) {
            for (int i = 0; i < this.getSlots(); i++) {
                SearchData searchData = new SearchData(i, (s) -> s instanceof GUIPane);
                this.searchSection(searchData);
                GUISection section = searchData.getSection();
                if (section == null) continue;
                Vector2d pos = searchData.getRelativePos();
                ItemStack removedItemStack = section.onItemRemove(pos);
                if (removedItemStack == null) continue;
                player.getInventory().addItem(removedItemStack);
            }
        }
        this.views.remove(view);
        this.onClose(player);
        if (this.views.size() == 0) {
            HandlerList.unregisterAll(this.guiListener);
            HandlerList.unregisterAll(this.itemListener);
        }
    }

    /**
     * Is called when the player closes the GUI.
     * @param player The player, who closed the GUI
     */
    public void onClose(Player player) {}

    /**
     * The generic Builder for GUIs
     * @param <B> The Builder that implements this one.
     * @param <G> The GUI that gets build
     * @param <P> The plugin that uses this builder.
     */
    @RequiredArgsConstructor
    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, G, P>, G extends GUI, P extends JavaPlugin> extends de.placeblock.betterinventories.Builder<B, G> {
        private final P plugin;
        private TextComponent title;
        private InventoryType type;
        private boolean removeItems = true;

        /**
         * Sets the title attribute
         * @param title The title of the GUI
         * @return Itself
         */
        public B title(TextComponent title) {
            this.title = title;
            return this.self();
        }

        /**
         * Sets the removeItems attribute
         * @param removeItems Whether to try to remove Items from the inventory on close.
         *                    The first player that closes the gui gets the items
         * @return Itself
         */
        public B removeItems(boolean removeItems) {
            this.removeItems = removeItems;
            return this.self();
        }

        /**
         * Sets the type attribute
         * @param type The type of the GUI
         * @return Itself
         */
        public B type(InventoryType type) {
            this.type = type;
            return this.self();
        }
    }
}
