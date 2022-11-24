package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Winterfell extends Environment{
    public Winterfell(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "Winterfell",false, true);
        // abilitatea speciala in clasa - Winterfell
        //Toate cărțile de pe rând stau o tură.
    }


    @Override
    void specialAbility(Table table, int row) {
        this.special = true;
        for(int i = 0; i < table.getTable()[row].length; ++i) {
            Minion minion = (Minion) table.getTable()[row][i];
            if (minion != null)
                minion.frozen = true;
        }

    }


}
