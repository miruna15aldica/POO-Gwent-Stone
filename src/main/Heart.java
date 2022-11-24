package main;

import java.util.ArrayList;

public class Heart extends Environment {
    public Heart(final int mana, final String description,
                  final ArrayList<String> colors) {
        super(mana, description, colors, "Heart Hound",
                true, false);
    }

    @Override
    public final void specialAbility(final Table table, final int row) {
        // abilitatea speciala in cadrul clasei - Heart Hound
        //Se fură minionul adversarului cu cea mai mare viață
        // de pe rând și se pune pe rândul “oglindit” aferent jucătorului.
        this.special = true;
        int aux = 0;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion.health >= aux) {
                aux = minion.health;
            }

        }
        final int maxrow = 3;
        for (int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion.health == aux) {
                table.putCardOnRow(minion, maxrow - row);
                table.getTable()[row][i] = null;
                break;
            }

        }

    }

    @Override
    public final Card copy() {
        return new Heart(mana, description, colors);
    }


}
