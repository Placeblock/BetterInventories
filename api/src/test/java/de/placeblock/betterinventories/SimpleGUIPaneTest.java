package de.placeblock.betterinventories;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import de.placeblock.betterinventories.builder.gui.ChestGUIBuilder;
import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.content.item.BaseGUIButton;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleGUIPaneTest {
    private MockPlugin plugin;

    @BeforeEach
    public void setup() {
        MockBukkit.getOrCreateMock();
        this.plugin = MockBukkit.createMockPlugin();
    }

    @Test
    public void zIndexTest() {
        ChestGUI chestGUI = new ChestGUIBuilder(this.plugin)
                .height(3)
                .title(Component.empty())
                .build();
        SimpleGUIPane pane = new SimpleGUIPane.Builder(chestGUI)
                .adoptMinMax(chestGUI.getCanvas())
                .build();
        chestGUI.getCanvas().setSection(pane);
        SimpleGUIPane fillPane = new SimpleGUIPane.Builder(chestGUI)
                .adoptMinMax(chestGUI.getCanvas())
                .build();
        fillPane.fill(new GUIItem.Builder(chestGUI).itemStack(new ItemStack(Material.CHEST)).build());
        pane.setSection(fillPane);
        BaseGUIButton button = new GUIButton.Builder(chestGUI)
                .itemStack(new ItemStack(Material.DIAMOND))
                .build();
        pane.setSectionAt(2, button);
        chestGUI.update();
        SearchData searchData = new SearchData(2, (s) -> true);
        chestGUI.searchSection(searchData);
        assert searchData.getSection().equals(button);
    }

}
