package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.classes.Request;
import me.efekos.awakensmponline.classes.RequestType;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestsJSON;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class accept extends SubCommand {
    public accept() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "accept";
    }

    /**
     * @return The aliases that can be used for this command. Can be null
     */
    @Override
    public List<String> getAliases() {
        return null;
    }

    /**
     * @return A description of what the subcommand does to be displayed
     */
    @Override
    public String getDescription() {
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-accept");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/friend accept " + cf.getString(p + "id");
    }

    private String a(String key){
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        return ColorTranslator.translateColorCodes(cf.getString(key));
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        Player p = (Player) sender;
        PlayerData pData = PlayerDataManager.getDataFromUniqueId(p.getUniqueId());
        if (args.length == 1){p.sendMessage(a("messages.commands.friend.generic.no-id"));return;}
        Request request = RequestsJSON.getDataFromId(args[1]);
        if(request == null) {p.sendMessage(a("messages.commands.friend.generic.no-real-id"));return;}
        if(request.getType() == RequestType.TEAMMATE){p.sendMessage(a("messages.commands.friend.generic.no-fri-id"));return;}


        Player friendP = p.getServer().getPlayer(request.getFrom());
        PlayerData friendPdata = PlayerDataManager.getDataFromUniqueId(friendP.getUniqueId());
        Friend friend = new Friend();
        friend.setAllowCoords(true);
        friend.setAllowWorld(true);
        friend.setName(friendP.getName());
        friend.setUuid(request.getFrom());
        friend.setWorld(friendP.getWorld().getName());
        friend.setCoords(friendP.getLocation());
        friend.setAllowHealth(true);
        friend.setAllowHunger(true);
        friend.setAllowXP(true);

        Friend friendtoFrom = new Friend();
        friendtoFrom.setAllowCoords(true);
        friendtoFrom.setAllowWorld(true);
        friendtoFrom.setName(p.getName());
        friendtoFrom.setUuid(request.getTo());
        friendtoFrom.setWorld(p.getWorld().getName());
        friendtoFrom.setCoords(p.getLocation());
        friendtoFrom.setAllowXP(true);
        friendtoFrom.setAllowHealth(true);
        friendtoFrom.setAllowHunger(true);

        pData.addFriend(friend);
        friendPdata.addFriend(friendtoFrom);
        PlayerDataManager.update(pData.getPlayerUniqueId(), pData);
        PlayerDataManager.update(friendPdata.getPlayerUniqueId(),friendPdata);
        RequestsJSON.deleteData(request.getId());
        p.sendMessage(a("messages.commands.friend.accept.success").replace("%friend%",friend.getName()));
        friendP.sendMessage(a("messages.commands.friend.accept.accepted").replace("%friend%",p.getName()));
    }

    /**
     * @param player The player who ran the command
     * @param args   The args passed into the command when run
     * @return A list of arguments to be suggested for autocomplete
     */
    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        PlayerDataManager.fetch(player);
        PlayerData data = PlayerDataManager.getDataFromUniqueId(player.getUniqueId());
        List<String> list = new ArrayList<>();
        if(args.length == 2){
            for (Request request : RequestsJSON.getAllData()) { // bütün istekler
                if (request.getType() == RequestType.FRIEND) { // arkadaşlık istekleri
                    if (request.getTo() == Objects.requireNonNull(data).getPlayerUniqueId()) { // player'a gönderilen istekler
                        list.add(request.getId());
                    }
                }
            }
        }
        return list;
    }
}
