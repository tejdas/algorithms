package xxx.yyy;

import java.util.Arrays;
import java.util.Random;

public class CustomBufferedReader {
    public int readFixed(final byte[] buf) {
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) (i % 128);
        }
        return buf.length;
    }

    private static final int BUFLEN = 4096;

    private byte[] buffer = null;

    private int bytesLeftToRead = 0;

    private void readNext() {
        buffer = new byte[BUFLEN];
        Arrays.fill(buffer, (byte) 0);
        bytesLeftToRead = readFixed(buffer);
    }

    public byte[] readVariable(final int bytesToRead) {

        if (buffer == null) {
            readNext();
        }

        final byte[] buf = new byte[bytesToRead];

        if (bytesToRead >= bytesLeftToRead) {

            int remaining = bytesToRead;
            int readSofar = 0;

            while (remaining > 0) {
                if (remaining <= bytesLeftToRead) {
                    System.arraycopy(buffer, BUFLEN - bytesLeftToRead, buf, readSofar, remaining);
                    bytesLeftToRead -= remaining;

                    if (bytesLeftToRead == 0) {
                        buffer = null;
                    }
                    return buf;
                } else {
                    // remaining > bytesLeftToRead

                    System.arraycopy(buffer, BUFLEN - bytesLeftToRead, buf, readSofar, bytesLeftToRead);
                    readSofar += bytesLeftToRead;
                    remaining -= bytesLeftToRead;

                    readNext();
                }
            }
            return buf;
        } else {
            System.arraycopy(buffer, BUFLEN - bytesLeftToRead, buf, 0, bytesToRead);
            bytesLeftToRead -= bytesToRead;
            return buf;
        }
    }

    public static void main1(final String[] args) {
        final int totalToRead = 50000;
        final CustomBufferedReader reader = new CustomBufferedReader();
        final Random r = new Random();
        int total = 0;
        while (total < totalToRead) {

            final int randomVal = r.nextInt(256) + 1;
            final int bytesToRead = randomVal < (totalToRead-total) ? randomVal : (totalToRead-total);
            final byte[] buf = reader.readVariable(bytesToRead);
            System.out.println(bytesToRead + "  " + buf.length);
            if (bytesToRead != buf.length) {
                System.out.println("DIFFERENT");
            }
            total += buf.length;
        }
        System.out.println("buf len: " + total);
    }

    public static void main(final String[] args) {
        final int totalToRead = 1000000;
        final CustomBufferedReader reader = new CustomBufferedReader();
        final Random r = new Random();
        int total = 0;
        int toggle = 0;
        while (total < totalToRead) {

            toggle++;
            final int more = (toggle%2 == 0)? 5000 : 10000;
            final int randomVal = r.nextInt(4096) + more;
            final int bytesToRead = randomVal < (totalToRead-total) ? randomVal : (totalToRead-total);
            final byte[] buf = reader.readVariable(bytesToRead);
            System.out.println(bytesToRead + "  " + buf.length);
            if (bytesToRead != buf.length) {
                System.out.println("DIFFERENT");
            }
            total += buf.length;
        }
        System.out.println("buf len: " + total);

        main1(null);
    }
}
