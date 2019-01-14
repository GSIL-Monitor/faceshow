package com.imTest;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ImTest {
    @JsonProperty(value = "Owner_Account")
    private String Owner_Account;

    public String getOwner_Account() {
        return Owner_Account;
    }

    public void setOwner_Account(String owner_Account) {
        Owner_Account = owner_Account;
    }

    public static void main(String[] args) {
        JsonObject json = new JsonObject();
        json.add("one", new JsonElement() {
            @Override
            public JsonElement deepCopy() {
                return null;
            }
        });
    }
}
