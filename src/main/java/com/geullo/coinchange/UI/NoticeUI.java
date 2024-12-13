package com.geullo.coinchange.UI;

import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class NoticeUI extends GuiScreen {
    Minecraft mc = Minecraft.getMinecraft();
    ScaledResolution scaledresolution = new ScaledResolution(mc);
    private double[] bgSize = new double[2],bgPos = new double[4],textSize = new double[2],textPos = new double[2];
    public List<String> notice = new ArrayList<>();
    private int displayTime = 1,b=1,visible = 0;
    public int windowWidth = scaledresolution.getScaledWidth();
    public int windowHeigth = scaledresolution.getScaledHeight();
    public int prevWindowWidth = 0;
    public int prevWindowHeigth = 0;

    public NoticeUI(String notice) {
        String c = notice;
        String[] a = c.split("");
        String b = "";
        for (int i = 0 ; i<a.length ; i++ ) {
            if (b.length()%35==0&&i!=0||b.contains("￦n")||b.contains("\\n")) {
                b=b.replace("￦n","");
                b=b.replace("\\n","");
                this.notice.add(b);
                b = "";
            }
            b = b.concat(a[i]);
            b = b.replace("§f","§0");
        }
        if (!b.equals("")) this.notice.add(b);
        initSize(visible,new ScaledResolution(Minecraft.getMinecraft()));
        List<String> list = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(notice, (int) (bgSize[0]*1.25));
        b="";
        for (int i=0;i<list.size();i++) {
            String[] n = list.get(i).split("");
            for (int j = 0;j<n.length;j++) {
                b=b.concat(a[i]);
                if (b.contains("\\n")||b.contains("￦n")) {
                    b=b.replace("\\n","").replace("￦n","");
                    this.notice.add(b);
                    b="";
                }
            }
        }
        initSize(visible,new ScaledResolution(Minecraft.getMinecraft()));
    }

    public void initSize(int visible,ScaledResolution sc) {
        double frame = Minecraft.getMinecraft().gameSettings.limitFramerate;
        prevWindowWidth = windowWidth;
        prevWindowHeigth = windowHeigth;
        windowWidth = sc.getScaledWidth();
        windowHeigth = sc.getScaledHeight();
        bgSize[0] = windowWidth / 2d / 1.25;
        bgSize[1] = windowHeigth / 2.8d / 2d;
        bgPos[0] = (windowWidth / 2d) - (bgSize[0] / 2);
        bgPos[1] = -bgSize[1];
        bgPos[1] = bgPos[1] + (Utils.percent(bgSize[1], Utils.percentPartial(frame/2, visible)));
        textPos = new double[(notice.size() + 1) * 2];
        textSize[0] = bgSize[0] / 4 / 2.8 / 1.35;
        textSize[1] = bgSize[1] / 2 / 1.35 / 1.35;
        for (int i = 0; i < notice.size() + 1; i += 2) {
            textPos[i] = bgPos[0] + (bgSize[0] / 2);
            if (notice.size() == 1)
                textPos[i + 1] = bgPos[1] + ((bgSize[1] / 2.25) - (((textSize[1] / 16.0f) * (notice.size() - i)) / 2.8));
            else {
                textPos[i + 1] = bgPos[1] + ((bgSize[1] / 2.85) - (textSize[1] / 16.0f));
                if (i != 0) textPos[i + 1] = textPos[i - 1] + (textSize[1] / 1.55f);
            }
        }
    }

    @SubscribeEvent
    public void renderNotice(RenderGameOverlayEvent e) {
        if (e.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            displayTime++;
            double frame = Minecraft.getMinecraft().gameSettings.limitFramerate;
            if (displayTime % (frame / (frame/2)) == 0 && displayTime <= frame)
                visible++;
            if (displayTime % (frame / (frame/2)) == 0 && displayTime > frame*3)
                visible--;
            initSize(visible,e.getResolution());
            GlStateManager.pushMatrix();
            mc.renderEngine.bindTexture(new ResourceLocation("coinchange", "alert_bg.png"));
            Render.setColor(0xffffffff);
            Render.drawRenderRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
            for (int i = 0; i < notice.size() + 1; i += 2) {
                if (i != 0) Render.drawString(notice.get(i - 1).replace("&","§"), (float) textPos[i], (float) textPos[i + 1], (int) textSize[0], (int) textSize[1], 1, 0x000000);
                else Render.drawString(notice.get(i).replace("&","§"), (float) textPos[i], (float) textPos[i + 1], (int) textSize[0], (int) textSize[1], 1, 0x000000);
            }
            if (displayTime > 10 && bgPos[1]==-bgSize[1])
                MinecraftForge.EVENT_BUS.unregister(this);
            GlStateManager.popMatrix();

        }
    }
}
