package de.simonsator.partyandfriends.velocity.clan.extension.chattoggle;

import de.simonsator.partyandfriends.velocity.api.PAFExtension;
import de.simonsator.partyandfriends.velocity.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.velocity.clan.commands.ClanCommands;
import de.simonsator.partyandfriends.velocity.clan.extension.chattoggle.commands.ToggleSubCommand;
import de.simonsator.partyandfriends.velocity.clan.extension.chattoggle.configuration.ClanChatToggleConfig;
import de.simonsator.partyandfriends.velocity.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class ChatTogglePlugin extends PAFExtension {
	public ChatTogglePlugin(Path folder) {
		super(folder);
	}

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

	@Override
	public String getName() {
		return "ClanChatToggle";
	}
}
