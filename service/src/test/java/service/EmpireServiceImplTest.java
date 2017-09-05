package service;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.service.EmpireServiceImpl;
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
public class EmpireServiceImplTest {

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

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        empireService = new EmpireServiceImpl();
        empireService.setEmpireDao(empireDao);
        empireService.setSectorService(sectorService);
        empireService.setArmyService(armyService);
        empireService.setCommerceService(commerceService);
        empireService.setResourceService(resourceDao);
        empireService.setScheduleService(scheduleService);
        empireService.setAlertService(alertService);
    }

    @Test
    public void testGetResources(){

    }

}
