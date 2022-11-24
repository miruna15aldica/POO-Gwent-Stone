package main;

import java.util.ArrayList;

public class Winterfell extends Environment {
    public Winterfell(final int mana, final String description,
                      final ArrayList<String> colors) {
        super(mana, description, colors, "Winterfell", true, false);

    }


    @Override
    final void specialAbility(final Table table, final int row) {
        //Winterfell - Toate cărțile de pe rând stau o tură.
        this.special = true;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null) {
                minion.frozen = true;
            }
        }

    }

    @Override
    public final Card copy() {
        return new Winterfell(mana, description, colors);
    }
}
