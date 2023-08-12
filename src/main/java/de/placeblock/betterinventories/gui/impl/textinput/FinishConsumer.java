package de.placeblock.betterinventories.gui.impl.textinput;

@FunctionalInterface
public interface FinishConsumer {

    void accept(String finalText, boolean abort);

}
