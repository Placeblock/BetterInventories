package de.placeblock.betterinventories;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import de.placeblock.betterinventories.builder.content.GUIItemBuilder;
import de.placeblock.betterinventories.builder.content.PaginatorBuilder;
import de.placeblock.betterinventories.builder.gui.ChestGUIBuilder;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPosition;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class PaginatorGUIPaneTest {
    private MockPlugin plugin;

    @BeforeEach
    public void setUp() {
        MockBukkit.getOrCreateMock();
        this.plugin = MockBukkit.createMockPlugin();
    }

    @Test
    public void testPaginatorWithoutControls() {
        ChestGUI chestGUI = new ChestGUIBuilder(this.plugin)
                .minHeight(1).maxHeight(6)
                .title(Component.empty())
                .build();
        PaginatorGUIPane paginator = new PaginatorBuilder(chestGUI)
                .adoptMinMax(chestGUI.getCanvas())
                .defaultControls(PaginatorControlsPosition.SPACE_EVENLY)
                .addItem(new GUIItemBuilder(chestGUI)
                    .item(new ItemStack(Material.ANVIL))
                    .build())
                .build();
        chestGUI.getCanvas().setSection(paginator);
        chestGUI.update();
        System.out.println(chestGUI.renderContent());
    }

    @Test
    public void testPaginatorWithControls() {
        ChestGUI gui = new ChestGUIBuilder(this.plugin)
                .minHeight(1).maxHeight(6)
                .title(Component.empty())
                .build();
        PaginatorGUIPane paginator = new PaginatorBuilder(gui)
                .adoptMinMax(gui.getCanvas())
                .defaultControls(PaginatorControlsPosition.SPACE_EVENLY)
                .build();
        paginator.addItems(Collections.nCopies(20, new GUIItem(gui, new ItemStack(Material.DIAMOND_BLOCK))));
        gui.getCanvas().setSection(paginator);
        gui.update();
        System.out.println(gui.renderContent());
    }

}
