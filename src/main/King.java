package main;

import java.util.ArrayList;

public class King extends Hero{
    public King(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "King Mudface", true, false);
        //special ability in cadrul clasei - Earth Born: +1 viață pentru toate cărțile de pe rând.
    }

    @Override
    void specialAbility(Table table, int row) {
        this.special = true;
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            minion.health = minion.health + 1;
        }
    }
}
