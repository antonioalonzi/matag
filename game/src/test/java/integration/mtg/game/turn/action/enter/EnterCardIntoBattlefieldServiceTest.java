package integration.mtg.game.turn.action.enter;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.CardInstanceFactory;
import com.aa.mtg.game.status.GameStatus;
import com.aa.mtg.game.turn.action.draw.DrawXCardsService;
import com.aa.mtg.game.turn.action.enter.EnterCardIntoBattlefieldService;
import com.google.common.collect.ImmutableMap;
import integration.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.aa.mtg.cards.Cards.SWAMP;
import static com.aa.mtg.cards.ability.Abilities.WHEN_IT_ENTERS_THE_BATTLEFIELD_TARGET_CREATURE_GETS_PLUS_2_2;
import static com.aa.mtg.cards.sets.CoreSet2019.DIREGRAF_GHOUL;
import static com.aa.mtg.cards.sets.RivalsOfIxalan.JADECRAFT_ARTISAN;
import static com.aa.mtg.cards.sets.ThroneOfEldraine.ARDENVALE_PALADIN;
import static com.aa.mtg.cards.sets.ThroneOfEldraine.CLOCKWORK_SERVANT;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EnterTestConfiguration.class)
public class EnterCardIntoBattlefieldServiceTest {

  @Autowired
  private EnterCardIntoBattlefieldService enterCardIntoBattlefieldService;

  @Autowired
  private CardInstanceFactory cardInstanceFactory;

  @Autowired
  private TestUtils testUtils;

  @Autowired
  private DrawXCardsService drawXCardsService;

  @Test
  public void enterTheBattlefield() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, SWAMP, "player-name");
    card.setController("player-name");

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    assertThat(card.getModifiers().getPermanentId()).isGreaterThan(0);
    assertThat(gameStatus.getPlayer1().getBattlefield().getCards()).contains(card);
  }

  @Test
  public void enterTheBattlefieldOpponent() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, SWAMP, "opponent-name");
    card.setController("opponent-name");

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    assertThat(card.getModifiers().getPermanentId()).isGreaterThan(0);
    assertThat(gameStatus.getPlayer2().getBattlefield().getCards()).contains(card);
  }

  @Test
  public void enterTheBattlefieldTapped() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, DIREGRAF_GHOUL, "player-name");
    card.setController("player-name");

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    card.getModifiers().isTapped();
  }

  @Test
  public void enterTheBattlefieldTrigger() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, JADECRAFT_ARTISAN, "player-name");
    card.setController("player-name");

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    assertThat(gameStatus.getStack().getItems()).contains(card);
    assertThat(card.getTriggeredAbilities()).contains(WHEN_IT_ENTERS_THE_BATTLEFIELD_TARGET_CREATURE_GETS_PLUS_2_2);
  }

  @Test
  public void enterTheBattlefieldAdamantTriggered() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, ARDENVALE_PALADIN, "player-name");
    card.setController("player-name");

    gameStatus.getTurn().setLastManaPaid(ImmutableMap.of(
        1, singletonList("WHITE"),
        2, singletonList("WHITE"),
        3, singletonList("WHITE"),
        4, singletonList("BLUE")
    ));

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    assertThat(card.getModifiers().getCounters().getPlus1Counters()).isEqualTo(1);
  }

  @Test
  public void enterTheBattlefieldAdamantNotTriggered() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, ARDENVALE_PALADIN, "player-name");
    card.setController("player-name");

    gameStatus.getTurn().setLastManaPaid(ImmutableMap.of(
            1, singletonList("WHITE"),
            2, singletonList("WHITE"),
            3, singletonList("BLUE"),
            4, singletonList("BLUE")
    ));

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    assertThat(card.getModifiers().getCounters().getPlus1Counters()).isEqualTo(0);
  }

  @Test
  public void enterTheBattlefieldAdamantSameTriggered() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, CLOCKWORK_SERVANT, "player-name");
    card.setController("player-name");

    gameStatus.getTurn().setLastManaPaid(ImmutableMap.of(
            1, singletonList("WHITE"),
            2, singletonList("WHITE"),
            3, singletonList("BLUE"),
            4, singletonList("BLUE")
    ));

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    verifyZeroInteractions(drawXCardsService);
  }

  @Test
  public void enterTheBattlefieldAdamantSameNotTriggered() {
    // Given
    GameStatus gameStatus = testUtils.testGameStatus();
    CardInstance card = cardInstanceFactory.create(gameStatus, 100, CLOCKWORK_SERVANT, "player-name");
    card.setController("player-name");

    gameStatus.getTurn().setLastManaPaid(ImmutableMap.of(
            1, singletonList("BLACK"),
            2, singletonList("BLACK"),
            3, singletonList("BLUE"),
            4, singletonList("BLACK")
    ));

    // When
    enterCardIntoBattlefieldService.enter(gameStatus, card);

    // Then
    verify(drawXCardsService).drawXCards(gameStatus.getPlayer1(), 1);
  }
}