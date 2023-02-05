package de.placeblock.betterinventories.util;

import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;


@Getter
public enum ArrowDirection {

    UP("http://textures.minecraft.net/texture/6ccbf9883dd359fdf2385c90a459d737765382ec4117b04895ac4dc4b60fc", "↑"),
    DOWN("http://textures.minecraft.net/texture/72431911f4178b4d2b413aa7f5c78ae4447fe9246943c31df31163c0e043e0d6", "↓"),
    LEFT("http://textures.minecraft.net/texture/37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645", "←"),
    RIGHT("http://textures.minecraft.net/texture/682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e", "→");

    private final URL texture;
    private final String name;

    ArrowDirection(String textureURL, String name) {
        try {
            this.texture = new URL(textureURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.name = name;
    }

}
