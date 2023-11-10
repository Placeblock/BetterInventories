package de.placeblock.betterinventories;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
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
import java.util.List;

public class PaginatorGUIPaneTest {
    private MockPlugin plugin;

    @BeforeEach
    public void setup() {
        MockBukkit.getOrCreateMock();
        this.plugin = MockBukkit.createMockPlugin();
    }

    @Test
    public void withoutControlsTest() {
        ChestGUI chestGUI = new ChestGUI.Builder<>(this.plugin)
                .minHeight(1).maxHeight(6)
                .title(Component.empty())
                .build();
        PaginatorGUIPane paginator = new PaginatorGUIPane.Builder(chestGUI)
                .adoptMinMax(chestGUI.getCanvas())
                .controls(PaginatorControlsPosition.SPACE_EVENLY)
                .addItem(new GUIItem.Builder(chestGUI)
                    .itemStack(new ItemStack(Material.ANVIL))
                    .build())
                .build();
        chestGUI.getCanvas().setSection(paginator);
        chestGUI.update();
        List<ItemStack> content = chestGUI.renderContent();
        assert content.size() == 9;
        assert content.get(0) != null;
        assert content.get(1) == null;
    }

    @Test
    public void withControlsTest() {
        ChestGUI gui = new ChestGUI.Builder<>(this.plugin)
                .minHeight(1).maxHeight(6)
                .title(Component.empty())
                .build();
        PaginatorGUIPane paginator = new PaginatorGUIPane.Builder(gui)
                .adoptMinMax(gui.getCanvas())
                .controls(PaginatorControlsPosition.SPACE_EVENLY)
                .build();
        paginator.addItems(Collections.nCopies(20, new GUIItem.Builder(gui).itemStack(new ItemStack(Material.DIAMOND_BLOCK)).build()));
        gui.getCanvas().setSection(paginator);
        gui.update();
        List<ItemStack> content = gui.renderContent();
        assert content.size() == 27;
        assert content.get(19) != null;
        assert content.get(20) == null;
    }

    @Test
    public void resizeTest() {
        int maxHeight = 6;
        ChestGUI gui = new ChestGUI.Builder<>(this.plugin)
                .minHeight(1).maxHeight(maxHeight)
                .title(Component.empty())
                .build();
        PaginatorGUIPane paginator = new PaginatorGUIPane.Builder(gui)
                .adoptMinMax(gui.getCanvas())
                .controls(PaginatorControlsPosition.SPACE_EVENLY)
                .build();
        gui.getCanvas().setSection(paginator);
        for (int i = 1; i < 90; i++) {
            paginator.addItem(new GUIItem.Builder(gui).itemStack(new ItemStack(Material.DIAMOND)).build());
            gui.update();
            List<ItemStack> content = gui.renderContent();
            float contentHeight = content.size() / 9F;
            assert contentHeight == maxHeight || contentHeight == Math.ceil(i / 9F);
        }
    }

    @Test
    public void controlsTest() {
        ChestGUI gui = new ChestGUI.Builder<>(this.plugin)
                .height(3)
                .title(Component.empty())
                .build();
        PaginatorGUIPane paginator = new PaginatorGUIPane.Builder(gui)
                .adoptMinMax(gui.getCanvas())
                .controls(PaginatorControlsPosition.SPACE_EVENLY)
                .build();
        paginator.addItems(Collections.nCopies(40, new GUIItem.Builder(gui).itemStack(new ItemStack(Material.DIAMOND_BLOCK)).build()));
        gui.getCanvas().setSection(paginator);
        gui.update();
        assert paginator.getPages() == 2;
        assert paginator.getCurrentPage() == 0;
        paginator.nextPage();
        assert paginator.getCurrentPage() == 1;
        paginator.nextPage();
        assert paginator.getCurrentPage() == 0;
        paginator.previousPage();
        assert paginator.getCurrentPage() == 1;
        paginator.previousPage();
        assert paginator.getCurrentPage() == 0;
    }

}
