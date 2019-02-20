package il.ac.afeka.comm.ip;

import java.util.Objects;

public class IPAddress {

    private String networkId;
    private String hostId;

    public IPAddress(String net, String host) {
        networkId = net;
        hostId = host;
    }

    public String getNetworkId() { return networkId; }
    public String getHostId() { return hostId; }

    @Override
    public String toString() {
        return "IPAddress{" +
                "networkId='" + networkId + '\'' +
                ", hostId='" + hostId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPAddress ipAddress = (IPAddress) o;
        return Objects.equals(networkId, ipAddress.networkId) &&
                Objects.equals(hostId, ipAddress.hostId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(networkId, hostId);
    }
}
