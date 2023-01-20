package de.simonsator.partyandfriends.velocity.clan.extension.chattoggle;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import de.simonsator.partyandfriends.velocity.api.PAFExtension;
import de.simonsator.partyandfriends.velocity.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.velocity.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.velocity.clan.api.events.PlayerLeftClanEvent;
import de.simonsator.partyandfriends.velocity.clan.commands.ClanCommands;
import de.simonsator.partyandfriends.velocity.clan.commands.subcommands.Chat;
import de.simonsator.partyandfriends.velocity.utilities.SubCommand;

import java.util.HashSet;
import java.util.UUID;

public class ChatManager {
	private final HashSet<UUID> players = new HashSet<>();
	private final SubCommand chatCommand = ClanCommands.getInstance().getSubCommand(Chat.class);
	private final PAFExtension PLUGIN;

	public ChatManager(PAFExtension chatTogglePlugin) {
		PLUGIN = chatTogglePlugin;
	}

	@Subscribe
	public void onWrite(PlayerChatEvent pEvent) {
		Player player = pEvent.getPlayer();
		String message = pEvent.getMessage();
		if (message.startsWith("/")) return;
		if (!contains(player.getUniqueId())) return;
		pEvent.setResult(PlayerChatEvent.ChatResult.denied());
		BukkitBungeeAdapter.getInstance().runAsync(PLUGIN, () ->
				chatCommand.onCommand(PAFPlayerManager.getInstance().getPlayer(player), ("chat " + message).split(" ")));
	}

	@Subscribe
	public void onServerLeave(DisconnectEvent pEvent) {
		remove(pEvent.getPlayer().getUniqueId());
	}

	@Subscribe
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
