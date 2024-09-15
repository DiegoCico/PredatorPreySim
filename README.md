# Animal Simulation Project

Welcome to the Animal Simulation Project! This project simulates the interactions between prey and predators in a dynamic environment, complete with a graphical representation of their population changes over time.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Class Descriptions](#class-descriptions)
  - [Animal](#animal)
  - [Predator](#predator)
  - [Prey](#prey)
  - [GraphFrame](#graphframe)
  - [SimulationGUI](#simulationgui)
- [License](#license)

## Features

- Simulates prey and predator interactions with age, breeding, and movement behaviors.
- Visual representation of the simulation using Java Swing.
- Dynamic graph displaying population changes of prey and predators over time.

## Installation

Clone the repository:

```bash
git clone https://github.com/diegocico/predadorpreysim.git
cd animal-simulation
```

Compile the Java files:

```bash
javac *.java
```

Run the simulation:

```bash
java Main
```

## Usage

After running the simulation, a window will open displaying the simulation area and the graph. Prey are represented by green circles, and predators are represented by red circles. The graph updates in real-time to show the population changes.

## Class Descriptions

### Animal

Base class representing an animal with attributes such as age and movement. Both predators and prey inherit from this class.

### Predator

Represents the predator in the simulation. Predators hunt prey and have behaviors such as hunting and reproduction.

### Prey

Represents the prey in the simulation. Prey have behaviors such as moving away from predators, reproducing, and aging.

### GraphFrame

Responsible for rendering the graph that shows the population changes of prey and predators over time.

### SimulationGUI

Manages the graphical user interface (GUI) and visual elements of the simulation, using Java Swing for displaying both the simulation and the graph.

