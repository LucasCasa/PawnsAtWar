package service;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.service.TroopServiceImpl;
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

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class TroopServiceImplTest {

  @InjectMocks
  private TroopServiceImpl troopService;

  @Mock
  private TroopDao troopDao;

  @Mock
  private ArmyService armyService;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAmount() {

  }
}
