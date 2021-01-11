package com.gmail.visualbukkit.blocks.minescape.execute;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.blocks.structures.StructFunction;

@Category(Category.MINESCAPE)
@Description("Open Dialogue")
public class StatOpenDialogue extends StatementBlock {

    public StatOpenDialogue() {
        init("Open dialogue ", new StringLiteralParameter());
    }

    @Override
    public String toJava() {
        return "";
    }
}
