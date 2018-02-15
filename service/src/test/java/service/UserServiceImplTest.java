package service;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;
import ar.edu.itba.service.UserServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserDao userDao;

  @Mock
  private EmpireService empireService;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFindUsername() {
    String username = "test";
    String email = "e@mail";
    User expected = new User(username, "", email);
    when(userDao.findByUsername(username)).thenReturn(expected);
    assertEquals(username, userService.findByUsername(username).getName());
  }

}
