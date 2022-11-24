package main;

import java.util.ArrayList;

public class Ripper extends Minion {
    public Ripper(final int mana, final String description, final ArrayList<String> colors,
                  final int health, final int attackDamage) {
        super(mana, description, colors, "The Ripper", 1,  health, attackDamage, false, true,
                false, true, false, true);

    }

    @Override
    public final void specialAbility(final Minion minion) {
        //  Weak Knees: -2 atac pentru un minion din tabăra adversă.
        minion.attackDamage -= 2;
        if (minion.attackDamage < 0) {
            minion.attackDamage = 0;
        }
        this.special = true;
    }

    @Override
    public final Card copy() {
        return new Ripper(mana, description, colors, health, attackDamage);
    }
}
