package main;

import java.util.ArrayList;

public class General extends Hero {
    public General(final int mana, final String description, final ArrayList<String> colors) {
        super(mana, description, colors, "General Kocioraw", true, false);
    }

    /**
     *
     * @param table
     * @param row
     */
    @Override
    final void specialAbility(final Table table, final int row) {
        // Blood Thirst: +1 atac pentru toate cărțile de pe rând.
        this.special = true;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null) {
                minion.attackDamage = minion.attackDamage + 1;
            }
        }
    }

    @Override
    public final Card copy() {
        return new General(mana, description, colors);
    }
}
