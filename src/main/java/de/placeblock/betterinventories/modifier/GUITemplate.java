package de.placeblock.betterinventories.modifier;

import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class GUITemplate {

    private final GUI gui;

    abstract void setup();

}
