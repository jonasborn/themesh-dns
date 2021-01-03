package de.themesh.dns.answer

import de.themesh.dns.Byteable
import de.themesh.dns.answer.body.DNSResponseBody

import java.nio.ByteBuffer

class DNSAnswerResponse implements Byteable {

    String name
    short type
    short cls
    int ttl
    short rdlength
    byte[] rdata

    DNSAnswerResponse() {
    }

    DNSAnswerResponse(String name, short type, short cls, int ttl, short rdlength, byte[] rdata) {
        this.name = name
        this.type = type
        this.cls = cls
        this.ttl = ttl
        this.rdlength = rdlength
        this.rdata = rdata
    }

    DNSAnswerResponse(String name, short type, short cls, int ttl, DNSResponseBody body) {
        this.name = name
        this.type = type
        this.cls = cls
        this.ttl = ttl
        this.rdata = body.toByteArray()
        this.rdlength = (short) rdata.length
    }

    public byte[] toByteArray() {
        def size = name.size() + 5 + 2 + 2 + 4 + 2 + rdlength

        def buffer = ByteBuffer.allocate(size)

        name.split("\\.").each {
            buffer.put((byte) it.length())
            buffer.put(it.getBytes("UTF-8"))
        }

        buffer.putShort(type)
        buffer.putShort(cls)
        buffer.putInt(ttl)
        buffer.putShort(rdlength)
        buffer.put(rdata)

        byte[] total = new byte[size]
        buffer.rewind()
        buffer.get(total)
        return total

    }



    @Override
    public String toString() {
        return "Response{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", cls=" + cls +
                ", ttl=" + ttl +
                ", rdlength=" + rdlength +
                ", rdata=" + Arrays.toString(rdata) +
                '}';
    }
}
