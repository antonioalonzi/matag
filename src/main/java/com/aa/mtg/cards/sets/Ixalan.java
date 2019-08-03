package com.aa.mtg.cards.sets;

import com.aa.mtg.cards.Card;
import com.aa.mtg.cards.properties.Color;
import com.aa.mtg.cards.properties.Cost;
import com.aa.mtg.cards.properties.Type;

import java.util.ArrayList;
import java.util.List;

import static com.aa.mtg.cards.ability.Abilities.*;
import static com.aa.mtg.cards.properties.Rarity.COMMON;
import static com.aa.mtg.cards.properties.Rarity.UNCOMMON;
import static com.aa.mtg.cards.properties.Type.ARTIFACT;
import static com.aa.mtg.cards.properties.Type.CREATURE;
import static com.aa.mtg.cards.properties.Type.INSTANT;
import static com.aa.mtg.cards.properties.Type.SORCERY;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

public class Ixalan implements MtgSet {

    public static final String XLN = "XLN";

    public static Card AIR_ELEMENTAL = new Card("Air Elemental", singleton(Color.BLUE), asList(Cost.BLUE, Cost.BLUE, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Elemental"), UNCOMMON, "Flying", 4, 4, singletonList(FLYING));
    public static Card ANCIENT_BRONTODON = new Card("Ancient Brontodon", singleton(Color.GREEN), asList(Cost.GREEN, Cost.GREEN, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), COMMON, "", 9, 9, emptyList());
    public static Card BISHOPS_SOLDIER = new Card("Bishop's Soldier", singleton(Color.WHITE), asList(Cost.WHITE, Cost.COLORLESS), singletonList(CREATURE), asList("Vampire", "Soldier"), COMMON, "", 2, 2, singletonList(LIFELINK));
    public static Card BRIGHT_REPRISAL = new Card("Bright Reprisal", singleton(Color.WHITE), asList(Cost.WHITE, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(INSTANT), emptyList(), UNCOMMON, "Destroy target attacking creature. Draw a card.", 0, 0, asList(TARGET_ATTACKING_CREATURE_GETS_DESTROYED, DRAW_1_CARD));
    public static Card CHARGING_MONSTROSAUR = new Card("Charging Monstrosaur", singleton(Color.RED), asList(Cost.RED, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), UNCOMMON, "Trample, haste", 5, 5, asList(TRAMPLE, HASTE));
    public static Card COBBLED_WINGS = new Card("Cobbled Wings", emptySet(), asList(Cost.COLORLESS, Cost.COLORLESS), singletonList(ARTIFACT), singletonList("Equipment"), COMMON, "Equipped creature has flying. Equip 1", 0, 0, singletonList(PAY_1_EQUIP_CREATURE_GETS_FLYING));
    public static Card COLOSSAL_DREADMAW = new Card("Colossal Dreadmaw", singleton(Color.GREEN), asList(Cost.GREEN, Cost.GREEN, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), COMMON, "Trample", 6, 6, singletonList(TRAMPLE));
    public static Card FRENZIED_RAPTOR = new Card("Frenzied Raptor", singleton(Color.RED), asList(Cost.RED, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), COMMON, "", 4, 2, emptyList());
    public static Card GRAZING_WHIPTAIL = new Card("Grazing Whiptail", singleton(Color.GREEN), asList(Cost.GREEN, Cost.GREEN, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), COMMON, "", 3, 4, singletonList(REACH));
    public static Card HEADWATER_SENTRIES = new Card("Headwater Sentries", singleton(Color.BLUE), asList(Cost.BLUE, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(CREATURE), singletonList("Merfolk Warrior"), COMMON, "", 2, 5, emptyList());
    public static Card HUATLIS_SNUBHORN = new Card("Huatli's Snubhorn", singleton(Color.WHITE), asList(Cost.WHITE, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), COMMON, "Vigilance", 2, 2, singletonList(VIGILANCE));
    public static Card LEGIONS_JUDGMENT = new Card("Legion's Judgment", singleton(Color.WHITE), asList(Cost.WHITE, Cost.COLORLESS, Cost.COLORLESS), singletonList(Type.SORCERY), emptyList(), COMMON, "Destroy target creature with power 4 or greater.", 0, 0, singletonList(TARGET_CREATURE_WITH_POWER_GREATER_OR_EQUAL_4_GETS_DESTROYED));
    public static Card MARK_OF_THE_VAMPIRE = new Card("Mark of the Vampire", singleton(Color.BLACK), asList(Cost.BLACK, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(Type.ENCHANTMENT), singletonList("Aura"), COMMON, "Enchant creature. Enchanted creature gets +2/+2 and has lifelink.", 0, 0, singletonList(ENCHANTED_CREATURE_GETS_PLUS_2_2_AND_LIFELINK));
    public static Card NEST_ROBBER = new Card("Nest Robber", singleton(Color.RED), asList(Cost.RED, Cost.COLORLESS), singletonList(CREATURE), singletonList("Dinosaur"), COMMON, "Haste", 2, 1, singletonList(HASTE));
    public static Card ONE_WITH_THE_WIND = new Card("One With the Wind", singleton(Color.BLUE), asList(Cost.BLUE, Cost.COLORLESS), singletonList(Type.ENCHANTMENT), singletonList("Aura"), COMMON, "Enchant creature. Enchanted creature gets +2/+2 and has flying.", 0, 0, singletonList(ENCHANTED_CREATURE_GETS_PLUS_2_2_AND_FLYING));
    public static Card RILE = new Card("Rile", singleton(Color.RED), singletonList(Cost.RED), singletonList(SORCERY), emptyList(), COMMON, "Rile deals 1 damage to target creature you control. That creature gains trample until end of turn. Draw a card.", 0, 0, asList(DEAL_1_DAMAGE_TO_CREATURE_YOU_CONTROL_THAT_CREATURE_GAINS_TRAMPLE, DRAW_1_CARD));
    public static Card SWASHBUCKLING = new Card("SwashBuckling", singleton(Color.RED), asList(Cost.RED, Cost.COLORLESS), singletonList(Type.ENCHANTMENT), singletonList("Aura"), COMMON, "Enchant creature. Enchanted creature gets +2/+2 and has haste.", 0, 0, singletonList(ENCHANTED_CREATURE_GETS_PLUS_2_2_AND_HASTE));

    private static Ixalan instance;

    private List<Card> cards = new ArrayList<>();

    private Ixalan() {
        cards.add(AIR_ELEMENTAL);
        cards.add(ANCIENT_BRONTODON);
        cards.add(BISHOPS_SOLDIER);
        cards.add(BRIGHT_REPRISAL);
        cards.add(CHARGING_MONSTROSAUR);
        cards.add(COBBLED_WINGS);
        cards.add(COLOSSAL_DREADMAW);
        cards.add(FRENZIED_RAPTOR);
        cards.add(GRAZING_WHIPTAIL);
        cards.add(HEADWATER_SENTRIES);
        cards.add(HUATLIS_SNUBHORN);
        cards.add(LEGIONS_JUDGMENT);
        cards.add(MARK_OF_THE_VAMPIRE);
        cards.add(NEST_ROBBER);
        cards.add(ONE_WITH_THE_WIND);
        cards.add(RILE);
        cards.add(SWASHBUCKLING);
    }

    @Override
    public String getName() {
        return "Ixalan";
    }

    @Override
    public String getCode() {
        return XLN;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    public static Ixalan ixalan() {
        if (instance == null) {
            instance = new Ixalan();
        }
        return instance;
    }
}
