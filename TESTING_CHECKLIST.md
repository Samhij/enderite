# Pre-Release Testing Checklist

Use this checklist before pushing to GitHub and creating a new release.

## 1. Build & Compilation
- [ ] `./gradlew clean build` completes successfully with no errors
- [ ] `./gradlew runDatagen` runs without errors and generates all necessary files
- [ ] No compiler warnings that indicate potential issues
- [ ] Generated files in `src/main/generated/` are up to date

## 2. Basic Functionality
- [ ] Mod loads in Minecraft without crashes
- [ ] All items appear in creative inventory and item groups
- [ ] All blocks place and break correctly
- [ ] Custom blocks (Void Infusion Table) have correct textures and GUIs
- [ ] No errors or warnings in game console on startup

## 3. Items & Tools
- [ ] All tools (Enderite & Void-Infused) can mine appropriate blocks
- [ ] Tools have correct attack damage and mining speed
- [ ] All armor pieces can be equipped
- [ ] Armor provides correct protection values
- [ ] All items have correct textures (no missing texture purple/black squares)
- [ ] Items are fireproof (test by throwing in lava)
- [ ] Tools and armor have correct durability values

## 4. Recipes

### Smelting/Blasting
- [ ] Raw Enderite → Enderite Scrap (furnace)
- [ ] Raw Enderite → Enderite Scrap (blast furnace)
- [ ] Enderite Ore → Enderite Scrap (furnace)
- [ ] Enderite Ore → Enderite Scrap (blast furnace)

### Crafting
- [ ] Enderite Ingot from 8 Enderite Scrap + 1 Echo Shard
- [ ] Void Infusion Table crafting recipe
- [ ] Block of Enderite from 9 Enderite Ingots
- [ ] 9 Enderite Ingots from Block of Enderite
- [ ] Block of Void from 9 Void-Infused Ingots
- [ ] 9 Void-Infused Ingots from Block of Void

### Smithing - Tools
- [ ] Netherite Sword → Enderite Sword (with template + ingot)
- [ ] Netherite Pickaxe → Enderite Pickaxe (with template + ingot)
- [ ] Netherite Axe → Enderite Axe (with template + ingot)
- [ ] Netherite Shovel → Enderite Shovel (with template + ingot)
- [ ] Netherite Hoe → Enderite Hoe (with template + ingot)

### Smithing - Armor
- [ ] Netherite Helmet → Enderite Helmet (with template + ingot)
- [ ] Netherite Chestplate → Enderite Chestplate (with template + ingot)
- [ ] Netherite Leggings → Enderite Leggings (with template + ingot)
- [ ] Netherite Boots → Enderite Boots (with template + ingot)

### Smithing Template
- [ ] Enderite Upgrade Smithing Template can be duplicated with End Stone
- [ ] Template found in End City chests (if applicable)

### Void Infusion
- [ ] Enderite Sword → Void-Infused Sword
- [ ] Enderite Pickaxe → Void-Infused Pickaxe
- [ ] Enderite Axe → Void-Infused Axe
- [ ] Enderite Shovel → Void-Infused Shovel
- [ ] Enderite Hoe → Void-Infused Hoe
- [ ] Enderite Helmet → Void-Infused Helmet
- [ ] Enderite Chestplate → Void-Infused Chestplate
- [ ] Enderite Leggings → Void-Infused Leggings
- [ ] Enderite Boots → Void-Infused Boots
- [ ] Enderite Ingot → Void-Infused Ingot
- [ ] Chorus Fruit → Void-Infused Chorus Fruit

## 5. Smithing Template UI
- [ ] Template accepts Netherite equipment in base slot (helmet, chestplate, leggings, boots)
- [ ] Template accepts Netherite tools in base slot (sword, pickaxe, axe, shovel, hoe)
- [ ] Template accepts Enderite Ingot in addition slot
- [ ] Empty slot textures display correctly (no pink/magenta squares)
- [ ] Base slot shows correct equipment icons
- [ ] Addition slot shows correct ingot icon
- [ ] Template tooltip shows "Netherite Equipment" for applies to
- [ ] Template tooltip shows "Enderite Ingot" for ingredients
- [ ] Upgraded items preserve enchantments
- [ ] Upgraded items preserve armor trims
- [ ] Upgraded items preserve custom names
- [ ] Upgraded items preserve other NBT data

## 6. Void Infusion Preservation
- [ ] All Enderite tools can be void-infused
- [ ] All Enderite armor can be void-infused
- [ ] Void infusion preserves enchantments (test with multiple enchantments)
- [ ] Void infusion preserves armor trims (test with different trims)
- [ ] Void infusion preserves custom names
- [ ] Void infusion preserves lore/custom NBT
- [ ] Void infusion repairs items to full durability
- [ ] Partially damaged items become fully repaired after void infusion
- [ ] Void Infusion Table GUI works correctly
- [ ] Void-Infused Pickaxe special ability works (breaking bedrock)

## 7. Enchantments

### Durability Enchantments
- [ ] Mending works on all Enderite tools
- [ ] Mending works on all Enderite armor
- [ ] Mending works on all Void-Infused tools
- [ ] Mending works on all Void-Infused armor
- [ ] Unbreaking works on all tools and armor

### Armor Enchantments
- [ ] Protection IV works on all armor pieces
- [ ] Fire Protection works on armor
- [ ] Blast Protection works on armor
- [ ] Projectile Protection works on armor
- [ ] Thorns works on armor
- [ ] Other armor enchantments work correctly

### Tool/Weapon Enchantments
- [ ] Sharpness works on swords
- [ ] Fortune works on pickaxe/axe/shovel
- [ ] Silk Touch works on tools
- [ ] Efficiency works on tools
- [ ] Looting works on swords
- [ ] Fire Aspect works on swords
- [ ] Other tool enchantments work correctly

### Curse Enchantments
- [ ] Curse of Vanishing can be applied
- [ ] Curse of Binding can be applied to armor

## 8. Armor Trims
- [ ] All Enderite armor pieces accept armor trims in smithing table
- [ ] All Void-Infused armor pieces accept armor trims in smithing table
- [ ] Armor trims display correctly on armor (test with multiple trim patterns)
- [ ] Trims are preserved through void infusion
- [ ] Different trim materials display correctly

## 9. Advancements
- [ ] "End Game" - Getting raw enderite
- [ ] "Echoes of Power" - Crafting enderite ingot
- [ ] "City Treasure" - Finding enderite upgrade template
- [ ] "Upgraded... Again" - First enderite equipment
- [ ] "Void Knight" - Full enderite armor set
- [ ] "What?" - Crafting void-infused hoe
- [ ] "Breaking the Rules" - Breaking bedrock with void-infused pickaxe
- [ ] "Overkill" - Killing chicken with enderite sword
- [ ] "Betrayal" - Killing enderman with enderite/void-infused sword
- [ ] "Void Infusion" - Crafting void infusion table
- [ ] "Breath of the Dragon" - Obtaining dragon's breath
- [ ] "Void Touched" - First void-infused item
- [ ] "Void Ascendant" - Full void-infused armor set
- [ ] "Master of the Void" - All void-infused items
- [ ] "Ender Dragon" - Defeat dragon with full enderite armor
- [ ] "Void Dragon" - Defeat dragon with full void-infused armor
- [ ] "Into the Void" - Enter the void dimension
- [ ] "Defying the Depths" - Escape the void
- [ ] "Bad Dream" - Die in the void
- [ ] "Nice Try" - Jump in the void (in the void)
- [ ] Other void-related advancements work correctly
- [ ] All advancement descriptions are correct
- [ ] No typos in advancement text

## 10. Translations & Localization
- [ ] All items have proper names (no "item.enderite.xxx" showing)
- [ ] All blocks have proper names
- [ ] Smithing template tooltips display correctly
- [ ] Smithing template shows "Enderite Upgrade" as name
- [ ] GUI titles display correctly ("Void Infusion Table")
- [ ] Advancement titles and descriptions display correctly
- [ ] No missing translation keys anywhere in the game
- [ ] Item group name displays correctly

## 11. World Generation
- [ ] Enderite Ore generates in The End
- [ ] Ore generation frequency is appropriate (not too common/rare)
- [ ] Ore can be mined with correct tool tier
- [ ] Smithing template spawns in End City chests (if applicable)
- [ ] Template has correct rarity in loot tables

## 12. Compatibility & Tags
- [ ] Beacon accepts Enderite Ingot as payment
- [ ] Beacon accepts Void-Infused Ingot as payment
- [ ] All tools are in correct tool tags (swords, pickaxes, etc.)
- [ ] All armor is in ARMOR_ENCHANTABLE tag
- [ ] All armor is in TRIMMABLE_ARMOR tag
- [ ] All tools/armor are in DURABILITY_ENCHANTABLE tag
- [ ] Repair materials work correctly (Enderite Ingot repairs Enderite items)
- [ ] Repair materials work correctly (Void-Infused Ingot repairs Void-Infused items)

## 13. Special Features
- [ ] Void-Infused Pickaxe can break bedrock
- [ ] Void-Infused Pickaxe bedrock breaking has correct behavior
- [ ] Void-Infused Chorus Fruit teleportation works
- [ ] All special item abilities function correctly
- [ ] Void dimension mechanics work (if applicable)

## 14. Performance & Stability
- [ ] No memory leaks during extended play (30+ minutes)
- [ ] No console spam/warnings during normal gameplay
- [ ] No crashes during normal gameplay
- [ ] Save/load works correctly with modded items in inventory
- [ ] Save/load works correctly with modded items in containers
- [ ] No lag spikes when using mod features
- [ ] Works smoothly with multiple players (if tested in multiplayer)

## 15. Multiplayer Testing (if applicable)
- [ ] Test in singleplayer
- [ ] Test in multiplayer/LAN
- [ ] Recipes work for all players
- [ ] Advancements trigger correctly for all players
- [ ] No sync issues between client and server
- [ ] Void Infusion Table works in multiplayer

## 16. Creative Mode Testing
- [ ] All items available in creative inventory
- [ ] Items are in correct creative tabs
- [ ] No crashes when giving items via /give command
- [ ] Items can be picked with middle-click in creative

## 17. Recipe Book
- [ ] Smelting recipes show in recipe book
- [ ] Crafting recipes show in recipe book
- [ ] Smithing recipes show in recipe book
- [ ] Recipe unlock notifications work correctly
- [ ] Recipes are categorized correctly

## 18. Textures & Models
- [ ] All item textures render correctly
- [ ] All block textures render correctly
- [ ] Armor textures render correctly on player
- [ ] Void-Infused armor has distinct appearance
- [ ] No z-fighting or texture issues
- [ ] Textures work with different resource pack settings

## 19. Git & Version Control
- [ ] All generated files from `runDatagen` are committed
- [ ] `src/main/generated/` files are up to date in git
- [ ] Version number updated in `gradle.properties`
- [ ] Changelog/commit messages are clear and descriptive
- [ ] No debug code or commented-out code left in
- [ ] No IDE-specific files committed (.idea, .vscode, etc.)
- [ ] `.gitignore` is properly configured

## 20. GitHub Release Preparation
- [ ] Version tag matches mod version
- [ ] Release notes written with all changes listed
- [ ] Known issues documented in release notes
- [ ] Installation instructions are clear
- [ ] Required dependencies listed (Fabric API version)
- [ ] Minecraft version compatibility specified (e.g., "1.21.4")
- [ ] Fabric Loader version requirement specified
- [ ] Link to issue tracker included

## 21. Build Artifacts
- [ ] JAR file builds successfully
- [ ] JAR file size is reasonable (not bloated)
- [ ] JAR doesn't include unnecessary dependencies
- [ ] JAR includes all necessary assets and data
- [ ] `fabric.mod.json` has correct version number
- [ ] `fabric.mod.json` has correct Minecraft version requirement
- [ ] Mod ID is correct in `fabric.mod.json`

## 22. Documentation
- [ ] README.md is up to date
- [ ] Features list is current
- [ ] Screenshots/images are up to date (if applicable)
- [ ] License file is present and correct
- [ ] Credits are accurate

## 23. Final Verification
- [ ] Test with a fresh `.minecraft` folder (clean install)
- [ ] Test with only Fabric API as dependency
- [ ] Verify no conflicts with popular mods (if possible)
- [ ] All config files work correctly (if applicable)
- [ ] Mod loads correctly on dedicated server (if applicable)

## 24. Specific to This Release

### Smithing Template Fix
- [ ] Smithing template no longer uses netherite upgrade behavior
- [ ] Template correctly requires netherite equipment (not diamond)
- [ ] Template correctly requires enderite ingot (not netherite ingot)
- [ ] Empty slot textures display properly (container/slot paths work)
- [ ] All armor pieces work with smithing template
- [ ] All tools work with smithing template

### Void Infusion Component Preservation
- [ ] Enchantments preserved through void infusion
- [ ] Armor trims preserved through void infusion
- [ ] Custom names preserved through void infusion
- [ ] Items are repaired to full durability during void infusion

### Enchantment Compatibility
- [ ] Mending can be applied to all void-infused items
- [ ] Mending can be applied to all enderite items
- [ ] All items are in DURABILITY_ENCHANTABLE tag

---

## Notes

- Check off each item as you test it
- Document any issues found during testing
- Retest after fixing any issues
- Consider asking for beta testers for major releases

## Version Tested

- **Date**:
- **Version**:
- **Minecraft Version**:
- **Fabric API Version**:
- **Tester**:

## Issues Found

List any issues discovered during testing:

1.
2.
3.
