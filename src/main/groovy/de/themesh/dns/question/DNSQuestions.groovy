package de.themesh.dns.question

import de.themesh.dns.header.DNSHeader

import java.nio.ByteBuffer

class DNSQuestions {

    public static DNSQuestion parse(byte[] bytes, DNSHeader header) {
        List<DNSQuestionQuery> result = []
        def buffer = ByteBuffer.wrap(bytes)
        buffer.position(header.length)

        for (int i = 0; i < header.qdcount; i++) {
            def builder = new StringBuilder()
            def q = new DNSQuestionQuery()
            def length = (int) buffer.get()
            while (length > 0) {
                byte[] temp = new byte[length]
                buffer.get(temp)
                builder.append(new String(temp))
                length = buffer.get()
                if (length > 0) builder.append(".")
            }
            q.name = builder.toString()
            q.type = buffer.getShort()
            q.cls = buffer.getShort()

        }

        return new DNSQuestion(
                bytes.length - buffer.remaining(),
                result
        )
    }



}
