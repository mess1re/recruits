package com.talhanation.recruits.fabric;

import com.talhanation.recruits.Main;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
//? if >=1.21 {
/*import net.minecraft.core.Holder;
*///?}

/**
 * Stand-ins for the swim speed and entity reach attributes Forge and NeoForge provide.
 * Recruits read entity reach themselves; swim speed has no effect on Fabric.
 */
final class FabricAttributes {

    //? if >=1.21 {
    /*static final Holder<Attribute> SWIM_SPEED = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(Main.MOD_ID, "swim_speed"),
            new RangedAttribute("attribute.name.recruits.swim_speed", 1.0D, 0.0D, 1024.0D).setSyncable(true));
    *///?} else {
    static final Attribute SWIM_SPEED = Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(Main.MOD_ID, "swim_speed"),
            new RangedAttribute("attribute.name.recruits.swim_speed", 1.0D, 0.0D, 1024.0D).setSyncable(true));
    static final Attribute ENTITY_REACH = Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(Main.MOD_ID, "entity_reach"),
            new RangedAttribute("attribute.name.recruits.entity_reach", 3.0D, 0.0D, 1024.0D).setSyncable(true));
    //?}

    private FabricAttributes() {
    }

    /**
     * Registers the attributes before the entity attributes that use them are built.
     */
    static void register() {
    }
}
