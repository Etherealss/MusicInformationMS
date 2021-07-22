package pers.wtk.service.impl;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author wtk
 * @description
 * @date 2021-07-18
 */
public class MementoPattern {
    public static void main(String[] args) {
        History history = new History();
        Document document = new Document();
        document.setContent("11111");
        history.add(document.save());
        // 修改
        document.setContent("22222");

        // 备份的恢复
        document.resume(history.getLastVersion());
        System.out.println(document.getContent()); // 111111
    }
}
/** 需要备份的对象 */
class Document {
    private String content;
    /** 备份 */
    public BackUp save() {
        return new BackUp(content);
    }
    public void resume(BackUp backUp) {
        content = backUp.getContent();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
/** 备忘录对象接口 */
interface Memento {

}
/** 备忘录对象，实现接口，且其属性要与目标对象的属性相同 */
class BackUp implements Memento {
    private String content;

    public BackUp(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class History {
    Stack<BackUp> history = new Stack<>();
    public void add(BackUp backUp) {
        history.add(backUp);
    }
    public BackUp getLastVersion() {
        return history.pop();
    }
}