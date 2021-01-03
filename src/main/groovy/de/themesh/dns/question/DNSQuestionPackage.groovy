package de.themesh.dns.question

import de.themesh.dns.header.DNSHeader
import de.themesh.dns.question.DNSQuestion

class DNSQuestionPackage {

    DNSHeader header
    DNSQuestion question

    DNSQuestionPackage(DNSHeader header, DNSQuestion question) {
        this.header = header
        this.question = question
    }
}
