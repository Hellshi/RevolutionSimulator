package Model.User;

import Model.Repository.BaseEntity;

public class UserEntity extends BaseEntity<UserEntity> implements IUser {
    private String id;
    private double salary;
    private String name;
    private Role role;

    public UserEntity() {
    }

    public void setAttributes(double salary, String name, Role role) {
        this.setSalary(salary);
        this.setName(name);
        this.setRole(role);
    }

    public String getId() { return id; }
    public double getSalary() { return salary; }
    public String getName() { return name; }
    public Role getRole() { return role; }

    public void setId(String id) {
        this.id = id;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static UserEntity fromJson(String json) {
        UserEntity user = new UserEntity();
        try {
            // Remove chaves e espaços extras
            String content = json.replace("{", "").replace("}", "").trim();
            String[] pairs = content.split(",");

            for (String pair : pairs) {
                String[] parts = pair.split(":");
                String key = parts[0].trim().replace("\"", "");
                String value = parts.length > 1 ? parts[1].trim().replace("\"", "") : "";

                switch (key) {
                    case "name": user.setName(value); break;
                    case "salary": user.setSalary(Double.parseDouble(value)); break;
                    case "role": user.setRole(Role.fromString(value)); break;
                    case "id": user.setId(value.equals("null") ? null : value); break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao converter JSON para UserEntity: " + e.getMessage());
        }
        return user;
    }
}
