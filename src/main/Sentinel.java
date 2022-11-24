package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Sentinel extends Minion {
    public Sentinel(int mana, String description, ArrayList<String> colors, int health, int attackDamage) {
        super(mana, description, colors,"Sentinel",1, health, attackDamage, false,
                false, true,true, false, false );
    }

    @Override
    void specialAbility(Minion minion) {

    }

    @Override
    public Card copy() {
        return new Sentinel(mana, description, colors, health, attackDamage);
    }
}
