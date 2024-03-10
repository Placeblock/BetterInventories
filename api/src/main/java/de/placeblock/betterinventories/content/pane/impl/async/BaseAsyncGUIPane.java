package de.placeblock.betterinventories.content.pane.impl.async;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.impl.async.LoadingGUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.BaseSimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Base class for {@link AsyncGUIPane}
 * @param <T> Result of the async task
 * @param <S> The implementing class to return this-type correctly e.g. {@link #addItemEmptySlot(GUIItem)}
 */
public abstract class BaseAsyncGUIPane<T, S extends BaseAsyncGUIPane<T, S>> extends BaseSimpleGUIPane<GUISection, S> {

    /**
     * The item that is shown while the task is running
     */
    private final GUIItem loadingGUIItem;
    /**
     * The callback that is executed if the task finishes
     */
    private final Consumer<T> onComplete;

    /**
     * Creates a new BaseSimpleGUIPane
     *
     * @param gui      The GUI
     * @param minSize  The minimum size of the Pane
     * @param maxSize  The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     * @param task The running task
     * @param onComplete The callback that is executed when the task finishes
     * @param loadingGUIItem The item that is shown while the task is running
     */
    protected BaseAsyncGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize,
                               CompletableFuture<T> task, @Nullable Consumer<T> onComplete,
                               GUIItem loadingGUIItem) {
        super(gui, minSize, maxSize, autoSize);
        this.onComplete = onComplete;
        this.loadingGUIItem = loadingGUIItem;
        this.setSectionAt(4, this.loadingGUIItem);
        task.thenAccept(this::taskComplete);
    }

    /**
     * Called when the task completes
     * @param data The result of the task
     */
    private void taskComplete(T data) {
        this.removeSection(this.loadingGUIItem);
        this.onTaskComplete(data);
    }

    /**
     * Called when the task completes. Can be overridden to use it instead of the callback
     * @param data The result of the task
     */
    protected void onTaskComplete(T data) {
        if (this.onComplete != null) {
            this.onComplete.accept(data);
        }
    }

    /**
     * Builder for creating {@link BaseAsyncGUIPane}
     * @param <B> The Builder that implements this one
     * @param <P> The BaseAsyncSimpleGUIPane that is built
     * @param <T> The result of the task
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P, T>, P extends BaseAsyncGUIPane<T, P>, T> extends BaseSimpleGUIPane.AbstractBuilder<B, P, GUISection> {
        private GUIItem loadingGUIItem;
        private final CompletableFuture<T> task;
        private Consumer<T> onComplete;

        /**
         * Creates a new Builder
         * @param gui The gui this Pane belongs to
         * @param task The running task
         */
        protected AbstractBuilder(GUI gui, CompletableFuture<T> task) {
            super(gui);
            this.loadingGUIItem = new LoadingGUIItem.Builder(gui).build();
            this.task = task;
        }

        /**
         * Sets the onComplete parameter
         * @param onComplete Callback that is executed when the task completes
         * @return this
         */
        public B onComplete(Consumer<T> onComplete) {
            this.onComplete = onComplete;
            return this.self();
        }

        /**
         * Sets the loading item that is shown while the task is running
         * @param loadingGUIItem The item
         * @return this
         */
        public B loadingItem(GUIItem loadingGUIItem) {
            this.loadingGUIItem = loadingGUIItem;
            return this.self();
        }
    }
}
