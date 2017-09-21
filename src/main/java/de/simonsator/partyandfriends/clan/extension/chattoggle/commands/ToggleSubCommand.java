package de.simonsator.partyandfriends.clan.extension.chattoggle.commands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.clan.ClansMain;
import de.simonsator.partyandfriends.clan.api.ClansManager;
import de.simonsator.partyandfriends.clan.api.abstractcommands.ClanSubCommand;
import de.simonsator.partyandfriends.clan.extension.chattoggle.ChatManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;

public class ToggleSubCommand extends ClanSubCommand implements Listener {
	private final ChatManager CHAT_MANAGER;
	private final Configuration CONFIG;

	public ToggleSubCommand(String[] pCommands, String pPermission, int pPriority, String pHelp, ChatManager pChatManager, Configuration pConfig) {
		super(pCommands, pPermission, pPriority, pHelp);
		CHAT_MANAGER = pChatManager;
		CONFIG = pConfig;
	}

	@Override
	protected void execute(OnlinePAFPlayer pPlayer, String[] args) {
		if (CHAT_MANAGER.contains(pPlayer.getUniqueId())) {
			pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Disabled"));
			CHAT_MANAGER.remove(pPlayer.getUniqueId());
			return;
		}

		if (ClansManager.getInstance().getClan(pPlayer) == null) {
			pPlayer.sendMessage(new TextComponent(ClansMain.getInstance().getClanPrefix()
					+ ClansMain.getInstance().getMessages().getString("General.NoClan")));
			pPlayer.sendMessage(
					new TextComponent(ClansMain.getInstance().getMessages().getString("General.HowToCreateAClan")));
			return;
		}
		pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Activated"));
		CHAT_MANAGER.addPlayer(pPlayer.getUniqueId());
	}

}
