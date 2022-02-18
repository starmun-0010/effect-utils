package xyz.starmun.effectutils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("effectutils")
public class EffectUtils {
    public EffectUtils() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommandEvent(RegisterCommandsEvent event) {
        EffectsCommand.register(event.getDispatcher());
    }
}
