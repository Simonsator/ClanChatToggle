package de.simonsator.partyandfriends.clan.extension.chattoggle;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.clan.commands.ClanCommands;
import de.simonsator.partyandfriends.clan.extension.chattoggle.commands.ToggleSubCommand;
import de.simonsator.partyandfriends.clan.extension.chattoggle.configuration.ClanChatToggleConfig;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

public class ChatTogglePlugin extends PAFExtension {
	@Override
	public void onEnable() {
		try {
			ConfigurationCreator config = new ClanChatToggleConfig(new File(getConfigFolder(), "config.yml"), this);
			ChatManager chatManager = new ChatManager(this);
			ClanCommands.getInstance().addCommand(new ToggleSubCommand(config.getStringList("Names").toArray(new String[0]), config.getString("Permission"),
					config.getInt("Priority"), config.getString("Messages.Help"), chatManager, config));
			BukkitBungeeAdapter.getInstance().registerListener(chatManager, this);
			registerAsExtension();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
