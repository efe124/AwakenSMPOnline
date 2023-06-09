package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command(name = "list",description = "See a list of your friends!",permission = "awakensmp.command.friend.list")
public class List extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.list.header")));

        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        data.getFriends().forEach(friend -> {
            PlayerData friendData = PlayerDataManager.fetch(friend.getPlayerId());

            player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.list.format")
                    .replace("%name%",friendData.getName())
            )),new TextComponent(" "), ButtonManager.generateModifyButton(friendData.getName()),new TextComponent(" "),ButtonManager.generateInventoryButton(friendData.getName()),new TextComponent(" "),ButtonManager.generateArmorButton(friendData.getName()),new TextComponent(" "),ButtonManager.generateRemoveButton(friendData.getName()));

        });

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.list.footer")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }

    public List(@NotNull String name) {
        super(name);
    }

    public List(@NotNull String name, @NotNull String description, @NotNull String usageMessage, java.util.@NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
