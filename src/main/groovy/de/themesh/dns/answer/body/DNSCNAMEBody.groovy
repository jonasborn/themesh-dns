package de.themesh.dns.answer.body

class DNSCNAMEBody implements DNSResponseBody {

    String name

    DNSCNAMEBody() {
    }

    DNSCNAMEBody(String name) {
        this.name = name
    }

    @Override
    void fill(byte[] data) {
        name = new String(data, "UTF-8")
    }

    @Override
    byte[] toByteArray() {
        return name.getBytes("UTF-8")
    }
}
