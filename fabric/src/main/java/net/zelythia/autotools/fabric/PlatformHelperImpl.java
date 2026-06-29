package net.zelythia.autotools.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Path;
import java.util.HashSet;

public class PlatformHelperImpl {
    public static HashSet<Path> getResourcePaths(String path) {
        HashSet<Path> out = new HashSet<>();

        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            mod.findPath(path).ifPresent(out::add);
        }

        return out;
    }

    public static boolean controllableAttackDown() {
        if (!FabricLoader.getInstance().isModLoaded("controllable")) return false;

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
