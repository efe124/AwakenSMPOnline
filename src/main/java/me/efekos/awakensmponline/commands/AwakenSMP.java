package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.commands.awakensmp.Deadplayers;
import me.efekos.awakensmponline.commands.awakensmp.Reloadconfig;
import me.efekos.awakensmponline.commands.awakensmp.Revive;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "awakensmp",description = "All awakensmp commands")
public class AwakenSMP extends CoreCommand {
    public AwakenSMP(@NotNull String name) {
        super(name);
    }

    public AwakenSMP(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<?extends SubCommand>> classes = new ArrayList<>();
        classes.add(Revive.class);
        classes.add(Deadplayers.class);
        classes.add(Reloadconfig.class);
        return classes;
    }

    @Override
    public void renderHelpList(CommandSender sender, ArrayList<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.help.header")));
        subInstances.forEach(subCommand -> {
            sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.help.format")
                    .replace("%syntax%",subCommand.getUsage())
                    .replace("%description%",subCommand.getDescription())
            ));
        });
        sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.help.footer")));
    }
}
