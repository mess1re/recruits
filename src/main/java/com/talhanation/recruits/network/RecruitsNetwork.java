package com.talhanation.recruits.network;

import com.talhanation.recruits.Main;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
//? if neoforge {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
*///?}

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class RecruitsNetwork {

    private static final ResourceLocation SERVERBOUND = id("serverbound");
    private static final ResourceLocation CLIENTBOUND = id("clientbound");
    private static final List<Class<? extends Message<?>>> MESSAGE_TYPES = new ArrayList<>();
    private static final Map<Class<?>, Integer> MESSAGE_IDS = new HashMap<>();

    private RecruitsNetwork() {
    }

    @SuppressWarnings("unchecked")
    public static void register(Class<?>[] messageTypes) {
        if (!MESSAGE_TYPES.isEmpty()) {
            throw new IllegalStateException("Recruits network was already registered");
        }

        for (int id = 0; id < messageTypes.length; id++) {
            Class<? extends Message<?>> messageType = (Class<? extends Message<?>>) messageTypes[id];
            if (!Message.class.isAssignableFrom(messageType)) {
                throw new IllegalArgumentException(messageType.getName() + " does not implement Message");
            }
            MESSAGE_TYPES.add(messageType);
            MESSAGE_IDS.put(messageType, id);
        }

        //? if forge {
        NetworkManager.registerReceiver(NetworkManager.c2s(), SERVERBOUND,
                (buffer, context) -> handle(decode(buffer), MessageSide.SERVER, context));
        EnvExecutor.runInEnv(Env.CLIENT, () -> () ->
                NetworkManager.registerReceiver(NetworkManager.s2c(), CLIENTBOUND,
                        (buffer, context) -> handle(decode(buffer), MessageSide.CLIENT, context)));
        //?} else {
        /*NetworkManager.registerReceiver(NetworkManager.c2s(), ServerboundPayload.TYPE,
                ServerboundPayload.STREAM_CODEC,
                (payload, context) -> handle(payload.message(), MessageSide.SERVER, context));
        EnvExecutor.runInEnv(Env.SERVER, () -> () ->
                NetworkManager.registerS2CPayloadType(ClientboundPayload.TYPE, ClientboundPayload.STREAM_CODEC));
        EnvExecutor.runInEnv(Env.CLIENT, () -> () ->
                NetworkManager.registerReceiver(NetworkManager.s2c(), ClientboundPayload.TYPE,
                        ClientboundPayload.STREAM_CODEC,
                        (payload, context) -> handle(payload.message(), MessageSide.CLIENT, context)));
        *///?}
    }

    public static void sendToServer(Message<?> message) {
        requireSide(message, MessageSide.SERVER);
        //? if forge {
        NetworkManager.sendToServer(SERVERBOUND, encode(message));
        //?} else {
        /*NetworkManager.sendToServer(new ServerboundPayload(message));
        *///?}
    }

    public static void sendToPlayer(ServerPlayer player, Message<?> message) {
        requireSide(message, MessageSide.CLIENT);
        //? if forge {
        NetworkManager.sendToPlayer(player, CLIENTBOUND, encode(message));
        //?} else {
        /*NetworkManager.sendToPlayer(player, new ClientboundPayload(message));
        *///?}
    }

    public static void sendToPlayer(Supplier<ServerPlayer> player, Message<?> message) {
        ServerPlayer serverPlayer = player.get();
        if (serverPlayer != null) {
            sendToPlayer(serverPlayer, message);
        }
    }

    private static FriendlyByteBuf encode(Message<?> message) {
        Integer id = MESSAGE_IDS.get(message.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered message: " + message.getClass().getName());
        }

        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        buffer.writeVarInt(id);
        message.toBytes(buffer);
        return buffer;
    }

    private static Message<?> decode(FriendlyByteBuf buffer) {
        int id = buffer.readVarInt();
        if (id < 0 || id >= MESSAGE_TYPES.size()) {
            throw new IllegalArgumentException("Unknown Recruits message id: " + id);
        }

        try {
            Message<?> message = MESSAGE_TYPES.get(id).getDeclaredConstructor().newInstance();
            return message.fromBytes(buffer);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException exception) {
            throw new IllegalStateException("Could not construct Recruits message " + MESSAGE_TYPES.get(id).getName(),
                    exception);
        }
    }

    private static void handle(Message<?> message, MessageSide receivingSide,
                               NetworkManager.PacketContext context) {
        requireSide(message, receivingSide);
        context.queue(() -> {
            if (receivingSide == MessageSide.SERVER) {
                if (!(context.getPlayer() instanceof ServerPlayer sender)) {
                    Main.LOGGER.warn("Ignored serverbound message {} without a server player",
                            message.getClass().getName());
                    return;
                }
                message.executeServerSide(new MessageContext(sender));
            } else {
                message.executeClientSide(new MessageContext(null));
            }
        });
    }

    private static void requireSide(Message<?> message, MessageSide expectedSide) {
        if (message.getExecutingSide() != expectedSide) {
            throw new IllegalArgumentException("Message " + message.getClass().getName() + " belongs to "
                    + message.getExecutingSide() + ", not " + expectedSide);
        }
    }

    private static ResourceLocation id(String path) {
        //? if forge {
        return new ResourceLocation(Main.MOD_ID, path);
        //?} else {
        /*return ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, path);
        *///?}
    }

    //? if neoforge {
    /*private record ServerboundPayload(Message<?> message) implements CustomPacketPayload {
        private static final Type<ServerboundPayload> TYPE = new Type<>(SERVERBOUND);
        private static final StreamCodec<RegistryFriendlyByteBuf, ServerboundPayload> STREAM_CODEC =
                StreamCodec.ofMember(ServerboundPayload::write, ServerboundPayload::decode);

        private static ServerboundPayload decode(RegistryFriendlyByteBuf buffer) {
            return new ServerboundPayload(RecruitsNetwork.decode(buffer));
        }

        private void write(RegistryFriendlyByteBuf buffer) {
            RecruitsNetwork.encode(message, buffer);
        }

        @Override
        public Type<ServerboundPayload> type() {
            return TYPE;
        }
    }

    private record ClientboundPayload(Message<?> message) implements CustomPacketPayload {
        private static final Type<ClientboundPayload> TYPE = new Type<>(CLIENTBOUND);
        private static final StreamCodec<RegistryFriendlyByteBuf, ClientboundPayload> STREAM_CODEC =
                StreamCodec.ofMember(ClientboundPayload::write, ClientboundPayload::decode);

        private static ClientboundPayload decode(RegistryFriendlyByteBuf buffer) {
            return new ClientboundPayload(RecruitsNetwork.decode(buffer));
        }

        private void write(RegistryFriendlyByteBuf buffer) {
            RecruitsNetwork.encode(message, buffer);
        }

        @Override
        public Type<ClientboundPayload> type() {
            return TYPE;
        }
    }

    private static void encode(Message<?> message, FriendlyByteBuf buffer) {
        Integer id = MESSAGE_IDS.get(message.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered message: " + message.getClass().getName());
        }
        buffer.writeVarInt(id);
        message.toBytes(buffer);
    }
    *///?}
}
