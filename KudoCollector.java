package scripts;

import org.tribot.api.General;
import org.tribot.api2007.GrandExchange;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripts.dax.api_lib.DaxWalker;
import scripts.dax.api_lib.models.DaxCredentials;
import scripts.dax.api_lib.models.DaxCredentialsProvider;
import scripts.framework.event.Event;
import scripts.framework.event.EventSet;
import scripts.items.ItemList;
import scripts.items.Restock;
import scripts.paint.PaintInfo;
import scripts.paint.SimplePaint;
import scripts.quests.miniquest.varrockmuseum.CleanSpecimen;
import scripts.quests.miniquest.varrockmuseum.NaturalHistoryQuiz;

import java.awt.*;

@ScriptManifest(category = "Tools", authors = {"Botuser420"}, name = "Kudo Collector")
public class KudoCollector extends Script implements PaintInfo, Painting, Starting {

    private EventSet events = new EventSet();
    private Event currentEvent;
    private boolean run = true;

    private final SimplePaint PAINT = new SimplePaint(this, SimplePaint.PaintLocations.TOP_MID_PLAY_SCREEN,
            new Color[]{new Color(255, 251, 255)}, "Trebuchet MS", new Color[]{new Color(50, 50, 50, 128)},
            new Color[]{new Color(50, 50, 50)}, 1, false, 5, 3, 0);

    public String[] getPaintInfo() {
        return new String[]{
                "Runtime: " + PAINT.getRuntimeString(),
                currentEvent != null ? currentEvent.toString() : "-",
                currentEvent != null ? currentEvent.getStatus() : "-",
        };
    }

    @Override
    public void run() {
        events.addAll(new NaturalHistoryQuiz(), new CleanSpecimen());
        currentEvent = events.getActiveTask();
        while (run) {
            if (currentEvent == null || currentEvent.isCompleted()) {
                currentEvent = events.getValidTask();
                if (currentEvent == null) {
                    General.println("Couldn't get a valid task. Stopping script");
                    run = false;
                }
            } else if (!currentEvent.isActive() && (currentEvent.getItemRequirements() != null && !ItemList.has(currentEvent.getItemRequirements()))) {
                if (Restock.buy(currentEvent.getItemRequirements()))
                    GrandExchange.close();
            } else
                currentEvent.execute();
            Sleep.small();
        }
    }

    @Override
    public void onPaint(Graphics graphics) {
        PAINT.paint(graphics);
    }

    private void setKey() {
        DaxWalker.setCredentials(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });
    }

    @Override
    public void onStart() {
        setKey();
        DaxWalker.setGlobalWalkingCondition(Walk.getDefaultCondition());
    }
}
