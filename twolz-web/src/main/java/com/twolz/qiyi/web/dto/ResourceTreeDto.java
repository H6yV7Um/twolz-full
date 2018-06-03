package com.twolz.qiyi.web.dto;

import java.io.Serializable;

/**
 * Created by liuwei
 * date: 2017-04-12
 */
public class ResourceTreeDto  implements Serializable {

    private int id;
    private String parent;
    private String text;
    private String icon;
    private State state;
    private String sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOpened(boolean opened) {
        if (state==null){
            state = new State();
            state.setOpened(opened);
        }
    }

    public void setSelected(boolean selected) {
        if (state==null){
            state = new State();
            state.setSelected(selected);
        }
    }


    class State {

        private boolean opened = true;

        private boolean selected = false;

        private boolean disabled = false;

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }
    }
}
