package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Ripper extends Minion{
    public Ripper(int mana, String description, ArrayList<String> colors, int health, int attackDamage) {
        super(mana, description, colors, "The Ripper",1,  health, attackDamage, false, true,
                false, true, false, true);
        // abilitatea o fac in clasa - Weak Knees: -2 atac pentru un minion din tabăra adversă.
    }

    @Override
    void specialAbility(Minion minion) {
        minion.attackDamage -= 2;
        if (minion.attackDamage < 0)
            minion.attackDamage = 0;
        this.special = true;
    }

    @Override
    public Card copy() {
        return new Ripper(mana, description, colors, health, attackDamage);
    }
}
