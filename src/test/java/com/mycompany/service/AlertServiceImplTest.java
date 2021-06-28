package com.mycompany.service;

import com.mycompany.entity.Alert;
import com.mycompany.repository.AlertRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class AlertServiceImplTest {

    @TestConfiguration
    static class AlertServiceImplTestConfiguration{
        @Bean
        public AlertService getService() {
            return new AlertServiceImpl();
        }
    }

    @Autowired
    private AlertService service;

    @MockBean
    private AlertRepository repository;

    List<Alert> alerts;

    @Before
    public void setup(){
        Alert alert = new Alert();
        alert.setVin("1HGCR2F3XFA027534");
        alert.setPriority("High");
        alert.setDescription("asdfg sg");
        alert.setTimestamp(new Date());
        alert.setLatitude(41.803194);
        alert.setLongitude(-88.144406);

        alerts = Collections.singletonList(alert);

        Mockito.when(repository.findByPriority(alert.getPriority()))
                .thenReturn(alerts);
        Mockito.when(repository.findByVin(alert.getVin()))
                .thenReturn(alerts);
        Mockito.when(repository.save(Mockito.any(Alert.class)))
                .thenReturn(alert);
    }

    @After
    public void cleanup() {
    }

    @Test
    public void testFindAlertsByPriority() {
        List<Alert> result = service.findAlertsByPriority(alerts.get(0).getPriority());
        Assert.assertEquals("alert list should match", alerts, result);
    }

    @Test
    public void testFindAlertsByVin() {
        List<Alert> result = service.findAlertsByVin(alerts.get(0).getVin());
        Assert.assertEquals("alert list should match", alerts, result);
    }

    @Test
    public void testCreate() {
        Alert result = service.create(alerts.get(0));
        Assert.assertEquals("alert object should be same", alerts.get(0), result);
    }
}
