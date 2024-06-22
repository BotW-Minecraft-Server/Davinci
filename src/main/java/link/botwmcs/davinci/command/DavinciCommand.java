package link.botwmcs.davinci.command;

import com.mojang.brigadier.CommandDispatcher;
import link.botwmcs.davinci.util.SendComponentUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

public class DavinciCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("davinci")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("demo")
                        .then(Commands.argument("target", EntityArgument.players())
                                .executes(context -> {
                                    EntityArgument.getPlayers(context, "target").forEach(SendComponentUtil::showDemo);
                                    return 1;
                                })
                        )
                )

        );
    }
}
