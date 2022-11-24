package main;

import java.util.ArrayList;

public class Cursed extends Minion{
    public Cursed(int mana, String description, ArrayList<String> colors, int health) {
        super(mana, description, colors, "The Cursed One", 1, health, 0, false, false, true,
                true, false, true);
        // abilitatea o implementez in cadrul clasei - Shapeshift: face swap între viața
        // unui minion din tabăra adversă și atacul aceluiași minion

    }

    @Override
    void specialAbility(Minion minion) {
        int aux = minion.health;
        minion.health = minion.attackDamage;
        minion.attackDamage = aux;
        this.special = true;
    }

    @Override
    public Card copy() {
        return new Cursed(mana, description, colors, health);
    }
}
