package com.matag.game.turn.action.enter;

import com.matag.cards.ability.trigger.TriggerSubtype;
import com.matag.game.turn.action.selection.CardInstanceSelectorService;
import com.matag.game.turn.action.when.WhenTriggerService;
import org.springframework.stereotype.Component;

import static com.matag.cards.ability.trigger.TriggerSubtype.WHEN_ENTER_THE_BATTLEFIELD;

@Component
public class WhenEnterTheBattlefieldService extends WhenTriggerService {
  public WhenEnterTheBattlefieldService(CardInstanceSelectorService cardInstanceSelectorService) {
    super(cardInstanceSelectorService);
  }

  @Override
  public TriggerSubtype triggerSubtype() {
    return WHEN_ENTER_THE_BATTLEFIELD;
  }
}
