package de.themesh.dns.header


import java.nio.ByteBuffer

class DNSHeaders {

    public static List<Boolean> bytesToBoolean(byte a, byte b) {
        List<Boolean> result = []
        result.addAll(Integer.toBinaryString((a+256)%256).collect { (it == 1) })
        result.addAll(Integer.toBinaryString((b+256)%256).collect { (it == 1) })
        return result
    }

    public static DNSHeader parse(byte[] bytes) {
        def b = ByteBuffer.wrap(bytes)
        def p = new DNSHeader()
        p.id = b.getShort()
        p.flags = bytesToBoolean(b.get(), b.get())
        p.qdcount = b.getShort()
        p.ancount = b.getShort()
        p.nscount = b.getShort()
        p.arcount = b.getShort()
        return p
    }
}
