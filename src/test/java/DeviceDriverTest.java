import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceDriverTest {

    @Mock
    FlashMemoryDevice mockHw;

    @Test
    public void readFiveTimes() {
        DeviceDriver driver = new DeviceDriver(mockHw);
        byte data = driver.read(0xAA);
        //5회 읽어야 PASS
        verify(mockHw,times(5)).read(0xAA);
    }

    @Test
    public void readDiffTry() {

        byte existing = (byte) 0xEE;
        byte existing_another = (byte) 0xEF;
        when(mockHw.read(0xAA)).thenReturn(existing)
                .thenReturn(existing)
                .thenReturn(existing)
                .thenReturn(existing)
                .thenReturn(existing)
                .thenReturn(existing_another);

        DeviceDriver driver = new DeviceDriver(mockHw);

        assertThrows(WriteFailException.class, () -> {
            driver.write(0xAA, (byte) 0x55);
        });
    }

    @Test
    public void writeTry() {
        byte existing = (byte) 0xFF;
        when(mockHw.read(0xAA)).thenReturn(existing);
        DeviceDriver driver = new DeviceDriver(mockHw);
        driver.write(0xAA, (byte) 0x55);
        verify(mockHw).write(0xAA, (byte) 0x55);
    }


    @Test
    public void alreadyTry() {
        byte existing = (byte) 0x11;
        when(mockHw.read(0xAA)).thenReturn(existing);

        DeviceDriver driver = new DeviceDriver(mockHw);

        assertThrows(WriteFailException.class, () -> {
            driver.write(0xAA, (byte) 0x55);
        });

    }

}
