package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class Hero extends Card {
    protected Boolean special;
    protected int health;
    protected Boolean abilityOnSelf;
    protected Boolean abilityOnEnemy;
    int n = 30;
    public Hero(final int mana, final String description,
                final ArrayList<String> colors, final String name,
                final Boolean abilityOnSelf, final Boolean abilityOnEnemy) {
        super(mana, description, colors, name, 3);
        this.abilityOnSelf = abilityOnSelf;
        this.abilityOnEnemy = abilityOnEnemy;
        this.health = n;
        this.special = false;
    }
    abstract void specialAbility(Table table, int row);

    /**
     *
     * @param attackDamage
     */
    public void damage(final int attackDamage) {
        if (this.health - attackDamage < 0) {
            this.health = 0;
        } else {
            this.health = this.health - attackDamage;
        }
    }
    @Override
    public final ObjectNode  cardTransformToAnObjectNode(final ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.mana);
        objectNode.put("health", this.health);
        objectNode.put("description", this.description);
        objectNode.put("name", this.name);
        objectNode.set("colors", showColors(objectMapper));
        return objectNode;
    }

    public final void setSpecial(final Boolean special) {
        this.special = special;
    }

    public final Boolean getSpecial() {
        return special;
    }

    public final int getHealth() {
        return health;
    }

    /**
     *
     * @return
     */
    public abstract Card copy();
}
