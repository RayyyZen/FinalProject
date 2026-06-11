<h1 align="center">🚨 SafeWay 🚨</h1>

![SafeWay](Data/SafeWay.png)

## Introduction

**SafeWay** is a Java and JavaFX project built with Maven that simulates the movement of agents inside a graph in order to model and visualize an evacuation scenario in a large building.

The simulation focuses on human behavior under emergency conditions, including decision-making, congestion, and different psychological states that influence movement.

It supports both time-based automatic simulation and manual control, with agents following different strategies and dynamically changing paths based on their state and environment.

## Prerequisites

- Java JDK 21 or later
- Maven 3.8+

## Installation

1. **Install Java using the following commands :**
```sh
sudo apt update
sudo apt install openjdk-21-jdk
```

2. **Install Maven using the following command :**
```sh
sudo apt install maven
```

3. **Clone the repository :** 
```sh
git clone https://github.com/RayyyZen/FinalProject.git
```

4. **Move into the project folder :** 
```sh
cd FinalProject
```

5. **Launch the application :**
```sh
mvn javafx:run
```

## Elements

### Agent

Agents represent individuals moving through the building.

They are characterized by:

- States :
  - Calm → follows the shortest time path
  - Panicked → follows the crowd
  - Crazy → moves randomly
  - Tired → follows shortest distance path

- Behaviors :
  - Priority giver
  - Normal
  - Priority taker

These behaviors influence how agents compete for paths and how decisions are made in congested situations.

### Location

Locations represent points in the graph where agents can be positioned.

- They store:
  - current agents
  - capacity (max agents)
  - and other informations

They are divided into two types:

#### Node

- Represent rooms, exits, or decision points
- Can be open or closed
- Control whether agents can pass

#### Edge

- Connect two nodes
- Have a distance and capacity
- Agents move progressively along edges
- Speed depends on congestion level

### Graph

The graph contains:

- nodes
- edges
- agents

It provides a global view of the simulation and manages interactions between all entities.

### Simulation

The simulation is the core engine of the project.

It:

- runs either in automatic (time-based) or manual mode
- manages the graph and all agents
- simulates evacuation dynamics
- supports saving and loading simulations using binary files

## Files

Simulations can be saved and restored later using serialized binary files, allowing replay and analysis of evacuation scenarios.

## License

This project is licensed under the BSD 2-Clause License. See the [LICENSE](LICENSE) file for details.