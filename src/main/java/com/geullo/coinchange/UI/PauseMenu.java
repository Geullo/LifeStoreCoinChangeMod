package com.geullo.coinchange.UI;

import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class PauseMenu extends GuiScreen {

    private double[] logoPos = new double[2], logoSize = new double[2],btnSize = new double[2];

    @Override
    public void initGui() {
        buttonList.clear();
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
        logoSize[0] = (width)/ 3.15d;
        logoSize[1] = (height) / 3.15d;
        if (sc.getScaledWidth()*sc.getScaleFactor()>=1280) {
            logoSize[0] = logoSize[0]/1.33d;
            logoSize[1] = logoSize[1]/1.45d;
        }
        logoPos[0] = width/2d - logoSize[0]/2;
        logoPos[1] = (int) (height / 2.5 + 24 + -16) - (logoSize[1]/3.5/2) - logoSize[1];

        buttonList.add(new GuiButton(1, width / 2 + 2, (int) (height / 2.5 + 48 + -16), 110,20,"연결 끊기"));

        buttonList.add(new GuiButton(4, width / 2 - 112, (int) (height / 2.5 + 24 + -16), 110,20,"§f§l인생상회§f로 돌아가기"));
        buttonList.add(new GuiButton(0, width / 2 - 112, (int) (height / 2.5 + 48 + -16), 110, 20, "설정"));
        buttonList.add(new GuiButton(6, width / 2 + 2, (int) (height / 2.5 + 24 + -16), 110, 20, I18n.format("gui.stats")));
    }
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.isConnectedToRealms();
                button.enabled = false;
                this.mc.world.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                }

            case 2:
            case 3:
            default:
                break;
            case 4:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            case 6:
                if (this.mc.player != null)
                    this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"ui/content_logo.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(logoPos[0],logoPos[1],logoSize[0],logoSize[1]);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
