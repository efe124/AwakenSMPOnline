package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.Friend;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "teleport",description = "Teleport to your friends!",permission = "awakensmp.command.friend.teleport")
public class Teleport extends SubCommand {
    public Teleport(@NotNull String name) {
        super(name);
    }

    public Teleport(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMP.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new FriendArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        Friend friend = data.getFriend(args[0]);

        if(friend==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-friend").replace("%player%",args[0])));
            return;
        }
        if(!friend.getModifications().isTeleportAllowed()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-allowed").replace("%player%",friend.getLastName())));
            return;
        }
        OfflinePlayer offlineFriendP = Bukkit.getOfflinePlayer(friend.getPlayerId());
        if(!offlineFriendP.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-online").replace("%player%", friend.getLastName())));
            return;
        }
        Player friendP = offlineFriendP.getPlayer();

        player.teleport(friendP.getLocation());
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.teleport.success").replace("%player%", friendP.getName())));
        friendP.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.teleport.hey").replace("%player%",friendP.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}