package fileio;

import main.*;

import java.util.ArrayList;

public final class CardInput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public CardInput() {
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }

    public Card toCard() {
        if(name.equals("Warden")) {
            return new Warden(mana, description, colors, health, attackDamage);
        } else if(name.equals("The Ripper")) {
            return new Ripper(mana, description, colors, health, attackDamage);
        } else if(name.equals("The Cursed One")) {
            return new Cursed(mana, description, colors, health);
        } else if(name.equals("Miraj")) {
            return new Miraj(mana, description, colors, health, attackDamage);
        } else if(name.equals("Goliath")) {
            return new Goliath(mana, description, colors, health, attackDamage);
        } else if(name.equals("Sentinel")) {
            return  new Sentinel(mana, description, colors, health, attackDamage);
        } else if(name.equals("Berserker")) {
            return  new Berserker(mana, description, colors, health, attackDamage);
        } else if(name.equals("Disciple")) {
            return new Disciple(mana, description, colors, health);
        } else if(name.equals("Heart Hound")) {
            return new Heart(mana, description, colors);
        } else if(name.equals("Firestorm")) {
            return new Firestorm(mana, description, colors);
        } else if(name.equals("Winterfell")) {
            return new Winterfell(mana, description, colors);
        } else if(name.equals("General Kocioraw")) {
            return  new General(mana, description, colors);
        } else if(name.equals("Empress Thorina")) {
            return new Empress(mana, description, colors);
        } else if(name.equals("Lord Royce")) {
            return new Lord(mana, description, colors);
        } else if(name.equals("King Mudface")) {
            return new King(mana, description, colors);
        }
        return null;
    }
}
