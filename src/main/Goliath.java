package main;

import java.util.ArrayList;

public class Goliath extends Minion {
    public Goliath(final int mana, final String description,
                   final ArrayList<String> colors, final int health, final int attackDamage) {
        super(mana, description, colors, "Goliath", 1,
                health, attackDamage, true, true,
                false, true, false, false);
    }

    @Override
    void specialAbility(final Minion minion) {

    }

    @Override
    public final Card copy() {
        return new Goliath(mana, description, colors, health, attackDamage);
    }
}
