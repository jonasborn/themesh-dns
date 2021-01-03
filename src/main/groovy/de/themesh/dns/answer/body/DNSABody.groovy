package de.themesh.dns.answer.body

class DNSABody implements DNSResponseBody {

    Inet4Address address

    DNSABody() {
    }

    DNSABody(Inet4Address address) {
        this.address = address
    }

    @Override
    void fill(byte[] data) {
        address = (Inet4Address) Inet4Address.getByAddress(data)
    }

    @Override
    byte[] toByteArray() {
        return address.getAddress()
    }
}
