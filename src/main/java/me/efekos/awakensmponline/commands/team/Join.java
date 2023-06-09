package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.commands.args.GotRequestUUIDArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.*;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
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
import java.util.UUID;

@Command(name = "join",description = "Join to a team that sent a friend request to you!",permission = "awakensmp.command.team.join")
public class Join extends SubCommand {
    public Join(@NotNull String name) {
        super(name);
    }

    public Join(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new GotRequestUUIDArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        Request req = RequestDataManager.get(UUID.fromString(args[0]));
        if(req==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.join.invalid-req").replace("%id%",args[0])));
            return;
        }
        if(req.getType()!= RequestType.TEAMMATE){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.join.not-team")));
            return;
        }
        if(req.getGetter()!=player.getUniqueId()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.join.not-got")));
        }
        //we have a team req and wanna accept it.

        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());

        if(data.getCurrentTeam()!=null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.already-in-team")));
            return;
        }

        data.setCurrentTeam(req.getSender());
        PlayerDataManager.update(data.getUuid(),data);
        req.setDone(true);
        TeamData team = TeamDataManager.get(req.getSender());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer.isOnline()){
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.team.joined").replace("%player%",player.getName())));
            }
        });

        team.getMembers().add(player.getUniqueId());
        TeamDataManager.update(team.getId(),team);

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.join.done").replace("%team%", team.getDisplayName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
