package main;

import java.util.ArrayList;

public class Firestorm extends Environment {
    public Firestorm(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "Firestorm", false, true);
        // abilitatea speciala e definita in clasa - Firestorm
        //Scade cu 1 viața tuturor minionilor de pe rând
    }

    @Override
    void specialAbility(Table table, int row) {
        this.special = true;
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null)
                minion.health = minion.health - 1;
        }
    }
}