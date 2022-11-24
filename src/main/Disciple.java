package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Disciple extends Minion {
    public Disciple(int mana, String description, ArrayList<String> colors, int health) {
        super(mana, description, colors, "Disciple", 1, health, 0, false, false, true,
                true, true, false );
        // abilitatea o fac in cadrul clasei - God's Plan: +2 viață pentru un minion din tabăra lui.
    }

    @Override
    void specialAbility(Minion minion) {
        minion.health = minion.health + 2;
        this.special = true;
    }


//    public ObjectNode cardTransformToAnObjectNode(ObjectMapper objectMapper) {
//        return null;
//    }

    @Override
    public Card copy() {
        return new Disciple(mana, description, colors, health);
    }

}
