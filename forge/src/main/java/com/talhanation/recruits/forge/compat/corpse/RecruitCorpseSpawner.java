package com.talhanation.recruits.forge.compat.corpse;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.compat.corpse.RecruitCorpseDeath;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.forge.mixin.corpse.CorpseEntityAccessor;
import de.maxhenkel.corpse.corelib.death.Death;
import de.maxhenkel.corpse.entities.CorpseEntity;
import net.minecraft.server.level.ServerLevel;

public final class RecruitCorpseSpawner {

    private RecruitCorpseSpawner() {
    }

    public static boolean spawnCorpse(AbstractInventoryEntity recruit) {
        if (!(recruit instanceof AbstractRecruitEntity recruitEntity)) {
            return false;
        }
        if (!(recruit.level() instanceof ServerLevel level)) {
            return false;
        }

        try {
            Death death = Death.fromNBT(RecruitCorpseDeath.createDeathTag(recruitEntity));

            CorpseEntity corpse = new CorpseEntity(level);
            ((CorpseEntityAccessor) corpse).recruits$setDeath(death);
            corpse.setCorpseUUID(death.getPlayerUUID());
            corpse.setCorpseName(death.getPlayerName());
            corpse.setEquipment(death.getEquipment());
            corpse.setCorpseModel(death.getModel());
            corpse.setPos(death.getPosX(), Math.max(death.getPosY(), level.getMinBuildHeight()), death.getPosZ());
            corpse.setYRot(recruitEntity.getYRot());

            level.addFreshEntity(corpse);
            return true;
        } catch (Exception e) {
            Main.LOGGER.warn("Was not able to spawn recruit corpse for {}", recruit.getName().getString(), e);
            return false;
        }
    }
}
