package com.aa.mtg.game.turn.action.selection;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.ability.Ability;
import com.aa.mtg.game.status.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.aa.mtg.cards.ability.Abilities.abilitiesFromParameters;
import static com.aa.mtg.cards.ability.Abilities.powerToughnessFromParameter;
import static com.aa.mtg.cards.ability.type.AbilityType.SELECTED_PERMANENTS_GET;

@Component
public class AbilitiesFromOtherPermanentsService {

    private final CardInstanceSelectorService cardInstanceSelectorService;

    @Autowired
    public AbilitiesFromOtherPermanentsService(CardInstanceSelectorService cardInstanceSelectorService) {
        this.cardInstanceSelectorService = cardInstanceSelectorService;
    }

    public int getPowerFromOtherPermanents(GameStatus gameStatus, CardInstance cardInstance) {
        int attachmentsPower = 0;

        for (String parameter : getParametersFromOtherPermanents(gameStatus, cardInstance)) {
            attachmentsPower += powerToughnessFromParameter(parameter).getPower();
        }

        return attachmentsPower;
    }

    public int getToughnessFromOtherPermanents(GameStatus gameStatus, CardInstance cardInstance) {
        int attachmentsToughness = 0;

        for (String parameter : getParametersFromOtherPermanents(gameStatus, cardInstance)) {
            attachmentsToughness += powerToughnessFromParameter(parameter).getToughness();
        }

        return attachmentsToughness;
    }

    public List<Ability> getAbilitiesFormOtherPermanents(GameStatus gameStatus, CardInstance cardInstance) {
        return abilitiesFromParameters(getParametersFromOtherPermanents(gameStatus, cardInstance));
    }

    private List<String> getParametersFromOtherPermanents(GameStatus gameStatus, CardInstance cardInstance) {
        List<String> parameters = new ArrayList<>();
        List<CardInstance> cards = gameStatus.getAllBattlefieldCards().withStaticAbility(SELECTED_PERMANENTS_GET).getCards();

        for (CardInstance card : cards) {
            for (Ability ability : card.getStaticAbilities()) {
                if (ability.getAbilityType() == SELECTED_PERMANENTS_GET) {
                    if (cardInstanceSelectorService.select(gameStatus, card, ability.getCardInstanceSelector()).withId(cardInstance.getId()).isPresent()) {
                        parameters.addAll(ability.getParameters());
                    }
                }
            }
        }

        return parameters;
    }
}
