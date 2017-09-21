package de.simonsator.partyandfriends.clan.extension.chattoggle.configuration;

import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

public class ClanChatToggleConfig extends ConfigurationCreator {
	public ClanChatToggleConfig(File file) throws IOException {
		super(file);
		readFile();
		loadDefaultValues();
		saveFile();
		process(configuration);
	}

	private void loadDefaultValues() {
		set("Names", "toggle", "toggle-msg");
		set("Permission", "");
		set("Priority", 1000);
		set("Messages.Activated",
				"&7From now on all you write into the chat will be automatically written to the clan chat");
		set("Messages.Disabled",
				"&7From now on you can write again normal into the chat");
		set("Messages.Help",
				"&8/&5clan toggle &8- &7Toggles if you msg directly into the clan chat");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = (new ClanChatToggleConfig(FILE)).getCreatedConfiguration();
	}

}
