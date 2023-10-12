package de.placeblock.betterinventories.gui;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.interaction.HandlerPriority;
import de.placeblock.betterinventories.interaction.InteractionHandler;
import de.placeblock.betterinventories.interaction.impl.ButtonClickHandler;
import de.placeblock.betterinventories.interaction.impl.CancelInteractionHandler;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.function.Function;


/**
 * Root class for GUIs
 * Can be used to create any sort of GUI that can be rendered to a list
 * e.g. Chest, Hopper, Furnace, Anvil, Brewing.
 * However, for most of these examples there exist better methods for creating a GUI already.
 */
@Getter
@SuppressWarnings("unused")
public abstract class GUI implements Listener {
    /**
     * The plugin
     */
    private final Plugin plugin;

    /**
     * The title of the GUI
     */
    private final TextComponent title;

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
     * The registered InteractionHandlers
     */
    private final SortedSetMultimap<HandlerPriority, InteractionHandler> interactionHandlers = TreeMultimap.create(Comparator.comparingInt(HandlerPriority::ordinal), Comparator.comparing(Object::hashCode));

    /**
     * Creates a new GUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     */
    public GUI(Plugin plugin, TextComponent title, InventoryType type) {
        this(plugin, title, type, true);
    }

    /**
     * Creates a new GUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     * @param registerDefaultHandlers Whether to register default-handlers
     */
    public GUI(Plugin plugin, TextComponent title, InventoryType type, boolean registerDefaultHandlers) {
        this.plugin = plugin;
        this.type = type;
        this.title = title;
        if (registerDefaultHandlers) {
            this.registerInteractionHandler(HandlerPriority.HIGH, new CancelInteractionHandler(this));
            this.registerInteractionHandler(HandlerPriority.LOW, new ButtonClickHandler(this));
        }
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
            this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
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
     * Reloads all Views (Removes all Players and adds all Players).
     * Needed when resizing the GUI or changing the GUI's title
     */
    public void reloadViews() {
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
     * @return The amount of slots this GUI has
     */
    public abstract int getSlots();

    /**
     * Renders the GUI on a list
     * @return The List
     */
    public abstract List<ItemStack> renderContent();

    /**
     * Returns the GUISection at a specific slot.
     * @param slot The slot
     * @return The GUISection at the slot or null
     */
    public abstract GUISection getClickedSection(int slot);


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
    protected void render() {
        this.content = this.renderContent();
    }

    /**
     * Updates the content of all Views
     */
    protected void updateViews() {
        for (GUIView view : this.views) {
            view.update(this.content);
        }
    }

    //  INTERACTION HANDLING

    /**
     * Registers a new InteractionHandler.
     * InteractionHandlers will receive Inventory Click- and DragEvents
     * @param priority The priority for the new Handler.
     * @param handler The handler
     */
    public void registerInteractionHandler(HandlerPriority priority, InteractionHandler handler) {
        this.interactionHandlers.put(priority, handler);
    }

    /**
     * Unregisters a new InteractionHandler
     * @param priority The priority of the Handler.
     * @param handler The handler
     */
    public void unregisterInteractionHandler(HandlerPriority priority, InteractionHandler handler) {
        this.interactionHandlers.remove(priority, handler);
    }

    /**
     * Called by Bukkit when Player clicks an Inventory
     * Calls the InteractionHandlers
     * @param event The Event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view == null) return;

        this.handleInteraction(i -> i.onClick(event));
    }

    /**
     * Called by Bukkit when Player drags an Inventory
     * Calls the InteractionHandlers
     * @param event The Event
     */
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view == null) return;

        this.handleInteraction(i -> i.onDrag(event));
    }

    /**
     * Calls the InteractionHandlers
     * @param handler Handler callback. Handler calling breaks if Handler callback returns true
     */
    private void handleInteraction(Function<InteractionHandler, Boolean> handler) {
        outer:
        for (HandlerPriority priority : this.interactionHandlers.keySet()) {
            SortedSet<InteractionHandler> pInteractionHandlers = this.interactionHandlers.get(priority);
            for (InteractionHandler interactionHandler : pInteractionHandlers) {
                boolean processed = handler.apply(interactionHandler);
                if (processed) break outer;
            }
        }
    }


    //  HANDLE GUI REMOVAL

    /**
     * Called by Bukkit when a Player closes an Inventory
     * @param event The Event
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view != null) {
            this.removePlayer(view);
        }
    }

    /**
     * Removes a player without closing the Inventory of the Player
     * @param view The View of the Player
     */
    private void removePlayer(GUIView view) {
        this.views.remove(view);
        this.onClose(view.getPlayer());
        if (this.views.size() == 0) {
            HandlerList.unregisterAll(this);
        }
    }

    /**
     * Is called when the player closes the GUI.
     * @param player The player, who closed the GUI
     */
    public void onClose(Player player) {}

}
