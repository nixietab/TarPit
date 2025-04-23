package com.nixietab.tarpitplugin

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.logging.Level

class TarpitPlugin : JavaPlugin(), Listener {
    // Configuration values
    private var tarpitDelay: Long = 30
    private var kickMessage: String = "§cYou are not whitelisted on this server"
    
    @Volatile
    private var isWhitelistEnabled: Boolean = false

    override fun onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig()
        
        loadConfig()
        
        server.pluginManager.registerEvents(this, this)
        
        updateWhitelistStatus()
        
        logger.info("TarpitPlugin has been enabled!")
    }

    private fun loadConfig() {
        reloadConfig()
        
        // Get values from config.yml, or use defaults if not found
        tarpitDelay = config.getLong("tarpit.delay", 30)
        kickMessage = config.getString("tarpit.message") 
            ?: "§cYou are not whitelisted on this server"
    }

    private fun updateWhitelistStatus() {
        isWhitelistEnabled = server.hasWhitelist()
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onAsyncPlayerPreLogin(event: AsyncPlayerPreLoginEvent) {
        // Early return if whitelist is disabled
        if (!isWhitelistEnabled) {
            return
        }

        val name = event.name
        val uuid = event.uniqueId

        try {
            // Check whitelist status asynchronously
            val isWhitelisted = CompletableFuture.supplyAsync {
                server.getOfflinePlayer(uuid).isWhitelisted
            }.get(5, TimeUnit.SECONDS) // Timeout after 5 seconds

            if (!isWhitelisted) {
                // Apply tarpit delay
                TimeUnit.SECONDS.sleep(tarpitDelay)
                
                // Deny login
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    kickMessage
                )
                
                // Log the tarpit
                logger.log(
                    Level.INFO,
                    "Applied tarpit delay ({0} seconds) to non-whitelisted player: {1} (UUID: {2})",
                    arrayOf(tarpitDelay, name, uuid)
                )
            }
        } catch (e: Exception) {
            // Log the error
            logger.log(
                Level.WARNING,
                "Failed to process login for player {0} (UUID: {1}): {2}",
                arrayOf(name, uuid, e.message)
            )
            
            // Deny login with a generic message
            event.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                "§cUnable to process login request. Please try again."
            )
        }
    }

    override fun onDisable() {
        logger.info("TarpitPlugin has been disabled!")
    }
}