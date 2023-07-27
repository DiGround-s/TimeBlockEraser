# TimeBlockEraser
TimeBlockEraser is a light-weight plugin for Minecraft servers running Spigot and most of its forks!

The plugin will remove entities and blocks after a given amount of time.


## Commands
* `/timeblockeraser about` - Get the information of the plugin
* `/timeblockeraser reload` - reload the configuration.


## Permissions

Default permissions
* `timeblockeraser.about`

Admin permissions:
* `timeblockeraser.reload`
* `timeblockeraser.bypass`

## Config

Default config:
```
# Enabled Worlds
enabled-worlds:
- BoxPvP
- MyWorld
- world
# Enable bypass permission
bypass-perm: true
# Set here the blocks you want to delete and the time in seconds!
blocks:
  COBWEB: 30
  OBSIDIAN: 40
# Set here the entities you want to delete and the time in seconds!
entities:
  ENDER_CRYSTAL: 10
```

# NOTE:
You need to change the blocks name and entities type if you use some Minecraft versions, this example was made for Minecraft 1.20


## Please report any issue on GitHub.

## Thank you for using my plugin!
