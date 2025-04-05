package net.memezforbeanz.jcraft_requiem.common.attack.moves.kingcrimsonrequiem;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NonNull;
import net.arna.jcraft.common.attack.core.ctx.MoveContext;
import net.arna.jcraft.common.attack.core.data.MoveType;
import net.arna.jcraft.common.attack.moves.base.AbstractMove;
import net.arna.jcraft.common.entity.projectile.BloodProjectile;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.KingCrimsonRequiemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

import java.util.Set;

public final class BloodThrowAttack extends AbstractMove<BloodThrowAttack, KingCrimsonRequiemEntity> {
    public BloodThrowAttack(final int cooldown, final int windup, final int duration, final float moveDistance) {
        super(cooldown, windup, duration, moveDistance);
        ranged = true;
    }

    @Override
    public @NonNull MoveType<BloodThrowAttack> getMoveType() {
        return Type.INSTANCE;
    }

    @Override
    public void onInitiate(final KingCrimsonRequiemEntity attacker) {
        super.onInitiate(attacker);

        attacker.getUserOrThrow().hurt(attacker.level().damageSources().magic(), 0.1f); // User throws their blood, dealing a bit of damage.
    }

    @Override
    public @NonNull Set<LivingEntity> perform(final KingCrimsonRequiemEntity attacker, final LivingEntity user, final MoveContext ctx) {
        final BloodProjectile bloodProjectile = new BloodProjectile(attacker.level(), user);
        bloodProjectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        bloodProjectile.shootFromRotation(user, user.getXRot(), user.getYRot(), 0, user.isShiftKeyDown() ? 1.33F : 0.66F, 0);
        bloodProjectile.setPos(attacker.getEyePosition());
        attacker.level().addFreshEntity(bloodProjectile);

        return Set.of();
    }

    @Override
    protected @NonNull BloodThrowAttack getThis() {
        return this;
    }

    @Override
    public @NonNull BloodThrowAttack copy() {
        return copyExtras(new BloodThrowAttack(getCooldown(), getWindup(), getDuration(), getMoveDistance()));
    }

    public static class Type extends AbstractMove.Type<BloodThrowAttack> {
        public static final Type INSTANCE = new Type();

        @Override
        protected @NonNull App<RecordCodecBuilder.Mu<BloodThrowAttack>, BloodThrowAttack> buildCodec(RecordCodecBuilder.Instance<BloodThrowAttack> instance) {
            return baseDefault(instance, BloodThrowAttack::new);
        }
    }
}
