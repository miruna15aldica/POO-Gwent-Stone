package main;

import java.util.ArrayList;

public class Disciple extends Minion {
    public Disciple(final int mana, final String description,
                    final ArrayList<String> colors, final int health) {
        super(mana, description, colors, "Disciple",
                1, health, 0, false, false, true,
                true, true, false);
    }

    @Override
    public final void specialAbility(final Minion minion) {
        // abilitatea o fac in cadrul clasei - God's Plan:
        // +2 viață pentru un minion din tabăra lui.
        minion.health = minion.health + 2;
        this.special = true;
    }

    @Override
    public final Card copy() {
        return new Disciple(mana, description, colors, health);
    }

}
