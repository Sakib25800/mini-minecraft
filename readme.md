# Mini Minecraft Text Adventure Game

## Game Overview

A text-based adventure game where the player must collect Ender Eyes to activate the End Portal. Steve needs to gather
blaze powder, find a stronghold, and activate the portal frame.

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

- Items: Ender Pearl (3kg)
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

### Inventory System

- Steve can only carry one instance of a given item

### Weight System

- Maximum carrying capacity: 5kg
- Steve must manage inventory weight strategically

### Win

To win, the player must:

1. Collect Blaze Powder from the Village
2. Collect Iron Sword from Stronghold
3. Kill Enderman for Ender Pearl
4. Combine these to create an Eye of Ender (Blaze Powder + Ender Pearl)
5. Take it to the Portal Room to activate the portal

### Commands

- Base commands: `go`, `back`, `quit`, `help`
- New commands:
    - `pick [item]`: Pick up an item from floor
    - `drop [item]`: Drop an item from inventory
    - `inventory`: Show current items and total weight
    - `craft [item1] [item2]`: Craft an item

  