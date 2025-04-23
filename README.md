# TarpitPlugin

[![Paper](https://img.shields.io/badge/Paper-1.20.x-blue)](https://papermc.io/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

A Minecraft plugin that implements a tarpit mechanism for non-whitelisted players. Instead of immediately rejecting unauthorized connection attempts, it delays the response to help mitigate potential automated attacks.

## Features

- 🕒 Configurable connection delay for non-whitelisted players
- 💬 Customizable rejection messages
- 🔒 Works with Minecraft's built-in whitelist system
- 🛡️ Helps protect against automated connection attempts

## Requirements

- Paper/Spigot 1.13+
- Java 17 or higher
- Whitelist enabled on the server

## Installation

1. Download the latest release from the [releases page](https://github.com/nixietab/Tarpit/releases)
2. Place the `TarpitPlugin.jar` in your server's `plugins` folder
3. Restart your server
4. Enable whitelist on your server using `/whitelist on`

## Configuration

The plugin creates a `config.yml` file in the `plugins/TarpitPlugin` folder:

```yaml
# TarpitPlugin Configuration
tarpit:
  # Delay in seconds before rejecting non-whitelisted players
  delay: 30
  # Message to show to non-whitelisted players
  message: "§c§lAccess Denied\n§r§cYou are not whitelisted on this server\n§eContact an administrator to get access"
```

## Building from Source

```bash
# Clone the repository
git clone https://github.com/nixietab/TarPit.git

# Change to project directory
cd TarpitPlugin

# Build the plugin
./gradlew shadowJar
```

The compiled plugin will be in `build/libs/TarpitPlugin.jar`

## Usage

If whitelist is enabled, the plugin will work perfectly on start

## Support

If you encounter any issues or have questions, please:
1. Check the [issues page](https://github.com/nixietab/TarPit/issues)
2. Create a new issue if your problem isn't already reported
