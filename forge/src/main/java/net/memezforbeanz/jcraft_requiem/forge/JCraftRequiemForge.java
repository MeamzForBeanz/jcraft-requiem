package net.memezforbeanz.jcraft_requiem.forge;

import net.memezforbeanz.jcraft_requiem.JCraftRequiem;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JCraftRequiem.MOD_ID)
public final class JCraftRequiemForge {
    public JCraftRequiemForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(JCraftRequiem.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        JCraftRequiem.init();
    }
}
