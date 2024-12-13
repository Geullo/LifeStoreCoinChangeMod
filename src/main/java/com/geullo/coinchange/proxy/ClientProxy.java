package com.geullo.coinchange.proxy;

import com.geullo.coinchange.Events.GuiOpen;
import com.geullo.coinchange.Packet;
import com.geullo.coinchange.util.OnlineImageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.util.List;

public class ClientProxy extends CommonProxy{
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("lifeEtc");

    public static File liveSkinFolder = new File(Minecraft.getMinecraft().mcDataDir, "resources/SKIN");
    @Override
    public void preInit() throws Exception {
    }

    @Override
    public void init() {
        List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "resourcePackList");
        IResourcePack pack = new OnlineImageResource(liveSkinFolder);
        defaultResourcePacks.add(pack);
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(pack);
        FMLCommonHandler.instance().bus().register(new GuiOpen());
        NETWORK.registerMessage(Packet.Handle.class, Packet.class, 0, Side.CLIENT);
    }

    @Override
    public void postInit() {
    }

}
