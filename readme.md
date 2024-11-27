# Mini Minecraft Text Adventure Game

## Game Overview

A text-based adventure where Steve must gather Blaze Powder and an Ender Pearl to activate the End Portal frame.

## Map (6 Rooms)

```
                 VILLAGE
                    |
                    |
                    N
                    |
                    |
       Nether--S--Plains--E--Forest
                    |
                    |
                    S
                    |
                    |
               Stronghold
                    |
                    |
                    S
                    |
                    |
               Portal Room
```

## Rooms

### Plains (Starting Room)

- Items: None
- Description: A grassy starting area connecting all major locations

### Village

- Items: Blaze Powder (2kg)
- Description: A village where rare materials remain

### Forest

- Items: None
- Description: Dark forest where Enderman build
- Mobs: Enderman

### Nether

- Items: None
- Description: A dangerous dimension with essential materials

### Stronghold

- Items: Iron Sword (5kg)
- Description: Ancient structure housing the End Portal

### Portal Room

- Items: None
- Description: Contains the End Portal frame that needs activation
- Special: Location where portal must be activated to win

## Game Mechanics

### Weight System

- Maximum carrying capacity: 5kg
- Steve must manage inventory weight strategically

### Win

To win, the player must:

1. Collect Iron Sword from Stronghold
    - `go south` -> `pickup iron_sword`
2. Kill Enderman for Ender Pearl (Enderman teleports so you must follow it)
    - `attack enderman` -> `drop iron_sword` -> `pickup ender_pearl`
3. Collect Blaze Powder from the Village (go back to Plains)
    - `go north` -> `pickup blaze_powder`
4. Combine these to create an Eye of Ender (Blaze Powder + Ender Pearl)
    - `craft ender_pearl blaze_powder`
5. Take it to the Portal Room to activate the portal (go back to Plains)
    - `go south` -> `go south` -> Fin

### Commands

- Base commands: `go`, `back`, `quit`, `help`
- New commands:
    - `map`: Show current location
    - `pick [item]`: Pick up an item from floor
    - `drop [item]`: Drop an item from inventory
    - `inventory`: Show current items and total weight
    - `craft [item1] [item2]`: Craft an item

# Report

## Base Functionality

- [x] The game has at least six locations/rooms
    - Plains, Village, Forest, Nether, Stronghold, End Portal
- [x] There are items in some rooms. Every room can hold any number of items. Some items can be picked up by the player,
  others can’t.
    - Rooms can hold items
- [x] The player can carry some items with him. Every item has a weight. The player can carry items only up to a certain
  total weight.
    - Max 5kg
- [x] The player can win. There has to be some situation that is recognised as the end of
  the game where the player is informed that he/she has won.
    - Reaching the End Portal room with the Eye of Ender
- [x] Implement a command “back” that takes you back to the last room you’ve been in. The “back” command should keep
  track of every move made, allowing the player to
  eventually return to its starting room.
    - Added `back`
- [x] Add at least four new commands (in addition to those that were present in the code
  you got from us).
    - Added `map, pick, drop, inventory, craft`

## Challenge Tasks

- [x] Add characters to your game. Characters are people or animals or monsters – anything that moves, really.
  Characters are also in rooms (like the player and the items). Unlike items, characters can move around by themselves.
    - Zombie and Enderman
- [x] Extend the parser to recognise three-word commands.
    - `craft [item1] [item2]`
- [x] Add a magic transporter room – every time you enter it you are transported to a
  random room in your game.
    - The Nether room

## Considerations and Examples

- Coupling
    - The `Room` enum and `Entity` class demonstrate loose coupling through the `LocationManager`
    - Neither `Room` nor `Entity` manage their own location state, rather they delegate to the `LocationManager`
    - Changes to how entity locations are tracked only need to be made in one place (allowing for a single source of
      truth)
    - The `Room` and `Entity` classes remain independent of each other's implementation details
    - Thus reducing dependencies between classes and makes the system more flexible
- Cohesion
    - The `Recipe` record class
    - The class has a single, well-defined purpose (representing a crafting recipe)
    - All its fields and methods are closely related to that purpose
    - It handles only recipe-related functionality (storing ingredients and checking matches)
    - There's no unrelated functionality mixed in
- Responsibility driven design
    - The `Mob` class hierarchy shows clear separation of responsibilities
    - Base Mob class handles common mob behaviors (movement, actions)
    - Specific mob types (like Enderman) handle their unique behaviors
- Maintainability
    - Internal Map is created in one clear centralized place: the `Room` enum
    - Making it easy to see and modify room relationships
    - New rooms can be added by extending the map structure very easily
    - The initialization code is self-documenting and follows a consistent pattern
