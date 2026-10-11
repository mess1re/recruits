package com.talhanation.recruits;

import com.talhanation.recruits.commands.PatrolSpawnCommand;
import com.talhanation.recruits.commands.RecruitsAdminCommands;
import com.talhanation.recruits.init.*;
import com.talhanation.recruits.network.*;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static final String MOD_ID = "recruits";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static boolean isMusketModLoaded;
    public static boolean isSmallShipsLoaded;
    public static boolean isSmallShipsCompatible;
    public static boolean isSiegeWeaponsLoaded;
    public static boolean isSiegeWeaponsCompatible;
    public static boolean isEpicKnightsLoaded;
    public static boolean isCorpseLoaded;
    public static boolean isRPGZLoaded;

    /**
     * Called by every loader when the mod is constructed.
     */
    public static void init() {
        registerContent();
        AttributeEvent.entityAttributeEvent();
        addCreativeTabs();

        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, selection) -> {
            PatrolSpawnCommand.register(dispatcher);
            RecruitsAdminCommands.register(dispatcher);
        });

        LifecycleEvent.SETUP.register(() -> {
            new RecruitEvents().register();
            new VillagerEvents().register();
            new PillagerEvents().register();
            new CommandEvents().register();
            new FactionEvents().register();
            new DamageEvent().register();
            new UpdateChecker().register();
            new ClaimEvents().register();

            setup();
        });
    }

    private static void addCreativeTabs() {
        CreativeTabRegistry.appendBuiltin(BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTabs.SPAWN_EGGS),
                ModItems.BOWMAN_SPAWN_EGG,
                ModItems.RECRUIT_SHIELD_SPAWN_EGG,
                ModItems.RECRUIT_SPAWN_EGG,
                ModItems.NOMAD_SPAWN_EGG,
                ModItems.HORSEMAN_SPAWN_EGG,
                ModItems.CROSSBOWMAN_SPAWN_EGG,
                ModItems.VILLAGER_NOBLE_SPAWN_EGG);

        CreativeTabRegistry.appendBuiltin(BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTabs.FUNCTIONAL_BLOCKS),
                ModBlocks.RECRUIT_BLOCK,
                ModBlocks.BOWMAN_BLOCK,
                ModBlocks.RECRUIT_SHIELD_BLOCK,
                ModBlocks.CROSSBOWMAN_BLOCK,
                ModBlocks.HORSEMAN_BLOCK,
                ModBlocks.NOMAD_BLOCK);
    }

    public static void registerContent() {
        ModBlocks.BLOCKS.register();
        ModPois.POIS.register();
        ModProfessions.PROFESSIONS.register();
        ModScreens.MENU_TYPES.register();
        ModItems.ITEMS.register();
        ModEntityTypes.ENTITY_TYPES.register();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void setup() {
        Class[] messages = {
                MessageAggro.class,
                MessageAggroGui.class,
                MessageAssassinate.class,
                MessageAssassinCount.class,
                MessageAssassinGui.class,
                MessageMountEntity.class,
                MessageMountEntityGui.class,
                MessageClearTargetGui.class,
                MessageCommandScreen.class,
                MessageDisband.class,
                MessageMovement.class,
                MessageFollowGui.class,
                MessageGroup.class,
                MessageListen.class,
                MessageRecruitGui.class,
                MessageHireGui.class,
                MessageHire.class,
                MessageProtectEntity.class,
                MessageDismount.class,
                MessageDismountGui.class,
                MessageUpkeepPos.class,
                MessageStrategicFire.class,
                MessageShields.class,
                MessageDebugGui.class,
                MessageUpkeepEntity.class,
                MessageClearTarget.class,
                MessageCreateTeam.class,
                MessageOpenTeamEditScreen.class,
                MessageLeaveTeam.class,
                MessageTeamMainScreen.class,
                MessageOpenTeamInspectionScreen.class,
                MessageOpenTeamListScreen.class,
                MessageAddPlayerToTeam.class,
                MessageOpenTeamAddPlayerScreen.class,
                MessageAddRecruitToTeam.class,
                MessageSendJoinRequestTeam.class,
                MessageRemoveFromTeam.class,
                MessageOpenDisbandScreen.class,
                MessageAssignRecruitToPlayer.class,
                MessageWriteSpawnEgg.class,
                MessageBackToMountEntity.class,
                MessageDisbandGroup.class,
                MessageAssignGroupToPlayer.class,
                MessagePromoteRecruit.class,
                MessageOpenPromoteScreen.class,
                MessageOpenSpecialScreen.class,
                MessageSendMessenger.class,
                MessagePatrolLeaderSetWaitTime.class,
                MessageToClientUpdateLeaderScreen.class,
                MessagePatrolLeaderAddWayPoint.class,
                MessagePatrolLeaderRemoveWayPoint.class,
                MessagePatrolLeaderSetPatrolState.class,
                MessagePatrolLeaderSetCycle.class,
                MessagePatrolLeaderSetInfoMode.class,
                MessageAssignGroupToCompanion.class,
                MessagePatrolLeaderSetPatrollingSpeed.class,
                MessageToClientUpdateHireState.class,
                MessageRemoveAssignedGroupFromCompanion.class,
                MessageAnswerMessenger.class,
                MessageToClientOpenMessengerAnswerScreen.class,
                MessageClearUpkeepGui.class,
                MessageApplyNoGroup.class,
                MessageToClientUpdateGroups.class,
                MessagePatrolLeaderSetRoute.class,
                MessagePatrolLeaderSetEnemyAction.class,
                MessageSetLeaderGroup.class,
                MessageTransferRoute.class,
                MessageToClientReceiveRoute.class,
                MessageFormationFollowMovement.class,
                MessageRest.class,
                MessageRangedFire.class,
                MessageSaveFormationFollowMovement.class,
                MessageClearUpkeep.class,
                MessageToClientUpdateFactions.class,
                MessageToClientUpdateOnlinePlayers.class,
                MessageChangeDiplomacyStatus.class,
                MessageToClientSetToast.class,
                MessageToClientUpdateDiplomacyList.class,
                MessageSaveTeamSettings.class,
                MessageToClientSetDiplomaticToast.class,
                MessageScoutTask.class,
                MessageToClientOpenTakeOverScreen.class,
                MessageToClientUpdateClaims.class,
                MessageUpdateClaim.class,
                MessageDoPayment.class,
                MessageToClientUpdateClaim.class,
                MessageToClientUpdateOwnFaction.class,
                MessageDeleteClaim.class,
                MessageToClientOpenNobleTradeScreen.class,
                MessageHireFromNobleVillager.class,
                MessageAttack.class,
                MessageToClientUpdateUnitInfo.class,
                MessageUpdateGroup.class,
                MessageMergeGroup.class,
                MessageSplitGroup.class,
                MessageAssignNearbyRecruitsInGroup.class,
                MessageTeleportPlayer.class,
                MessageSendTreaty.class,
                MessageAnswerTreaty.class,
                MessageToClientOpenTreatyAnswerScreen.class,
                MessageToClientUpdateTreaties.class,
                MessageFaceCommand.class,
                MessageSetTargetPrio.class,
                MessageAddEmbargo.class,
                MessageRemoveEmbargo.class,
                MessageToClientUpdateEmbargoes.class,
                MessageAddEmbargoFaction.class,
                MessageToClientWorldMapIdentity.class,
                MessageDebugScreen.class,
                MessageToClientUpdateMessengerScreen.class
        };


        RecruitsNetwork.register(messages);


        isMusketModLoaded = Platform.isModLoaded("musketmod");//MusketMod
        isSmallShipsLoaded = Platform.isModLoaded("smallships");//small ships
        isSiegeWeaponsLoaded = Platform.isModLoaded("siegeweapons");//siege weapons
        isRPGZLoaded = Platform.isModLoaded("rpgz");//rpgz mod
        isCorpseLoaded = Platform.isModLoaded("corpse");//corpse mod
        isEpicKnightsLoaded = Platform.isModLoaded("magistuarmory");//epic knights mod

        isSmallShipsCompatible = false;
        if(isSmallShipsLoaded){
            String smallshipsversion = Platform.getMod("smallships").getVersion();
            isSmallShipsCompatible = isVersionAtLeast(smallshipsversion, "2.0.0-b1.4");
        }

        isSiegeWeaponsCompatible = false;
        if(isSiegeWeaponsLoaded){
            String siegeweaponsVersion = Platform.getMod("siegeweapons").getVersion();
            isSiegeWeaponsCompatible = isVersionAtLeast(siegeweaponsVersion, "0.2.5");
        }
    }

    public static boolean isVersionAtLeast(String installedVersion, String minVersion) {
        String[] installed = installedVersion.split("[.\\-]", -1);
        String[] minimum  = minVersion.split("[.\\-]", -1);

        int len = Math.max(installed.length, minimum.length);
        for (int i = 0; i < len; i++) {
            String a = i < installed.length ? installed[i] : "0";
            String b = i < minimum.length  ? minimum[i]   : "0";

            int cmp;
            try {
                cmp = Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
            } catch (NumberFormatException e) {
                cmp = a.compareTo(b);
            }

            if (cmp != 0) return cmp > 0;
        }
        return true;
    }
}
