package top.hycqwq.slimyfloor;

import java.util.Optional;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.util.math.random.ChunkRandom;

public class Slimyfloor implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("SlimyFloor");

    @Override
    public void onInitialize() {
        FabricLoader fabric = FabricLoader.getInstance();
        Optional<net.fabricmc.loader.api.ModContainer> mc = fabric.getModContainer("slimy-floor-hycmod");
        String version = "?version?";
        if (mc.isPresent() && (mc.get() instanceof ModContainer)) {
            ModContainer m = mc.get();
            version = m.getMetadata().getVersion().getFriendlyString();
        }
        LOGGER.info("SlimyFloor {} loaded", version);
    }

    public static Boolean isInSlimeChunk(final ServerWorld level, final BlockPos pos) {
        if (pos.getY() < 40 && level.getRegistryKey() == World.OVERWORLD) {
            ChunkPos cpos = new ChunkPos(pos);
            if (ChunkRandom.getSlimeRandom(cpos.x, cpos.z, level.getSeed(), 0x3ad8025fL).nextInt(10) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void displaySlimeParticle(final ServerWorld level, final Position pos, final int count) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        level.spawnParticles((ParticleEffect) ParticleTypes.ITEM_SLIME, x, y, z, count, 0.2d, 0.0d, 0.2d, 0.0d);
    }

}
