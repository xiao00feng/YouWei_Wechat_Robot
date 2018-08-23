package cn.webank.mumble.mpc.giftcard.common.utils;

public enum ChangeCodeAction {
    LOCK_AND_ASSIGN("1", "只锁code"),
    UNLOCK_AND_CONSUME("2", "解锁并核销code"),
    ONLY_UNLOCK_WITH_FAILED("3", "解锁"),
    ;

    private String action;
    private String actionName;

    ChangeCodeAction(String action, String actionName) {
        this.action = action;
        this.actionName = actionName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
