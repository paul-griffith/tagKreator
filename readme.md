# TagKreator

A Kotlin data model + DSL for Inductive Automation's [Ignition](https://inductiveautomation.com/ignition/) -
specifically, as the name implies, a tool to programmatically create tags.

## What?

Ignition tags are stored (as of version 8.0.0) as JSON content. This project is a complete recreation of that JSON
structure, using from-scratch Kotlin classes. This, in combination with some of Kotlin's features, allows a (mostly)
type-safe **D**omain **S**pecific **L**anguage to create Ignition tags, in a way that's not totally unfamiliar to users
familiar with programming and/or Ignition's tag format.

## Usage

For now, as an entirely half-baked project, the only way to use this library is by cloning the project, opening it in an
IDE (IntelliJ is strongly recommended), and modifying a source
file. [`Main.kt`](src/main/kotlin/io/github/paulgriffith/tagkreator/Main.kt) is the recommended starting point, and also
serves as a contextual example. Once changed, you can either directly run the `main` method using IntelliJ's helpful
builtin, or at the command line via `./gradlew run`. 

## Disclaimer

This is a personal project - not an official product of Inductive Automation. As the license mentions, this is provided
with _zero_ guarantees about functionality, support, future maintenance, etc. Use at your own risk. 