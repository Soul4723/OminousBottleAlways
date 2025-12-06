# OminousBottleAlways

OminousBottleAlways is a simple Paper plugin that forces raid/patrol captains to always drop one OMINOUS_BOTTLE with proper Bad Omen levels.

## Requirements
- Java 21 or newer
- Paper 1.21.4+

## Installation
1. Build plugin or download jar.
2. Place jar file in `plugins` folder of your Paper server.
3. Start or restart your server.

## Behavior
- Detects raid/patrol captains when they are killed
- Ensures captains always drop exactly one OMINOUS_BOTTLE
- Generates random Bad Omen amplifier levels (1-5) on the bottle
- Respects server's `doMobLoot` game rule

## Notes
- This project uses Paper API features that are available in Paper 1.21.4+. If you run on older versions, behavior may vary.
- No configuration required - plugin works out of the box.
- No commands or permissions - plugin is fully automatic.

## License
This project is licensed under the MIT License. See `LICENSE` file for details.
