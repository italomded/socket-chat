package br.com.italomded.chat.command;

public enum Param {
    ACCEPT {
        @Override
        public boolean getValue() {
            return true;
        }
    },
    DENY {
        @Override
        public boolean getValue() {
            return false;
        }
    };

    public abstract boolean getValue();
}
