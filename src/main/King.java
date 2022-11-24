package main;

import java.util.ArrayList;

public class King extends Hero {
    public King(final int mana, final String description,
                final ArrayList<String> colors) {
        super(mana, description, colors, "King Mudface",
                true, false);
    }

    @Override
    public final void specialAbility(final Table table, final int row) {
        //special ability in cadrul clasei - Earth Born: +1 viață pentru toate cărțile de pe rând.
        this.special = true;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null) {
                minion.health = minion.health + 1;
            }
        }
    }

    @Override
    public final Card copy() {
        return new King(mana, description, colors);
    }
}
