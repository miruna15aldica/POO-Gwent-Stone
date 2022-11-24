package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Hero extends Card{
    // cardType = 3

    protected Boolean special;
    protected int health;
    protected Boolean abilityOnSelf;
    protected Boolean abilityOnEnemy;
    
    public Hero(int mana, String description, ArrayList<String> colors, String name,
                Boolean abilityOnSelf, Boolean abilityOnEnemy) {
        super(mana, description, colors, name, 3);
        this.abilityOnSelf = abilityOnSelf;
        this.abilityOnEnemy = abilityOnEnemy;
        this.health = 30;
        this.special = false;
    }
    // avem si o abilitate speciala, dar trebuie vazut cum o facem si pe aia
    abstract void specialAbility(Table table, int row);

    public void damage(int attackDamage) {
        if(this.health - attackDamage < 0) {
            this.health = 0;
        } else {
            this.health = this.health - attackDamage;
        }
    }
    @Override
    public ObjectNode  cardTransformToAnObjectNode(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.mana);
        objectNode.put("health", this.health);
        objectNode.put("description", this.description);
        objectNode.put("name", this.name);
        objectNode.set("colors", showColors(objectMapper));
        // objectNode.put("attackDamage", this.attackDamage);
        return objectNode;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getSpecial() {
        return special;
    }

    public int getHealth() {
        return health;
    }

    public abstract Card copy();
}
