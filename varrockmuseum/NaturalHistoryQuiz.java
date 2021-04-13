package scripts.quests.miniquest.varrockmuseum;

import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.types.RSVarBit;
import scripts.NPC;
import scripts.Sleep;
import scripts.Walk;
import scripts.framework.event.EventFramework;
import scripts.utilities.ObjectUtil;

import java.util.Arrays;

public class NaturalHistoryQuiz extends EventFramework {

    private final int QUESTION_MASTER = 533;
    private String status = "Starting mini quest";


    @Override
    public void execute() {
        if (isActive()) {
            for (Exhibit exhibit : Exhibit.values()) {
                if (RSVarBit.get(exhibit.getVarbit()).getValue() != 3) {
                    status = "Solving " + exhibit.name();
                    if (Walk.shouldWalk(exhibit.getLocation(), 5)) {
                        if (Walk.walkTo(exhibit.getLocation()))
                            Sleep.till(() -> !Player.isMoving());
                    } else if (Interfaces.isInterfaceSubstantiated(QUESTION_MASTER)) {
                        RSInterface answer = Interfaces.get(QUESTION_MASTER, i -> exhibit.getAnswers().contains(i.getText()));
                        if (answer != null && answer.click()) {
                            Sleep.till(() -> !Interfaces.isInterfaceSubstantiated(answer));
                        }
                    } else {
                        if (ObjectUtil.click(exhibit.getPlaqueId(), "Study"))
                            Sleep.till(() -> Interfaces.isInterfaceSubstantiated(QUESTION_MASTER));
                    }
                    return;
                }
            }
        }
        NPC.talkTo(new RSTile(1759, 4956, 0), Arrays.asList("Sure thing."), 6001);
    }

    @Override
    public String toString() {
        return "Natural History Quiz";
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean isCompleted() {
        return Game.getSetting(1014) < 0;
    }

    @Override
    public boolean isActive() {
        return Game.getSetting(1014) > 0;
    }
}
