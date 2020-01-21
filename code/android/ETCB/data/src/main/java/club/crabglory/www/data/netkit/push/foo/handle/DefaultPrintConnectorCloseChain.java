package club.crabglory.www.data.netkit.push.foo.handle;

import club.crabglory.www.data.netkit.push.clink.core.Connector;

/**
 * 关闭链接链式结构
 */
class DefaultPrintConnectorCloseChain extends ConnectorCloseChain {

    @Override
    protected boolean consume(ConnectorHandler handler, Connector connector) {
        System.out.println(handler.getClientInfo() + ":Exit!!, Key:" + handler.getKey().toString());
        return false;
    }
}
