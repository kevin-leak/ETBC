package club.crabglory.www.data.netkit.push.foo.handle;


import club.crabglory.www.data.netkit.push.clink.box.StringReceivePacket;

/**
 * 默认String接收节点，不做任何事情
 */
class DefaultNonConnectorStringPacketChain extends ConnectorStringPacketChain {
    @Override
    protected boolean consume(ConnectorHandler handler, StringReceivePacket packet) {
        return false;
    }
}
