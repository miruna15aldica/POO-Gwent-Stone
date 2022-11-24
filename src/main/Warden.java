package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Warden extends Minion {
    public Warden(int mana, String description, ArrayList<String> colors, int health, int attackDamage) {
        super(mana, description, colors, "Warden",1, health, attackDamage, true, true, false,
                true, false, false);
    }

    @Override
    void specialAbility(Minion minion) {

    }


}
