package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Objects;

public class Miraj extends Minion{
    public Miraj(int mana, String description, ArrayList<String> colors, int health, int attackDamage) {
        super(mana, description, colors, "Miraj", 1, health, attackDamage, false,
                true, false, true, false, true);
        // fac abilitatea separat - Skyjack: face swap între viața lui și viața unui minion din tabăra adversă.
    }
    @Override
    void specialAbility(Minion minion) {
        int aux = minion.health;
        minion.health = this.health;
        this.health = aux;
        this.special = true;
    }

}
