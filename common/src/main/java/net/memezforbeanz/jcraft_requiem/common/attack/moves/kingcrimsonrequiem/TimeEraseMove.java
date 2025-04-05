package net.memezforbeanz.jcraft_requiem.common.attack.moves.kingcrimsonrequiem;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.NonNull;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.common.attack.core.ctx.MoveContext;
import net.arna.jcraft.common.attack.core.ctx.MoveVariable;
import net.arna.jcraft.common.attack.core.ctx.WeakMoveVariable;
import net.arna.jcraft.common.attack.core.data.MoveType;
import net.arna.jcraft.common.attack.moves.base.AbstractMove;
import net.arna.jcraft.common.component.living.CommonStandComponent;
import net.arna.jcraft.common.entity.PlayerCloneEntity;
import net.arna.jcraft.common.entity.stand.StandEntity;
import net.arna.jcraft.common.network.s2c.ShaderActivationPacket;
import net.arna.jcraft.common.util.JUtils;
import net.arna.jcraft.platform.JComponentPlatformUtils;
import net.arna.jcraft.registry.JSoundRegistry;
import net.arna.jcraft.registry.JStatusRegistry;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.KingCrimsonRequiemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

import java.util.Set;

@Getter
public final class TimeEraseMove extends AbstractMove<TimeEraseMove, KingCrimsonRequiemEntity> {
    public static final MoveVariable<Mob> DOPPELGANGER = new WeakMoveVariable<>(Mob.class);
    private final int erasureDuration;

    public TimeEraseMove(final int cooldown, final int windup, final int duration, final float moveDistance, final int erasureDuration) {
        super(cooldown, windup, duration, moveDistance);
        this.erasureDuration = erasureDuration;
    }

    @Override
    public @NonNull MoveType<TimeEraseMove> getMoveType() {
        return Type.INSTANCE;
    }

    @Override
    public void onInitiate(final KingCrimsonRequiemEntity attacker) {
        super.onInitiate(attacker);

        if (attacker.getUser() instanceof final ServerPlayer player) {
            player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(JSoundRegistry.TIME_ERASE.get()), SoundSource.PLAYERS,
                    attacker.getX(), attacker.getY(), attacker.getZ(), 1, 1, 0));
        }
    }

    @Override
    public void tick(final KingCrimsonRequiemEntity attacker) {
        if (!attacker.hasUser()) return;
        tickTimeErase(attacker);
    }

    @Override
    public @NonNull Set<LivingEntity> perform(final KingCrimsonRequiemEntity attacker, final LivingEntity user, final MoveContext ctx) {
        attacker.setTETime(erasureDuration);

        attacker.setCurrentMove(null);
        Mob doppelganger = null;

        if (user instanceof final ServerPlayer player) {
            // Shader handling
            ShaderActivationPacket.send(player, attacker, 0, 120, ShaderActivationPacket.Type.CRIMSON);

            PlayerCloneEntity clone = new PlayerCloneEntity(attacker.level());
            clone.setShouldRenderForMaster(false);
            clone.disableDrops();
            clone.disableItemExchange();

            // Copy properties
            clone.setMaster(player);

            doppelganger = clone;
        } else if (user instanceof Mob mob) {
            doppelganger = JUtils.mobCloneOf(mob);
        }

        ctx.set(DOPPELGANGER, doppelganger);
        if (doppelganger == null) {
            return Set.of();
        }

        // Copy rotation
        doppelganger.copyPosition(user);
        doppelganger.setYHeadRot(user.getYHeadRot());
        doppelganger.setYBodyRot(user.getVisualRotationYInDegrees());

        // Copy equipment
        doppelganger.setItemSlot(EquipmentSlot.MAINHAND, user.getMainHandItem().copy());
        doppelganger.setItemSlot(EquipmentSlot.OFFHAND, user.getOffhandItem().copy());
        doppelganger.setItemSlot(EquipmentSlot.HEAD, user.getItemBySlot(EquipmentSlot.HEAD).copy());
        doppelganger.setItemSlot(EquipmentSlot.CHEST, user.getItemBySlot(EquipmentSlot.CHEST).copy());
        doppelganger.setItemSlot(EquipmentSlot.LEGS, user.getItemBySlot(EquipmentSlot.LEGS).copy());
        doppelganger.setItemSlot(EquipmentSlot.FEET, user.getItemBySlot(EquipmentSlot.FEET).copy());

        // Copy health and make immortal
        doppelganger.setHealth(user.getHealth());
        doppelganger.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 32767, 9, true, false));

        // Set and summon King Crimson replica, make it block forever
        summonFakeKC(attacker);

        // Look at enemy
        doppelganger.setTarget(user.getLastHurtByMob());

        attacker.level().addFreshEntity(doppelganger);

        return Set.of();
    }

    private void summonFakeKC(final KingCrimsonRequiemEntity attacker) {
        Mob doppelganger = attacker.getMoveContext().get(DOPPELGANGER);
        CommonStandComponent standData = JComponentPlatformUtils.getStandData(doppelganger);
        standData.setTypeAndSkin(attacker.getStandType(), attacker.getSkin());

        StandEntity<?, ?> clone = JCraft.summon(attacker.level(), doppelganger);
        if (clone == null) {
            return;
        }

        clone.blocking = true;
        clone.setMoveStun(32767);
        clone.setSilent(true);
    }

    public void tickTimeErase(final KingCrimsonRequiemEntity attacker) {
        LivingEntity user = attacker.getUserOrThrow();
        int teTime = attacker.getTETime();
        if (teTime > 0) {
            attacker.setTETime(--teTime);

            if (attacker.blocking || attacker.getCurrentMove() != null && attacker.getMoveStun() < attacker.getCurrentMove().getWindupPoint() * 3 / 2) {
                attacker.cancelTE();
            }

            // Invulnerability and invisibility
            user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 9, true, false));
            user.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, true, false));
            // Inability to be stunned
            user.removeEffect(JStatusRegistry.DAZED.get());
            // Inability to be hit (by projectiles)
            AABB noBox = new AABB(0, 0, 0, 0, 0, 0);
            user.setBoundingBox(noBox);
            user.noPhysics = true;

            if (teTime <= 0) {
                // Play exit noise
                if (user instanceof ServerPlayer player) {
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(JSoundRegistry.TIME_ERASE_EXIT.get()),
                            SoundSource.PLAYERS, attacker.getX(), attacker.getY(), attacker.getZ(), 1, 1, 0));
                }

                /* Return targets to position
                for (TimeEraseData timeEraseData : timeEraseInfo) {
                    Vec3d tePos = timeEraseData.getPosition();
                    timeEraseData.getEntity().teleport(tePos.x, tePos.y, tePos.z);
                }
                 */
            }
        }

        Mob doppelganger = attacker.getMoveContext().get(TimeEraseMove.DOPPELGANGER);
        if (teTime <= 0 && doppelganger != null) // Doppelgänger disappears at the end of Time Erase
        {
            doppelganger.discard();
        }

        attacker.setSilent(teTime > 0);

        if (user.hasCustomName()) {
            user.setCustomNameVisible(teTime <= 0);
        }
    }

    @Override
    public void registerExtraContextEntries(final MoveContext ctx) {
        ctx.register(DOPPELGANGER);
    }

    @Override
    protected @NonNull TimeEraseMove getThis() {
        return this;
    }

    @Override
    public @NonNull TimeEraseMove copy() {
        return copyExtras(new TimeEraseMove(getCooldown(), getWindup(), getDuration(), getMoveDistance(), getErasureDuration()));
    }

    public static class Type extends AbstractMove.Type<TimeEraseMove> {
        public static final Type INSTANCE = new Type();

        @Override
        protected @NonNull App<RecordCodecBuilder.Mu<TimeEraseMove>, TimeEraseMove> buildCodec(RecordCodecBuilder.Instance<TimeEraseMove> instance) {
            return baseDefault(instance).and(Codec.INT.fieldOf("erasure_duration").forGetter(TimeEraseMove::getErasureDuration))
                    .apply(instance, applyExtras(TimeEraseMove::new));
        }
    }
}
