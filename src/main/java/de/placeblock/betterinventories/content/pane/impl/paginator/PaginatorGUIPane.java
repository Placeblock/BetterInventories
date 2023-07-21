package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.*;


@Getter
@SuppressWarnings("unused")
public class PaginatorGUIPane extends GUIPane {
    private final List<GUIItem> items = new ArrayList<>();
    private final Set<PaginatorControlsPane> controls = new HashSet<>();
    private final PaginatorControlsPane defaultControl;
    private final PaginatorControlsPosition defaultControlsPosition;
    private final SimpleGUIPane contentPane;
    private int currentPage;
    @Setter
    private boolean repeat;
    private boolean showDefaultControls;

    public PaginatorGUIPane(GUI gui, Vector2d size, boolean repeat) {
        this(gui, size, size.getX(), size.getY(), false, repeat, 0, true, PaginatorControlsPosition.RIGHT);
    }

    public PaginatorGUIPane(GUI gui, Vector2d size, int maxWidth, int maxHeight, boolean autoSize, boolean repeat, int currentPage, boolean defaultControls, PaginatorControlsPosition defaultControlsPosition) {
        super(gui, size);
        if (this.getWidth() < 2) {
            throw new IllegalArgumentException("The width of a PaginatorGUIPane has a minimum width of 2");
        }
        this.defaultControlsPosition = defaultControlsPosition;
        this.contentPane = new SimpleGUIPane(gui, this.size);
        this.currentPage = currentPage;
        this.repeat = repeat;
        if (defaultControls) {
            this.defaultControl = new PaginatorControlsPane(gui, this, new Vector2d(this.getWidth(), 1), this.defaultControlsPosition);
            this.controls.add(this.defaultControl);
        } else {
            this.defaultControl = null;
        }
        this.updateSize(maxWidth, maxHeight);
        this.update();
    }

    public void addItem(GUIItem item) {
        this.items.add(item);
        this.update();
    }


    private void updateSize() {
        this.updateSize(this.getWidth(), this.getHeight());
    }

    private void updateSize(int maxWidth, int maxHeight) {
        int newWidth = Math.min(maxWidth, this.items.size());
        int newHeight = (int) Math.min(Math.ceil(this.items.size() * 1F / newWidth), maxHeight);
        this.setSize(new Vector2d(newWidth, newHeight));

        this.contentPane.setWidth(this.getWidth());
        this.contentPane.setHeight(this.isDefaultControlEnabled() ? this.getHeight() - 1 : this.getHeight());
        for (PaginatorControlsPane control : this.controls) {
            control.setWidth(this.getWidth());
        }
        this.currentPage = Math.min(this.currentPage, this.getPages());
        this.initControls();
        this.update();
    }

    public void clearItems() {
        this.items.clear();
        this.update();
    }

    public void addControl(PaginatorControlsPane control) {
        this.controls.add(control);
    }

    public void nextPage() {
        this.setCurrentPage((this.currentPage + 1) % this.getPages());
    }

    public void previousPage() {
        this.setCurrentPage(Util.modulo(this.currentPage - 1, this.getPages()));
    }

    public void setCurrentPage(int index) {
        this.currentPage = index;
        this.update();
    }

    public int getPages() {
        return (int) Math.ceil(this.items.size()*1F/this.contentPane.getSlots());
    }

    private void update() {
        this.showDefaultControls = this.items.size() > this.getSlots();
        this.updateControls();
        this.contentPane.clear();
        int startIndex = this.contentPane.getSlots()*this.currentPage;
        for (int i = 0; i < this.contentPane.getSlots() && i < this.items.size() - startIndex; i++) {
            this.contentPane.setSectionAt(i, this.items.get(i+startIndex));
        }
    }

    private boolean isDefaultControlEnabled() {
        return this.showDefaultControls && this.defaultControl != null;
    }

    private void updateControls() {
        for (PaginatorControlsPane control : this.controls) {
            control.updateButtons();
        }
    }

    private void initControls() {
        for (PaginatorControlsPane control : this.controls) {
            control.init();
        }
    }

    @Override
    public List<ItemStack> render() {
        List<ItemStack> rendered = this.getEmptyContentList(ItemStack.class);
        rendered = this.renderOnList(new Vector2d(), this.contentPane, rendered);
        if (this.isDefaultControlEnabled()) {
            rendered = this.renderOnList(new Vector2d(0, this.getHeight()-1), this.defaultControl, rendered);
        }
        return rendered;
    }

    @Override
    public GUISection getSectionAt(Vector2d position) {
        if (position.getY() >= this.getHeight()-1 && this.isDefaultControlEnabled()) {
            return this.defaultControl.getSectionAt(position.subtract(new Vector2d(0, this.getHeight()-1)));
        } else {
            return this.contentPane.getSectionAt(position);
        }
    }
}
