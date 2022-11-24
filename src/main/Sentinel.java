package main;

import java.util.ArrayList;

public class Sentinel extends Minion {
    public Sentinel(final int mana, final String description, final ArrayList<String> colors,
                    final int health, final int attackDamage) {
        super(mana, description, colors, "Sentinel", 1, health,
                attackDamage, false,
                false, true,
                true, false, false);
    }

    @Override
    void specialAbility(final Minion minion) {

    }

    @Override
    public final Card copy() {
        return new Sentinel(mana, description, colors, health, attackDamage);
    }
}
