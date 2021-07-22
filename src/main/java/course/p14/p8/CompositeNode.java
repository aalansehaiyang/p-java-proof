package course.p14.p8;


import java.util.List;

public class CompositeNode extends AbstractNode {

    private Long nodeId;
    private List<AbstractNode> childNodes;  //存放子节点列表

    public CompositeNode(Long nodeId, List<AbstractNode> childNodes) {
        this.nodeId = nodeId;
        this.childNodes = childNodes;
    }

    @Override
    public void add(AbstractNode abstractNode) {
        childNodes.add(abstractNode);
    }

    @Override
    public void remove(AbstractNode abstractNode) {
        childNodes.remove(abstractNode);
    }

    @Override
    public void action() {
        for (AbstractNode childNode : childNodes) {
            childNode.action();
        }
    }

}
