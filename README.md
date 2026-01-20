# Enderite Mod

A Minecraft Fabric mod that adds Enderite, a powerful material from The End dimension that surpasses Netherite, along with a mysterious Void dimension and unique gameplay mechanics.

## Overview

Enderite introduces an end-game progression system with two tiers of equipment:
- **Enderite**: An upgrade from Netherite with unique armor abilities
- **Void Infused**: The ultimate tier with powerful buffs and trade-offs

## Features

### Materials & Items

#### Enderite Ore & Processing
- **Enderite Ore**: Found in The End dimension
- **Raw Enderite**: Obtained by mining Enderite Ore
- **Enderite Scrap**: Smelted from Raw Enderite
- **Enderite Ingot**: Crafted from Enderite Scrap
- **Enderite Block**: Storage block made from 9 Enderite Ingots
- **Enderite Upgrade Smithing Template**: Used to upgrade Netherite gear to Enderite

#### Void Infused Materials
- **Void Infused Ingot**: Created through the Void Infusion process
- **Void Infused Block**: Storage block for Void Infused Ingots
- **Void Infused Chorus Fruit**: Special consumable that teleports you from The Void dimension
- **Void Infusion Table**: Special crafting station for creating Void Infused items

### Equipment

#### Enderite Toolset
All Enderite tools and armor are fireproof and more durable than Netherite.

**Tools:**
- Enderite Sword (3 attack damage, -2.4 attack speed)
- Enderite Pickaxe (1.0 attack damage, -2.8 attack speed)
- Enderite Shovel (1.5 attack damage, -3.0 attack speed)
- Enderite Axe (6.0 attack damage, -3.2 attack speed)
- Enderite Hoe (0.0 attack damage, -3.0 attack speed)

**Armor with Unique Buffs:**
- **Enderite Helmet**: Prevents Endermen from becoming aggressive when you look at them
- **Enderite Chestplate**: Built-in Elytra functionality (gliding capability)
- **Enderite Leggings**: 8% chance to negate incoming projectile damage
- **Enderite Boots**: +1 safe fall damage distance

#### Void Infused Equipment
Rare tier equipment with enhanced abilities but comes with the Curse of Vanishing.

**Tools:**
- Void Infused Sword
- Void Infused Pickaxe (Can break bedrock - see special mechanics)
- Void Infused Shovel
- Void Infused Axe
- Void Infused Hoe

**Armor with Enhanced Buffs:**
- **Void Infused Helmet**: Grants Luck effect (better loot) + Darkness effect
- **Void Infused Chestplate**: Built-in Elytra functionality
- **Void Infused Leggings**: 12% chance to negate projectile damage (improved from 8%)
- **Void Infused Boots**: Grants Weaving effect + improved safe fall distance

All Void Infused armor automatically receives the Curse of Vanishing enchantment.

### Special Mechanics

#### Bedrock Breaking
The Void Infused Pickaxe has the unique ability to break bedrock blocks, allowing for creative world manipulation.

#### The Void Dimension
A mysterious custom dimension accessible through special means:
- Unique challenges and structures
- Custom fog effects
- Special mobs and loot
- Contains resources needed for Void Infusion

### Entities

#### Enderite Golem
A powerful guardian creature that can be spawned using Enderite Blocks (similar to Iron Golem construction):
- 200 health (100 hearts) - double that of an Iron Golem
- 15 base attack damage
- Hostile to Iron Golems
- Uses a custom crack overlay texture system
- Features custom rendering

### World Generation

#### Ore Generation
Enderite Ore generates naturally in The End dimension using configured features and placements.

#### Structures
Custom structures generate in The Void dimension.

### Progression & Advancements

The mod includes a comprehensive advancement tree with unique achievements:

**Main Progression:**
- Root advancement to start your Enderite journey
- City Treasure - Find loot in End Cities
- Upgraded Again - Obtain Enderite equipment
- Void Infusion Table - Craft the Void Infusion Table
- Void Infused - Create Void Infused equipment
- Void Knight - Obtain full Void Infused armor
- Void Ascendant - Master-level achievement
- Void Master - Ultimate achievement

**Challenge Advancements:**
- Get Ender Eye - Obtain an Eye of Ender
- Ender Dragon - Defeat the Ender Dragon
- Enter The Void - Enter The Void dimension
- Die in Void - Experience death in The Void
- Escape Void - Successfully escape The Void dimension
- Void Structure - Discover a structure in The Void
- Kill Blaze Void - Defeat a Blaze in The Void
- Trade with Villager Void - Trade with a villager in The Void
- Loot Bastion Void - Find treasure in a Bastion in The Void
- Break Spider Spawner - Destroy a spider spawner
- Get Levitation - Obtain the Levitation effect
- Dragon's Breath - Collect Dragon's Breath
- Nice Try - Secret achievement
- What - Secret achievement
- Betrayal - Secret achievement
- Echoes of Power - Secret achievement
- Overkill - Deal massive damage
- Unbreakable - Achievement related to durability
- Void Dragon - Ultimate boss challenge

### Technical Details

**Mod Information:**
- Mod ID: `enderite`
- Version: 3.1.0-beta
- License: GPL-3.0
- Minecraft Version: 1.21.4
- Loader: Fabric 0.16.14
- Fabric API: 0.119.2+1.21.4
- Yarn Mappings: 1.21.4+build.8

**Main Classes:**
- Main Entry Point: `net.lonk.enderite.Enderite`
- Client Entry Point: `net.lonk.enderite.EnderiteClient`
- Data Generator: `net.lonk.enderite.EnderiteDataGenerator`

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download the Enderite mod
4. Place the mod jar file in your `.minecraft/mods` folder
5. Launch Minecraft with the Fabric profile

## Crafting & Recipes

The mod includes various crafting recipes:
- Smelting/Blasting Raw Enderite to get Enderite Scrap
- Crafting Enderite Ingots from Enderite Scrap
- Crafting Enderite Blocks (9 Enderite Ingots)
- Smithing Table recipes to upgrade Netherite gear to Enderite
- Void Infusion Table recipes for creating Void Infused items
- Enderite Upgrade Smithing Template crafting

## Loot Tables

The mod modifies vanilla loot tables to include Enderite-related items in appropriate locations, primarily in End dimension structures and chests.

## Development

This mod uses Fabric's data generation system for:
- Block and item models
- Loot tables
- Recipes
- Advancements
- Tags (block, item, structure)
- World generation features
- Language files (English)

## License

This project is licensed under GPL-3.0.

## Credits

Developed by Lonk
