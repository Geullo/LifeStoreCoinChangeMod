package com.geullo.coinchange.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;


@SuppressWarnings("WeakerAccess")
public class SoundEffect {
    public static SoundEvent STATION_MOVE;
    public static SoundEvent MAIN_MENU;



    public static void registerSounds(IForgeRegistry<SoundEvent> e){
        STATION_MOVE = registerSound("station.move",e);
        MAIN_MENU = registerSound("mainmenu",e);
    }

    private static SoundEvent registerSound(String soundName, IForgeRegistry e){
        final ResourceLocation soundId = new ResourceLocation(Reference.MOD_ID,soundName);
        SoundEvent soundEvent = new SoundEvent(soundId).setRegistryName(soundName);
        e.register(soundEvent);
        return soundEvent;
    }
}
