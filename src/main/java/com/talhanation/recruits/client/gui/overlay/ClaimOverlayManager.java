package com.talhanation.recruits.client.gui.overlay;

import com.talhanation.recruits.client.ClientManager;
import com.talhanation.recruits.client.api.ClientClaimEvent;
import com.talhanation.recruits.client.api.ClientOverlayEvent;
import com.talhanation.recruits.config.RecruitsClientConfig;
import com.talhanation.recruits.events.RecruitsEventBus;
import com.talhanation.recruits.world.RecruitsClaim;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
//? if >=1.21 {
/*import net.minecraft.client.DeltaTracker;
*///?}
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class ClaimOverlayManager {
    private OverlayState currentState = OverlayState.HIDDEN;
    private long stateChangeTime = 0;
    private long claimEntryTime = 0;
    private int tickCounter = 0;
    private ChunkPos lastPlayerChunk = null;
    private String lastKnownClaimName = "";
    private String lastKnownFactionName = "";
    private int lastKnownHealth = -1;
    private boolean lastKnownSiegeState = false;

    private static final long FADE_DURATION = 500;
    private static final long FULL_DISPLAY_DURATION = 5000;
    private static final int DATA_UPDATE_INTERVAL = 20;
    private static final int CHUNK_CHECK_INTERVAL = 10;
    private static final int PANEL_WIDTH = 150;

    private final ClaimOverlayRenderer renderer = new ClaimOverlayRenderer();

    public enum OverlayState {
        HIDDEN,
        FULL,
        COMPACT
    }

    public ClaimOverlayManager() {

    }

    public void register() {
        ClientTickEvent.CLIENT_POST.register(this::onClientTick);
        ClientGuiEvent.RENDER_HUD.register(this::onRenderGameOverlay);
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(this::onWorldUnload);
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(this::onClientLogin);
    }

    public void onClientTick(Minecraft minecraft) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        tickCounter++;

        if (tickCounter % CHUNK_CHECK_INTERVAL == 0) {
            updateCurrentClaim(mc.player.blockPosition());
        }

        boolean needsUpdate = tickCounter % DATA_UPDATE_INTERVAL == 0;

        if (needsUpdate && ClientManager.currentClaim != null) {
            checkForDataChanges();
        }

        updateOverlayState();
    }

    //? if >=1.21 {
    /*public void onRenderGameOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    *///?} else {
    public void onRenderGameOverlay(GuiGraphics guiGraphics, float partialTick) {
    //?}
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (mc.gui.getTabList().visible) return;

        if (!mc.gui.getBossOverlay().events.isEmpty()) {
            return;
        }

        if (mc.level != null && mc.level.dimension() != Level.OVERWORLD)return;

        if(RecruitsClientConfig.DisableClaimGUIOverlay.get()) return;

        float alpha = calculateAlpha();
        if (alpha <= 0.01f) return;

        if (ClientManager.currentClaim != null) {
            boolean cancelled = RecruitsEventBus.post(new ClientOverlayEvent.RenderPre(guiGraphics, ClientManager.currentClaim, currentState, alpha));
            if (!cancelled) {
                renderer.render(guiGraphics, mc, ClientManager.currentClaim, currentState, alpha, getPanelWidth());

                RecruitsEventBus.post(new ClientOverlayEvent.RenderPost(guiGraphics, ClientManager.currentClaim, currentState, alpha));
            }
        }
    }

    public void onWorldUnload(LocalPlayer player) {
        reset();
    }

    public void onClientLogin(LocalPlayer player) {
        reset();
    }

    private void updateCurrentClaim(BlockPos playerPos) {
        ChunkPos currentChunk = new ChunkPos(playerPos);

        if (currentChunk.equals(lastPlayerChunk)) {
            ClientManager.currentClaim = null;
            for (RecruitsClaim claim : ClientManager.recruitsClaims) {
                if (claim.containsChunk(currentChunk)) {
                    ClientManager.currentClaim = claim;
                    break;
                }
            }
            return;
        }

        lastPlayerChunk = currentChunk;
        RecruitsClaim previousClaim = ClientManager.currentClaim;

        ClientManager.currentClaim = null;
        for (RecruitsClaim claim : ClientManager.recruitsClaims) {
            if (claim.containsChunk(currentChunk)) {
                ClientManager.currentClaim = claim;
                break;
            }
        }

        handleClaimTransition(previousClaim, ClientManager.currentClaim);
    }

    private void handleClaimTransition(RecruitsClaim previousClaim, RecruitsClaim newClaim) {
        if (previousClaim == null && newClaim != null) {
            RecruitsEventBus.post(new ClientClaimEvent.Enter(newClaim, null));
            claimEntryTime = System.currentTimeMillis();
            transitionToState(OverlayState.FULL, true);
            updateCachedData(newClaim);
        }
        else if (previousClaim != null && newClaim == null) {
            RecruitsEventBus.post(new ClientClaimEvent.Leave(previousClaim, null));
            transitionToState(OverlayState.HIDDEN, true);
        }
        else if (previousClaim != null && newClaim != null && !previousClaim.equals(newClaim)) {
            RecruitsEventBus.post(new ClientClaimEvent.Leave(previousClaim, newClaim));
            RecruitsEventBus.post(new ClientClaimEvent.Enter(newClaim, previousClaim));
            claimEntryTime = System.currentTimeMillis();
            transitionToState(OverlayState.FULL, true);
            updateCachedData(newClaim);
        }
    }

    private void checkForDataChanges() {
        if (ClientManager.currentClaim == null) return;

        RecruitsClaim claim = ClientManager.currentClaim;
        boolean hasChanges = false;

        if (!claim.getName().equals(lastKnownClaimName)) {
            lastKnownClaimName = claim.getName();
            hasChanges = true;
        }

        if (!claim.getOwnerFactionStringID().equals(lastKnownFactionName)) {
            lastKnownFactionName = claim.getOwnerFactionStringID();
            hasChanges = true;
        }

        if (claim.getHealth() != lastKnownHealth) {
            int previousHealth = lastKnownHealth;
            lastKnownHealth = claim.getHealth();
            hasChanges = true;

            if (previousHealth != -1) {
                RecruitsEventBus.post(
                        new ClientClaimEvent.HealthChanged(claim, previousHealth, claim.getHealth()));
            }
        }

        if (claim.isUnderSiege != lastKnownSiegeState) {
            lastKnownSiegeState = claim.isUnderSiege;
            hasChanges = true;

            if (claim.isUnderSiege) {
                boolean cancelled = RecruitsEventBus.post(new ClientClaimEvent.SiegeStarted(claim));
                if (!cancelled) {
                    claimEntryTime = System.currentTimeMillis();
                    transitionToState(OverlayState.FULL, false);
                }
            }
            else {
                boolean wasConquered = !claim.getOwnerFactionStringID().equals(lastKnownFactionName);
                RecruitsEventBus.post(new ClientClaimEvent.SiegeEnded(claim, wasConquered));
            }
        }

        if (hasChanges) {
            renderer.markDataChanged();
        }
    }

    private void updateCachedData(RecruitsClaim claim) {
        if (claim == null) {
            lastKnownClaimName = "";
            lastKnownFactionName = "";
            lastKnownHealth = -1;
            lastKnownSiegeState = false;
            return;
        }

        lastKnownClaimName = claim.getName();
        lastKnownFactionName = claim.getOwnerFactionStringID();
        lastKnownHealth = claim.getHealth();
        lastKnownSiegeState = claim.isUnderSiege;
    }

    private void updateOverlayState() {
        if (ClientManager.currentClaim == null || ClientManager.currentClaim.isUnderSiege) {
            return;
        }

        long timeInClaim = System.currentTimeMillis() - claimEntryTime;
        OverlayState desiredState = (timeInClaim < FULL_DISPLAY_DURATION) ?
                OverlayState.FULL : OverlayState.COMPACT;

        if (currentState != desiredState) {
            transitionToState(desiredState, true);
        }
    }

    private void transitionToState(OverlayState newState, boolean fade) {
        if (currentState == newState) return;

        boolean cancelled = RecruitsEventBus.post(new ClientOverlayEvent.StateChanged(ClientManager.currentClaim, currentState, newState, calculateAlpha()));
        if (cancelled) return;

        if (fade) {
            stateChangeTime = System.currentTimeMillis();
        } else {
            stateChangeTime = System.currentTimeMillis() - FADE_DURATION;
        }

        currentState = newState;
    }

    private float calculateAlpha() {
        if (currentState == OverlayState.HIDDEN) return 0f;

        long elapsed = System.currentTimeMillis() - stateChangeTime;

        if (elapsed >= FADE_DURATION) {
            return 1.0f;
        }

        float progress = (float) elapsed / FADE_DURATION;

        if (currentState == OverlayState.HIDDEN) {
            return 1.0f - progress;
        } else {
            return progress;
        }
    }

    public int getPanelWidth() {
        return PANEL_WIDTH;
    }

    public OverlayState getCurrentState() {
        return currentState;
    }

    private void reset() {
        ClientManager.recruitsClaims.clear();
        ClientManager.activeSiegeClaims.clear();
        ClientManager.currentClaim = null;
        ClientManager.groupAggroState.clear();
        ClientManager.groupMoveState.clear();
        currentState = OverlayState.HIDDEN;
        lastPlayerChunk = null;
        tickCounter = 0;
        updateCachedData(null);
        renderer.clearCache();
    }
}
