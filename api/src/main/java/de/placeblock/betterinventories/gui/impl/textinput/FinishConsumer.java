package de.placeblock.betterinventories.gui.impl.textinput;

@FunctionalInterface
public interface FinishConsumer {

    boolean accept(String finalText, boolean abort);

}
