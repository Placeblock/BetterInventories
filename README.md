## What is BetterInventories?
Creating Inventories with Bukkit can be quite complicated, especially if they get more complex.
Better Inventories solves these problems by allowing you to create reusable components, which
you can place inside the GUI wherever you want. It is highly customizable, DRY, simple and extensive
if you need to implement more complex features.

Features:
- Supports different types of Inventories (Chest, Anvil)
- Custom titles
- Built-in Page system
- Clean management of different parts of the GUI (Components)
- Listen to button clicks
- Customizable click sound
- Switch Inventory and update GUI size without centering the player's cursor
- Text input using Anvil
- And many more...

## Examples
Examples can be found in the example module.

## Installation
You can download and use the sourcecode at any time.<br>
For the build-tool users out there:
#### Repository
Gradle Kotlin
```kotlin
mavenCentral()
```
```kotlin
implementation("de.codelix:betterinventories:VERSION") // Dependency
```
Gradle Groovy
```groovy
mavenCentral()
```
```groovy
implementation "de.codelix:betterinventories:VERSION" // Dependency
```
Maven
```xml
<dependency>
    <groupId>de.codelix</groupId>
    <artifactId>betterinventories</artifactId>
    <version>VERSION</version>
</dependency>
```

## Basic structure

BetterInventories consists of two major components.

### The GUI Class
A GUI is what people can see. They can interact with it and do stuff with it. Multiple players can see one GUI.
There are different types of GUI e.g. the CanvasGUI and the ChestGUI (more info in JavaDoc). For the most of the GUI 
implementations there are builder available which make GUI creation easier

### The GUISection class
The content of GUIs is created using GUISections. These are building blocks which can be 
used to create many different GUIs. For just Items and Buttons you can use GUIItem or 
GUIButton. GUIPanes are GUISections that can contain other GUISections. If you want to 
structure your inventory with a left and right half you can use two GUIPanes which you 
place inside your GUI. There are several implementations of the GUIPane which make 
your life easier. For the most of the GUISection implementations there are builder available 
which make creation simpler.

## Sizing
`For simple GUIs you dont have to worry about that :)`<br>
Some GUIPanes and GUIs resize themselves. The auto-sizing GUI is called the ChestGUI and 
sets it's size based on the content (if autoSize is enabled). The SimpleGUIPane can resize 
itself based on the contents too.
Before an GUI is rendered every GUISection gets recursively updated. It begins at the top 
of the "GUI-Tree" and calls the updateSizeRecursive(Vector2d) of the first GUIPane. 
Usually this method calls the same method for every child (recursive) and then calls 
updateSize(Vector2d). However, you can override these methods and implement it different, 
e.g. the PaginatorGUIPane first updates its size and then it's children.

Every GUIPane and the ChestGUI implement Sizeable which means they have a size, a 
min-size and a max-size. If you ever set the size of a GUIPane you should always use 
the setSize(Vector2d) method as it automatically limits the size to min and max. 
As the size of GUIPanes is responsive and not fix you cannot set the size of a GUIPane 
in the constructor. The size of a GUIPane is initially set to the minSize, but usually 
the size changes after the first updateSizeRecursive(Vector2d) call. If you still want 
to set your GUIPane to a fixed size you can just set max and min size to the same, 
builders will do that automatically if you use size(Vector2d) instead of minSize(Vector) 
and maxSize(Vector2d) .
This leads to a highly responsive GUI and GUISection that can automatically resize itself.