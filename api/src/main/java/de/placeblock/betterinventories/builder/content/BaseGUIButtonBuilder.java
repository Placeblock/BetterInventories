package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;

import java.util.function.Consumer;


@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseGUIButtonBuilder<G extends GUIButton, B extends BaseGUIButtonBuilder<G, B>> extends BaseGUIItemBuilder<G, B> {
    private String permission = null;
    private Sound clickSound = null;
    private int cooldown = 0;
    private Consumer<ClickData> onClick;
    private Consumer<ClickData> onRightClick;
    private Consumer<ClickData> onLeftClick;
    private Consumer<ClickData> onShiftClick;
    private Consumer<ClickData> onShiftRightClick;
    private Consumer<ClickData> onShiftLeftClick;

    public BaseGUIButtonBuilder(GUI gui) {
        super(gui);
    }

    public B onClick(Consumer<ClickData> onClick) {
        this.onClick = onClick;
        return (B) this;
    }

    public B onLeftClick(Consumer<ClickData> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return (B) this;
    }

    public B onRightClick(Consumer<ClickData> onRightClick) {
        this.onRightClick = onRightClick;
        return (B) this;
    }


    public B onShiftClick(Consumer<ClickData> onShiftClick) {
        this.onShiftClick = onShiftClick;
        return (B) this;
    }

    public B onShiftLeftClick(Consumer<ClickData> onShiftLeftClick) {
        this.onShiftLeftClick = onShiftLeftClick;
        return (B) this;
    }

    public B onShiftRightClick(Consumer<ClickData> onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return (B) this;
    }

    public B permission(String permission) {
        this.permission = permission;
        return (B) this;
    }

    public B clickSound(Sound sound) {
        this.clickSound = sound;
        return (B) this;
    }

    public B cooldown(int cooldown) {
        this.cooldown = cooldown;
        return (B) this;
    }

    protected Consumer<ClickData> getOnClick() {
        return this.onClick;
    }

    protected Consumer<ClickData> getOnLeftClick() {
        return this.onLeftClick;
    }

    protected Consumer<ClickData> getOnRightClick() {
        return this.onRightClick;
    }

    protected Consumer<ClickData> getOnShiftClick() {
        return this.onShiftClick;
    }

    protected Consumer<ClickData> getOnShiftLeftClick() {
        return this.onShiftLeftClick;
    }

    protected Consumer<ClickData> getOnShiftRightClick() {
        return this.onShiftRightClick;
    }

    protected int getCooldown() {return this.cooldown;}
    protected Sound getClickSound() {return this.clickSound;}
    protected String getPermission() {return this.permission;}
}
