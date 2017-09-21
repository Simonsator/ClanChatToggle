package de.simonsator.partyandfriends.clan.extension.chattoggle;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.clan.commands.ClanCommands;
import de.simonsator.partyandfriends.clan.extension.chattoggle.commands.ToggleSubCommand;
import de.simonsator.partyandfriends.clan.extension.chattoggle.configuration.ClanChatToggleConfig;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;

public class ChatTogglePlugin extends PAFExtension {
	@Override
	public void onEnable() {
		try {
			Configuration config = new ClanChatToggleConfig(new File(getConfigFolder(), "config.yml")).getCreatedConfiguration();
			ChatManager chatManager = new ChatManager();
			ClanCommands.getInstance().addCommand(new ToggleSubCommand(config.getStringList("Names").toArray(new String[0]), config.getString("Permission"),
					config.getInt("Priority"), config.getString("Messages.Help"), chatManager, config));
			getProxy().getPluginManager().registerListener(this, chatManager);
			registerAsExtension();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
