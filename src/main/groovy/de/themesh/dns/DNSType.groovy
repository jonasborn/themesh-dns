package de.themesh.dns

enum DNSType {

    A (1as short),
    AAAA (28as short),
    AFSDB (18as short),
    APL (42as short),
    CAA (257as short),
    CDNSKEY (60as short),
    CDS (59as short),
    CERT (37as short),
    CNAME (5as short),
    CSYNC (62as short),
    DHCID (49as short),
    DLV (32769as short),
    DNAME (39as short),
    DNSKEY (48as short),
    DS (43as short),
    EUI48 (108as short),
    EUI64 (109as short),
    HINFO (13as short),
    HIP (55as short),
    IPSECKEY (45as short),
    KEY (25as short),
    KX (36as short),
    LOC (29as short),
    MX (15as short),
    NAPTR (35as short),
    NS (2as short),
    NSEC (47as short),
    NSEC3 (50as short),
    NSEC3PARAM (51as short),
    OPENPGPKEY (61as short),
    PTR (12as short),
    RRSIG (46as short),
    RP (17as short),
    SIG (24as short),
    SMIMEA (53as short),
    SOA (6as short),
    SRV (33as short),
    SSHFP (44as short),
    TA (32768as short),
    TKEY (249as short),
    TLSA (52as short),
    TSIG (250as short),
    TXT (16as short),
    URI (256as short),
    ZONEMD (63as short),
    SVCB (64as short),
    HTTPS (65 as short),
    UNKNOWN(-1 as short)

    public static DNSType fromShort(short s) {
        def v =  values().find {it.s = s}
        if (v == null) return UNKNOWN
        return v
    }

    short s

    DNSType(short s) {
        this.s = s
    }

    public boolean is(short s) {
        return this.s == s
    }


}
