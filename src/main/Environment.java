package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class Environment extends Card{
    // cardType = 2
    protected boolean abilityOnEnemy;
    protected boolean abilityOnSelf;
    protected Boolean special;
    public Environment(int mana, String description, ArrayList<String> colors, String name,
                       boolean abilityOnEnemy, boolean abilityOnSelf) {
        super(mana, description, colors, name, 2);
        this.abilityOnEnemy = abilityOnEnemy;
        this.abilityOnSelf = abilityOnSelf;
        this.special = false;
    }
   abstract void specialAbility(Table table, int row);

    @Override
    public ObjectNode  cardTransformToAnObjectNode(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.mana);
        // objectNode.put("health", this.health);
        objectNode.put("name", this.name);
        objectNode.put("description", this.description);
        objectNode.set("colors", showColors(objectMapper));
       // objectNode.put("attackDamage", this.attackDamage);
        return objectNode;
    }
}
