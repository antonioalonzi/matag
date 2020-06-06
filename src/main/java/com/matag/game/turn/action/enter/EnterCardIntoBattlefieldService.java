package com.matag.game.turn.action.enter;

import com.matag.game.cardinstance.CardInstance;
import com.matag.game.status.GameStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.matag.cards.properties.Type.CREATURE;

@Component
@AllArgsConstructor
public class EnterCardIntoBattlefieldService {
  private static final Logger LOGGER = LoggerFactory.getLogger(EnterCardIntoBattlefieldService.class);

  private final EntersTheBattlefieldWithService entersTheBattlefieldWithService;
  private final WhenEnterTheBattlefieldService whenEnterTheBattlefieldService;

  public void enter(GameStatus gameStatus, CardInstance cardInstance) {
    String controller = cardInstance.getController();
    gameStatus.getPlayerByName(controller).getBattlefield().addCard(cardInstance);

    cardInstance.getModifiers().setPermanentId(gameStatus.nextCardId());

    if (cardInstance.isOfType(CREATURE)) {
      cardInstance.getModifiers().setSummoningSickness(true);
    }

    LOGGER.info(cardInstance.getIdAndName() + " entered the battlefield.");

    entersTheBattlefieldWithService.entersTheBattlefieldWith(gameStatus, cardInstance);
    whenEnterTheBattlefieldService.whenTriggered(gameStatus, cardInstance);
  }

}
