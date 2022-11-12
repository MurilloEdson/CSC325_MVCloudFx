package models;

public class Person {

    private String name;
    private String major;
    private int age;

    public Person(String name, String major, int age) {
        this.name = name;
        this.major = major;
        this.age = age;
    }

    public String getName() {
        return name;
    }public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return getName() + " , " + getMajor() + " , " + getAge();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj != null) {
            
            Person other = (Person) obj;
            if (this.name.equals(other.name)) {
                if (this.major.equals(other.major)) {
                    if (this.age == (other.age)) {
                        equal = true;
                    }
                }
            }
        }
        return equal;
    }

}
