package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class Environment extends Card {
    // cardType = 2
    protected boolean abilityOnEnemy;
    protected boolean abilityOnSelf;
    protected Boolean special;
    public Environment(final int mana, final String description, final ArrayList<String> colors,
                       final String name, final boolean abilityOnEnemy,
                       final boolean abilityOnSelf) {
        super(mana, description, colors, name, 2);
        this.abilityOnEnemy = abilityOnEnemy;
        this.abilityOnSelf = abilityOnSelf;
        this.special = false;
    }
   abstract void specialAbility(Table table, int row);

    @Override
    public final ObjectNode  cardTransformToAnObjectNode(final ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.mana);
        objectNode.put("name", this.name);
        objectNode.put("description", this.description);
        objectNode.set("colors", showColors(objectMapper));
        return objectNode;
    }

    /**
     *
     * @return
     */
    public abstract Card copy();
}
