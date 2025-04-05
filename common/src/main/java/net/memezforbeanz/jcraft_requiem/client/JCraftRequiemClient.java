package net.memezforbeanz.jcraft_requiem.client;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import lombok.Getter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.client.JClientConfig;
import net.arna.jcraft.client.JCraftClient;
import net.arna.jcraft.client.gravity.util.GravityChannelClient;
import net.arna.jcraft.client.gui.hud.JCraftAbilityHud;
import net.arna.jcraft.client.net.ClientPacketHandler;
import net.arna.jcraft.client.particle.*;
import net.arna.jcraft.client.registry.JClientEventsRegistry;
import net.arna.jcraft.client.registry.JRenderLayerRegistry;
import net.arna.jcraft.client.renderer.effects.AttackHitboxEffectRenderer;
import net.arna.jcraft.client.renderer.effects.TimeErasePredictionEffectRenderer;
import net.arna.jcraft.client.rendering.RenderHandler;
import net.arna.jcraft.client.rendering.handler.CrimsonShaderHandler;
import net.arna.jcraft.client.rendering.handler.EpitaphVignetteShaderHandler;
import net.arna.jcraft.client.rendering.handler.InversionShaderHandler;
import net.arna.jcraft.client.rendering.handler.ZaWarudoShaderHandler;
import net.arna.jcraft.client.util.ClientEntityHandlerImpl;
import net.arna.jcraft.client.util.TrackedKeyBinding;
import net.arna.jcraft.common.attack.core.MoveInputType;
import net.arna.jcraft.common.entity.stand.StandEntity;
import net.arna.jcraft.common.util.MovementInputType;
import net.arna.jcraft.registry.JParticleTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class JCraftRequiemClient {


    public static void registerParticleSpriteSets() {
      //  ParticleProviderRegistry.register(JRPartParticleTypeRegistry.KCPARTICLE, KCParticle.Factory::new);

    }
}
