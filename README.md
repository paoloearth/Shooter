<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Build Status](https://travis-ci.com/paoloearth/Shooter.svg?token=s5s6Ee4HHvKbox1jnEF9&branch=main)](https://travis-ci.com/paoloearth/Shooter)

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <h3 align="center"><b>Shooter :gun:</b></h3>
  <p align="center">
    <br />
    <a href="https://github.com/paoloearth/Shooter"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/paoloearth/Shooter/issues">Report Bug</a>
    ·
    <a href="https://github.com/paoloearth/Shooter/issues">Request Feature</a>
</p>



<p align="center">
  <img  src="/images/islands_gif.gif">
</p>


<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project </a>
      <ul>
        <li><a href="#built-with">Built With </a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project :blue_book:

There are many great GAMES, however, this is better
Here's why:
* Reason 1 :fire:
* Reason 2 :smile:



### Built With :hammer:

This section should list any major frameworks that you built your project using. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.
* [Java](https://java.com)



<!-- GETTING STARTED -->
## Getting Started :information_source:

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites :memo:

This is an example of how to list things you need to use the software and how to install them.


<!-- USAGE EXAMPLES -->
## Usage :game_die:

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_



<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/paoloearth/Shooter/issues) for a list of proposed features (and known issues).

## Prerequisites

- [ ] Learn how to collaborate & the GitHub workflow

- [ ] Basic knowledge of Functional Programming

- [ ] Basic knowledge of Java FX

## Version 1.0

**Idea:**

Working implementation of a 2-players,2-D shooter game where:

- Both players play on the same machine (only 1 client)

- There is collision detection with obstacles (walls, bushes) for both players & projectiles

- One pressed the executable, a game is launched:
  
  - No menù, map selection etc.

- Only extra functionalities other than:
  
  - "pause" button to stop the game
  
  - "exit" button to stop and close 

---

**Tentative TASK list:**

- [ ] Set up properly the project (GitHub, tags, branches, issues, versioning etc.)

- [ ] Prototyping: how will the game look?
  
  Forget for a moment java constraints and sketch:
  
  - the window
  
  - the buttons
  
  - the player's info (life, bonuses)
  
  How will the scale of the players be ?  
  
  What kind of elements will be present? 

- [ ] Create a JavaFX interface(functional style)
  
  - Components of the interface
  
  - Class structure 
  
  - Must be responsive! (scale automatically when changing the size of the window)
    
    -  [A LayoutSample.java (Release 8) (oracle.com)](https://docs.oracle.com/javase/8/javafx/layout-tutorial/layoutsamplejava.htm#CHDIIDCJ)
    
    - [(13) JavaFx BorderPane Resizing - JavaFX Layout Tutorial (2019) - YouTube](https://www.youtube.com/watch?v=pO4J6JflZeg&t=19s)

- [ ] JavaFX map & Objects:  (always keep functional programming in mind)
  
  - How do we encode the map? Will it be a set of tiles or there is a better way? How will each tile be? Grid?
  
  - How do we encode the objects? Do we use images? png or svg (scalable)? What is the best design? Interface class to abstract objects to then derive?
  
  - What work best for interactions between things? 
  
  - Is the whole map always visible or is it split in portions depending on the position of the player?

- [ ] JavaFX moving objects (functional style)
  
  - How do we move objects in the map in the best way?
    
    - [How to create a game loop in JavaFX - learn by creating a top-down space game – Eden Coding](https://edencoding.com/game-loop-javafx/) (recent arcticle: august 2020)
  
  - Which set of keys should we use as moving-keys for the 2 players?
  
  - Which directions can sprites take?
  
  - Animated sprite?

- [ ] JavaFX collision (Functional styles)
  
  - How do we handle collisions for both sprites, objects and projectiles?
  
  - Border of the map?

- [ ] How do we manage life?
  
  - How to measure, increase or decrease life 
  
  - How to represent life? Health bar of set of hearths?

- [ ] Animation: eg. loading, stop etc.
  
  - [Creating a Sprite Animation with JavaFX - netopyr.com](https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/)

- [ ] Start a match/End a match/Restart a match# Version 2.0

## Version 1.1 ... 1.9

Same game as above but with new features:

- Menu from which you can do things (e.g. select a map)

- Possibility of selecting the sprites for each player

- Set of new in-game features:
  
  - More obstacles
  
  - Powerups & their effects

## Version 2.0

**Idea:**

Working implementation of a 2-players,2-D shooter game where:

- Both players play on the **distinct machine on the same LAN**

A server must take care of everything!

More effects, sprites etc.

## Version 3.0

**Idea:**

Multiplayer (e.g 2 vs 2)

More effects, sprites, bonuses, maps

## Version 4.0

**Idea**:

Game server is on the cloud ->  players no more needs to be on the same LAN



---



## Idea:

- Cespugli rendono invisible il personaggio

- Reti rallentano i proiettili

##### Bonus:

- Proiettili più veloci per x secondi

- Immunità per x secondi 

##### Malus:






<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact :telephone:

Paolo Pulcini  - paoloearth@gmail.com

Project Link: [https://github.com/paoloearth/Shooter](https://github.com/paoloearth/Shooter)



[product-screenshot]: images/screenshot.png
