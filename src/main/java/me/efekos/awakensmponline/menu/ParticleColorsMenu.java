package me.efekos.awakensmponline.menu;

import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.ParticleColor;
import me.efekos.awakensmponline.data.ParticleOptions;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.commands.translation.TranslateManager;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class ParticleColorsMenu extends Menu {
    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public String getTitle() {
        return TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.title"));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        ParticleOptions options = data.getParticleOptions();

        switch (Objects.requireNonNull(event.getCurrentItem()).getType()){
            case PAPER:
                back();
                break;
            case WHITE_CONCRETE:
            case ORANGE_CONCRETE:
            case MAGENTA_CONCRETE:
            case BLUE_CONCRETE:
            case YELLOW_CONCRETE:
            case LIME_CONCRETE:
            case PINK_CONCRETE:
            case BLACK_CONCRETE:
            case CYAN_CONCRETE:
            case BROWN_CONCRETE:
            case GRAY_CONCRETE:
            case LIGHT_GRAY_CONCRETE:
            case RED_CONCRETE:
            case GREEN_CONCRETE:
            case LIGHT_BLUE_CONCRETE:
            case PURPLE_CONCRETE:
                options.setColor(translateMaterial(event.getCurrentItem().getType()));
                data.setParticleOptions(options);
                PlayerDataManager.update(data.getUuid(),data);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS,100,1);
                refresh();
                break;
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    private ParticleColor translateMaterial(Material material){
        if(material == Material.WHITE_CONCRETE) return ParticleColor.WHITE;
        if(material == Material.ORANGE_CONCRETE) return ParticleColor.ORANGE;
        if(material == Material.MAGENTA_CONCRETE) return ParticleColor.MAGENTA;
        if(material == Material.BLUE_CONCRETE) return ParticleColor.BLUE;
        if(material == Material.YELLOW_CONCRETE) return ParticleColor.YELLOW;
        if(material == Material.LIME_CONCRETE) return ParticleColor.LIME;
        if(material == Material.PINK_CONCRETE) return ParticleColor.PINK;
        if(material == Material.GRAY_CONCRETE) return ParticleColor.GRAY;
        if(material == Material.LIGHT_GRAY_CONCRETE) return ParticleColor.LIGHT_GRAY;
        if(material == Material.CYAN_CONCRETE) return ParticleColor.CYAN;
        if(material == Material.PURPLE_CONCRETE) return ParticleColor.PURPLE;
        if(material == Material.LIGHT_BLUE_CONCRETE) return ParticleColor.LIGHT_BLUE;
        if(material == Material.BROWN_CONCRETE) return ParticleColor.BROWN;
        if(material == Material.GREEN_CONCRETE) return ParticleColor.GREEN;
        if(material == Material.RED_CONCRETE) return ParticleColor.RED;
        if(material == Material.BLACK_CONCRETE) return ParticleColor.BLACK;
        return null;
    }

    @Override
    public void fill() {
        inventory.setItem(0,createItem(Material.WHITE_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.white"))));
        inventory.setItem(1,createItem(Material.ORANGE_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.orange"))));
        inventory.setItem(2,createItem(Material.MAGENTA_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.magenta"))));
        inventory.setItem(3,createItem(Material.LIGHT_BLUE_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.light_blue"))));
        inventory.setItem(4,createItem(Material.YELLOW_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.yellow"))));
        inventory.setItem(5,createItem(Material.LIME_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.lime"))));
        inventory.setItem(6,createItem(Material.PINK_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.pink"))));
        inventory.setItem(7,createItem(Material.GRAY_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.gray"))));
        inventory.setItem(8,createItem(Material.LIGHT_GRAY_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.light_gray"))));
        inventory.setItem(9,createItem(Material.CYAN_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.cyan"))));
        inventory.setItem(10,createItem(Material.PURPLE_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.purple"))));
        inventory.setItem(11,createItem(Material.BLUE_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.blue"))));
        inventory.setItem(12,createItem(Material.BROWN_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.brown"))));
        inventory.setItem(13,createItem(Material.GREEN_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.green"))));
        inventory.setItem(14,createItem(Material.RED_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.red"))));
        inventory.setItem(15,createItem(Material.BLACK_CONCRETE,TranslateManager.translateColors(LangConfig.get("menus.particle_options_color.colors.black"))));


        PlayerData data = PlayerDataManager.fetch(owner.getUniqueId());
        ParticleOptions options = data.getParticleOptions();


        for (int i = 0; i < 16; i++) {
            ItemStack item = inventory.getItem(i);
            assert item != null;
            ItemMeta meta = item.getItemMeta();

            boolean isThis = options.getColor() == translateMaterial(item.getType());

            assert meta != null;
            meta.setLore(Arrays.asList(" ", TranslateManager.translateColors(LangConfig.get(isThis ? "menus.particle_options.selected" : "menus.particle_options.unselected"))));
            if(isThis) meta.addEnchant(Enchantment.MENDING,1,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }

        inventory.setItem(26,createItem(Material.PAPER,TranslateManager.translateColors(LangConfig.get("menus.buttons.back"))));

        fillEmptyWith(createItem(Material.BLACK_STAINED_GLASS_PANE," "));
    }

    public ParticleColorsMenu(MenuData data) {
        super(data);
    }
}
