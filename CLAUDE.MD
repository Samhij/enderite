# Enderite Mod - Claude Development Guide

## Project Overview

**Enderite** is a Minecraft Fabric mod that adds a new tier of materials and tools called "Enderite" - an End dimension-focused upgrade path similar to Netherite for the Nether.

- **Mod ID**: `enderite`
- **Minecraft Version**: 1.21.1
- **Fabric Loader**: 0.18.4
- **Fabric API**: 0.116.7+1.21.1
- **License**: GPL-3.0
- **Current Version**: 0.0.1

## Project Structure

```
enderite/
├── src/main/java/net/lonk/enderite/
│   ├── Enderite.java              # Main mod entry point
│   ├── EnderiteDataGenerator.java # Data generation entry point
│   ├── block/
│   │   └── ModBlocks.java         # Block definitions (Enderite Ore)
│   ├── item/
│   │   ├── ModItems.java          # Item definitions (tools, armor, materials)
│   │   ├── ModItemGroups.java     # Creative tab organization
│   │   ├── ModToolMaterials.java  # Tool tier definition
│   │   └── ModArmorMaterials.java # Armor material definition
│   ├── world/
│   │   ├── ModConfiguredFeatures.java # Ore feature configuration
│   │   ├── ModPlacedFeatures.java     # Ore placement configuration
│   │   ├── ModOrePlacement.java       # Placement helper methods
│   │   └── gen/
│   │       └── ModWorldGeneration.java # World gen registration
│   ├── util/
│   │   └── ModLootTableModifiers.java # Loot table injection
│   ├── mixin/
│   │   ├── InGameHudMixin.java        # Armor bar scaling for 25 armor
│   │   └── EndermanEntityMixin.java   # Enderman non-aggro with full armor
│   └── datagen/
│       ├── ModAdvancementProvider.java
│       ├── ModBlockTagProvider.java
│       ├── ModItemTagProvider.java
│       ├── ModLootTableProvider.java
│       ├── ModModelProvider.java
│       ├── ModRecipeProvider.java
│       ├── ModWorldGenProvider.java
│       └── lang/
│           └── ModEnglishLangProvider.java
└── src/main/resources/
    ├── fabric.mod.json            # Mod metadata
    ├── enderite.mixins.json       # Mixin configuration
    └── assets/enderite/
        └── textures/              # Item, block, and armor textures
```

## Current Features

### Items
- **Enderite Ingot**: Primary crafting material (`enderite_ingot`)
- **Raw Enderite**: Ore drop item (`raw_enderite`)
- **Enderite Scrap**: Intermediate material (`enderite_scrap`)
- **Enderite Upgrade Smithing Template**: Used to upgrade items to Enderite tier (`enderite_upgrade_smithing_template`)

### Tools (Full Set)
- **Enderite Sword**: Attack damage 3, attack speed -2.4
- **Enderite Pickaxe**: Attack damage 1.0, attack speed -2.8
- **Enderite Shovel**: Attack damage 1.5, attack speed -3.0
- **Enderite Axe**: Attack damage 6.0, attack speed -3.2
- **Enderite Hoe**: Attack damage 0.0, attack speed -3.0
- Tool Material: Custom Enderite tier (ModToolMaterials.ENDERITE)

### Armor (Full Set)
- **Enderite Helmet**: 40 durability multiplier
- **Enderite Chestplate**: 40 durability multiplier
- **Enderite Leggings**: 40 durability multiplier
- **Enderite Boots**: 40 durability multiplier
- Armor Material: Custom Enderite material (ModArmorMaterials.ENDERITE_ARMOR_MATERIAL)
- **Special Feature**: Wearing full Enderite armor prevents Endermen from aggroing when looking at them

### Blocks
- **Enderite Ore**: Found in the End dimension (`enderite_ore`)
  - Uses End Stone properties as base
  - Generates between Y-levels 30-80 in the End
  - Spawns in veins of up to 4 blocks
  - 3 veins per chunk

### World Generation
- **Ore Generation**: Enderite ore generates in End dimension
  - Height range: Y30 to Y80 (uniform distribution)
  - Vein size: 4 blocks
  - Frequency: 3 attempts per chunk
  - Replaces: End Stone blocks only

### Loot Table Modifications
- **End City Treasure Chests**: 10% chance to contain Enderite Upgrade Smithing Template
- **Bastion Treasure Chests**: 5% chance to contain Enderite Upgrade Smithing Template

### Special Mechanics
- **Enderman Non-Aggro**: Players wearing full Enderite armor set will not trigger Enderman aggro when looking at them
  - Implemented via mixin injection into `EndermanEntity.isPlayerStaring()`
  - Requires all 4 armor pieces to be Enderite
- **Armor Bar Scaling**: Custom HUD rendering to properly display 25 armor points (max Enderite armor) in 10 icons
  - Each icon represents 2.5 armor points instead of 2

## Development Patterns

### Registration Pattern
The mod uses a consistent registration pattern across items and blocks:

1. **Items** (src/main/java/net/lonk/enderite/item/ModItems.java:18-20):
   - Static final fields for each item
   - `registerItem()` helper method
   - `register()` method called during mod initialization

2. **Blocks** (src/main/java/net/lonk/enderite/block/ModBlocks.java:17-24):
   - Static final fields for each block
   - `registerBlock()` automatically creates BlockItem
   - `register()` method called during mod initialization

3. **Initialization Flow** (src/main/java/net/lonk/enderite/Enderite.java:16-23):
   ```java
   ModItemGroups.init();
   ModItems.init();
   ModBlocks.init();
   ModWorldGeneration.generateWorldGeneration();
   ModLootTableModifiers.modifyLootTables();
   ```

### Data Generation
The mod uses Fabric's data generation system for:
- Block/item models (ModModelProvider)
- Recipes (ModRecipeProvider)
- Loot tables (ModLootTableProvider)
- Block/item tags (ModBlockTagProvider, ModItemTagProvider)
- Advancements (ModAdvancementProvider)
- Localization (ModEnglishLangProvider)
- World generation features (ModWorldGenProvider)

Run data generation with:
```bash
./gradlew runDatagen
```

### Mixin System
The mod uses Mixins for gameplay modifications:
- **InGameHudMixin**: Modifies armor bar rendering to support 25+ armor points
- **EndermanEntityMixin**: Prevents enderman aggro when wearing full Enderite armor

Mixins are configured in `enderite.mixins.json` with:
- Common mixins (affect both client and server)
- Client-only mixins

## Key Implementation Details

### Identifiers
All resource identifiers use the pattern:
```java
Identifier.of(Enderite.MOD_ID, name)
```

### Logging
Centralized logger available via:
```java
Enderite.LOGGER
```

### Namespace
- Java package: `net.lonk.enderite`
- Maven group: `net.lonk`
- Resource namespace: `enderite:`

## Current Status

### Completed Features
- [x] Tool item definitions with custom material tier
- [x] Armor item definitions with custom armor material
- [x] Material tier definition for tools (ModToolMaterials.ENDERITE)
- [x] Armor material definition (ModArmorMaterials.ENDERITE_ARMOR_MATERIAL)
- [x] Ore generation in End dimension (Y30-80, 3 veins/chunk, size 4)
- [x] Loot table for enderite ore
- [x] Smithing template for upgrading to Enderite
- [x] Texture assets for all tools and armor
- [x] GPL-3.0 license applied
- [x] Enderman non-aggro when wearing full Enderite armor
- [x] Armor bar HUD scaling for 25+ armor points
- [x] Loot table injection (End Cities and Bastions)
- [x] All datagen providers set up and functioning

### TODO / Future Development

Potential features to add:
- [ ] Crafting recipes (via datagen)
- [ ] Smelting/blasting recipes for raw enderite
- [ ] More End-themed features:
  - [ ] Ender pearl efficiency (no damage when wearing full set)
  - [ ] Levitation immunity (shulker resistance)
  - [ ] Enderite storage block
  - [ ] Deepslate Enderite Ore variant
  - [ ] Ender-themed special items (bow, shield, etc.)
- [ ] Additional advancements
- [ ] More comprehensive tags
- [ ] Integration with other End-related structures

## Build & Development

### Building
```bash
./gradlew build
```

### Running Data Generation
```bash
./gradlew runDatagen
```

### Running the Game
```bash
./gradlew runClient  # Client
./gradlew runServer  # Server
```

## Code Style Notes

- Region comments used for organization (`// region`, `// endregion`)
- Helper methods marked as private
- Registration methods are public static void
- All registrations happen at class loading time (static initializers)

## Important Reminders for Claude

1. Always read files before modifying them
2. Follow the existing registration patterns in ModItems and ModBlocks
3. Use the centralized logger (Enderite.LOGGER) for logging
4. Maintain consistency with Minecraft 1.21.1 API
5. All new content should use the datagen system rather than manual JSON files
6. Textures are already present - focus on implementing the game logic
7. Keep the GPL-3.0 license intact
8. World generation must be initialized in Enderite.onInitialize()
9. Mixins must be registered in enderite.mixins.json (common or client array)
10. Loot table modifications use Fabric's LootTableEvents.MODIFY system
