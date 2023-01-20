package de.simonsator.partyandfriends.velocity.clan.extension.chattoggle.commands;

import de.simonsator.partyandfriends.velocity.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.velocity.clan.ClansMain;
import de.simonsator.partyandfriends.velocity.clan.api.ClansManager;
import de.simonsator.partyandfriends.velocity.clan.api.abstractcommands.ClanMemberCommand;
import de.simonsator.partyandfriends.velocity.clan.extension.chattoggle.ChatManager;
import de.simonsator.partyandfriends.velocity.utilities.ConfigurationCreator;

public class ToggleSubCommand extends ClanMemberCommand {
	private final ChatManager CHAT_MANAGER;
	private final ConfigurationCreator CONFIG;

	public ToggleSubCommand(String[] pCommands, String pPermission, int pPriority, String pHelp, ChatManager pChatManager, ConfigurationCreator pConfig) {
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
			pPlayer.sendMessage(PREFIX + ClansMain.getInstance().getMessages().getString("General.NoClan"));
			pPlayer.sendMessage(ClansMain.getInstance().getMessages().getString("General.HowToCreateAClan"));
			return;
		}
		pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Activated"));
		CHAT_MANAGER.addPlayer(pPlayer.getUniqueId());
	}

}
