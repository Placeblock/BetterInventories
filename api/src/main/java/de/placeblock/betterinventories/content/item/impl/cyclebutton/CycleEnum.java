package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;

import java.util.List;

public interface CycleEnum {

    Material getMaterial();

    TextComponent getTitle();

    List<TextComponent> getLore();

}
