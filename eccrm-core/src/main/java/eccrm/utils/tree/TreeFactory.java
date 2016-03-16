package eccrm.utils.tree;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * 树工厂，用于创建一棵树
 *
 * @author miles
 * @datetime 13-12-13 下午4:34
 */
public class TreeFactory {
    private static Logger logger = Logger.getLogger(TreeFactory.class);

    /**
     * 通过一个实现了Tree接口的集合，构建成一棵树
     * （实际上返回的也是一个集合，只是集合中的元素只有根节点，所有的子节点都在根节点下）
     *
     * @param nodes 要转成树的集合
     * @param clazz Tree的实现
     * @param <T>   StaticTree
     */
    public static <T extends StaticTree> List<T> buildTree(List<T> nodes, Class<T> clazz) {
        return buildTree(nodes, clazz, null);
    }

    public static <T extends StaticTree> List<T> buildTree(List<T> nodes, Class<T> clazz, String parentId) {
        List<T> trees = new ArrayList<T>();
        Set<Serializable> ids = new TreeSet<Serializable>();
        Map<Serializable, T> linkedHashMap = new LinkedHashMap<Serializable, T>();
        //convert list to map
        for (T node : nodes) {
            linkedHashMap.put(node.getId(), node);
        }
        //遍历map中的所有元素，设置孩子节点
        for (Map.Entry<Serializable, T> entry : linkedHashMap.entrySet()) {
            T temp = entry.getValue();
            if (temp.getParent() == null || temp.getParent().getId() == null || temp.getParent().getId().equals(parentId)) {
                trees.add(temp);
                continue;
            }

            T parent = linkedHashMap.get(temp.getParent().getId());
            if (parent == null) {
                ids.add(temp.getParent().getId());
                continue;
            }
            parent.addChild(temp);
        }
        if (ids.size() > 0) {
            String id = StringUtils.join(ids, ",");
            logger.error("上级id为[" + id + "]的数据不存在!");
        }
        return trees;
    }
}
