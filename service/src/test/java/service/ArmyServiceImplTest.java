package service;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.*;
import ar.edu.itba.service.ArmyServiceImpl;
import ar.edu.itba.service.SectorServiceImpl;
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

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ArmyServiceImplTest {

  @InjectMocks
  private ArmyServiceImpl armyService;

  @Mock
  private ArmyDao armyDao;

  @Mock
  private TroopService troopService;

  @Mock
  private User user;

  @Mock
  private Point p;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testSplitArmy() {
    Army a = new Army(p, user, true);
    a.setIdArmy(5);
    Army b = new Army(p, user, true);
    b.setTroops(new ArrayList<>());
    b.setIdArmy(6);
    List<Troop> troops = new ArrayList<>();
    troops.add(new Troop(a, TroopType.warrior.getType(), 10));
    troops.add(new Troop(a, TroopType.archer.getType(), 20));
    troops.add(new Troop(a, TroopType.horseman.getType(), 5));
    a.setTroops(troops);
    when(armyService.getArmyById(5)).thenReturn(a);
    when(armyDao.addArmy(p, user, false)).thenReturn(b);
    doAnswer(inv -> b.getTroops().add(new Troop(b, inv.getArgumentAt(1, Integer.class),
      inv.getArgumentAt(2, Integer.class)))).when(troopService).addTroop(6, 0, 2);
    doAnswer(inv -> b.getTroops().add(new Troop(b, inv.getArgumentAt(1, Integer.class),
      inv.getArgumentAt(2, Integer.class)))).when(troopService).addTroop(6, 1, 7);
    Map<TroopType, Integer> toSplit = new HashMap<>();
    toSplit.put(TroopType.warrior, 2);
    toSplit.put(TroopType.archer, 7);
    Army newArmy = armyService.splitArmy(5, toSplit);
    List<Troop> shouldBeNewArmy = new ArrayList<>();
    shouldBeNewArmy.add(new Troop(newArmy, TroopType.warrior.getType(), 2));
    shouldBeNewArmy.add(new Troop(newArmy, TroopType.archer.getType(), 7));
    shouldBeNewArmy.forEach(army -> assertTrue(newArmy.getTroops().stream()
      .anyMatch(t -> t.getType() == army.getType() && t.getQuantity() == army.getQuantity())));
  }
}
