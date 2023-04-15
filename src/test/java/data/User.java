package data;

public class User {
    private String name;
    private String job;
    private int age;

    public User(UserBuilder user) {
        this.name = user.name;
        this.job = user.job;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setJob(String job) {
        this.job = job;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public String getJob() {
        return this.job;
    }

    public int getAge() {
        return this.age;
    }

    public static class UserBuilder {
        private String name;
        private String job;
        private int age;

        public UserBuilder(String name) {
            this.name = name;
        }

        public UserBuilder setUserAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder setUserJob(String job) {
            this.job = job;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
    
}
