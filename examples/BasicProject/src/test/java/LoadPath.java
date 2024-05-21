
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ThePinkAlliance.ChoreoExtended.ChoreoExtended;
import com.ThePinkAlliance.ChoreoExtended.EventCommandData;
import edu.wpi.first.hal.HAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadPath {

    @BeforeEach
    public void beforeAll() {
        HAL.initialize(500, 0);
    }

    @Test
    public void load() {
        var trajectory = ChoreoExtended.getTrajectory("OneEvent");

        for (var event : trajectory.getEvents()) {
            System.out.println(event.getTimestamp());
            for (var e : event.getData()) {
                assertEquals("test", e.getName());
                assertEquals("named", e.getType());
            }
        }
    }

    @Test
    public void loadMultipleEvents() {
        var trajectory = ChoreoExtended.getTrajectory("MultipleEvents");

        for (var event : trajectory.getEvents()) {
            for (int i = 0; i < event.getData().length; i++) {
                EventCommandData data = event.getData()[i];
                String name = data.getName();
                String type = data.getType();

                for (var e : event.getData()) {
                    System.out.println(e.getName());
                    System.out.println(e.getType());
                }

                /**
                 * 5/21/24 4:51PM
                 * Well data seserialization still needs some work. Currently there's a
                 * duplication bug with sequential commands also their command types aren't
                 * being processed correctly showing up as named instead of sequential, which
                 * could possibly be related to this duplication bug.
                 */
                if (i == 0) {
                    assertEquals("seq-1", name);
                    assertEquals("named", type);
                } else if (i == 1) {
                    assertEquals("seq-2", name);
                    assertEquals("named", type);
                } else if (i == 2) {
                    assertEquals("point-2", name);
                    assertEquals("named", type);
                }
            }
        }
    }

}
