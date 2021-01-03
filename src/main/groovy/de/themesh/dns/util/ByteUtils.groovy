package de.themesh.dns.util

class ByteUtils {

    /**
     * Dumps an array in a binary representation
     * @param A array
     * @param Bytes per line
     */
    public static void dump(byte[] array, int l) {
        l = l -1
        int pos = 0;
        array.each {
            print(Integer.toBinaryString(it & 0xff).padLeft(8, "0"))
            pos++
            if (pos > l) {
                pos = 0
                println()
            } else {
                print(", ")
            }
        }

    }


    public static byte[] join(byte[]...arrays) {
        def l = arrays as List<Byte>
        return l.flatten() as byte[]
    }

}
