package org.example.basic.section;

import org.example.basic.convert.entity.MethodParams;
import org.example.basic.convert.inf.IConvertSection;
import org.example.comom.stru.ListNode;

import java.util.HashMap;
import java.util.Map;

public class SectionFactory {

    public static Map<Class<? extends IConvertSection>, IConvertSection> SECTION = new HashMap<>();

    static {
        SECTION.put(FristParamSection.class, new FristParamSection());
        SECTION.put(FristListNodeSection.class, new FristListNodeSection());
        SECTION.put(AnswerSection.class, new AnswerSection());
        SECTION.put(CycleLinkedNode.class, new CycleLinkedNode());
    }

    public static class AnswerSection implements IConvertSection {
        @Override
        public MethodParams paramsAns(Object[] params, int pos) {
            return new MethodParams(params, null);
        }
    }

    public static class FristParamSection implements IConvertSection {
        @Override
        public MethodParams paramsAns(Object[] params, int pos) {
            return new MethodParams(params, params[0]);
        }
    }


    public static class FristListNodeSection implements IConvertSection {
        @Override
        public MethodParams paramsAns(Object[] params, int pos) {
            Object param = params[0];
            if (param instanceof ListNode) {
                params[0] = ((ListNode<?>) param).next;
            }
            return new MethodParams(params, param);
        }
    }


    public static class CycleLinkedNode implements IConvertSection {
        @Override
        public MethodParams paramsAns(Object[] params, int pos) {
            Object param = params[0];
            if (!(param instanceof ListNode) || pos <= 0) {
                return new MethodParams(params, null);
            }
            ListNode<Integer> root = (ListNode<Integer>) param;
            int size = 1;
            ListNode<Integer> posNode = root;
            ListNode<Integer> tail = root;
            while (tail.next != null) {
                tail = tail.next;
                size++;
                if (pos == size) {
                    posNode = tail;
                }
            }
            tail.next = posNode;
            return new MethodParams(params, null);
        }
    }


    public static IConvertSection find(Class<? extends IConvertSection> clazz) {
        return SECTION.get(clazz);
    }


}
