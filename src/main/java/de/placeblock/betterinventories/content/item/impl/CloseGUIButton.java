package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import io.schark.design.texts.Texts;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class CloseGUIButton extends GUIButton {
    public CloseGUIButton(GUI gui) {
        super(gui, Util.setHeadTexture(
                new ItemBuilder(Texts.negative("Schließen"), Material.PLAYER_HEAD).build(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQwYTE0MjA4NDRjZTIzN2E0NWQyZTdlNTQ0ZDEzNTg0MWU5ZjgyZDA5ZTIwMzI2N2NmODg5NmM4NTE1ZTM2MCJ9fX0="));
    }

    @Override
    public void onClick(Player player) {
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }
}
