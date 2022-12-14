import org.junit.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestMedicalService {
    @Test
    public void checkMessageByBloodPressure (){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        PatientInfo patientInfo = new PatientInfo("Семен", "Михайлов", LocalDate.of(1982, 1, 16),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78)));
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.spy(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(Mockito.anyString(), new BloodPressure(60, 120));

        Mockito.verify(sendAlertService, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    public void checkMessageByTemperature (){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        PatientInfo patientInfo = new PatientInfo("Семен", "Михайлов", LocalDate.of(1982, 1, 16),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78)));
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.spy(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(Mockito.anyString(), new BigDecimal("35"));

        Mockito.verify(sendAlertService, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    public void checkNoMessageByItsOk (){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        PatientInfo patientInfo = new PatientInfo("Семен", "Михайлов", LocalDate.of(1982, 1, 16),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78)));
        Mockito.when(patientInfoRepository.getById(Mockito.anyString())).thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.spy(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(Mockito.anyString(), new BloodPressure(125, 78));
        medicalService.checkTemperature(Mockito.anyString(), new BigDecimal("36.6"));

        Mockito.verify(sendAlertService, Mockito.never()).send(Mockito.anyString());
    }

}
