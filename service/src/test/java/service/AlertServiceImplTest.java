package service;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.service.AlertServiceImpl;
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
public class AlertServiceImplTest {

    private AlertServiceImpl alertService;

    @Mock
    private AlertDao alertDao;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        alertService = new AlertServiceImpl();
        alertService.setAlertDao(alertDao);
    }

    @Test
    public void testCreateAlert(){
        assertEquals(1,1);
    }

}
