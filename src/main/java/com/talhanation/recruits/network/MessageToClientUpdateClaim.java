package com.talhanation.recruits.network;

import com.talhanation.recruits.client.ClientManager;
import com.talhanation.recruits.client.api.ClientClaimEvent;
import com.talhanation.recruits.client.gui.worldmap.claim.WorldMapClaimIndex;
import com.talhanation.recruits.network.codec.ClaimNetworkCodec;
import com.talhanation.recruits.world.RecruitsClaim;
import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.talhanation.recruits.events.RecruitsEventBus;

public class MessageToClientUpdateClaim implements Message<MessageToClientUpdateClaim> {
    private RecruitsClaim claim;

    public MessageToClientUpdateClaim() {
    }

    public MessageToClientUpdateClaim(RecruitsClaim claim) {
        this.claim = claim;
    }

    @Override
    public MessageSide getExecutingSide() {
        return MessageSide.CLIENT;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void executeClientSide(MessageContext context) {
        this.updateOrAddClaim(claim);
    }

    @Environment(EnvType.CLIENT)
    private void updateOrAddClaim(RecruitsClaim newClaim) {
        if (newClaim == null) return;

        if (newClaim.isRemoved) {
            removeClaim(newClaim);
            return;
        }

        for (int i = 0; i < ClientManager.recruitsClaims.size(); i++) {
            RecruitsClaim existing = ClientManager.recruitsClaims.get(i);
            if (existing.getUUID().equals(newClaim.getUUID())) {
                ClientManager.recruitsClaims.set(i, newClaim);
                WorldMapClaimIndex.invalidate();

                boolean isCurrentClaim = ClientManager.currentClaim != null
                        && ClientManager.currentClaim.getUUID().equals(newClaim.getUUID());

                // Aktuellen Claim-Zeiger ebenfalls aktualisieren
                if (isCurrentClaim) {
                    ClientManager.currentClaim = newClaim;
                }

                ClientManager.updateActiveSiege(newClaim);

                RecruitsEventBus.post(new ClientClaimEvent.DataUpdated(newClaim, isCurrentClaim));
                return;
            }
        }

        ClientManager.recruitsClaims.add(newClaim);
        WorldMapClaimIndex.invalidate();
        ClientManager.updateActiveSiege(newClaim);
        RecruitsEventBus.post(
                new ClientClaimEvent.DataUpdated(newClaim, false));
    }

    @Environment(EnvType.CLIENT)
    private void removeClaim(RecruitsClaim removedClaim) {
        boolean wasCurrentClaim = ClientManager.currentClaim != null
                && ClientManager.currentClaim.getUUID().equals(removedClaim.getUUID());

        ClientManager.recruitsClaims.removeIf(
                claim -> claim != null && claim.getUUID().equals(removedClaim.getUUID()));
        ClientManager.activeSiegeClaims.remove(removedClaim.getUUID());
        if (wasCurrentClaim) {
            ClientManager.currentClaim = null;
        }

        WorldMapClaimIndex.invalidate();
        RecruitsEventBus.post(new ClientClaimEvent.DataUpdated(removedClaim, wasCurrentClaim));
    }

    @Override
    public MessageToClientUpdateClaim fromBytes(FriendlyByteBuf buf) {
        this.claim = ClaimNetworkCodec.readNullableClaim(buf);

        return this;
    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {
        ClaimNetworkCodec.writeNullableClaim(buf, this.claim);
    }
}
