package de.themesh.dns.answer

import de.themesh.dns.Byteable
import de.themesh.dns.header.DNSHeader
import de.themesh.dns.question.DNSQuestion
import de.themesh.dns.util.ByteUtils

class DNSAnswerPackage implements Byteable {

    DNSHeader header
    DNSQuestion question
    DNSAnswer answer

    DNSAnswerPackage(DNSHeader header, DNSQuestion question, DNSAnswer answer) {
        this.header = header
        this.question = question
        this.answer = answer
    }

    @Override
    byte[] toByteArray() {
        return ByteUtils.join(header.toByteArray(), question.toByteArray(), answer.toByteArray())
    }
}
