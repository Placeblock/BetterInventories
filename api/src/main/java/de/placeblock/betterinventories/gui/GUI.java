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
    private final Plugin plugin;
    private final TextComponent title;
    private final List<GUIView> views = new ArrayList<>();
    private final InventoryType type;
    private final SortedSetMultimap<HandlerPriority, InteractionHandler> interactionHandlers = TreeMultimap.create(Comparator.comparingInt(HandlerPriority::ordinal), Comparator.comparing(Object::hashCode));
    private List<ItemStack> content = new ArrayList<>();

    public GUI(Plugin plugin, TextComponent title, InventoryType type) {
        this(plugin, title, type, true);
    }

    public GUI(Plugin plugin, TextComponent title, InventoryType type, boolean registerDefaultHandlers) {
        this.plugin = plugin;
        this.type = type;
        this.title = title;
        if (registerDefaultHandlers) {
            this.registerInteractionHandler(HandlerPriority.HIGH, new CancelInteractionHandler(this));
            this.registerInteractionHandler(HandlerPriority.LOW, new ButtonClickHandler(this));
        }
    }

    public abstract Inventory createBukkitInventory();


    //  VIEW AND PLAYER MANAGEMENT

    /**
     * Shows the GUI to a player.
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

    public List<Player> getPlayers() {
        return this.views.stream().map(GUIView::getPlayer).toList();
    }

    public GUIView getView(Inventory inventory) {
        for (GUIView view : this.views) {
            if (view.getInventory().equals(inventory)) {
                return view;
            }
        }
        return null;
    }

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

    public abstract int getSlots();
    protected abstract List<ItemStack> renderContent();
    public abstract GUISection getClickedSection(int slot);


    //  UPDATE AND RENDERING

    public void update() {
        this.render();
        this.updateViews();
    }

    protected void render() {
        this.content = this.renderContent();
    }

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view == null) return;

        this.handleInteraction(i -> i.onClick(event));
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view == null) return;

        this.handleInteraction(i -> i.onDrag(event));
    }

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

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view != null) {
            this.removePlayer(view);
        }
    }

    private void removePlayer(GUIView view) {
        this.views.remove(view);
        this.onClose(view.getPlayer());
        if (this.views.size() == 0) {
            HandlerList.unregisterAll(this);
        }
    }

    public void onClose(Player player) {}

}
