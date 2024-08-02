package top.hycqwq.slimyfloor.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import top.hycqwq.slimyfloor.Slimyfloor;

@Mixin(PlayerEntity.class)
public abstract class EntityMixin extends Entity {

    public EntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    protected final void tickMixin(final CallbackInfo info) {
        if (this.getWorld() instanceof ServerWorld level) {
            if (Boolean.TRUE.equals(Slimyfloor.isInSlimeChunk(level, this.getBlockPos()))) {
                if (this.random.nextFloat() < 0.02f) {
                    Slimyfloor.displaySlimeParticle(level, this.getPos(), this.random.nextInt(3));
                }
            }
        }
    }

}