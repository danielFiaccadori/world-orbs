package net.dndats.world_orbs.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ModKeybinds {

    public static final KeyMapping USE_ORB = new KeyMapping(
            "key.world_orbs.use_orb",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_R,
            "key.categories.world_orbs"
    );

}
