package com.geullo.coinchange.UI;

import com.geullo.coinchange.Packet;
import com.geullo.coinchange.PacketList;
import com.geullo.coinchange.Render;
import com.geullo.coinchange.proxy.ClientProxy;
import com.geullo.coinchange.util.Reference;
import com.geullo.coinchange.util.Sound;
import com.geullo.coinchange.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavorCouponUI extends GuiScreen {
    private double[] bgPos = new double[2], bgSize = new double[2],btnPos = new double[6],btnSize = new double[2],
            playerSize = new double[2],playerPosX,playerPosY,selectPlayerPos = new double[2];
    public String selPlayer = "",user = "";
    private List<String> players = new ArrayList<>();
    public FavorCouponUI(String user)  {
        File skin = ClientProxy.liveSkinFolder;
        Packet.sendMessage(PacketList.GET_FAKE_NAME.recogCode);
        try {
            players.add("d7297");
            players.add("Daju_");
            players.add("samsik23");
            players.add("RuTaeY");
            players.add("Huchu95");
            players.add("KonG7");
            players.add("Seoneng");
            players.add("Noonkkob");
            playerPosX = new double[players.size()];
            playerPosY = new double[players.size()];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        bgSize[0] = width/1.75d;
        bgSize[1] = height/1.83d;
        bgPos[0] = (width/2d) - bgSize[0]/2d;
        bgPos[1] = (height/2d) - bgSize[1]/2d;
        btnSize[0] = bgSize[0]/2.25d/1.5;
        btnSize[1] = bgSize[1]/4.25d/1.5;
        double gap = btnSize[0]/2,gapy;
        btnPos[0] = bgPos[0]+((bgSize[0]/2) - ((btnSize[0]*2)+gap)/2);
        btnPos[1] = bgPos[1]+((bgSize[1]/1.3)-(btnSize[1]/2));
        btnPos[2] = bgPos[0]+((bgSize[0]/2) + (gap/2));
        btnPos[3] = btnPos[1];
        btnPos[4] = bgPos[0]+((bgSize[0]/2) - (btnSize[0]/2));
        btnPos[5] = btnPos[1];
        playerSize[0] = bgSize[0]/5/1.85;
        playerSize[1] = bgSize[1]/3.5/1.45;
        gap = playerSize[0]/3;
        gapy = playerSize[1]/3.25;
        double divSize = players.size()/2d;
        playerPosX[0] = bgPos[0]+(bgSize[0]/2) - (((playerSize[0]*divSize)+(gap*(divSize-1)))/2);
        playerPosY[0] = bgPos[1]+(bgSize[1]/1.7) - (((playerSize[1]*(players.size()/divSize))+(gapy*((players.size()/divSize)-1)))/2);
        for (int i=1;i<players.size();i++) {
            if (i%(int) divSize==0) {
                playerPosX[i] = playerPosX[0];
                playerPosY[i] = playerPosY[0]+playerSize[1]+gapy;
            }
            else {
                playerPosX[i] = playerPosX[i-1]+playerSize[0]+gap;
                playerPosY[i] = playerPosY[i-1];
            }
        }
        selectPlayerPos[0] = bgPos[0]+((bgSize[0]/2) - (playerSize[0]/2));
        selectPlayerPos[1] = bgPos[1]+((bgSize[1]/2) - (playerSize[1]/2.12));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/favor_coupon/background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);
        if (selPlayer.equals("")) {
            for (int i=0;i<players.size();i++) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/skins/" + players.get(i) + ".png"));
                if (Utils.mouseBetweenIcon(mouseX,mouseY,playerPosX[i],playerPosY[i],playerSize[0],playerSize[1])) Render.setColor(0xff7D7D7D);
                else Render.setColor(0xffffffff);
                Render.drawTexturedRect(playerPosX[i], playerPosY[i],playerSize[0],playerSize[1],0.125,0.125,0.25,0.25);
                Render.drawTexturedRect(playerPosX[i], playerPosY[i],playerSize[0],playerSize[1],0.625,0.125,0.75,0.25);
            }
        }
        else {
            if (getKrNm().equals(user)) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/skins/" + selPlayer + ".png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1],playerSize[0],playerSize[1],0.125,0.125,0.25,0.25);
                Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1],playerSize[0],playerSize[1],0.625,0.125,0.75,0.25);
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/favor_coupon/favor_btn.png"));
                Render.setColor(0xffffffff);
                if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[4], btnPos[5], btnSize[0], btnSize[1])) {
                    List<String> st = new ArrayList<>();
                    Render.drawTexturedRect(btnPos[4], btnPos[5], btnSize[0], btnSize[1], 0.5d, 0d, 1d, 0.5d);
                    st.add("본인의 호감도를 1점 올립니다.");
                    Render.drawTooltip(st, mouseX, mouseY);
                } else
                    Render.drawTexturedRect(btnPos[4], btnPos[5], btnSize[0], btnSize[1], 0d, 0d, 0.5d, 0.5d);
            }
            else {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/skins/" + selPlayer + ".png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1],playerSize[0],playerSize[1],0.125,0.125,0.25,0.25);
                Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1],playerSize[0],playerSize[1],0.625,0.125,0.75,0.25);
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/favor_coupon/favor_btn.png"));
                Render.setColor(0xffffffff);
                List<String> st = new ArrayList<>();
                if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[0], btnPos[1], btnSize[0], btnSize[1])) {
                    Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1], 0.5d, 0d, 1d, 0.5d);
                    st.add("§e§l" + getKrNm() + "님§f의 호감도를 4점 올립니다.");
                } else
                    Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1], 0d, 0d, 0.5d, 0.5d);
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "ui/favor_coupon/favor_btn.png"));
                if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[2], btnPos[3], btnSize[0], btnSize[1])) {
                    Render.drawTexturedRect(btnPos[2], btnPos[3], btnSize[0], btnSize[1], 0.5d, 0.5d, 1d, 1d);
                    st.clear();
                    st.add("§e§l" + getKrNm() + "님§f의 호감도를 4점 내립니다.");
                }
                else Render.drawTexturedRect(btnPos[2], btnPos[3], btnSize[0], btnSize[1], 0d, 0.5d, 0.5d, 1d);

                if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[0], btnPos[1], btnSize[0], btnSize[1]) || Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[2], btnPos[3], btnSize[0], btnSize[1]))
                    Render.drawTooltip(st, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            if (selPlayer.equals("")) mc.displayGuiScreen(null);
            else selPlayer = "";
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (selPlayer.equals("")) {
            for (int i=0;i<players.size();i++)
                if (Utils.mouseBetweenIcon(mouseX,mouseY,playerPosX[i],playerPosY[i],playerSize[0],playerSize[1])) {
                    selPlayer = players.get(i);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                }
        }
        else {
            if (getKrNm().equals(user)) {
                if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[4], btnPos[5], btnSize[0], btnSize[1])) {
                    Packet.sendMessage(PacketList.UP_FAVOR.recogCode + "self");
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                }
            }
            else {
                if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[0], btnPos[1], btnSize[0], btnSize[1])) {
                    Packet.sendMessage(PacketList.UP_FAVOR.recogCode + getKrNm());
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                } else if (Utils.mouseBetweenIcon(mouseX, mouseY, btnPos[2], btnPos[3], btnSize[0], btnSize[1])) {
                    Packet.sendMessage(PacketList.DOWN_FAVOR.recogCode + getKrNm());
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                }
            }
        }
    }

    private String getKrNm() {
        switch (selPlayer) {
            case "d7297":
                return "양띵";
            case "Daju_":
                return "다주";
            case "RuTaeY":
                return "루태";
            case "samsik23":
                return "삼식";
            case "Noonkkob":
                return "눈꽃";
            case "Huchu95":
                return "후추";
            case "Seoneng":
                return "서넹";
            case "KonG7":
                return "콩콩";
            default:
                return selPlayer;
        }
    }

}
