package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The message format in an AsyncPlayerChatEvent", "Returns: string"})
@Event(AsyncPlayerChatEvent.class)
@Modifier(ModificationType.SET)
public class ExprMessageFormat extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("message format");
    }

    @Override
    public String toJava() {
        return "event.getFormat()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? "event.setFormat(" + delta + ");" : null;
    }
}
