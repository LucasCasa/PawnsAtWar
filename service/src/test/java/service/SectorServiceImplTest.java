package service;


import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.SectorType;
import ar.edu.itba.model.User;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SectorServiceImplTest {

  @InjectMocks
  private SectorServiceImpl sectorService;

  @Mock
  private BuildingDao buildingDao;

  @Mock
  private User user;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateCastle() {
    List<Point> allCastles = new ArrayList<>();
    when(buildingDao.getAllCastles()).thenReturn(allCastles);
    when(buildingDao.getBuilding(any(Point.class))).thenReturn(new Sector(null, mock(Point.class), SectorType.EMPTY.getType(), 0));
    assertTrue(sectorService.createCastle(user));
  }


}
