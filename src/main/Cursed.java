package main;

import java.util.ArrayList;

public class Cursed extends Minion {
    public Cursed(final int mana, final String description,
                  final ArrayList<String> colors, final int health) {
        super(mana, description, colors, "The Cursed One", 1, health, 0, false, false, true,
                true, false, true);
    }

    @Override
    public final void specialAbility(final Minion minion) {
        // abilitatea o implementez in cadrul clasei - Shapeshift: face swap între viața
        // unui minion din tabăra adversă și atacul aceluiași minion
        int aux = minion.health;
        minion.health = minion.attackDamage;
        minion.attackDamage = aux;
        this.special = true;
    }

    @Override
    public final Card copy() {
        return new Cursed(mana, description, colors, health);
    }
}
