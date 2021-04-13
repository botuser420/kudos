package scripts.quests.miniquest.varrockmuseum;

import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.data.ItemCollections;
import scripts.data.ItemID;
import scripts.data.NpcID;
import scripts.data.ObjectID;
import scripts.framework.event.EventFramework;
import scripts.utilities.ObjectUtil;
import scripts.quests.data.QuestHelperQuest;
import scripts.quests.requirements.Requirement;
import scripts.quests.requirements.item.ItemRequirement;
import scripts.quests.requirements.item.ItemRequirements;
import scripts.quests.requirements.quest.QuestRequirement;
import scripts.quests.requirements.quest.QuestState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CleanSpecimen extends EventFramework {

    private final ItemRequirements cleaningKit = new ItemRequirements("Cleaning Kit", new ItemRequirement("Rock pick", ItemID.ROCK_PICK), new ItemRequirement("Rock pick", ItemID.ROCK_PICK),
            new ItemRequirement("Specimin brush", ItemID.SPECIMEN_BRUSH),
            new ItemRequirement("Leather gloves", ItemID.LEATHER_GLOVES, 1, true),
            new ItemRequirement("Leather boots", ItemID.LEATHER_BOOTS, 1, true));

    private long lastAnimation = 0;
    private boolean gotInstructions = false, completed = false;


    @Override
    public void execute() {
        int animationId = Player.getAnimation();
        Optional<Display> currentDisplay = hasItem();

        if (animationId == 6217 || animationId == 6459) {
            lastAnimation = System.currentTimeMillis();
        }

        if (!cleaningKit.check()) {
            if (NPCChat.getOptions() != null) {
                Keyboard.typeKeys('1');
            } else if (ObjectUtil.click(ObjectID.TOOLS_24535, "Take", new RSTile(3259, 3443, 0)))
                Sleep.till(() -> NPCChat.getOptions() != null);
        } else if (currentDisplay.isPresent()) {
            if (!gotInstructions) {
                checkDialog(NPC.talkTo(new RSTile(3265, 3444, 0), Arrays.asList("I found something interesting."), NpcID.SINCO_DOAR, NpcID.TINSE_TORPE));
            } else if (Walk.shouldWalk(currentDisplay.get().getLocation()))
                Walk.walkTo(currentDisplay.get().getLocation());
            else if (Invent.useItemOnObject(new int[]{currentDisplay.get().getItemId()}, new int[]{currentDisplay.get().getCaseId()})) {
                if (Sleep.till(() -> currentDisplay.get().isFinished()))
                    gotInstructions = false;
            }
        } else if (!isCleaning()) {
            if (Arrays.stream(Inventory.getAll()).anyMatch(rsItem -> ItemCollections.getCleanedFinds().contains(rsItem.getID()))) {
                if (Game.getItemSelectionState() == 1)
                    Mouse.click(1);
                Inventory.dropByIds(ItemCollections.getCleanedFinds());
            } else if (!Inventory.isFull() && ObjectUtil.click(ObjectID.DIG_SITE_SPECIMEN_ROCKS, "Take", new RSTile(3263, 3445, 0)))
                Sleep.small();
            else if (Inventory.getCount(ItemID.UNCLEANED_FIND) > 0 && ObjectUtil.click(ObjectID.SPECIMEN_TABLE_24556, "Clean", new RSTile(3262, 3444, 0))) {
                if (Sleep.till(() -> Player.getAnimation() == 6217))
                    lastAnimation = System.currentTimeMillis();
            }

        }
    }

    private boolean isCleaning() {
        return Inventory.getCount(ItemID.UNCLEANED_FIND) > 0 && Timing.timeFromMark(lastAnimation) < 3000;
    }

    private Optional<Display> hasItem() {
        return Arrays.stream(Display.values())
                .filter(display -> Inventory.getCount(display.getItemId()) > 0)
                .filter(display -> !display.isFinished())
                .findFirst();
    }

    private void checkDialog(ArrayList<String> dialog) {
        if (dialog != null) {
            gotInstructions = dialog.stream().anyMatch(s -> s.contains(" case "));
        }
    }

    @Override
    public String toString() {
        return "Kudo Collector";
    }

    @Override
    public String getStatus() {
        return "Cleaning specimen";
    }

    @Override
    public boolean isCompleted() {
        if (!completed)
            completed = Arrays.stream(Display.values()).allMatch(Display::isFinished);
        return completed;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return Arrays.asList(new QuestRequirement(QuestHelperQuest.THE_DIG_SITE, QuestState.FINISHED));
    }

}

