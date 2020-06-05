package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Drops an item stack at a location")
public class StatDropItem extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("drop", ItemStack.class, "at", Location.class, new ChoiceParameter("naturally", "unnaturally"));
    }

    @Override
    public String toJava() {
        return "dropItem(" + arg(0) + "," + arg(1) + "," + arg(2).equals("naturally") + ");";
    }

    @UtilMethod
    public static void dropItem(ItemStack item, Location loc, boolean naturally) {
        if (naturally) {
            loc.getWorld().dropItemNaturally(loc, item);
        } else {
            loc.getWorld().dropItem(loc, item);
        }
    }
}
