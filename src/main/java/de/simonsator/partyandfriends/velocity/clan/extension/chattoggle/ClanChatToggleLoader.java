package de.simonsator.partyandfriends.velocity.clan.extension.chattoggle;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import de.simonsator.partyandfriends.velocity.VelocityExtensionLoadingInfo;
import de.simonsator.partyandfriends.velocity.main.PAFPlugin;

import java.nio.file.Path;

@Plugin(id = "clanchattoggle", name = "ClanChatToggle", version = "1.0.2-RELEASE",
		url = "https://www.spigotmc.org/resources/clan-chat-toggle-for-clans.47498/",
		description = "Adds the subcommand /clan toggle."
		, authors = {"JT122406", "Simonsator"}, dependencies = {@Dependency(id = "partyandfriends")})
public class ClanChatToggleLoader {

	private final Path folder;


	@Inject
	public ClanChatToggleLoader(@DataDirectory final Path pFolder) {
		folder = pFolder;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		PAFPlugin.loadExtension(new VelocityExtensionLoadingInfo(new ChatTogglePlugin(folder),
				"clanchattoggle",
				"ClanChatToggle",
				"1.0.2-RELEASE", "JT122406, Simonsator"));
	}

}
