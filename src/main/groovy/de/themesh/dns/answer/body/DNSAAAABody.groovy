package de.themesh.dns.answer.body

class DNSAAAABody implements DNSResponseBody {

    Inet6Address address

    DNSAAAABody() {
    }

    DNSAAAABody(Inet6Address address) {
        this.address = address
    }

    @Override
    void fill(byte[] data) {
        address = Inet6Address.getByAddress(data) as Inet6Address
    }

    @Override
    byte[] toByteArray() {
        return address.getAddress()
    }
}
