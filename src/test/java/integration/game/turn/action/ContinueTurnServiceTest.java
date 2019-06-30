package integration.game.turn.action;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.Cards;
import com.aa.mtg.game.event.Event;
import com.aa.mtg.game.event.EventSender;
import com.aa.mtg.game.player.LifeService;
import com.aa.mtg.game.status.GameStatus;
import com.aa.mtg.game.turn.Turn;
import com.aa.mtg.game.turn.action.ContinueTurnService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.IntStream;

import static com.aa.mtg.game.turn.phases.CleanupPhase.CL;
import static com.aa.mtg.game.turn.phases.DrawPhase.DR;
import static com.aa.mtg.game.turn.phases.EndTurnPhase.ET;
import static com.aa.mtg.game.turn.phases.Main1Phase.M1;
import static com.aa.mtg.game.turn.phases.UntapPhase.UT;
import static com.aa.mtg.game.turn.phases.UpkeepPhase.UP;
import static integration.utils.TestUtils.testGameStatus;
import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ContinueTurnServiceTest.ContinueTurnServiceTestConfiguration.class)
public class ContinueTurnServiceTest {
    @Autowired
    private ContinueTurnService continueTurnService;

    @Autowired
    private EventSender eventSender;

    private GameStatus gameStatus = testGameStatus();
    private CardInstance aCard = new CardInstance(gameStatus, 1, Cards.FOREST, "owner");

    @Test
    public void testContinueTurnUntapPlayer() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(UT);
        turn.setCurrentPhaseActivePlayer("player-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(UP);
        expectedTurn.setCurrentPhaseActivePlayer("player-name");
        expectedTurn.addCardToCardsPlayedWithinTurn(aCard);
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_ACTIVE_PLAYER_BATTLEFIELD", gameStatus.getPlayer1().getBattlefield().getCards())
        );

        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn)
        );
    }

    @Test
    public void testContinueTurnUpkeepPlayer() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(UP);
        turn.setCurrentPhaseActivePlayer("player-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(UP);
        expectedTurn.setCurrentPhaseActivePlayer("opponent-name");
        expectedTurn.addCardToCardsPlayedWithinTurn(aCard);
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn)
        );
    }

    @Test
    public void testContinueTurnUpkeepOpponent() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(UP);
        turn.setCurrentPhaseActivePlayer("opponent-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(DR);
        expectedTurn.setCurrentPhaseActivePlayer("player-name");
        expectedTurn.addCardToCardsPlayedWithinTurn(aCard);
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn)
        );
    }

    @Test
    public void testContinueTurnDrawPlayer() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(DR);
        turn.setCurrentPhaseActivePlayer("player-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(M1);
        expectedTurn.setCurrentPhaseActivePlayer("player-name");
        expectedTurn.addCardToCardsPlayedWithinTurn(aCard);
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn));
    }

    @Test
    public void testContinueTurnDrawPlayerSecondTurn() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(2);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(DR);
        turn.setCurrentPhaseActivePlayer("player-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        BDDMockito.verify(eventSender).sendToPlayer(gameStatus.getPlayer1(),
                new Event("UPDATE_ACTIVE_PLAYER_HAND", gameStatus.getCurrentPlayer().getHand().getCards()));
        BDDMockito.verify(eventSender).sendToPlayer(gameStatus.getPlayer2(),
                new Event("UPDATE_ACTIVE_PLAYER_HAND", gameStatus.getCurrentPlayer().getHand().maskedHand()));

        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(2);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(M1);
        expectedTurn.setCurrentPhaseActivePlayer("player-name");
        expectedTurn.addCardToCardsPlayedWithinTurn(aCard);
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn));
    }

    @Test
    public void testContinueTurnM1Player() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(M1);
        turn.setCurrentPhaseActivePlayer("player-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(M1);
        expectedTurn.setCurrentPhaseActivePlayer("opponent-name");
        expectedTurn.addCardToCardsPlayedWithinTurn(aCard);
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn));
    }

    @Test
    public void testContinueTurnET() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(ET);
        turn.setCurrentPhaseActivePlayer("player-name");

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(ET);
        expectedTurn.setCurrentPhaseActivePlayer("opponent-name");
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn));
    }

    @Test
    public void testContinueTurnETDiscardACard() {
        // Given
        IntStream.rangeClosed(1, 8).forEach(i -> gameStatus.getPlayer1().getHand().addCard(aCard));

        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(ET);
        turn.setCurrentPhaseActivePlayer("player-name");

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(1);
        expectedTurn.setCurrentTurnPlayer("player-name");
        expectedTurn.setCurrentPhase(ET);
        expectedTurn.setCurrentPhaseActivePlayer("player-name");
        expectedTurn.setTriggeredNonStackAction("DISCARD_A_CARD");
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn));
    }

    @Test
    public void testContinueTurnCLPlayer() {
        // Given
        Turn turn = new Turn();
        turn.setTurnNumber(1);
        turn.setCurrentTurnPlayer("player-name");
        turn.setCurrentPhase(CL);
        turn.setCurrentPhaseActivePlayer("player-name");
        turn.addCardToCardsPlayedWithinTurn(aCard);

        ReflectionTestUtils.setField(gameStatus, "turn", turn);

        // When
        continueTurnService.continueTurn(gameStatus);

        // Then
        Turn expectedTurn = new Turn();
        expectedTurn.setTurnNumber(2);
        expectedTurn.setCurrentTurnPlayer("opponent-name");
        expectedTurn.setCurrentPhase(UT);
        expectedTurn.setCurrentPhaseActivePlayer("opponent-name");
        BDDMockito.verify(eventSender).sendToPlayers(
                asList(gameStatus.getPlayer1(), gameStatus.getPlayer2()),
                new Event("UPDATE_TURN", expectedTurn));
    }

    @Configuration
    @ComponentScan(basePackageClasses = {Turn.class, GameStatus.class, CardInstance.class, LifeService.class})
    public static class ContinueTurnServiceTestConfiguration {
        @Bean
        public EventSender eventSender() {
            return Mockito.mock(EventSender.class);
        }
    }
}