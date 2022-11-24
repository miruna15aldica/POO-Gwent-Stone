package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class Card {


    protected int mana;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;

    protected int cardType;


    public Card(final int mana, final String description,
                final ArrayList<String> colors, final String name, final int cardType) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.cardType = cardType;
    }

    public final int getMana() {
        return mana;
    }

    /**
     *
     * @param objectMapper
     * @return
     */
     public ArrayNode showColors(final ObjectMapper objectMapper) {
         // functie care ne afiseaza culorile fiecarei carti
        ArrayNode colorName = objectMapper.createArrayNode();
        for (String color : colors) {
            colorName.add(color);
        }
        return colorName;
    }

    /**
     *
     * @param objectMapper
     * @return
     */
    public abstract ObjectNode cardTransformToAnObjectNode(ObjectMapper objectMapper);

    public final int getCardType() {
        return cardType;
    }

    /**
     *
     * @return
     */
    public abstract Card copy();
}
