package main;

import java.util.ArrayList;

public class Lord extends Hero {
    public Lord(final int mana, final String description,
                final ArrayList<String> colors) {
        super(mana, description, colors, "Lord Royce",
                false, true);

    }

    @Override
    public final void specialAbility(final Table table, final int row) {
        // special ability in cadrul clasei - Sub-Zero: îngheață cartea
        // cu cel mai mare atac de pe rând.
        this.special = true;
        int aux = 0;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null && aux <= minion.attackDamage) {
                aux = minion.attackDamage;
            }
        }
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null && aux == minion.attackDamage) {
                minion.frozen = true;
                break;
            }
        }

    }

    @Override
    public final Card copy() {
        return new Lord(mana, description, colors);
    }


}
