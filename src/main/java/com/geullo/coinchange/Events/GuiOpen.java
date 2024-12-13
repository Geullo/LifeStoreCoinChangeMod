package com.geullo.coinchange.Events;

import com.geullo.coinchange.UI.*;
import com.geullo.coinchange.util.Sound;
import com.geullo.coinchange.util.SoundEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.*;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiOpen {
    private ISound sound = Sound.getSound(SoundEffect.MAIN_MENU, SoundCategory.RECORDS,2.25f,1f);
    public static TrainMoveUI ui;
    public static ISound trainSound = Sound.getSound(SoundEffect.STATION_MOVE, SoundCategory.PLAYERS,0.85f,1f);
    public GuiOpen() {}
    @SubscribeEvent
    public void onGui(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiMainMenu) e.setGui(new MainMenu());
        else if (e.getGui() instanceof GuiIngameMenu) e.setGui(new PauseMenu());
    }
    @SubscribeEvent
    public void onGui(PlayerInteractEvent.RightClickItem e) {
//        if (e.getItemStack().getItem().getRegistryName().toString().contains("favor_charm")) {
//            Minecraft.getMinecraft().displayGuiScreen(new FavorCharmUI());
//        }
    }
    @SubscribeEvent
    public void onBackgroundMusic(TickEvent.ClientTickEvent e) {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;
        if (screen!=null&&((screen instanceof MainMenu)|| (screen instanceof GuiOptions)|| (screen instanceof GuiVideoSettings)|| (screen instanceof GuiControls)|| (screen instanceof GuiScreenOptionsSounds)|| (screen instanceof GuiCustomizeSkin)|| (screen instanceof GuiScreenResourcePacks)|| (screen instanceof GuiLanguage)|| (screen instanceof GuiSnooper)|| (screen instanceof GuiScreenServerList)|| (screen instanceof GuiMultiplayer)|| (screen instanceof ScreenChatOptions))&&Minecraft.getMinecraft().world==null) {
            if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound)) {
                Minecraft.getMinecraft().getSoundHandler().stopSounds();
                Minecraft.getMinecraft().getSoundHandler().playSound(sound);
            }
        }
        else if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound)) Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
    }
}
