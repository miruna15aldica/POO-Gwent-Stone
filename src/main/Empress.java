package main;

import java.util.ArrayList;

public class Empress extends Hero{
    public Empress(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors, "Empress Thorina",  false, true);
        // special ability in cadrul clasei - Low Blow: distruge cartea cu cea mai mare viață de pe rând.
    }


    @Override
    void specialAbility(Table table, int row) {
            this.special = true;
            int aux = 0;
            for(int i = 0; i < table.getTable()[row].length; ++i) {
                Minion minion = (Minion) table.getTable()[row][i];
                if (aux < minion.health)
                    aux = minion.health;
            }
            for(int i = 0; i < table.getTable()[row].length; ++i) {
                Minion minion = (Minion) table.getTable()[row][i];
                if (aux == minion.health) {
                    minion.health = 0;
                    break;
                }
            }
    }
}
