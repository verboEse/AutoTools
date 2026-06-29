package net.zelythia.autotools.neoforge;

import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class PlatformHelperImpl {
    public static HashSet<Path> getResourcePaths(String path) {
        HashSet<Path> out = new HashSet<>();

        for (IModFileInfo modFile : ModList.get().getModFiles()) {
            Path modPath = modFile.getFile().getFilePath().resolve(path);
            if (Files.exists(modPath)) {
                out.add(modPath);
            }
        }

        return out;
    }

    public static boolean controllableAttackDown() {
        if (!ModList.get().isLoaded("controllable")) return false;

        try {
            Object controllable = Class.forName("com.mrcrayfish.controllable.Controllable")
                .getMethod("getController")
                .invoke(null);
            if (controllable == null) return false;
            Object buttonBindings = Class.forName("com.mrcrayfish.controllable.client.binding.ButtonBindings")
                .getField("ATTACK")
                .get(null);
            return (boolean) buttonBindings.getClass().getMethod("isButtonDown").invoke(buttonBindings);
        } catch (Exception e) {
            return false;
        }
    }
}
