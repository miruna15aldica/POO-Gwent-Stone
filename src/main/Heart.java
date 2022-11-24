package main;

import java.util.ArrayList;

public class Heart extends Environment{
    public Heart(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "Heart Hound", false, true);
        // abilitatea speciala in cadrul clasei - Heart Hound
        //Se fură minionul adversarului cu cea mai mare viață de pe rând și se pune pe rândul “oglindit” aferent jucătorului.
    }

    @Override
    void specialAbility(Table table, int row) {
        this.special = true;
        int aux = 0;
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if(minion.health >= aux) {
                aux = minion.health;
            }

        }
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if(minion.health == aux) {
                table.putCardOnRow(minion, 3 - row);
                table.getTable()[row][i] = null;
                break;
            }

        }

    }



}
