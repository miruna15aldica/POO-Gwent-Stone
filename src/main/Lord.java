package main;

import java.util.ArrayList;

public class Lord extends Hero {
    public Lord(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "Lord Royce", false, true);
        // special ability in cadrul clasei - Sub-Zero: îngheață cartea cu cel mai mare atac de pe rând.
    }

    @Override
    void specialAbility(Table table, int row) {
        this.special = true;
        int aux = 0;
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null && aux <= minion.attackDamage)
                aux = minion.attackDamage;
        }
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null && aux == minion.attackDamage) {
                minion.frozen = true;
                break;
            }
        }

    }

    @Override
    public Card copy() {
        return new Lord(mana, description, colors);
    }


}
