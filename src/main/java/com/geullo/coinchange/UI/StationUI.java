package com.geullo.coinchange.UI;

import com.geullo.coinchange.Events.GuiOpen;
import com.geullo.coinchange.Packet;
import com.geullo.coinchange.PacketList;
import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Reference;
import com.geullo.coinchange.util.Sound;
import com.geullo.coinchange.util.SoundEffect;
import com.geullo.coinchange.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StationUI extends GuiScreen {
    public HashMap<String,Station> stationMap = new HashMap();
    public List<Station> stations = new ArrayList<>();
    private double[] bgPos = new double[2], bgSize = new double[2],stationPosX,stationPosY,stationSize=new double[2];
    private boolean mouseGrabbed = false;

    public StationUI() {
        stationMap.put("햇무리",new Station("햇무리","haetmuri"));
        stations.add(stationMap.get("햇무리"));
        stationMap.put("포포",new Station("포포","popo"));
        stations.add(stationMap.get("포포"));
        stationMap.put("할데스",new Station("할데스","haldesu"));
        stations.add(stationMap.get("할데스"));
        stationMap.put("에클레",new Station("에클레","equele"));
        stations.add(stationMap.get("에클레"));
        stationMap.put("피오사",new Station("피오사","piosa"));
        stations.add(stationMap.get("피오사"));
        stationMap.put("베렌티",new Station("베렌티","berenty"));
        stations.add(stationMap.get("베렌티"));
        stationPosX = new double[stations.size()];
        stationPosY = new double[stations.size()];
    }

    @Override
    public void initGui() {
        bgSize[0] = width/1.35;
        bgSize[1] = height/1.695;
        bgPos[0] = (width/2d)-(bgSize[0]/2);
        bgPos[1] = (height/2d)-(bgSize[1]/2);
        stationSize[0] = bgSize[0]/4.5/1.65;
        stationSize[1] = bgSize[1]/1.65;
        double gap = (stationSize[0]/4/2);
        stationPosX[0] = bgPos[0]+((bgSize[0]/2)-(((stationSize[0]* stations.size())+(gap*(stations.size()-1)))/2));
        stationPosY[0] = bgPos[1]+(stationSize[1]/1.85);
        for (int i = 1; i< stations.size(); i++) {
            stationPosX[i] = stationPosX[i-1]+stationSize[0]+gap;
            stationPosY[i] = stationPosY[0];
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            if (mc.currentScreen != null) drawDefaultBackground();
            if (!mouseGrabbed) {
                Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
                mouseGrabbed = true;
            }
        } catch (Exception e) {e.printStackTrace();}
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/station/background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);
        Station[] sts = stations.toArray(new Station[0]);
        for (int i = 0; i< stations.size(); i+=2) {
            if (i+1< stations.size()) drawStation(i+1,mouseX,mouseY,sts[i+1]);
            drawStation(i,mouseX,mouseY,sts[i]);
        }
        for (int i = 0; i< stations.size(); i+=2) {
            if (Utils.mouseBetweenIcon(mouseX, mouseY, stationPosX[i], stationPosY[i], stationSize[0], stationSize[1])&&!sts[i].locked) {
                List<String> st = new ArrayList<>();
                st.add("§e§l" + sts[i].name + "마을§f 역으로 이동 합니다.");
                Render.drawTooltip(st, mouseX, mouseY);
            }
            int j = i+1;
            if (j<stations.size())
                if (Utils.mouseBetweenIcon(mouseX, mouseY, stationPosX[j], stationPosY[j], stationSize[0], stationSize[1])&&!sts[j].locked) {
                    List<String> st = new ArrayList<>();
                    st.add("§e§l" + sts[j].name + "마을§f 역으로 이동 합니다.");
                    Render.drawTooltip(st, mouseX, mouseY);
            }
        }
    }
    private void drawStation(int idx,int mouseX,int mouseY,Station station) {
        if (station.isLocked()) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/station/lock/" + station.engName + ".png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(stationPosX[idx],stationPosY[idx],stationSize[0],stationSize[1]);
        }
        else {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/station/"+station.engName+".png"));
            if (Utils.mouseBetweenIcon(mouseX,mouseY,stationPosX[idx],stationPosY[idx],stationSize[0],stationSize[1])) Render.setColor(0xff919191);
            else Render.setColor(0xffffffff);
            Render.drawTexturedRect(stationPosX[idx],stationPosY[idx],stationSize[0],stationSize[1]);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Station[] sts = stations.toArray(new Station[0]);
        for (int i = 0; i< stations.size(); i+=2) {
            if (Utils.mouseBetweenIcon(mouseX,mouseY,stationPosX[i],stationPosY[i],stationSize[0],stationSize[1])&&!sts[i].locked) {
                Packet.sendMessage(PacketList.TELEPORT_TRAIN.recogCode+sts[i].name);
                Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                mc.displayGuiScreen(null);
                GuiOpen.trainSound = Sound.getSound(SoundEffect.STATION_MOVE, SoundCategory.PLAYERS,0.85f,1f);
                if (GuiOpen.ui!=null) {
                    MinecraftForge.EVENT_BUS.unregister(GuiOpen.ui);
                    GuiOpen.ui = null;
                    Minecraft.getMinecraft().getSoundHandler().stopSound(GuiOpen.trainSound);
                }
                GuiOpen.ui = new TrainMoveUI(sts[i].engName);
                Minecraft.getMinecraft().displayGuiScreen(new TrainMoveUI2(sts[i].engName));
                Minecraft.getMinecraft().getSoundHandler().playSound(GuiOpen.trainSound);
            }
            int k = i+1;
            if (k< stations.size()) {
                if (Utils.mouseBetweenIcon(mouseX,mouseY,stationPosX[k],stationPosY[k],stationSize[0],stationSize[1])&&!sts[k].locked) {
                    Packet.sendMessage(PacketList.TELEPORT_TRAIN.recogCode+sts[k].name);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                    mc.displayGuiScreen(null);
                    GuiOpen.trainSound = Sound.getSound(SoundEffect.STATION_MOVE, SoundCategory.PLAYERS,0.85f,1f);
                    if (GuiOpen.ui!=null) {
                        MinecraftForge.EVENT_BUS.unregister(GuiOpen.ui);
                        GuiOpen.ui = null;
                        Minecraft.getMinecraft().getSoundHandler().stopSound(GuiOpen.trainSound);
                    }
                    GuiOpen.ui = new TrainMoveUI(sts[k].engName);
                    Minecraft.getMinecraft().displayGuiScreen(new TrainMoveUI2(sts[k].engName));
                    Minecraft.getMinecraft().getSoundHandler().playSound(GuiOpen.trainSound);
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) Minecraft.getMinecraft().displayGuiScreen(null);
    }

    public static class Station {
        private String name;
        private String engName;
        private boolean locked;

        public Station(String name,String engName) {
            this.name = name;
            this.engName = engName;
        }
        public String getEngName() {
            return engName;
        }

        public String getName() {
            return name;
        }

        public void setEngName(String engName) {
            this.engName = engName;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isLocked() {return locked;}
        public void setLocked(boolean locked) {
            if (name.equalsIgnoreCase("햇무리")) this.locked = false;
            else this.locked = locked;
        }

    }
}
