package net.memezforbeanz.jcraft_requiem.common.attack.moves.kingcrimsonrequiem;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NonNull;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.common.attack.core.data.MoveType;
import net.arna.jcraft.common.attack.moves.base.AbstractCounterAttack;
import net.arna.jcraft.common.attack.moves.base.AbstractMove;
import net.arna.jcraft.common.attack.moves.shared.CounterMissMove;
import net.arna.jcraft.common.util.JUtils;
import net.arna.jcraft.registry.JSoundRegistry;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.KingCrimsonRequiemEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class EpitaphAttack extends AbstractCounterAttack<EpitaphAttack, KingCrimsonRequiemEntity> {
    private final CounterMissMove<KingCrimsonRequiemEntity> counterMiss = new CounterMissMove<>(20);

    public EpitaphAttack(final int cooldown, final int windup, final int duration, final float moveDistance) {
        super(cooldown, windup, duration, moveDistance);
    }

    @Override
    public @NonNull MoveType<EpitaphAttack> getMoveType() {
        return Type.INSTANCE;
    }

    @Override
    public void whiff(final @NonNull KingCrimsonRequiemEntity attacker, final @NonNull LivingEntity user) {
        attacker.setMove(counterMiss, KingCrimsonRequiemEntity.State.COUNTER_MISS);
        JCraft.stun(user, counterMiss.getDuration(), 0);
        attacker.playSound(JSoundRegistry.KC_RAGE.get(), 1, 1);
    }

    @Override
    public void counter(final @NonNull KingCrimsonRequiemEntity attacker, final Entity countered, final DamageSource counteredDamageSource) {
        super.counter(attacker, countered, counteredDamageSource);

        if (countered == null) {
            return;
        }
        // Swaps positions with countered
        final LivingEntity user = attacker.getUserOrThrow();
        final Vec3 ePos = countered.position();
        if (!countered.isInWall()) {
            final Vec3 uPos = user.position();

            countered.teleportToWithTicket(uPos.x, uPos.y, uPos.z);
            user.teleportToWithTicket(ePos.x, ePos.y, ePos.z);
        }

        user.lookAt(EntityAnchorArgument.Anchor.EYES, countered.getEyePosition());

        if (countered instanceof LivingEntity livingEntity) {
            JCraft.stun(livingEntity, 20, 0);
            JUtils.cancelMoves(livingEntity);
        }

        attacker.level().playSound(null, ePos.x, ePos.y, ePos.z, JSoundRegistry.TE_TP.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    protected @NonNull EpitaphAttack getThis() {
        return this;
    }

    @Override
    public @NonNull EpitaphAttack copy() {
        return copyExtras(new EpitaphAttack(getCooldown(), getWindup(), getDuration(), getMoveDistance()));
    }

    public static class Type extends AbstractMove.Type<EpitaphAttack> {
        public static final Type INSTANCE = new Type();

        @Override
        protected @NonNull App<RecordCodecBuilder.Mu<EpitaphAttack>, EpitaphAttack> buildCodec(RecordCodecBuilder.Instance<EpitaphAttack> instance) {
            return baseDefault(instance, EpitaphAttack::new);
        }
    }
}
