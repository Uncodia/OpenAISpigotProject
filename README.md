# OpenAISpigotProject

This is a plugin for the popular game Minecraft, built on the Bukkit API. The plugin utilizes OpenAI API to generate text responses in-game, triggered by player chat and displayed as a hologram above the closest Villager.

## Installation
For installation you will need to have a Spigot server running. You can download the latest version of Spigot [here](https://www.spigotmc.org/wiki/buildtools/). Once you have a Spigot server running, you can install the plugin by placing the .jar file in the plugins folder of your server.
You have to add your API token to OpenAISpigot\config.properties like this:
```OPENAI_API_KEY=YOUR_API_KEY```
You can get your API key from [OpenAI](https://beta.openai.com/).

## Classes

### ChatListener

The ChatListener class listens to player chat events and triggers the response generation. It searches for the closest Villager within a 10 block radius of the player and shakes its head. The player's message is then passed as a prompt to the OpenAI API, and the generated response is displayed in-game.

### OpenAiCodeCompletionTask

The OpenAiCodeCompletionTask class makes a request to the OpenAI API and retrieves a text response based on the prompt. The text response is then displayed in-game as a hologram above the closest Villager found by the ChatListener class.