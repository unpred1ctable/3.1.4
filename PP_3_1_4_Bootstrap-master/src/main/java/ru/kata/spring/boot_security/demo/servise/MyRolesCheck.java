package ru.kata.spring.boot_security.demo.servise;

public class MyRolesCheck {
    private boolean UserState = false;
    private boolean AdminState = false;


    public boolean getUserState() {
        return UserState;
    }

    public void setUserState(boolean userState) {
        UserState = userState;
    }

    public boolean getAdminState() {
        return AdminState;
    }

    public void setAdminState(boolean adminState) {
        AdminState = adminState;
    }
}
