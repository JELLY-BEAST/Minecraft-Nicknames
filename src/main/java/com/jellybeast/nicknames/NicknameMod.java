package com.jellybeast.nicknames;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class NicknameMod implements ModInitializer {
    public static final String MOD_ID = "minecraft_nicknames";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(
                Commands.literal("nickname")
                    .requires(source -> source.hasPermission(2))
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
                                target.setCustomName(displayName);
                                target.setCustomNameVisible(true);
                                target.setTabListDisplayName(displayName);

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
