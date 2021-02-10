# DarwinWorld 
Simple 2d simulation that visualise basics animals' life processes in jungle. Was made with the purpose of broaden knowledge of Java nad JavaFX.

## Technologies

Project is created with:
* Java version: 14.0.2 
* JavaFX version: 15.0.1
* Gson version: 2.8.6

## Setup

To run this project using IntelliJ install necessary libraries and add VM options:
* Linux/Mac

```
--module-path /path/to/javafx-sdk-15.0.1/lib --add-modules javafx.controls,javafx.fxml
```
* Windows
```
--module-path "\path\to\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml
```

If you use another programming environment, refer to [link](https://openjfx.io/openjfx-docs/) for more information.

## Description  
Animals are born, copulate, eat and die on the map which is divided into two areas: steppe and jungle. Every day grow three plants, one on the steppe, one in the jungle. 
* **Move** - each animal's move cost energy. Direction of move is determined by its genome.
* **Copulate** - Happens when at least two animals visit the same position. Two strongest of them copulate, spending 25% of their energy by giving it to the child. Also, genome is hereditary. 
* **Eat** - Happens when at least one animal visit place where plant grows. The plant is eaten by the strongest animal, which energy increases by plant's energy. 
* **Die** - Animals die when their energy reaches 0 value.

Besides visuals, we can also analyze simulation data like:
* **Statistics of current day**: number of alive animals, number of grass, average energy, average life, average num of children, dominating genome.
* **Statistics of one chosen animal after n epochs**: number of descendants, number of children, death time, genotype.
* **Statistics of n epochs**: average lifetime, average energy, num of children, num of grass, num of alive animals, dominating genome.


We can change the simulation parameters by modifying parameters.json file.
To receive statistics of n epochs stop simulation, insert n, click button, start simulation. Data will be automatically saved into statistics.txt.
To receive statistics of one chosen animal after n epochs stop simulation, click on animal, insert n, click button, start simulation.

You can find more detailed description [here](https://github.com/apohllo/obiektowe-lab/tree/master/proj1).

## Visuals 
![](darwinVisuals.gif)
