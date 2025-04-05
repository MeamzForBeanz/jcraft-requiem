package net.memezforbeanz.jcraft_requiem.fabric.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.arna.jcraft.client.JCraftClient;
import net.arna.jcraft.client.events.JClientEvents;
import net.arna.jcraft.client.gui.hud.EpitaphOverlay;
import net.arna.jcraft.client.registry.JEntityModelLayerRegistry;
import net.arna.jcraft.client.registry.JEntityRendererRegister;
import net.arna.jcraft.client.registry.JItemPropertiesRegistry;
import net.arna.jcraft.client.registry.JModelPredicateProviderRegistry;

import net.fabricmc.api.ClientModInitializer;

import net.memezforbeanz.jcraft_requiem.client.JCraftRequiemClient;
import net.memezforbeanz.jcraft_requiem.registry.JREntityRendererRegister;
import net.minecraft.client.Minecraft;


public final class JCraftRequiemFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        JREntityRendererRegister.registerEntityRenderers(JREntityRendererRegister.RendererData::registerFabric);

        JCraftClient.registerParticleSpriteSets();

    }
}
