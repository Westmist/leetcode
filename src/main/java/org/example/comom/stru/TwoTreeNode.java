package org.example.comom.stru;

import lombok.Data;

@Data
public class TwoTreeNode<T> {

    public T val;

    public TwoTreeNode<T> left;

    public TwoTreeNode<T> right;

    public TwoTreeNode(T val) {
        this.val = val;
    }

}
