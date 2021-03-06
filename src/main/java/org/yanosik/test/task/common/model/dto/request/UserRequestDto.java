package org.yanosik.test.task.common.model.dto.request;

public class UserRequestDto {
    private Long id;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //this method shows password only for demonstrating purposes
    @Override
    public String toString() {
        return "UserRequestDto{"
                + "id=" + id
                + ", password='" + password + '\''
                + '}';
    }
}
