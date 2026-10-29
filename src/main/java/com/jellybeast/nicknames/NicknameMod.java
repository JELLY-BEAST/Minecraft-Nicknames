package com.jellybeast.nicknames;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class NicknameMod implements ModInitializer {
    public static final String MOD_ID = "minecraft_nicknames";

    // Nicknames are held by UUID so they remain associated with the right player for this server session.
    private static final Map<UUID, Component> NICKNAMES = new ConcurrentHashMap<>();

    public static Component getNickname(ServerPlayer player) {
        return NICKNAMES.get(player.getUUID());
    }

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(
                Commands.literal("nickname")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .then(Commands.argument("UserToNickName", EntityArgument.player())
                        .then(Commands.argument("Nickname", StringArgumentType.greedyString())
                            .executes(context -> {
                                ServerPlayer target = EntityArgument.getPlayer(context, "UserToNickName");
                                String nickname = StringArgumentType.getString(context, "Nickname").trim();

                                if (nickname.isEmpty()) {
                                    context.getSource().sendFailure(Component.literal("Nickname cannot be empty."));
                                    return 0;
                                }

                                Component displayName = Component.literal(nickname);
                                NICKNAMES.put(target.getUUID(), displayName);

                                // Show the nickname above the player's head.
                                target.setCustomName(displayName);
                                target.setCustomNameVisible(true);

                                // Refresh the tab-list display name for every connected client.
                                context.getSource().getServer().getPlayerList().broadcastAll(
                                    new ClientboundPlayerInfoUpdatePacket(
                                        ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME,
                                        target
                                    )
                                );

                                context.getSource().sendSuccess(
                                    () -> Component.literal("Set " + target.getGameProfile().name() + "'s nickname to " + nickname + "."),
                                    true
                                );
                                return 1;
                            })
                        )
                    )
            )
        );
    }
}
