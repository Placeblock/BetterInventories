package de.placeblock.betterinventories.content.pane.impl.async;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Inventory that shows a loading item while a completable future is running
 * @param <T> The type of the completable future
 */
@SuppressWarnings("unused")
public class AsyncGUIPane<T> extends BaseAsyncGUIPane<T, AsyncGUIPane<T>>{
    /**
     * Creates a new BaseSimpleGUIPane
     *
     * @param gui            The GUI
     * @param minSize        The minimum size of the Pane
     * @param maxSize        The maximum size of the Pane
     * @param autoSize       Whether to automatically resize the pane according to the children.
     *                       If true it will set the size to the bounding box of all children.
     * @param task           The running task
     * @param onComplete     The callback that is executed when the task finishes
     * @param loadingGUIItem The item that is shown while the task is running
     */
    protected AsyncGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize, CompletableFuture<T> task, @Nullable Consumer<T> onComplete, GUIItem loadingGUIItem) {
        super(gui, minSize, maxSize, autoSize, task, onComplete, loadingGUIItem);
    }

    /**
     * Builder for creating {@link AsyncGUIPane}
     * @param <T> The result of the task
     */
    public static class Builder<T> extends BaseAsyncGUIPane.AbstractBuilder<Builder<T>, AsyncGUIPane<T>, T> {
        /**
         * Creates a new Builder
         * @param gui The gui this Pane belongs to
         * @param task The running task
         */
        protected Builder(GUI gui, CompletableFuture<T> task) {
            super(gui, task);
        }

        @Override
        public AsyncGUIPane<T> build() {
            return new AsyncGUIPane<>(this.getGui(), this.getMinSize(), this.getMaxSize(), this.isAutoSize(),
                    this.getTask(), this.getOnComplete(), this.getLoadingGUIItem());
        }

        @Override
        protected Builder<T> self() {
            return this;
        }
    }
}
