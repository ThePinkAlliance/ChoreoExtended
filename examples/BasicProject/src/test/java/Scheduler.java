import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.ThePinkAlliance.ChoreoExtended.ChoreoExtended;
import com.ThePinkAlliance.ChoreoExtended.ChoreoTrajectoryState;
import com.ThePinkAlliance.ChoreoExtended.EventCommandData;
import com.ThePinkAlliance.ChoreoExtended.EventMarker;
import com.ThePinkAlliance.ChoreoExtended.EventScheduler;
import com.ThePinkAlliance.ChoreoExtended.actions.InstantAction;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Scheduler {

  @BeforeEach
  public void beforeAll() {
    HAL.initialize(500, 0);
  }

  @Test
  public void load() {
    EventScheduler scheduler = new EventScheduler();
    var trajectory = ChoreoExtended.getTrajectory("OneEvent");

    Timer timer = new Timer();

    scheduler.loadEvents(trajectory.getEvents(), List.of(new InstantAction(() -> System.out.println("HI!!!"), "test")));

    timer.start();
    while (!timer.hasElapsed(trajectory.getTotalTime())) {
      scheduler.run(() -> timer.get());
    }
  }
}
