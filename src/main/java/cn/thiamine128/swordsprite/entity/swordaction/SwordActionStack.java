package cn.thiamine128.swordsprite.entity.swordaction;

import java.util.ArrayDeque;
import java.util.Stack;


public class SwordActionStack {
    private Stack<AbstractSwordAction> actions;

    public SwordActionStack() {
        actions = new Stack<>();
    }

    public void addAction(AbstractSwordAction action) {
        actions.push(action);
    }

    public void tick() {
        actions.peek().tick();

        if (actions.peek().isFinished()) {
            actions.pop();
        }
    }

    public AbstractSwordAction current() {
        return actions.peek();
    }
}
