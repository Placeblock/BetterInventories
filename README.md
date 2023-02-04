# BetterInventories - API for Inventory Management

## Introduction

To design a rich Inventory System with high re-usability, clean code and extensibility 
can be hard. I've tried my best to cover all three aspects.

There are several questions that arise when thinking about these .
1. There should be a feature to encapsulate one part of an Inventory to reuse it
2. There should be support for several Inventory Types
3. Open new Inventories without a cursor moving to the center of the players screen.
4. Decentralized use of inventories without a manager
5. ...

There are some Key Concepts that try to solve these Problems:

### GUISection

A GUISection is a container which can hold other GUISections. It's based on <b>recursion</b>.
It is used to represent a group of GUISections or a single GUISection and has attributes like width and height.

For example the GUIItem class is a GUISection and holds one ItemStack. Of course its size is 1x1. 

In contrast, there is GUIPane, which is also a GUISection. It is used
to represent a group of GUISections.

### GUI

A GUI represents an Inventory that can be shown to other players. It contains a main GUISection which is called
the canvas. Everything you "draw" on it will be rendered and shown to the player.

### In detail

If you want to create a new GUI you always have to ask you one question. What is the GUI going to contain?
Based on the answer you can choose from one of the predefined GUIs or create your own.
Some examples for predefined GUIs:
- CanvasGUI: A basic GUI which contains one canvas you can draw on
- ChestGUI: A CanvasGUI which has support for Auto-Resize
- FramedGUI: A ChestGUI which automatically adds borders to either the top and bottom or left and right side of the inventory
- CraftingTableGUI: A GUI which contains a GUIPane for the 3x3 Crafting Grid and one GUIItem for the result
- ...

## How to create a GUI



There are always two ways of creating GUIs and GUISections. You can create them by using builders or by
extending them which allows you to add extra functionality.