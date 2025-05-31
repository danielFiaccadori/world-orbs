package net.dndats.world_orbs.registry;

import net.dndats.world_orbs.Worldorbs;
import net.dndats.world_orbs.blocks.test.ApotheosisOrbBlock;
import net.dndats.world_orbs.blocks.test.ApotheosisOrbBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;

public class ModBlockEntities {

    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, Worldorbs.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Worldorbs.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<Block, Block> APOTHEOSIS_ORB =
            BLOCKS.register("apotheosis_orb", ApotheosisOrbBlock::new);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ApotheosisOrbBlockEntity>> APOTHEOSIS_ORB_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("apotheosis_orb", () -> BlockEntityType.Builder.of(
               ApotheosisOrbBlockEntity::new, APOTHEOSIS_ORB.get()).build(null));

    public static Collection<DeferredHolder<Block, ? extends Block>> blocks() {
        return BLOCKS.getEntries();
    }

}
