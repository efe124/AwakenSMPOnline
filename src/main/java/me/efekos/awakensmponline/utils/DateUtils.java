package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.simpler.commands.translation.TranslateManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String translateDate(Date date){
        String hour = new SimpleDateFormat("HH").format(date);
        String min = new SimpleDateFormat("mm").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String month = new SimpleDateFormat("MM").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String sec = new SimpleDateFormat("ss").format(date);

        return TranslateManager.translateColors(LangConfig.get("items.tracking_compass.date-format")
                .replace("%hour%",hour)
                .replace("%minute%",min)
                .replace("%day%",day)
                .replace("%month%",month)
                .replace("%year%",year)
                .replace("%second%",sec)
        );
    }
}
