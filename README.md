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
- Panes that accept items and provide items (IOPanes). You can just take them out of the inventory!!!
- Listen to button clicks
- Customizable click sound
- Switch Inventory and update GUI size without centering the player's cursor
- Text input using Anvil
- And many more...

## Documentation
Examples can be found in the example module.

[Here](https://www.javadoc.io/doc/de.codelix/BetterInventories) is the JavaDoc

## Versioning
Semantic versioning is used since version 1.3.7

## Installation
You can download and use the sourcecode at any time.<br>
For the build-tool users out there:
#### Repository
Gradle Kotlin
```kotlin
mavenCentral()
```
```kotlin
implementation("de.codelix:BetterInventories:VERSION") // Dependency
```
Gradle Groovy
```groovy
mavenCentral()
```
```groovy
implementation "de.codelix:BetterInventories:VERSION" // Dependency
```
Maven
```xml
<dependency>
    <groupId>de.codelix</groupId>
    <artifactId>BetterInventories</artifactId>
    <version>VERSION</version>
</dependency>
