package main;

import java.util.ArrayList;

public class Empress extends Hero {
    public Empress(final int mana, final String description, final ArrayList<String> colors) {
        super(mana, description, colors,
                "Empress Thorina",  false, true);

    }


    @Override
    public final void specialAbility(final Table table, final int row) {
        // special ability in cadrul clasei -
        // Low Blow: distruge cartea cu cea mai mare viață de pe rând.
            this.special = true;
            int aux = 0;
            for (int i = 0; i < table.getTable()[row].length; ++i) {
                Minion minion = (Minion) table.getTable()[row][i];
                if (minion != null && aux < minion.health) {
                    aux = minion.health;
                }
            }
            for (int i = 0; i < table.getTable()[row].length; ++i) {
                Minion minion = (Minion) table.getTable()[row][i];
                if (minion != null && aux == minion.health) {
                    minion.health = 0;
                    break;
                }
            }
    }

    @Override
    public final Card copy() {
        return new Empress(mana, description, colors);
    }
}
