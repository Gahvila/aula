package net.gahvila.aula.Utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class EmptyChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return createChunkData(world);
    }

    public static void createWorld() {
        if (Bukkit.getWorld("world") == null) {
            WorldCreator worldCreator = new WorldCreator("world");
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.generator(new EmptyChunkGenerator());
            worldCreator.generateStructures(false);
            worldCreator.createWorld();
        }
    }
}
