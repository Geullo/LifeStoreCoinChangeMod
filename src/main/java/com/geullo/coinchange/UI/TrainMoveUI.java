package com.geullo.coinchange.UI;

import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrainMoveUI extends GuiScreen {
    private int visible = 0;
    private final String station;
    private float alpha = 1;
    public TrainMoveUI(String station) {
        this.station = station.toLowerCase();
        ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
        ex.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                visible++;
                if (visible>=85) alpha-=1f/29f;
                if (visible+1>=115) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                    return;
                }
            }
        },0,500, TimeUnit.MILLISECONDS);
    }
    @SubscribeEvent
    public void renderNotice(TickEvent.ClientTickEvent e) {
        visible++;
        if (visible>=85) alpha-=1f/30f;
        if (alpha<0) alpha = 0;
        if (visible+1>=4) {
            MinecraftForge.EVENT_BUS.unregister(this);
            return;
        }
    }
    @SubscribeEvent
    public void renderNotice(RenderGameOverlayEvent e) {
        if (e.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            GlStateManager.disableDepth();
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/station/motion/"+station+".png"));
            if (visible+1>=115) {
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            Render.setColor(0xffffff,(int) (alpha*0xff));
            Render.drawRenderRect(0,0,e.getResolution().getScaledWidth(),e.getResolution().getScaledHeight());
            GlStateManager.enableDepth();
        }
    }
    public static enum Stations {
        berenty,
        equele,
        haetmuri,
        haldesu,
        piosa,
        popo
        ;
    }
    private int fade(int vi) {
        return (vi/100)*0xff;
    }
}
