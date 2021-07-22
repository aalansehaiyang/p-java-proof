package course.p14.p8;


public class LeafNode extends AbstractNode {

    private Long nodeId;

    public LeafNode(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public void add(AbstractNode abstractNode) {
        // 无子节点，无需处理
        return;
    }

    @Override
    public void remove(AbstractNode abstractNode) {
        // 无子节点，无需处理
        return;
    }

    @Override
    public void action() {
        System.out.println("叶子节点编号：" + nodeId);
    }
}
