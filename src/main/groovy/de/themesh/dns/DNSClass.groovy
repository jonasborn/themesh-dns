package de.themesh.dns

enum DNSClass {

    IN(1 as short),
    CS(2 as short),
    CH(3 as short),
    HS(4 as short)

    public static DNSClass fromShort(short s) {
        return values().find {it.s = s}
    }

    short s

    DNSClass(short s) {
        this.s = s
    }
}
