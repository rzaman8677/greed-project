# Greed Project

A Java implementation of the dice game **Greed**, with support for human players and computer strategies.

## Project Overview

This repository includes:
- A Greed game engine (`GreedGame`)
- Core scoring logic (`ScoringCombination`)
- Player abstractions for human and computer players
- Strategy hooks for AI-style play (`GreedStrategy`)
- A sample strategy implementation (`ZamanGreedStrategy`)

## Repository Structure

- `/src` – Java source files
- `/bin` – Compiled `.class` files

## Requirements

- Java (JDK 8+ recommended)

## Build

From the repository root:

```bash
javac -d bin src/*.java
```

## Run

### Play an interactive turn-based game
```bash
java -cp bin GreedGame
```

### Evaluate a strategy over many turns
```bash
java -cp bin StrategyEvaluator
```

## Notes

- The project is currently package-less (default Java package), so compile and run from the repository root.
- `.project` and `.classpath` are included for Eclipse-based workflows.