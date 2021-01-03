package de.themesh.dns.header


import java.nio.ByteBuffer

class DNSHeader {


    /**
     * Used to convert booleans representing single bits to an integer
     * @param booleans Some booleans representing single bits
     * @return An integer
     */
    private static int booleanToInt(boolean ... booleans) {
        def l = booleans.collect { (it) ? 1 : 0 }
        return Integer.parseInt(l.join())
    }

    /**
     * Used to convert an single integer to a list of booleans representing the binary representation
     * @param i Any integer
     * @return A list of booleans
     */
    private static List<Boolean> intToBooleans(int i) {
        Integer.toBinaryString(i).padLeft(4, "0").collect { (it == "1") }
    }


    public short id = 0
    private List<Boolean> flags = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false]
    public short qdcount = 0
    public short ancount = 0
    public short nscount = 0
    public short arcount = 0

    DNSHeader(short id, List<Boolean> flags, short qdcount, short ancount, short nscount, short arcount) {
        this.id = id
        this.flags = flags
        this.qdcount = qdcount
        this.ancount = ancount
        this.nscount = nscount
        this.arcount = arcount
    }
/**
     * Used to convert the binary representation stored in the flag array to an integer
     * @param ints Integers representing the positions in the flags list
     * @return An integer based on the values received from the flags array
     * @throws ArrayIndexOutOfBoundsException
     */
    private int flagsToInt(int ... ints) throws ArrayIndexOutOfBoundsException {
        def l = ints.collect { flags[it] } as boolean[]
        return booleanToInt(l)
    }

    public void setQR(boolean b) {
        flags[0] = b;
    }

    public boolean getQR() {
        return flags[0]
    }

    public void setOPCode(int i) {
        def b = intToBooleans(i)
        flags[1] = b[0]
        flags[2] = b[1]
        flags[3] = b[2]
        flags[4] = b[3]
    }

    public boolean getOPCode() {
        return flagsToInt(1, 2, 3, 4)
    }

    public void setAA(boolean b) {
        flags[5] = b
    }

    public boolean getAA() {
        return flags[5]
    }

    public void setTC(boolean b) {
        flags[6] = b
    }

    public boolean getTC() {
        return flags[6]
    }

    public void setRD(boolean b) {
        flags[7] = b
    }

    public boolean getRD() {
        return flags[7]
    }

    public void setRA(boolean b) {
        flags[8] = b
    }

    public boolean getRA() {
        return flags[8]
    }


    public void setZ(int i) {
        def b = intToBooleans(i)
        flags[9] = b[2]
        flags[10] = b[3]
    }

    public int getZ() {
        return flagsToInt(9, 10)
    }

    public void setRCode(int i) {
        def b = intToBooleans(i)
        flags[11] = b[0]
        flags[12] = b[1]
        flags[13] = b[2]
        flags[14] = b[3]
    }

    public int getRCode() {
        return flagsToInt(11, 12, 13, 14)
    }

    public byte[] toByteArray() {
        def b = ByteBuffer.allocate(12)
        b.putShort(id)
        b.put((byte) Integer.parseInt(flags.subList(0, 8).collect { (it) ? "1" : "0" }.join(), 2))
        b.put((byte) Integer.parseInt(flags.subList(8, 15).collect { (it) ? "1" : "0" }.join(), 2))
        b.putShort(qdcount)
        b.putShort(ancount)
        b.putShort(nscount)
        b.putShort(arcount)
        b.rewind()
        byte[] result = new byte[12]
        b.get(result)
        return result
    }

    public int getLength() {
        return 12
    }

}
