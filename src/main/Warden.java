package main;

import java.util.ArrayList;

public class Warden extends Minion {
    public Warden(final int mana, final String description, final ArrayList<String> colors,
                  final int health, final int attackDamage) {
        super(mana, description, colors, "Warden", 1,
                health, attackDamage, true, true,
                false,
                true, false, false);
    }

    @Override
    void specialAbility(final Minion minion) {

    }

    @Override
    public final Card copy() {
        return new Warden(mana, description, colors, health, attackDamage);
    }
}
