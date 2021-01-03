package de.themesh.dns.question

import de.themesh.dns.Byteable

import java.nio.ByteBuffer

public  class DNSQuestionQuery implements Byteable {
    String name
    short type
    short cls

    DNSQuestionQuery() {
    }

    DNSQuestionQuery(String name, short type, short cls) {
        this.name = name
        this.type = type
        this.cls = cls
    }

    public byte[] toByteArray() {
        def size = name.size() + 5

        def buffer = ByteBuffer.allocate(size)

        name.split("\\.").each {
            buffer.put((byte) it.length())
            buffer.put(it.getBytes("UTF-8"))
        }
        buffer.putShort(type)
        buffer.putShort(cls)

        byte[] total = new byte[size]
        buffer.rewind()
        buffer.get(total)
        return total
    }


    @Override
    public String toString() {
        return "Query{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", cls=" + cls +
                '}';
    }
}