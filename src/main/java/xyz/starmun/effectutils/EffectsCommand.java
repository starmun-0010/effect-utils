package xyz.starmun.effectutils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;
import java.util.stream.Collectors;

public class EffectsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("effects")
            .then(Commands.literal("count").executes(EffectsCommand::logCount))
            .then(Commands.literal("list").executes(EffectsCommand::list))
            .then(Commands.literal("conflicts").executes(EffectsCommand::conflicts)));

    }

    private static int conflicts(CommandContext<CommandSourceStack> context) {
        long count = Registry.MOB_EFFECT.stream().count();
        context.getSource().sendSuccess(new TextComponent("Total conflicts: "+( count <=256? 0: count-256)),false);
        StringBuilder builder = new StringBuilder();
        Map<Integer, String> map = Registry.MOB_EFFECT.entrySet().stream()
                .collect(Collectors.toMap(e-> MobEffect.getId(e.getValue()), e->e.getValue().getDescriptionId()));
        map.forEach((key,value)->{
            if(key > 255){
                builder.append("id: ");
                builder.append(key);
                builder.append(" - ");
                builder.append(value);
                builder.append(" --> ");
                builder.append("id: ");
                builder.append(key % 256);
                builder.append(" - ");
                builder.append(map.get(key%256));
                builder.append(System.lineSeparator());
            }
        });
        context.getSource().sendSuccess(new TextComponent(builder.toString()),false);
        context.getSource().sendSuccess(new TextComponent("Total conflicts: "+( count <=256? 0: count-256)),false);
        return 1;
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        StringBuilder builder = new StringBuilder();
        Registry.MOB_EFFECT.entrySet().forEach((e)->{
            builder.append("id: ");
            builder.append(MobEffect.getId(e.getValue()));
            builder.append(" - ");
            builder.append(e.getValue().getEffect().getDescriptionId());
            builder.append(System.lineSeparator());
        });
        context.getSource().sendSuccess(new TextComponent(builder.toString()),false);

        return 1;
    }

    private static int logCount(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(new TextComponent(""+Registry.MOB_EFFECT.stream().count()),false);
        return 01;
    }
}