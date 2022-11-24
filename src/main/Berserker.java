package main;

import java.util.ArrayList;

public class Berserker extends Minion {
    public Berserker(final int mana, final String description, final ArrayList<String> colors,
                     final int health, final int attackDamage) {
        super(mana, description, colors, "Berserker", 1, health, attackDamage, false, false, true,
                true, false, false);
    }

    @Override
    void specialAbility(final Minion minion) {

    }

    @Override
    public final Card copy() {
        return new Berserker(mana, description, colors, health, attackDamage);
    }
}
