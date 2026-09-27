package com.talhanation.recruits.init;

//? if neoforge {
/*import com.talhanation.recruits.entities.AbstractInventoryEntity;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
*///?}

public final class RecruitsCapabilities {

    private RecruitsCapabilities() {
    }

    //? if neoforge {
    /*public static void register(RegisterCapabilitiesEvent event) {
        registerInventory(event, ModEntityTypes.RECRUIT);
        registerInventory(event, ModEntityTypes.RECRUIT_SHIELDMAN);
        registerInventory(event, ModEntityTypes.BOWMAN);
        registerInventory(event, ModEntityTypes.CROSSBOWMAN);
        registerInventory(event, ModEntityTypes.NOMAD);
        registerInventory(event, ModEntityTypes.HORSEMAN);
        registerInventory(event, ModEntityTypes.MESSENGER);
        registerInventory(event, ModEntityTypes.SCOUT);
        registerInventory(event, ModEntityTypes.PATROL_LEADER);
        registerInventory(event, ModEntityTypes.CAPTAIN);
        registerInventory(event, ModEntityTypes.VILLAGER_NOBLE);
        registerInventory(event, ModEntityTypes.SIEGE_ENGINEER);
    }

    private static <T extends AbstractInventoryEntity> void registerInventory(
            RegisterCapabilitiesEvent event,
            RegistrySupplier<EntityType<T>> entityType
    ) {
        event.registerEntity(Capabilities.ItemHandler.ENTITY, entityType.get(),
                (entity, context) -> new InvWrapper(entity.inventory));
        event.registerEntity(Capabilities.ItemHandler.ENTITY_AUTOMATION, entityType.get(),
                (entity, side) -> new InvWrapper(entity.inventory));
    }
    *///?}
}
