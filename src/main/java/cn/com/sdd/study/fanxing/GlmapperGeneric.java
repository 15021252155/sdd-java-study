package cn.com.sdd.study.fanxing;

/**
 * @author suidd
 * @name GlmapperGeneric
 * @description 泛型学习1
 * @date 2020/6/3 9:08
 * Version 1.0
 **/
public class GlmapperGeneric<T> {
    private T t;

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //不指定类型
     * @date 2020/6/3 9:11
     **/
    public void noSpecifyType() {
        GlmapperGeneric glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.setT("test");
        String test = (String) glmapperGeneric.getT();
        System.out.println("noSpecifyType=" + test);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //指定类型
     * @date 2020/6/3 9:11
     **/
    public void specifyType() {
        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.setT("test");
        String test = glmapperGeneric.getT();
        System.out.println("specifyType=" + test);
    }

    public static void main(String[] args) {
        GlmapperGeneric generic = new GlmapperGeneric();
        generic.noSpecifyType();
        generic.specifyType();
    }
}