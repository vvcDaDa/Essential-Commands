package com.fibermc.essentialcommands.commands.bench;

import com.fibermc.essentialcommands.ECText;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static com.fibermc.essentialcommands.EssentialCommands.CONFIG;

public class WastebinCommand implements Command<ServerCommandSource> {

    public WastebinCommand() {}

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity senderPlayer = source.getPlayer();

        senderPlayer.openHandledScreen(createScreenHandlerFactory(senderPlayer.getEntityWorld(), senderPlayer.getBlockPos()));

        source.sendFeedback(
            ECText.getInstance().getText("cmd.workbench.feedback", ECText.getInstance().get("cmd.wastebin.name")),
            CONFIG.BROADCAST_TO_OPS.getValue()
        );

        return 0;
    }

    private @NotNull NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
            (syncId, inventory, player) ->
                GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, new SimpleInventory(27)),
            ECText.getInstance().getText("cmd.wastebin.name").setStyle(Style.EMPTY)
        );
    }

}