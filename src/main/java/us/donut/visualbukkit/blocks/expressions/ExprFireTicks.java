package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The number of ticks before an entity stops being on fire", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprFireTicks extends ModifiableExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("fire ticks of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFireTicks()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return arg(0) + ".setFireTicks(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "-" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
