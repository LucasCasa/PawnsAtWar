package service;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.service.ArmyServiceImpl;
import ar.edu.itba.service.SectorServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ArmyServiceImplTest {

    private ArmyServiceImpl armyService;

    @Mock
    private ArmyDao armyDao;

    @Mock
    private TroopService troopService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        armyService = new ArmyServiceImpl();
        armyService.setArmyDao(armyDao);
        armyService.setTroopService(troopService);
    }

    @Test
    public void testAddArmy(){

    }
}
