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


    public Card(int mana, String description, ArrayList<String> colors, String name, int cardType) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.cardType = cardType;
    }

    public int getMana() {
        return mana;
    }


    public ArrayNode showColors(ObjectMapper objectMapper) {
        ArrayNode colorName = objectMapper.createArrayNode();
        for(String color : colors) {
            colorName.add(color);
        }
        return colorName;
    }

    public abstract ObjectNode cardTransformToAnObjectNode(ObjectMapper objectMapper);

    public int getCardType() {
        return cardType;
    }
//    public ArrayNode cardTransformToArrayNode(ObjectMapper objectMapper) {
//        ArrayNode cardsName = objectMapper.createArrayNode();
//        for(Card card : player.currentCards) {
//            cardsName.add(card.cardTransformToAnObjectNode(objectMapper));
//        }
//        return cardsName;
//    }

    public abstract Card copy();
}
