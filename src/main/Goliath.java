package main;

import java.util.ArrayList;

public class Goliath extends Minion {
    public Goliath(int mana, String description, ArrayList<String> colors, int health, int attackDamage) {
        super(mana, description, colors, "Goliath",1, health, attackDamage, true, true,
                false, true, false, false);
    }

    @Override
    void specialAbility(Minion minion) {

    }

    @Override
    public Card copy() {
        return new Goliath(mana, description, colors, health, attackDamage);
    }
}
