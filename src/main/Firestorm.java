package main;

import java.util.ArrayList;

public class Firestorm extends Environment {
    public Firestorm(final int mana, final String description, final ArrayList<String> colors) {
        super(mana, description, colors, "Firestorm", true, false);
    }

    @Override
    public final void specialAbility(final Table table, final int row) {
        // abilitatea speciala e definita in clasa - Firestorm
        //Scade cu 1 viața tuturor minionilor de pe rând
        this.special = true;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null) {
                minion.health = minion.health - 1;
                if (minion.health < 0) {
                    minion.health = 0;
                }
            }
        }
    }

    @Override
    public final Card copy() {
        return new Firestorm(mana, description, colors);
    }
}
