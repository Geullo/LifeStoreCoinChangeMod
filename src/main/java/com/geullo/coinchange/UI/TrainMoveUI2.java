package com.geullo.coinchange.UI;

import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrainMoveUI2 extends GuiScreen {
    private int visible = 0, a=0;
    private final String station;
    private float alpha = 1;
    private ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    public TrainMoveUI2(String station) {
        this.station = station.toLowerCase();
        ex.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                a++;
                if (visible>4) {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    ex.shutdown();
                    return;
                }
                else {
                    visible++;
                }
            }
        },0,500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (visible>4) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
        else {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/station/motion/"+station+".png"));
            Render.setColor(0xffffffff);
        }
        Render.drawTexturedRect(0,0,width,height);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/station/motion/"+visible+".png"));
        Render.setColor(0xD6ffffff);
        Render.drawTexturedRect((511d/1280d)*width,(645d/720d)*height,(258d/1280d)*width,(31d/720d)*height);
    }

    @Override
    public void onGuiClosed() {
        Render.deleteTexture(new ResourceLocation(Reference.MOD_ID,"ui/station/motion/"+station+".png"));
        ex.shutdown();
        super.onGuiClosed();
    }

    public enum Stations {
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
