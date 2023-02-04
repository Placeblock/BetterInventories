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

#### In detail

If you want to create a new GUI you always have to ask you one question. What is the GUI going to contain?
Based on the answer you can choose from one of the predefined GUIs or create your own.
Some examples for predefined GUIs:
- CanvasGUI: A basic GUI which contains one canvas you can draw on
- ChestGUI: A CanvasGUI which has support for Auto-Resize
- FramedGUI: A ChestGUI which automatically adds borders to either the top and bottom or left and right side of the inventory
- CraftingTableGUI: A GUI which contains a GUIPane for the 3x3 Crafting Grid and one GUIItem for the result
- ...

GUIs can be shown to players. If you do so, a GUIView will be created containing the player and the BukkitInventory.
If you update a GUI the content will be rendered and inserted into the BukkitInventories of the Views.

## How to create a GUI

There are always two ways of creating GUIs and GUISections. You can create them by using builders or by
extending them which allows you to add extra functionality.
Creating a GUI is easy. Just instantiate a GUI class.

> Don't forget to call gui.update() after adding items to a gui!

## ItemBuilder

If you want to create ItemStacks for a GUIItem you can use the ItemBuilder which also has support for
head texture!
Just instantiate a ```new ItemBuilder()``` with a title and a material and there you go!

The following operations are supported by ItemBuilder:
- Title and Material
- Lore
- Enchantments
- Item Attributes
- Item Flags
- Unbreakable
- Skin Texture

## Implemented GUISections

There are some GUISections already implemented to make life easier.

### PaginatorGUIPane
A GUIPane which creates pages for several Items you add to the Paginator.

By default, it automatically creates controls to switch the page, but if you want to create your own ones,
you can disable the default controls and add your own PaginatorControlsPane

For the position of the arrows the following options are available in the PaginatorControlsPosition enum:
- CENTER: Both buttons are in the center of the controls GUIPane
- LEFT: Both buttons are on the left side of the controls GUIPane
- RIGHT: Both buttons are on the right side of the controls GUIPane
- SPACE_BETWEEN: One button is on the right side and the other one is on the left side of the controls GUIPane
- SPACE_EVENLY: The space between the buttons and the border is even

### FramedGUIPane
FramedGUIPane creates borders around your content. It provides a frame GUIPane where you can draw on.
You shouldn't draw directly on the canvas as this will potentially override the border.

Another Feature of the FramedGUIPane is the option to pass a so called "BackGUI". If you do so, a Back-Button
will be inserted into a border which opens the passed GUI.

### SwitchGUIButton
Automatically opens the passed GUI onClick

### TeleportGUIButton
Automatically teleports a player onClick

### CommandGUIButton
Automatically executes a command for a player on click

### BackGUIButton
Basically a SwitchGUIButton but with a specific ItemStack
