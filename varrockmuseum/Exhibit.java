package scripts.quests.miniquest.varrockmuseum;

import org.tribot.api2007.types.RSTile;
import scripts.NPC;
import scripts.quests.data.QuestHelperQuest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Exhibit {
    LIZARD(3675, 24605, new RSTile(1743, 4977, 0), Arrays.asList("Sunlight.", "The Slayer Masters.", "Three.", "Squamata.", "It becomes sleepy.", "Hair.")),
    TORTOISE(3680, 24606, new RSTile(1753, 4977, 0), Arrays.asList("Mibbiwocket.", "Vegetables.", "Admiral Bake.", "Hard shell.", "Twenty years.", "Gnomes.")),
    DRAGON(3672, 24607, new RSTile(1768, 4977, 0), Arrays.asList("Runite.", "Anti dragon-breath shield.", "Unknown.", "Elemental.", "Old battle sites.", "Twelve.")),
    WYVERN(3681, 24608, new RSTile(1778, 4977, 0), Arrays.asList("Climate change.", "Two.", "Asgarnia.", "Reptiles.", "Dragons.", "Below room temperature.")),
    SNAIL(3674, 24613, new RSTile(1776, 4962, 0), Arrays.asList("It is resistant to acid.", "Spitting acid.", "Fireproof oil.", "Acid-spitting snail.", "Contracting and stretching.", "An operculum.")),
    SNAKE(3677, 24614, new RSTile(1783, 4962, 0), Arrays.asList("Stomach acid.", "Tongue.", "Seeing how you smell.", "Constriction.", "Squamata.", "Anywhere.")),
    SLUG(3682, 24616, new RSTile(1781, 4958, 0), Arrays.asList("Nematocysts.", "The researchers keep vanishing.", "Seaweed.", "Defense or display.", "Ardougne.", "They have a hard shell.")),
    MONKEY(3676, 24615, new RSTile(1774, 4958, 0), Arrays.asList("Simian.", "Harmless.", "Bitternuts.", "Red.", "Seaweed.")),
    KALPHITE(3684, 24618, new RSTile(1761, 4938, 0), Arrays.asList("Pasha.", "Worker.", "Lamellae.", "Carnivores.", "Scarab beetles.", "Scabaras.")),
    TERRORBIRD(3683, 24617, new RSTile(1756, 4940, 0), Arrays.asList("Anything.", "Gnomes.", "Eating plants.", "Stones.", "0.", "Four.")),
    PENGUIN(3673, 24612, new RSTile(1743, 4958, 0), Arrays.asList("Sight.", "Planning.", "A layer of fat.", "Cold.", "Social.", "During breeding.")),
    MOLE(3678, 24611, new RSTile(1735, 4958, 0), Arrays.asList("Subterranean.", "They dig holes.", "Wyson the Gardener.", "A labour.", "Insects and other invertebrates.", "The Talpidae family.")),
    CAMEL(3679, 24609, new RSTile(1737, 4962, 0), Arrays.asList("Toxic dung.", "Two.", "Omnivore.", "Annoyed.", "Al Kharid.", "Milk.")),
    LEECH(3685, 24610, new RSTile(1744, 4962, 0), Arrays.asList("Water.", "'Y'-shaped.", "Apples.", "Environment.", "They attack by jumping.", "It doubles in size."));


    int varbit;
    int plaqueId;
    RSTile location;
    List<String> answers;

    public int getVarbit() {
        return varbit;
    }

    public int getPlaqueId() {
        return plaqueId;
    }

    public RSTile getLocation() {
        return location;
    }

    public List<String> getAnswers() {
        return answers;
    }

    Exhibit(int varbit, int plaqueId, RSTile location, List<String> answers) {
        this.varbit = varbit;
        this.plaqueId = plaqueId;
        this.location = location;
        this.answers = answers;
    }

}