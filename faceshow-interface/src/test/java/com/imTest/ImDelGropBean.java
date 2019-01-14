package com.imTest;

import java.util.List;

public class ImDelGropBean {
    private String GroupId;
    private String[]MemberToDel_Account;

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String[] getMemberToDel_Account() {
        return MemberToDel_Account;
    }

    public void setMemberToDel_Account(String[] memberToDel_Account) {
        MemberToDel_Account = memberToDel_Account;
    }
}
