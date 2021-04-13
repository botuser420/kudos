package scripts.quests.miniquest.varrockmuseum;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.data.ItemID;

enum Display {
    POTTERY(ItemID.POTTERY, 12139, 1536, new RSTile(3260, 3452, 0)),
    ANCIENT_COIN(ItemID.ANCIENT_COIN, 12234, 882, new RSTile(3260, 3450, 0)),
    OLD_COIN(ItemID.OLD_COIN, 15484, 882, new RSTile(3260, 3448, 0)),
    ANCIENT_SYMBOL(ItemID.ANCIENT_SYMBOL, 12138, 882, new RSTile(3263, 3450, 0)),
    OLD_SYMBOL(ItemID.OLD_SYMBOL, 12137, 882, new RSTile(3263, 3448, 0));

    int itemId;
    int caseId;
    int pointCount;
    RSTile location;

    public RSTile getLocation() {
        return location;
    }

    public int getItemId() {
        return itemId;
    }

    public int getCaseId() {
        return caseId;
    }

    public boolean isFinished() {
        RSObject[] displayObject = Objects.findNearest(25, caseId);
        if (displayObject.length > 0) {
            RSModel model = displayObject[0].getModel();
            return model != null && model.getPoints().length != pointCount;
        }
        return false;
    }

    Display(int itemId, int caseId, int pointCount, RSTile location) {
        this.itemId = itemId;
        this.caseId = caseId;
        this.pointCount = pointCount;
        this.location = location;
    }
}
