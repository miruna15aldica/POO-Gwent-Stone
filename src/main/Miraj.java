package main;

import java.util.ArrayList;

public class Miraj extends Minion {
    public Miraj(final int mana, final String description, final ArrayList<String> colors,
                 final int health, final int attackDamage) {
        super(mana, description, colors, "Miraj", 1,
                health, attackDamage, false,
                true, false,
                true, false, true);

    }
    @Override
    public final void specialAbility(final Minion minion) {
        // - Skyjack: face swap
        // între viața lui și viața unui minion din tabăra adversă.
        int aux = minion.health;
        minion.health = this.health;
        this.health = aux;
        this.special = true;
    }

    @Override
    public final Card copy() {
        return new Miraj(mana, description, colors, health, attackDamage);
    }
}
