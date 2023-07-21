package de.placeblock.betterinventories.template;

import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class GUITemplate<G extends GUI> {

    protected final G gui;

}
