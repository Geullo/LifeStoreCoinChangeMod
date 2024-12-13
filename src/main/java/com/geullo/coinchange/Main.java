package com.geullo.coinchange;

import com.geullo.coinchange.proxy.CommonProxy;
import com.geullo.coinchange.util.Reference;
import com.geullo.coinchange.util.SoundEffect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME)
public class Main {
    @Instance
    public static Main instacne;

    @SidedProxy(clientSide = Reference.CLIENTPROXY,serverSide = Reference.COMMONPROXY)
    public static CommonProxy proxy;
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            SoundEffect.registerSounds(event.getRegistry());
        }

    }
}
