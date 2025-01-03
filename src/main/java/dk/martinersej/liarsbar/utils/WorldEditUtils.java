package dk.martinersej.liarsbar.utils;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

public class WorldEditUtils {

    public static void runWithSession(@Nonnull World inWorld, @Nonnull Consumer<EditSession> consumer) {
        runWithSession(getWEWorld(inWorld), consumer);
    }

    public static void runWithSession(@Nonnull com.sk89q.worldedit.world.World inWorld, @Nonnull Consumer<EditSession> consumer) {
        EditSessionFactory sessionFactory = WorldEdit.getInstance().getEditSessionFactory();
        EditSession session = sessionFactory.getEditSession(inWorld, Integer.MAX_VALUE); // Max block change limit

        try {
            consumer.accept(session);
        } finally {
            session.flushQueue();
        }
    }

    public static com.sk89q.worldedit.world.World getWEWorld(World world) {
        return BukkitUtil.getLocalWorld(world);
    }

    public static World getWorld(com.sk89q.worldedit.world.World world) {
        Bukkit.broadcastMessage(world.getName());
        return ((BukkitWorld) world).getWorld();
    }

    public static Clipboard loadSchematic(File file, com.sk89q.worldedit.world.World world) {
        ClipboardFormat format = ClipboardFormat.findByFile(file);
        if (format == null) {
            return null;
        }
        try {
            ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()));
            return reader.read(world.getWorldData());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Clipboard loadSchematic(File file, World world) {
        return loadSchematic(file, getWEWorld(world));
    }

    public static void pasteSchematic(Clipboard clipboard, World world, BlockVector position) {
        WorldData worldData = getWEWorld(world).getWorldData();
        runWithSession(world, session -> {
            session.setFastMode(true);

            Operation operation = new ClipboardHolder(clipboard, worldData).createPaste(session, worldData).to(position).ignoreAirBlocks(true) // Don't paste air
                .build();
            try {
                Operations.complete(operation);
            } catch (WorldEditException e) {
                e.printStackTrace();
            }
        });
    }

    public static void pasteSchematic(Clipboard clipboard, Location location) {
        pasteSchematic(clipboard, location.getWorld(), new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public static void clearSchematic(Region region) {
        runWithSession(getWorld(region.getWorld()), session -> {
            try {
                session.setBlocks(region, new BaseBlock(0));
            } catch (MaxChangedBlocksException e) {
                e.printStackTrace();
            }
        });
    }

    public static void clearSchematic(Clipboard clipboard) {
        clearSchematic(clipboard.getRegion());
    }

    public static boolean isAll(@Nonnull Region region, @Nonnull BaseBlock baseBlock) {
        for (BlockVector block : region) {
            if (!region.getWorld().getBlock(block).equals(baseBlock)) {
                return false;
            }
        }
        return true;
    }

    public static boolean contains(@Nonnull Region region, @Nonnull BaseBlock baseBlock) {
        for (BlockVector block : region) {
            if (region.getWorld().getBlock(block).equals(baseBlock)) {
                return true;
            }
        }
        return false;
    }
}
