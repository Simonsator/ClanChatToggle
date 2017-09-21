package de.simonsator.partyandfriends.clan.extension.chattoggle;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
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

/**
 * @author Simonsator
 * @version 1.0.0 03.10.16
 */
public class ChatManager implements Listener {
	private HashSet<UUID> players = new HashSet<>();
	private final SubCommand chatCommand = ClanCommands.getInstance().getSubCommand(Chat.class);

	@EventHandler
	public void onWrite(ChatEvent pEvent) {
		OnlinePAFPlayer p = PAFPlayerManager.getInstance().getPlayer((ProxiedPlayer) pEvent.getSender());
		String message = pEvent.getMessage();
		if (message.startsWith("/"))
			return;
		if (!contains(p.getUniqueId()))
			return;
		pEvent.setCancelled(true);
		chatCommand.onCommand(p, ("chat " + message).split(" "));
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
