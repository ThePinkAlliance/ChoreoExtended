
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ThePinkAlliance.ChoreoExtended.ChoreoExtended;
import com.ThePinkAlliance.ChoreoExtended.EventCommandData;
import com.ThePinkAlliance.ChoreoExtended.EventMarker;
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

        for (int i = 0; i < trajectory.getEvents().size(); i++) {
            EventMarker event = trajectory.getEvents().get(i);

            if (i == 0) {
                var expected = new EventCommandData[] { new EventCommandData("named", "seq-1"),
                        new EventCommandData("named",
                                "seq-2") };
                var actual = event.getData();

                assertEquals(expected[0].getName(), actual[0].getName());
                assertEquals(expected[1].getName(), actual[1].getName());
            } else if (i == 1) {
                assertEquals("point-2", event.getData()[0].getName());
            }

        }
    }

}
