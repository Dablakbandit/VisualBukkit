package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The fly speed of a player", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprFlySpeed extends ModifiableExpressionBlock<Float> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("fly speed of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFlySpeed()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return arg(0) + ".setFlySpeed(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "-" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
