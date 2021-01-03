package de.themesh.dns.answer

import com.google.common.io.BaseEncoding
import de.themesh.dns.header.DNSHeader

import java.nio.ByteBuffer

class DNSAnswers {

    private static String readName(ByteBuffer buffer, int length) {
        StringBuilder builder = new StringBuilder()

        while (length > 0) {

            byte[] temp = new byte[length]
            buffer.get(temp)
            builder.append(new String(temp))
            length = buffer.get()
            if (length > 0) builder.append(".")
        }
        return builder.toString()

    }

    public static DNSAnswerResponse parse(byte[] bytes, DNSHeader header, int position = 12) {
        def buffer = ByteBuffer.wrap(bytes)
        buffer.position(position)

        List<DNSAnswerResponse> answers = []

        for (int i = 0; i < header.ancount; i++) {
            println "FOR: " + i
            println "POS: " + buffer.position()

            def a = new DNSAnswerResponse()
            def length = new LengthInfo(buffer.get())

            if (length.isPointer()) {
                def p = new Pointer(bytes, length, buffer.get())
                a.name = readName(p.buffer, p.buffer.get())
            } else {
                //Set the buffer and get the first length
                a.name = readName(buffer, buffer.get())
            }
            println "NAME:" + a.name
            println "POS:" + buffer.position()
            a.type = buffer.getShort()
            println "TYP:" + a.type
            a.cls = buffer.getShort()
            println "CLS:" + a.cls
            a.ttl = buffer.getInt()
            println "TTL:" + a.ttl
            a.rdlength = buffer.getShort()
            println "DATLEN:" + a.rdlength
            byte[] temp = new byte[a.rdlength]
            buffer.get(temp)
            a.rdata = temp
            answers.add(a)
        }

        return new DNSAnswerResponse(answers)
    }

    public static class Pointer {
        ByteBuffer buffer;
        LengthInfo length
        byte b

        Pointer(byte[] bytes, LengthInfo length, byte b) {
            this.buffer = ByteBuffer.wrap(bytes) //Creates a new buffer from the beginning of the byte array
            this.length = length
            this.b = b
            println "POINTERPOS1" + BaseEncoding.base16().encode(length.b)
            println "POINTERPOS2:" + BaseEncoding.base16().encode(b)
            def p = getPosition()
            println "POS:" + p
            println "REM:" + buffer.remaining()
            buffer.position((int) p) //Moves to the pointer position in the byte array
        }

        public int getPosition() {
            def joined = Integer.toBinaryString((length.b + 256) % 256) + Integer.toBinaryString((b + 256) % 256)
            joined = joined.substring(2, joined.length())
            while (joined.startsWith("0")) joined = joined.substring(1, joined.length())
            println joined
            return Integer.parseInt(joined, 2)
        }
    }

    public static DNSAnswers parse(byte[] bytes, int position = 12) {
        def q = new DNSAnswers()
        def buffer = ByteBuffer.wrap(bytes)
        buffer.position(position)

        def builder = new StringBuilder()

        def lengthByte = buffer.get()
        def length = (int) lengthByte
        if (Integer.toBinaryString((lengthByte + 256) % 256).substring(0, 2) == "11") {
            def inner = ByteBuffer.wrap(bytes)
            def total = Integer.toBinaryString((lengthByte + 256) % 256) + Integer.toBinaryString((buffer.get() + 256) % 256)
            def pos = total.substring(2, total.length())
            inner.position(Byte.parseByte(pos, 2))
            println inner.position()
            q.name = readName(inner, inner.get())

        } else {

            //read(b, length)

            while (length > 0) {
                byte[] temp = new byte[length]
                buffer.get(temp)
                builder.append(new String(temp))
                length = buffer.get()
                if (length > 0) builder.append(".")
            }

            q.name = builder.toString()
        }
        q.type = buffer.getShort()
        q.cls = buffer.getShort()
        q.ttl = buffer.getShort()
        q.rdlength = buffer.getShort()
        byte[] temp = new byte[buffer.remaining()]
        buffer.get(temp)
        q.rdata = temp
        return q
    }

    String name
    short type
    short cls
    short ttl
    short rdlength
    byte[] rdata

    public PackageAnswerBody getBody() {
        println rdlength
        return new PackageAnswerBody(rdata)
    }

    public static class PackageAnswerBody {

        byte[] data

        PackageAnswerBody(byte[] data) {
            this.data = data
        }

        public String getAsIPv4() {
            println Arrays.deepToString(data)
            return ((int) data[0]) + "." + ((int) data[1]) + "." + ((int) data[2]) + "." + ((int) data[3])
        }

    }

    static class LengthInfo {
        byte b

        LengthInfo(byte b) {
            this.b = b
        }

        public int asInt() {
            return (int) b
        }

        public byte asByte() {
            return b
        }

        public boolean isPointer() {
            println "POINTERINFO " + Integer.toBinaryString((b + 256) % 256)
            return (Integer.toBinaryString((b + 256) % 256).substring(0, 2) == "11")
        }
    }


}
