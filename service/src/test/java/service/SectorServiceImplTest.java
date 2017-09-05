package service;


import ar.edu.itba.interfaces.BuildingDao;
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
public class SectorServiceImplTest {

    private SectorServiceImpl sectorService;

    @Mock
    private BuildingDao buildingDao;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sectorService = new SectorServiceImpl();
        sectorService.setBuildingmDao(buildingDao);
    }

    @Test
    public void testAddBuilding(){

    }



}
