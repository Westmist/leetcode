package org.example.basic.convert;

import org.example.basic.ConvertFactory;
import org.example.comom.linkednode.ListNode;

/**
 * 转链表返回头节点
 */
public  class ListNodeConvert implements IConvert<ListNode> {
    @Override
    public ListNode convert(String src) {
        return null;
    }

    @Override
    public ListNode convert(String src, Class<?> tClazz) {
        // 去掉字符串的方括号和单引号
        src = src.replace("[", "").replace("]", "").replace("'", "");

        // 拆分字符串为字符数组
        String[] elements = src.split(",");

        Object[] rt = new Object[elements.length];

        for (int i = 0; i < elements.length; i++) {
            rt[i] = ConvertFactory.convertToType(elements[i], tClazz);
        }
        return ListNode.build(rt);
    }
}
