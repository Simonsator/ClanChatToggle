package de.simonsator.partyandfriends.clan.extension.chattoggle;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.clan.api.events.PlayerLeftClanEvent;
import de.simonsator.partyandfriends.clan.commands.ClanCommands;
import de.simonsator.partyandfriends.clan.commands.subcommands.Chat;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashSet;
import java.util.UUID;

public class ChatManager implements Listener {
	private final HashSet<UUID> players = new HashSet<>();
	private final SubCommand chatCommand = ClanCommands.getInstance().getSubCommand(Chat.class);
	private final PAFExtension PLUGIN;

	public ChatManager(PAFExtension chatTogglePlugin) {
		PLUGIN = chatTogglePlugin;
	}

	@EventHandler
	public void onWrite(ChatEvent pEvent) {
		ProxiedPlayer player = (ProxiedPlayer) pEvent.getSender();
		String message = pEvent.getMessage();
		if (message.startsWith("/")) return;
		if (!contains(player.getUniqueId())) return;
		pEvent.setCancelled(true);
		BukkitBungeeAdapter.getInstance().runAsync(PLUGIN, () ->
				chatCommand.onCommand(PAFPlayerManager.getInstance().getPlayer(player), ("chat " + message).split(" ")));
	}

	@EventHandler
	public void onServerLeave(PlayerDisconnectEvent pEvent) {
		remove(pEvent.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onClanLeave(PlayerLeftClanEvent pEvent) {
		remove(pEvent.getPlayer().getUniqueId());
	}


	public void addPlayer(UUID pUUID) {
		players.add(pUUID);
	}

	public void remove(UUID pUUID) {
		players.remove(pUUID);
	}

	public boolean contains(UUID pUUID) {
		return players.contains(pUUID);
	}

}
