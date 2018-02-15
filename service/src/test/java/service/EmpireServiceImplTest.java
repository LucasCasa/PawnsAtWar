package service;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.service.EmpireServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EmpireServiceImplTest {

  @InjectMocks
  private EmpireServiceImpl empireService;

  @Mock
  private EmpireDao empireDao;

  @Mock
  private SectorService sectorService;

  @Mock
  private ArmyService armyService;

  @Mock
  private CommerceService commerceService;

  @Mock
  private ResourceDao resourceDao;

  @Mock
  private ScheduleService scheduleService;

  @Mock
  private AlertService alertService;

  @Mock
  private User user;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testTimeElapsed() {
    List<Resource> resources = new ArrayList<>();
    resources.add(new Resource(0, 1000));
    resources.add(new Resource(1, 2000));
    when(resourceDao.getResources(user)).thenReturn(resources);
    when(user.getEmpire()).thenReturn(mock(Empire.class));
    when(user.getEmpire().getLastUpdate()).thenReturn(Date.from(new Date().toInstant().minusSeconds(3600)));
    assertEquals(empireService.timeLapsed(user), 3600);
  }

  @Test
  public void testStorage() {
    List<Sector> s = new ArrayList<>();
    s.add(new Sector(user, mock(Point.class), SectorType.CASTLE.getType(), 1));
    s.add(new Sector(user, mock(Point.class), SectorType.CASTLE.getType(), 3));
    when(empireDao.getBuilding(user, SectorType.CASTLE.getType())).thenReturn(s);
    assertEquals(empireService.getMaxStorage(user), 3000 + 1000 * 3 + 729 + 1000 + 27);
  }

  @Test
  public void testValidCastlePosition() {
    Point location = new Point(50, 50);
    List<List<Sector>> sectorList = new ArrayList<>();
    List<Sector> sectors = new ArrayList<>();
    sectors.add(new Sector(null, mock(Point.class), 0, 0));
    sectorList.add(sectors);
    when(alertService.getBuildingConstructed(location)).thenReturn(new ArrayList<>());
    when(sectorService.getSector(location, 2)).thenReturn(sectorList);
    assertTrue(empireService.validCastlePosition(location));
  }

  @Test
  public void testValidCastlePositionWhenAlreadyConstructing() {
    Point location = new Point(50, 50);
    List<Alert> beingConstructed = new ArrayList<>();
    beingConstructed.add(mock(Alert.class));
    when(alertService.getBuildingConstructed(location)).thenReturn(beingConstructed);
    assertFalse(empireService.validCastlePosition(location));
  }

  @Test
  public void testValidCastlePositionWhenInRangeOfOwned() {
    Point location = new Point(50, 50);
    List<List<Sector>> sectorList = new ArrayList<>();
    List<Sector> sectors = new ArrayList<>();
    sectors.add(new Sector(user, mock(Point.class), 0, 0));
    sectorList.add(sectors);
    when(sectorService.getSector(location, 2)).thenReturn(sectorList);
    when(alertService.getBuildingConstructed(location)).thenReturn(new ArrayList<>());
    assertFalse(empireService.validCastlePosition(location));
  }
}
