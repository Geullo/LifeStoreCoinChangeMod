package com.geullo.coinchange.UI;

import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Reference;
import com.geullo.coinchange.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class MainMenu extends GuiMainMenu {
    private double[] logoSize = new double[2], logoPos = new double[2],btnSize = new double[2],btnPos = new double[2];
    private int frame = 0;
    private int logoClickCnt = 0;
    private int copyRightWidth;
    private boolean mouseGrabbed = false;

    @Override
    public void initGui() {
        logoSize[0] = width/10d/2d;
        logoSize[1] = logoSize[0];
        logoPos[0] = logoSize[0]/4d/2;
        logoPos[1] = height - logoSize[1]-logoPos[0];
        btnSize[0] = logoSize[0];
        btnSize[1] = logoSize[1];
        btnPos[0] = width-btnSize[0]-logoPos[0];
        btnPos[1] = height-btnSize[1]-1;
        copyRightWidth = (int) (width-fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!")-4-btnSize[0]);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!mouseGrabbed) {
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            mouseGrabbed = true;
        }
        drawUI(mouseX,mouseY);
        frame = frame+2>=149?0:frame+1;
    }
    public void drawUI(int mouseX,int mouseY) {
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/mainmenu/bg_"+frame+".png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(0,0,width,height);

        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/mainmenu/setting.png"));
        if (Utils.mouseBetweenIcon(mouseX,mouseY,btnPos[0],btnPos[1],btnSize[0],btnSize[1])) Render.setColor(0xff6c6c6c);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(btnPos[0],btnPos[1],btnSize[0],btnSize[1]);

        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/mainmenu/logo.png"));
        Render.setColor(0xB3ffffff);
        Render.drawTexturedRect(logoPos[0], logoPos[1], logoSize[0], logoSize[1]);
        this.drawString(fontRenderer, "Copyright Mojang AB. Do not distribute!", copyRightWidth, this.height - 10, -1);
    }

    @Override
    public void updateScreen() {
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (Utils.mouseBetweenIcon(mouseX,mouseY,btnPos[0],btnPos[1],btnSize[0],btnSize[1])) Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(this,Minecraft.getMinecraft().gameSettings));
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,logoPos[0],logoPos[1],logoSize[0],logoSize[1])) {
            logoClickCnt++;
            if (logoClickCnt==5) {
                logoClickCnt = 0;
                Minecraft.getMinecraft().displayGuiScreen(new GuiWorldSelection(this));
            }
        }
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,0,0,width,height))
            Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(this));
    }

    @Override
    public void onGuiClosed() {
        for (int i=0;i<150;i++) {
            Render.deleteTexture(new ResourceLocation(Reference.MOD_ID,"bg_"+i));
        }
        Render.deleteTexture(new ResourceLocation(Reference.MOD_ID,"ui/mainmenu/logo.png"));
        Render.deleteTexture(new ResourceLocation(Reference.MOD_ID,"ui/mainmenu/setting.png"));
    }
}
