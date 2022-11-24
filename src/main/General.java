package main;

import java.util.ArrayList;

public class General extends Hero{
    public General(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "General Kocioraw", true, false);
        // Blood Thirst: +1 atac pentru toate cărțile de pe rând.
    }

    @Override
    void specialAbility(Table table, int row) {
        this.special = true;
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            minion.attackDamage = minion.attackDamage + 1;
        }

    }
}
