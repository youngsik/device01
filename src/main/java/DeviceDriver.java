/**
 * This class is used by the operating system to interact with the hardware 'FlashMemoryDevice'.
 */
public class DeviceDriver {

    FlashMemoryDevice hw;
    public DeviceDriver(FlashMemoryDevice hardware) {
        // TODO: implement this method
        hw = hardware;
    }

    public byte read(long address) {
        // TODO: implement this method
        byte originData =hw.read(address) ;

        for(int i=0; i<4; i++)
        {
            byte testdata=hw.read(address);
            if(testdata!=originData)
            {
                throw new ReadFailException("error");
            }
        }

        return originData;
    }

    public void write(long address, byte data) {
        // TODO: implement this method
        byte curdata=hw.read(address);
        if(curdata !=(byte) 0xFF)
        {
            throw new WriteFailException("Address already has data");
        }
        hw.write(address,data);
    }
}
