package service;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;
import ar.edu.itba.service.AlertServiceImpl;
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

import java.sql.Date;
import java.time.Instant;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AlertServiceImplTest {

  @InjectMocks
  private AlertServiceImpl alertService;

  @Mock
  private AlertDao alertDao;

  @Mock
  private User user;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateAlert() {
    assertNull(alertService.createAlert(null, "something", Date.from(Instant.now()), "ATTACK", new Point(0, 0), null, null));
    assertNull(alertService.createAlert(user, null, Date.from(Instant.now()), "DEFEND", new Point(0, 0), null, null));
  }

  @Test
  public void testFindById() {
      int existingId = 6;
      when(alertDao.findById(existingId)).thenReturn(mock(Alert.class));
      assertNotNull(alertService.findById(existingId));
      assertNull(alertService.findById(99));
  }
}
